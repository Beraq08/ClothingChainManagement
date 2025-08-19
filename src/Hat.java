public class Hat extends Product {
    private String type; 
    private String fabric; 

    public Hat(String name, int stock, double price,String gender, String size,String type,String fabric) {
        super(name, stock, price,gender,size);
        this.type = type;
        this.fabric = fabric;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: " + type + ", Fabric: " + fabric;
    }
}
