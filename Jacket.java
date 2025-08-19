public class Jacket extends Product {
    private String color;
    private String material; 

    public Jacket(String name, int stock, double price,String gender, String size,String color,String material) {
        super(name, stock, price,gender,size);
        this.color = color;
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return super.toString() + ", Color: " + color + ", Material: " + material;
    }
}
