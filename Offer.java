public class Offer {
    private String offerName;
    private double discountPercentage;

    public Offer(String offerName, double discountPercentage) {
        this.offerName = offerName;
        this.discountPercentage = discountPercentage;
    }

    public String getOfferName() {
        return offerName;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }
}
