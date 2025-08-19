public class Jeans extends Product {
    private int waistSize; 
    private int length; 

    public Jeans(String name, int stock, double price,String gender, String size,int waistSize,int length) {
        super(name, stock, price,gender,size);
        this.waistSize = waistSize;
        this.length = length;
    }

    public int getWaistSize() {
        return waistSize;
    }

    public void setWaistSize(int waistSize) {
        this.waistSize = waistSize;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return super.toString() + ", Waist Size: " + waistSize + ", Length: " + length;
    }
}
