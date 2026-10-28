package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {
    private final int transactionId;
    private final Wallet source;
    private final Wallet destination;
    private final BigDecimal amount;
    private final BigDecimal fee;
    private final LocalDateTime dateTime;

    public Transfer(int transactionId, Wallet source, Wallet destination,
                    BigDecimal amount, BigDecimal fee, LocalDateTime dateTime) {
        this.transactionId = transactionId;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.fee = fee;
        this.dateTime = dateTime;
    }

    public int getTransactionId() { return transactionId; }
    public Wallet getSource() { return source; }
    public Wallet getDestination() { return destination; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getFee() { return fee; }
    public LocalDateTime getDateTime() { return dateTime; }
}
