package model;

import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Product(int ProductID, String name, double price, int inStock, int min, int max) {
        setProductID(ProductID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    } // end initializer

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    } // end addAssociatedPart

    public boolean removeAssociatedPart(Part part) {
        boolean removed;
        try {
            associatedParts.remove(part);
            removed = true;
        } catch (IndexOutOfBoundsException e) {
                removed = false;
            }
        return removed;
    } // end removeAssociatedPart

    public Part lookupAssociatedPart(int partNum) {
        return associatedParts.get(partNum);
    } // end lookupAssociatedPart

    public int getProductID() {
        return productID;
    } // end getProductID

    private void setProductID(int productID) {
        this.productID = productID;
    } // end setProductID

    public String getName() {
        return name;
    } // end getName

    private void setName(String name) {
        this.name = name;
    } // end setName

    public double getPrice() {
        return price;
    } // end getPrice

    private void setPrice(double price) {
        this.price = price;
    } // end setPrice

    public int getInStock() {
        return inStock;
    } // end getInStock

    private void setInStock(int inStock) {
        this.inStock = inStock;
    } // end setInstock

    public int getMin() {
        return min;
    } // end getMin

    private void setMin(int min) {
        this.min = min;
    } // end setMin

    public int getMax() {
        return max;
    } // end getMax

    private void setMax(int max) {
        this.max = max;
    } // end setMax
} // end Product
