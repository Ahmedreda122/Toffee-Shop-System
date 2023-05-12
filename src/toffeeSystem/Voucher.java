package toffeeSystem;

/**
 * The `Voucher` class represents a voucher that can be used to discount a
 * purchase.
 */
public class Voucher {
    private String code;
    private double price;
    private boolean used;

    /**
     * Constructs a new voucher object with the specified code and price.
     *
     * @param code  the code of the voucher
     * @param price the amount of the voucher
     */
    public Voucher(String code, double price) {
        this.code = code;
        this.price = price;
        this.used = false;
    }

    /**
     * Returns the code of this voucher.
     *
     * @return the code of the voucher
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the amount of this voucher.
     *
     * @return the amount of the voucher
     */
    public double getAmount() {
        return price;
    }

    /**
     * Returns whether or not this voucher has been used.
     *
     * @return true if the voucher has been used, false otherwise
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Sets the used status of this voucher.
     *
     * @param used true if the voucher has been used, false otherwise
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * Redeems this voucher and returns the discounted price.
     *
     * @param totalPrice the total price of the purchase
     * @return the discounted price
     */
    public double redeem(double totalPrice) {
        double discountedPrice = totalPrice - price;
        System.out.println("Voucher redeemed: " + code);
        System.out.println("Discount amount: " + price);
        System.out.println("Total price after discount: " + discountedPrice);
        return discountedPrice;
    }
}
