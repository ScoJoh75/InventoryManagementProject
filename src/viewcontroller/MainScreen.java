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
    private Button productSearch;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private Button partDelete;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private Button productAdd;

    @FXML
    private Button productModify;

    @FXML
    private Button mainExit;

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
    private Button productDelete;

    @FXML
    private Button partSearch;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;

    private static boolean entered;
    static Inventory inventory = new Inventory();

    /**
     * Initializes the controller class and sets up initial data for the partTableView
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!entered){
            inventory.addPart(new Inhouse(1, "Frame", 15, 5, 5, 10, 15));
            inventory.addPart(new Inhouse(2, "Handlebars", 10.00, 5, 10, 25, 10));
            inventory.addPart(new Outsourced(3, "Pedals", 2.50, 10, 10, 50, "Ryder Inc."));
            inventory.addPart(new Outsourced(4, "HandleGrips", 1.45, 10, 10, 50, "CycleBiz Corp."));
            entered=true;
        } // end if
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(inventory.getAllParts());
        partTableView.getSelectionModel().select(0);

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView.setItems(inventory.getAllProducts());
        productTableView.getSelectionModel().select(0);
    } // end initialize

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
            result = "The item you searched for has been selected!";
        } else {
            result = "The item you were searching for could not be located!";
        } // end if
        alert.setContentText(result);

        alert.showAndWait();
    } // end partSearchHandler

    @FXML
    void partAddModHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) partAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModPart.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if(event.getSource()==partModify) {
            AddModPart controller = loader.getController();
            Part part = partTableView.getSelectionModel().getSelectedItem();
            controller.setPart(part);
        } // end if
    } // end partAddModHandler

    @FXML
    void partDeleteHandler() {
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
            if(inventory.deletePart(part)) {
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
    } // end partDeleteHandler

    @FXML
    void productSearchHandler(ActionEvent event) {
        System.out.println("You are searching for: " + productSearchField.getText());
    } // end productSearchHandler

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
        if(event.getSource()==productModify) {
            AddModProduct controller = loader.getController();
            Product product = productTableView.getSelectionModel().getSelectedItem();
            controller.setProduct(product);
        } // end if
    } // end productAddHandler

    @FXML
    void productDeleteHandler(ActionEvent event) {
        System.out.println("You are deleting a PRODUCT!");
    } // end productDeleteHandler

    @FXML
    void mainExitHandler(ActionEvent event) {
        Platform.exit();
    } // end mainExitHandler
} // end MainScreen
