public class Shoes extends Product {
    private String type; 

    public Shoes(String name, int stock, double price,String gender, String size,String type) {
        super(name, stock, price,gender,size);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: " + type;
    }
}
