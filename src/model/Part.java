package model;

public abstract class Part {
    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public int getPartID() {
        return partID;
    } // end getPartID

    void setPartID(int partID) {
        this.partID = partID;
    } // end setPartID

    public String getName() {
        return name;
    } // end getName

    void setName(String name) {
        this.name = name;
    } // end setName

    public double getPrice() {
        return price;
    } // end getPrice

    void setPrice(double price) {
        this.price = price;
    } // end setPrice

    public int getInStock() {
        return inStock;
    } // end getInStock

    void setInStock(int inStock) {
        this.inStock = inStock;
    } // end setInStock

    public int getMin() {
        return min;
    } // end getMin

    void setMin(int min) {
        this.min = min;
    } // end setMin

    public int getMax() {
        return max;
    } // end getMax

    void setMax(int max) {
        this.max = max;
    } // end setMax
} // end Part
