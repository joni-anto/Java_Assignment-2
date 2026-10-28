package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatementEntry {
    private final int transactionId;
    private final LocalDateTime dateTime;
    private final String type;
    private final String description;
    private final BigDecimal amount;
    private final BigDecimal fee;
    private final BigDecimal resultingBalance;

    public StatementEntry(int transactionId, LocalDateTime dateTime,
            String type, String description, BigDecimal amount,
            BigDecimal fee, BigDecimal resultingBalance) {
        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.fee = fee;
        this.resultingBalance = resultingBalance;
    }

    public int getTransactionId() { return transactionId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getFee() { return fee; }
    public BigDecimal getResultingBalance() { return resultingBalance; }

    @Override
    public String toString() {
        return String.format("%-5d | %-19s | %-10s | %-24s | Amount: Rs. %8.2f | Fee: Rs. %6.2f | Balance: Rs. %8.2f",
                transactionId, dateTime.toString().replace('T', ' '), type,
                description, amount, fee, resultingBalance);
    }
}
