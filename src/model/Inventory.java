package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    private int partID = 1;
    private int productID = 1;

    public int getPartID() {
        return partID;
    } // end partID
    public int getProductID() { return productID; } // end productID

    public void addProduct(Product product) {
        this.products.add(product);
        partID++;
    } // end addProduct

    public boolean removeProduct(Product product) {
        boolean removed;
        try {
            products.remove(product);
            removed = true;
        } catch (IndexOutOfBoundsException e) {
            removed = false;
        }
        return removed;
    } // end removeProduct

    public Product lookupProduct(int productNum) {
        return products.get(productNum);
    } // end lookupProduct

    public void updateProduct(int productNum, Product product) {
        products.set(productNum, product);
    } // end updateProduct

    public void addPart(Part part) {
        this.allParts.add(part);
        partID++;
    } // end addPart

    public boolean deletePart(Part part) {
        boolean removed;
        try {
            allParts.remove(part);
            removed = true;
        } catch (IndexOutOfBoundsException e) {
            removed = false;
        }
        return removed;
    } // end deletePart

    public Part lookupPart(int partNum) {
        return allParts.get(partNum);
    } // end lookupPart

    public void updatePart(int partNum, Part part) {
        this.allParts.set(partNum, part);
    } // end updatePart

    public ObservableList<Part> getAllParts (){
        return allParts;
    } // end getAllParts

    public ObservableList<Product> getAllProducts () { return products; } // end getAllProducts
} // end Inventory
