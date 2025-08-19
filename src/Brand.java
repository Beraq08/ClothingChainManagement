import java.util.ArrayList;
import java.util.List;

public class Brand {
    private String brandName;
    private ArrayList<Product> products;
    private double profit;

    public Brand(String brandName) {
        this.brandName = brandName;
        this.products = new ArrayList<>();
        this.profit = 0;
    }

    public String getBrandName() {
        return brandName;
    }

    public double getProfit() {
        return profit;
    }
   
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public void updateProduct(Product originalProduct, Product updatedProduct) {
        int index = products.indexOf(originalProduct);
        if (index >= 0) {
            products.set(index, updatedProduct); 
        }
    }
    public boolean isproductexist(String name) {
    	for (Product product : products) {
			if(product.getName().equals(name)) {
				return true;
			}
		}
    	return false;
    }

    public void displayProducts() {
        System.out.println("Brand: " + brandName);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    // selling
    public String sellProduct(String productName, int quantity) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                if (product.getStock() >= quantity) {
                    Sale sale = new Sale(product, quantity);
                    sale.performSale(); 
                    profit += sale.calculateProfit(); 
                    if (product.getStock() == 0) {
                        products.remove(product); 
                    }
                    return "Sale successful! Profit: " + sale.calculateProfit();
                } else {
                    return "Insufficient stock!";
                }
            }
        }
        return "Product not found!";
    }
    
    public List<Product> getProducts() {
        return products;
    }
}
