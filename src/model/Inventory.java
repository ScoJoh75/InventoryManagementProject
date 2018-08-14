package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    private int index = 1;

    public int getIndex() {
        return index;
    } // end index

    public void addProduct(Product product) {
        this.products.add(product);
    } // end addProduct

    public boolean removeProduct(int productNum) {
        boolean removed;
        try {
            products.remove(productNum);
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
        index++;
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
    }

} // end Inventory
