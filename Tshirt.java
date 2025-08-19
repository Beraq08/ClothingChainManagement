public class Tshirt extends Product {
    private String type; 
    private String color; 

    public Tshirt(String name, int stock, double price, String gender, String size, String type, String color) {
        super(name, stock, price, gender, size);
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: " + type + ", Color: " + color;
    }
}
