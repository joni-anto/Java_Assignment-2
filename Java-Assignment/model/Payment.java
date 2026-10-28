package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private final int paymentId;
    private final Wallet wallet;
    private final String merchant;
    private final BigDecimal amount;
    private final BigDecimal fee;
    private final LocalDateTime dateTime;

    public Payment(int paymentId, Wallet wallet, String merchant,
                   BigDecimal amount, BigDecimal fee, LocalDateTime dateTime) {
        this.paymentId = paymentId;
        this.wallet = wallet;
        this.merchant = merchant;
        this.amount = amount;
        this.fee = fee;
        this.dateTime = dateTime;
    }

    public int getPaymentId() { return paymentId; }
    public Wallet getWallet() { return wallet; }
    public String getMerchant() { return merchant; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getFee() { return fee; }
    public LocalDateTime getDateTime() { return dateTime; }
}
