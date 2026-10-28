package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Wallet {
    private final int walletId;
    private final User owner;
    private BigDecimal balance;

    public Wallet(int walletId, User owner) {
        this.walletId = walletId;
        this.owner = owner;
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    public int getWalletId() { return walletId; }
    public User getOwner() { return owner; }
    public BigDecimal getBalance() { return balance; }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public void debit(BigDecimal amount) {
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient wallet balance.");
        }
        balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }
}