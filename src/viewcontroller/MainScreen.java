package viewcontroller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    @FXML
    private TextField partSearchField;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private Button productAdd;

    @FXML
    private Button productModify;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private Button partAdd;

    @FXML
    private Button partModify;

    @FXML
    private TableColumn<Product, Double> productCostColumn;

    @FXML
    private TextField productSearchField;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Part, Double> partCostColumn;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;

    private static boolean entered;
    static Inventory inventory = new Inventory();

    /**
     * Initializes the controller class and sets up initial data for the TableViews
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!entered){
            // These items are only added on first run and will not be run on subsequent returns to the MainScreen controller.
            inventory.addPart(new Inhouse(1, "Frame", 15, 5, 5, 10, 15));
            inventory.addPart(new Inhouse(2, "Handlebars", 10.00, 5, 10, 25, 10));
            inventory.addPart(new Outsourced(3, "Pedals", 2.50, 10, 10, 50, "Ryder Inc."));
            inventory.addPart(new Outsourced(4, "HandleGrips", 1.45, 10, 10, 50, "CycleBiz Corp."));
            entered = true;
        } // end if
        // Below are all the calls to create bindings for the table and columns for parts.
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(inventory.getAllParts());
        partTableView.getSelectionModel().select(0);
        // Below are all the calls to create binding for the table and columns for products.
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView.setItems(inventory.getAllProducts());
        productTableView.getSelectionModel().select(0);
    } // end initialize

    /**
     * partSearchHandler reads the information entered in the the partSearchField and then compares it to either
     * the ID of the parts in the inventory list, or the Name of the parts in the list.
     * If found it will alert the user and then select the part in the partTableView.
     * If nothing is found, it will alert the user that if failed to locate an item.
     */
    @FXML
    void partSearchHandler() {
        boolean found = false;
        for (Part part : inventory.getAllParts()) {
            try {
                if (Integer.parseInt(partSearchField.getText()) == part.getPartID()) {
                    found = true;
                    partTableView.getSelectionModel().select(part);
                } // end if
            } catch (NumberFormatException e) {
                if (partSearchField.getText().toLowerCase().equals(part.getName().toLowerCase())) {
                    found = true;
                    partTableView.getSelectionModel().select(part);
                } // end if
            } // end try
        } // end for

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Result");
        alert.setHeaderText(null);
        String result;
        if (found) {
            result = "The Part you searched for has been selected for you!";
        } else {
            result = "The Part you were searching for could not be located! \n Please try your search again!";
        } // end if
        alert.setContentText(result);

        alert.showAndWait();
    } // end partSearchHandler

    /**
     * partAddModHandler initiates the swap from the MainScreen to the AddModPart screen.
     * It takes button ActionEvent as a parameter in order to know if the user clicked
     * the Add or Modify button.
     * If the Modify button was clicked it will pass the selected part in the TableView
     * to the setPart method of the AddModPart class.
     * @param event the fxid of the button that was clicked.
     */
    @FXML
    void partAddModHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) partAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModPart.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if(event.getSource() == partModify && inventory.getAllParts().size() > 0) {
            AddModPart controller = loader.getController();
            Part part = partTableView.getSelectionModel().getSelectedItem();
            controller.setPart(part);
        } // end if
    } // end partAddModHandler

    /**
     * partDeleteHandler determines the part selected in the partTableView. Then it calls
     * and alert to have the user verify if they truly wish to delete the part. If confirmed
     * the selected part is passed into the deletePart method of inventory.
     * Then a users is alerted to if the removal succeeded of failed.
     */
    @FXML
    void partDeleteHandler() {
        if(inventory.getAllParts().size() > 0) {
            Part part = partTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!!!");
            alert.setHeaderText(null);
            String s = "WARNING!! You are about to delete: \n" +
                    "     Part: " + part.getPartID() + " " + part.getName() + "\n" +
                    "\n This action cannot be undone! \n Click OK to delete the selected part.";
            alert.setContentText(s);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (inventory.deletePart(part)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Remove Successful.");
                    alert.setHeaderText(null);
                    s = "The Selected part has been successfully removed.";
                    alert.setContentText(s);

                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Remove Failed.");
                    alert.setHeaderText(null);
                    s = "The Selected part was NOT removed.";
                    alert.setContentText(s);

                    alert.showAndWait();
                } // end if
            } // end if
        } // end if
    } // end partDeleteHandler

    /**
     * productSearchHandler reads the information entered in the productSearchField and then compares it to either
     * the ID of the products in the inventory list, or the Name of the products in the list.
     * If found it will alert the user and then selects the part in the productTableView.
     * If nothing is found, it will alert the user that if failed to locate an item.
     */
    @FXML
    void productSearchHandler() {
        boolean found = false;
        for (Product product : inventory.getAllProducts()) {
            try {
                if (Integer.parseInt(productSearchField.getText()) == product.getProductID()) {
                    found = true;
                    productTableView.getSelectionModel().select(product);
                } // end if
            } catch (NumberFormatException e) {
                if (productSearchField.getText().toLowerCase().equals(product.getName().toLowerCase())) {
                    found = true;
                    productTableView.getSelectionModel().select(product);
                } // end if
            } // end try
        } // end for

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Result");
        alert.setHeaderText(null);
        String result;
        if (found) {
            result = "The Product you searched for has been selected for you!";
        } else {
            result = "The Product you were searching for could not be located! \nPlease try another search.";
        } // end if
        alert.setContentText(result);

        alert.showAndWait();
    } // end productSearchHandler

    /**
     * productAddModHandler initiates the swap from the MainScreen to the AddModProduct screen.
     * It takes button ActionEvent as a parameter in order to know if the user clicked
     * the Add or Modify button.
     * If the Modify button was clicked it will pass the selected product in the TableView
     * to the setProduct method of the AddModProduct class.
     * @param event the fxid of the button that was clicked.
     */
    @FXML
    void productAddModHandler(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage=(Stage) productAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModProduct.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if(event.getSource() == productModify && inventory.getAllProducts().size() > 0) {
            AddModProduct controller = loader.getController();
            Product product = productTableView.getSelectionModel().getSelectedItem();
            controller.setProduct(product);
        } // end if
    } // end productAddHandler

    /**
     * productDeleteHandler determines the product selected in the productTableView. Then it calls
     * an alert to have the user verify if they truly wish to delete the product. If confirmed
     * the selected product is passed into the deleteProduct method of inventory.
     * Then a users is alerted to if the removal succeeded of failed.
     */
    @FXML
    void productDeleteHandler() {
        if(inventory.getAllProducts().size() > 0) {
            Product product = productTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!!!");
            alert.setHeaderText(null);
            String s = "WARNING!! You are about to delete: \n" +
                    "     Product: " + product.getProductID() + " " + product.getName() + "\n" +
                    "\n This action cannot be undone! \n Click OK to delete the selected Product.";
            alert.setContentText(s);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (inventory.removeProduct(product)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Remove Successful.");
                    alert.setHeaderText(null);
                    s = "The Selected product has been successfully removed.";
                    alert.setContentText(s);

                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Remove Failed.");
                    alert.setHeaderText(null);
                    s = "The Selected product has Parts associated with it.\n" +
                            "Products with associated Parts can not be deleted.";
                    alert.setContentText(s);

                    alert.showAndWait();
                } // end if
            } // end if
        } // end if
    } // end productDeleteHandler

    /**
     * mainExitHandler closes the application.
     */
    @FXML
    void mainExitHandler() {
        Platform.exit();
    } // end mainExitHandler
} // end MainScreen
