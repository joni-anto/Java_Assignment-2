package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

import exception.WalletException;
import model.*;

public class WalletService {
    private final Map<Integer, User> users = new LinkedHashMap<>();
    private final Map<Integer, Wallet> wallets = new LinkedHashMap<>();
    private final Map<Integer, Payment> payments = new LinkedHashMap<>();
    private final Map<Integer, Refund> refunds = new LinkedHashMap<>();
    private final Map<Integer, Transfer> transfers = new LinkedHashMap<>();
    private final List<TopUp> topUps = new ArrayList<>();
    private final List<StatementEntry> entries = new ArrayList<>();
    private int userSeq = 1, walletSeq = 1001, transactionSeq = 5001;

    private BigDecimal zero() { return new BigDecimal("0.00"); }

    public User addUser(String name, String phone) throws WalletException {
        if (name == null || name.trim().isEmpty())
            throw new WalletException("User name cannot be empty.");
        User user = new User(userSeq++, name.trim(), phone.trim());
        users.put(user.getUserId(), user);
        return user;
    }

    public Wallet createWallet(int userId) throws WalletException {
        User user = findUser(userId);
        if (user.getWallet() != null)
            throw new WalletException("This user already has a wallet.");
        Wallet wallet = new Wallet(walletSeq++, user);
        user.setWallet(wallet);
        wallets.put(wallet.getWalletId(), wallet);
        return wallet;
    }

    public User findUser(int id) throws WalletException {
        User user = users.get(id);
        if (user == null) throw new WalletException("User ID not found.");
        return user;
    }

    public Wallet findWallet(int id) throws WalletException {
        Wallet wallet = wallets.get(id);
        if (wallet == null) throw new WalletException("Wallet ID not found.");
        return wallet;
    }

    private void addEntry(Wallet wallet, String type, String description,
                          BigDecimal amount, BigDecimal fee) {
        entries.add(new StatementEntry(transactionSeq++, LocalDateTime.now(),
                type, description, amount, fee, wallet.getBalance()));
    }

    public TopUp topUp(int walletId, BigDecimal amount, boolean confirmed)
            throws WalletException {
        Wallet wallet = findWallet(walletId);
        if (!confirmed) throw new WalletException("Top-up was not confirmed; balance unchanged.");
        wallet.credit(amount);
        TopUp topUp = new TopUp(transactionSeq, wallet, amount,
                LocalDateTime.now(), "TOP-" + transactionSeq);
        topUps.add(topUp);
        addEntry(wallet, "TOP-UP", topUp.getConfirmationReference(), amount, zero());
        return topUp;
    }

    public Transfer transfer(int sourceId, int destinationId, BigDecimal amount)
            throws WalletException {
        Wallet source = findWallet(sourceId);
        Wallet destination = findWallet(destinationId);
        if (sourceId == destinationId)
            throw new WalletException("Source and destination wallets must be different.");
        BigDecimal fee = zero();
        BigDecimal total = amount.add(fee);
        if (!source.hasSufficientBalance(total))
            throw new WalletException("Insufficient funds. Transfer declined; balances unchanged.");
        source.debit(total);
        destination.credit(amount);
        int id = transactionSeq++;
        Transfer transfer = new Transfer(id, source, destination, amount, fee, LocalDateTime.now());
        transfers.put(id, transfer);
        entries.add(new StatementEntry(id, transfer.getDateTime(), "TRANSFER OUT",
                "To wallet " + destinationId, amount, fee, source.getBalance()));
        entries.add(new StatementEntry(id, transfer.getDateTime(), "TRANSFER IN",
                "From wallet " + sourceId, amount, zero(), destination.getBalance()));
        return transfer;
    }

    public Payment payMerchant(int walletId, String merchant, BigDecimal amount)
            throws WalletException {
        Wallet wallet = findWallet(walletId);
        if (merchant == null || merchant.trim().isEmpty())
            throw new WalletException("Merchant name cannot be empty.");
        BigDecimal fee = zero();
        BigDecimal total = amount.add(fee);
        if (!wallet.hasSufficientBalance(total))
            throw new WalletException("Insufficient funds. Payment declined; balance unchanged.");
        wallet.debit(total);
        int id = transactionSeq++;
        Payment payment = new Payment(id, wallet, merchant.trim(), amount, fee, LocalDateTime.now());
        payments.put(id, payment);
        addEntry(wallet, "PAYMENT", "Merchant: " + merchant.trim(), amount, fee);
        return payment;
    }

    public Refund refund(int paymentId, BigDecimal amount) throws WalletException {
        Payment payment = payments.get(paymentId);
        if (payment == null) throw new WalletException("Payment ID not found; refund rejected.");
        if (amount.compareTo(payment.getAmount()) > 0)
            throw new WalletException("Refund cannot exceed the original payment amount.");
        for (Refund r : refunds.values()) {
            if (r.getPayment().getPaymentId() == paymentId)
                throw new WalletException("This payment has already been refunded.");
        }
        Wallet wallet = payment.getWallet();
        wallet.credit(amount);
        int id = transactionSeq++;
        Refund refund = new Refund(id, payment, amount, LocalDateTime.now());
        refunds.put(id, refund);
        addEntry(wallet, "REFUND", "Payment ID " + paymentId, amount, zero());
        return refund;
    }

    public void viewStatement(int walletId) throws WalletException {
        Wallet wallet = findWallet(walletId);
        System.out.println("\nSTATEMENT FOR WALLET " + walletId + " - " + wallet.getOwner().getName());
        System.out.println("Transaction | Date and Time      | Type       | Description              | Amount / Fee / Running Balance");
        boolean found = false;
        for (StatementEntry e : entries) {
            boolean belongs = false;
            for (Wallet w : wallets.values()) {
                if (w == wallet && (e.getDescription().contains("wallet " + walletId)
                        || (e.getType().equals("TOP-UP") && e.getDescription().startsWith("TOP-"))
                        || (e.getType().equals("PAYMENT") && payments.values().stream().anyMatch(p -> p.getWallet() == wallet && e.getDescription().equals("Merchant: " + p.getMerchant())))
                        || (e.getType().equals("REFUND") && refunds.values().stream().anyMatch(r -> r.getPayment().getWallet() == wallet && e.getDescription().equals("Payment ID " + r.getPayment().getPaymentId()))))) {
                    belongs = true; break;
                }
            }
            if (belongs) { System.out.println(e); found = true; }
        }
        if (!found) System.out.println("No statement entries yet.");
        System.out.printf("Current balance: Rs. %.2f%n", wallet.getBalance());
    }

    public void displayUsers() {
        if (users.isEmpty()) { System.out.println("No users registered."); return; }
        for (User user : users.values()) {
            System.out.println(user);
            if (user.getWallet() != null)
                System.out.printf("  Balance: Rs. %.2f%n", user.getWallet().getBalance());
        }
    }

    public void displayWalletReceipt(Wallet wallet, String action, BigDecimal amount, BigDecimal fee) {
        System.out.println("\n----- TRANSACTION RECEIPT -----");
        System.out.println("Transaction: " + action);
        System.out.println("Wallet ID : " + wallet.getWalletId());
        System.out.println("User      : " + wallet.getOwner().getName());
        System.out.printf("Amount    : Rs. %.2f%n", amount);
        System.out.printf("Fee       : Rs. %.2f%n", fee);
        System.out.printf("Balance   : Rs. %.2f%n", wallet.getBalance());
        System.out.println("-------------------------------");
    }

    public Collection<User> getUsers() { 
return users.values(); 
}
}
