package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TopUp {
    private final int transactionId;
    private final Wallet wallet;
    private final BigDecimal amount;
    private final LocalDateTime dateTime;
    private final String confirmationReference;

    public TopUp(int transactionId, Wallet wallet, BigDecimal amount,
                 LocalDateTime dateTime, String confirmationReference) {
        this.transactionId = transactionId;
        this.wallet = wallet;
        this.amount = amount;
        this.dateTime = dateTime;
        this.confirmationReference = confirmationReference;
    }

    public int getTransactionId() { return transactionId; }
    public Wallet getWallet() { return wallet; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getConfirmationReference() { return confirmationReference; }
}
