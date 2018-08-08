package View_Controller;

import Model.Inhouse;
import Model.ListOfParts;
import Model.Outsourced;
import Model.Part;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreen implements Initializable {

    @FXML
    private TextField partSearchField;

    @FXML
    private Button productSearch;

    @FXML
    private TableColumn<?, ?> partNameColumn;

    @FXML
    private TableColumn<?, ?> productIDColumn;

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
    private TableView<?> productTableView;

    @FXML
    private Button partAdd;

    @FXML
    private Button partModify;

    @FXML
    private TableColumn<?, ?> productCostColumn;

    @FXML
    private TextField productSearchField;

    @FXML
    private TableColumn<?, ?> productInventoryLevelColumn;

    @FXML
    private TableColumn<?, ?> productNameColumn;

    @FXML
    private TableColumn<?, ?> partCostColumn;

    @FXML
    private Button productDelete;

    @FXML
    private Button partSearch;

    @FXML
    private TableColumn<?, ?> partIDColumn;

    @FXML
    private TableColumn<?, ?> partInventoryLevelColumn;
    static boolean entered;
    /**
     * Initializes the controller class and sets up initial data
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!entered){
            ListOfParts.parts.add(new Inhouse(1, "Frame", 15, 5, 5, 10, 15));
            ListOfParts.parts.add(new Inhouse(2, "Handlebars", 10.00, 5, 10, 25, 10));
            ListOfParts.parts.add(new Outsourced(3, "Pedals", 2.50, 10, 10, 50, "Ryder Inc."));
            ListOfParts.parts.add(new Outsourced(4, "HandleGrips", 1.45, 10, 10, 50, "CycleBiz Corp."));
            entered=true;
        }
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("instock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(ListOfParts.parts);
    }

    @FXML
    void partSearchHandler(ActionEvent event) {
        System.out.println("You are searching for: " + partSearchField.getText());
    }

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
        if(event.getSource()==partAdd){
            System.out.println("You are adding a part!");
        } else if(event.getSource()==partModify) {
            System.out.println("You are modifying a part!");
            AddModPart controller = loader.getController();
            Part part = partTableView.getSelectionModel().getSelectedItem();
            controller.setPart(part);
        } // end if
    } // end partAddModHandler

    @FXML
    void partDeleteHandler(ActionEvent event) {
        System.out.println("You are deleting a part!");
    }

    @FXML
    void productSearchHandler(ActionEvent event) {
        System.out.println("You are searching for: " + productSearchField.getText());
    }

    @FXML
    void productAddHandler(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage=(Stage) productAdd.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AddModProduct.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("You are adding a PRODUCT!");
    }

    @FXML
    void productModifyHandler(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage=(Stage) productModify.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AddModProduct.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("You are Modifying a PRODUCT!");
    }

    @FXML
    void productDeleteHandler(ActionEvent event) {
        System.out.println("You are deleting a PRODUCT!");
    }

    @FXML
    void mainExitHandler(ActionEvent event) {
        Platform.exit();
    }

}
