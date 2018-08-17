package viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static viewcontroller.MainScreen.inventory;

public class AddModProduct implements Initializable {

    @FXML
    private TableView<Part> associatedPartsTableView;

    @FXML
    private Label productTitle;

    @FXML
    private TextField productID;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TextField productInventory;

    @FXML
    private TextField productMax;

    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Double> associatedPartCostColumn;

    @FXML
    private TextField productName;

    @FXML
    private TextField productSearchField;

    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryLevelColumn;

    @FXML
    private TableColumn<Part, Double> partCostColumn;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productMin;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;

    @FXML
    private Button productSave;

    private Product product = new Product();
    private boolean modifying;

    /**
     * Initializes the controller class and sets up initial data for the TableViews
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productID.setText(String.valueOf(inventory.getProductID()));
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(inventory.getAllParts());
        partTableView.getSelectionModel().select(0);

        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        associatedPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTableView.setItems(product.getAssociatedParts());
    } // end initialize

    /**
     * productSearchHandler reads the information entered in the the productSearchField and then compares it to either
     * the ID of the parts in the inventory list, or the Name of the parts in the list.
     * If found it will alert the user and then select the part in the partTableView.
     * If nothing is found, it will alert the user that if failed to locate an item.
     */
    @FXML
    void productSearchHandler() {
        boolean found = false;
        for (Part part : inventory.getAllParts()) {
            try {
                if (Integer.parseInt(productSearchField.getText()) == part.getPartID()) {
                    found = true;
                    partTableView.getSelectionModel().select(part);
                } // end if
            } catch (NumberFormatException e) {
                if (productSearchField.getText().toLowerCase().equals(part.getName().toLowerCase())) {
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
            result = "The Part you searched for has been selected in the table!";
        } else {
            result = "The Part you were searching for could not be located! \nPlease try another search.";
        } // end if
        alert.setContentText(result);

        alert.showAndWait();
    } // end productSearchHandler

    /**
     * productAddHandler captures the part selected in the partTableView and passes it to the addAssociatedPart
     * method of the product to be inserted into the associatedPartsList of the product.
     */
    @FXML
    void productAddHandler() {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(part);
    } // end productAddHandler

    /**
     * productDeleteHandler captures the part selected in the associatedPartsTableView and passes it to the
     * removeAssociatedPart method of the product to be removed from the associatedPartsList of the product.
     */
    @FXML
    void productDeleteHandler() {
        Part part = associatedPartsTableView.getSelectionModel().getSelectedItem();
        product.removeAssociatedPart(part);
    } // end productDeleteHandler

    /**
     * productSaveHandler captures the data in the fields, and then calls the products set methods to add the
     * information to the product.
     * On save it will check information entered as well as verify various constraints. If everything passes
     * the product will be saved and the user returned to the MainScreen.
     * If everything does not pass the checks, the user will be informed of the problems and then be allowed to correct
     * the issues before trying to save again.
     */
    @FXML
    void productSaveHandler() throws IOException{
        // TODO: Implement Error checking and handling for data captured from the form fields.
        int index = inventory.getAllProducts().indexOf(product);

        product.setProductID(Integer.parseInt(productID.getText()));
        product.setName(productName.getText());
        product.setPrice(Double.parseDouble(productPrice.getText()));
        product.setInStock(Integer.parseInt(productInventory.getText()));
        product.setMin(Integer.parseInt(productMin.getText()));
        product.setMax(Integer.parseInt(productMax.getText()));

        /* If statement below runs the check to make sure min, max, and current stock levels meet system requirements
            before going ahead and allowing the save/update to happen.
            If successful, the save/update occurs and the user is returned to the MainScreen.
            If unsuccessful, the user is returned to the AddModProduct screen so they can correct the issues.
         */
        if (inventory.checkStock(
                Integer.parseInt(productMin.getText()),
                Integer.parseInt(productMax.getText()),
                Integer.parseInt(productInventory.getText()))
                && inventory.checkMinParts(product.getAssociatedParts())) {

            if (modifying) {
                inventory.updateProduct(index, product);
            } else {
                inventory.addProduct(product);
            } // end if

            Stage stage;
            Parent root;
            stage = (Stage) productSave.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end if
    } // end productSaveHandler

    /**
     * productCancelHandler brings up a confirmation for the user to let them know that proceeded will result in the
     * loss of any entered information. If the user proceeds they are returned to the MainScreen, otherwise they are
     * returned to the AddModProduct window to make additional changes and save their product.
     */
    @FXML
    void productCancelHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Product Creation!");
        alert.setHeaderText(null);
        String s = "Click OK to cancel and return to Main Screen. \n" +
                "Any information entered will be lost.";
        alert.setContentText(s);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) productSave.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    } // end productCancelHandler

    /**
     * setProduct is called when a user decides to modify an existing product.
     * The product selected in the product table will be passed into this method, that product
     * will then be used to determine the values of the fields as they pertain to the product
     * selected, and it will also set the 'modifying' variable which determines the behavior
     * of the save button.
     * @param product the product selected at the time the modify button was clicked.
     */
    void setProduct(Product product) {
        this.product = product; // Sets this classes product instance to the product being passed.
        modifying = true;
        // Lines below set the on screen fields with the data in the passed product.
        productTitle.setText("Modify Product");
        productID.setText(Integer.toString(product.getProductID()));
        productName.setText(product.getName());
        productInventory.setText(Integer.toString(product.getInStock()));
        productPrice.setText(Double.toString(product.getPrice()));
        productMin.setText(Integer.toString(product.getMin()));
        productMax.setText(Integer.toString(product.getMax()));
        // Lines below update the bindings of the product table view to the passed products parts list.
        associatedPartsTableView.setItems(product.getAssociatedParts());
        associatedPartsTableView.getSelectionModel().select(0);
    } // end setPart

} // end AddModProduct
