package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Refund {
    private final int refundId;
    private final Payment payment;
    private final BigDecimal amount;
    private final LocalDateTime dateTime;

    public Refund(int refundId, Payment payment, BigDecimal amount,
                  LocalDateTime dateTime) {
        this.refundId = refundId;
        this.payment = payment;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public int getRefundId() { return refundId; }
    public Payment getPayment() { return payment; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getDateTime() { return dateTime; }
}