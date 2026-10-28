package main;

import java.math.BigDecimal;
import java.util.Scanner;

import exception.WalletException;
import model.*;
import service.WalletService;
import util.InputUtil;

public class WalletApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputUtil input = new InputUtil(scanner);
        WalletService service = new WalletService();
        boolean running = true;

        while (running) {
            System.out.println("\n===== DIGITAL WALLET & TRANSFERS =====");
            System.out.println("1. Add User");
            System.out.println("2. Create Wallet");
            System.out.println("3. Top-up");
            System.out.println("4. Transfer");
            System.out.println("5. Pay Merchant");
            System.out.println("6. Refund");
            System.out.println("7. View Statement");
            System.out.println("8. Display Users");
            System.out.println("0. Exit");

            int choice = input.readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1: {
                        String name = input.readLine("User name: ");
                        String phone = input.readLine("Phone number: ");
                        User user = service.addUser(name, phone);
                        System.out.println("User added successfully. User ID: " + user.getUserId());
                        break;
                    }
                    case 2: {
                        int userId = input.readInt("User ID: ");
                        Wallet wallet = service.createWallet(userId);
                        System.out.println("Wallet created successfully. Wallet ID: " + wallet.getWalletId());
                        System.out.printf("Opening balance: Rs. %.2f%n", wallet.getBalance());
                        break;
                    }
                    case 3: {
                        int walletId = input.readInt("Wallet ID: ");
                        BigDecimal amount = input.readAmount("Top-up amount (Rs.): ");
                        String answer = input.readLine("Confirm top-up? (yes/no): ");
                        TopUp topUp = service.topUp(walletId, amount, answer.equalsIgnoreCase("yes"));
                        service.displayWalletReceipt(topUp.getWallet(), "TOP-UP " + topUp.getConfirmationReference(), amount, BigDecimal.ZERO);
                        break;
                    }
                    case 4: {
                        int sourceId = input.readInt("Source wallet ID: ");
                        int destinationId = input.readInt("Destination wallet ID: ");
                        BigDecimal amount = input.readAmount("Transfer amount (Rs.): ");
                        Transfer transfer = service.transfer(sourceId, destinationId, amount);
                        System.out.printf("Transfer successful. Transaction ID: %d%n", transfer.getTransactionId());
                        System.out.printf("Transferred: Rs. %.2f | Fee: Rs. %.2f%n", transfer.getAmount(), transfer.getFee());
                        System.out.printf("Source balance: Rs. %.2f%n", transfer.getSource().getBalance());
                        System.out.printf("Destination balance: Rs. %.2f%n", transfer.getDestination().getBalance());
                        break;
                    }
                    case 5: {
                        int walletId = input.readInt("Wallet ID: ");
                        String merchant = input.readLine("Merchant name: ");
                        BigDecimal amount = input.readAmount("Payment amount (Rs.): ");
                        Payment payment = service.payMerchant(walletId, merchant, amount);
                        System.out.println("Payment successful. Payment ID: " + payment.getPaymentId());
                        service.displayWalletReceipt(payment.getWallet(), "MERCHANT PAYMENT - " + payment.getMerchant(), amount, payment.getFee());
                        break;
                    }
                    case 6: {
                        int paymentId = input.readInt("Original payment ID: ");
                        BigDecimal amount = input.readAmount("Refund amount (Rs.): ");
                        Refund refund = service.refund(paymentId, amount);
                        System.out.println("Refund successful. Refund ID: " + refund.getRefundId());
                        service.displayWalletReceipt(refund.getPayment().getWallet(), "REFUND", amount, BigDecimal.ZERO);
                        break;
                    }
                    case 7: {
                        int walletId = input.readInt("Wallet ID: ");
                        service.viewStatement(walletId);
                        break;
                    }
                    case 8:
                        service.displayUsers();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using Digital Wallet & Transfers.");
                        break;
                    default:
                        System.out.println("Invalid choice. Select a listed option.");
                }
            } catch (WalletException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
