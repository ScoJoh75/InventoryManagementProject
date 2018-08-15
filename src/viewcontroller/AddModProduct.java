package viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inhouse;
import model.Outsourced;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    private Product product;
    private boolean modifying;
    private ObservableList<Part> partsList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class and sets up initial data for the partTableView
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
        associatedPartsTableView.setItems(partsList);
        associatedPartsTableView.getSelectionModel().select(0);
    } // end initialize

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
            result = "The item you searched for has been selected!";
        } else {
            result = "The item you were searching for could not be located!";
        } // end if
        alert.setContentText(result);

        alert.showAndWait();
    } // end productSearchHandler

    @FXML
    void productAddHandler() {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        partsList.add(part);
    } // end productAddHandler

    @FXML
    void productDeleteHandler() {
        Part part = associatedPartsTableView.getSelectionModel().getSelectedItem();
        partsList.remove(part);
    } // end productDeleteHandler

    @FXML
    void productSaveHandler() {

    } // end productSaveHandler

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
     * setPart is called when a user decides to modify an existing part.
     * The part selected in the part table will be passed into this method, that part
     * will then be used to determine the status of the part type radio buttons, the
     * values of the fields as they pertain to the part selected, and it will also set the
     * 'modifying' variable which determines the behavior of the save button.
     * @param product the part selected in the part table when modify was clicked.
     */
    public void setProduct(Product product) {
        this.product = product;
        modifying = true;
        productTitle.setText("Modify Product");
        productID.setText(Integer.toString(product.getProductID()));
        productName.setText(product.getName());
        productInventory.setText(Integer.toString(product.getInStock()));
        productPrice.setText(Double.toString(product.getPrice()));
        productMin.setText(Integer.toString(product.getMin()));
        productMax.setText(Integer.toString(product.getMax()));
        // TODO: Add setting associated parts to the associatedPartTableView
    } // end setPart

} // end AddModProduct
