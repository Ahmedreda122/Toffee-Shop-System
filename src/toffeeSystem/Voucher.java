package toffeeSystem;
public class Voucher {
    private String code;
    private double price;
    private boolean used;

    public Voucher(String code, double amount) {
        this.code = code;
        this.price = amount;
        this.used = false;
    }

    public String getCode() {
        return code;
    }

    public double getAmount() {
        return price;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    public double redeem(double totalPrice) {
        double discountedPrice = totalPrice - price;
        System.out.println("Voucher redeemed: " + code);
        System.out.println("Discount amount: " + price);
        System.out.println("Total price after discount: " + discountedPrice);
        return discountedPrice;
    }
}
