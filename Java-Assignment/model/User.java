package model;

public class User {
    private final int userId;
    private final String name;
    private final String phone;
    private Wallet wallet;

    public User(int userId, String name, String phone) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Wallet getWallet() { return wallet; }
    public void setWallet(Wallet wallet) { this.wallet = wallet; }

    @Override
    public String toString() {
        return "User ID: " + userId + " | Name: " + name
                + " | Phone: " + phone
                + " | Wallet: " + (wallet == null ? "Not created" : wallet.getWalletId());
    }
}