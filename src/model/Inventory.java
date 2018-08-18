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
        boolean removed = false;
            if(product.getAssociatedParts().size() < 1) {
                products.remove(product);
                removed = true;
            } // end if
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
        String s = "";
        if(min > max || max < min) {
            valid = false;
            s += "Minimum stock must be less than the Maximum stock level!\n";
        } // end min/max

        if(stock < min || stock > max) {
            valid = false;
            s += "Your current inventory level must be between the minimum and maximum levels!\n";
        } // end stock check if

        if(!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Error!");
            alert.setHeaderText(null);
            alert.setContentText(s);
            alert.showAndWait();
        } // end if
        return valid;
    } // end checkStock

    public boolean isValid(ObservableList<Part> partList) {
        boolean valid = true;
        String s = "";
        if(partList.size() < 1) {
            valid = false;
            s = "A product must contain at least one part! \n" +
                    "Please add at least one part and try saving again.\n";
        } // end if

        if(!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Information is Invalid!");
            alert.setHeaderText(null);
            alert.setContentText(s);
            alert.showAndWait();
        } //end if

        return valid;
    } // end isValid
} // end Inventory
