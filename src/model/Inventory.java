package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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
        productID++;
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

    public boolean checkStock(int min, int max, int stock) {
        boolean valid = true;
        if(min > max || max < min) {
            valid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Min/Max Error!");
            alert.setHeaderText(null);
            String s = "Minimum stock must be less than the Maximum stock level!";
            alert.setContentText(s);
            alert.showAndWait();
        } else if(stock < min || stock > max) {
            valid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Current Inventory Error!");
            alert.setHeaderText(null);
            String s = "Your current inventory level must be between the minimum and maximum levels!";
            alert.setContentText(s);
            alert.showAndWait();
        } // end if
        return valid;
    } // end checkStock

    public boolean checkMinParts(ObservableList<Part> partList) {
        boolean pass = true;
        if(partList.size() < 1) {
            pass = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts Association Error!");
            alert.setHeaderText(null);
            String s = "A product must contain at least one part! \n" +
                    "Please add at least one part and try saving again.";
            alert.setContentText(s);
            alert.showAndWait();
        } // end if
        return pass;
    } // end checkMinParts
} // end Inventory
