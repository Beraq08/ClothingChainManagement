public abstract class Product {
    private String name;
    private int stock;
    private double price;
    private String gender;
    private String size;

    public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Product(String name, int stock, double price,String gender,String size) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.gender=gender;
        this.size=size;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public String getGender() {
		return gender;
	}
    
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double sell(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return price * quantity;
        } else {
            System.out.println("Insufficient stock!");
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Product: " + name + ", Stock: " + stock + ", Price: " + price;
    }
}
