public class Sale {
    private Product product;
    private int quantitySold;

    public Sale(Product product, int quantitySold) {
        this.product = product;
        this.quantitySold = quantitySold;
    }

    public double calculateProfit() {
        return product.getPrice() * quantitySold;
    }

    public void performSale() {
        double revenue = product.sell(quantitySold);
        if (revenue > 0) {
            System.out.println("Sale successful! Revenue: " + revenue);
        }
    }
}
