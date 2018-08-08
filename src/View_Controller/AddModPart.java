package View_Controller;

import Model.Inhouse;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddModPart {

    @FXML
    private TextField partSourceName;

    @FXML
    private TextField partPrice;

    @FXML
    private Button partSave;

    @FXML
    private TextField partMin;

    @FXML
    private TextField partID;

    @FXML
    private Label sourceLabel;

    @FXML
    private Label partTitle;

    @FXML
    private TextField partInventory;

    @FXML
    private Button partCancel;

    @FXML
    private TextField partMax;

    @FXML
    private RadioButton radioInHouse;

    @FXML
    private ToggleGroup Source;

    @FXML
    private TextField partName;

    @FXML
    private RadioButton radioOutsourced;

    private boolean partInHouse = true;
    Part part;

    @FXML
    // This handler checks the status of the Radio buttons. It sets a variable to determine what type is being created.
    void radioSourceHandler(ActionEvent event) {
        if(event.getSource()==radioInHouse){
            sourceLabel.setText("Machine ID");
            partInHouse = true;
        } else if(event.getSource()==radioOutsourced){
            sourceLabel.setText("Company Name");
            partInHouse = false;
        } // end if
    } // end radioSourceHandler

    @FXML
    void partSaveHandler(ActionEvent event) throws IOException {
        int ID = Integer.parseInt(partID.getText());
        String name = partName.getText();
        double price = Double.parseDouble(partPrice.getText());
        int stock = Integer.parseInt(partInventory.getText());
        int min = Integer.parseInt(partMin.getText());
        int max = Integer.parseInt(partMax.getText());

        if(partInHouse){
            int machineID = Integer.parseInt(partSourceName.getText());
            Inhouse part = new Inhouse(ID, name, price, stock, min, max, machineID);
        } else {
            String companyName = partSourceName.getText();
            Outsourced part = new Outsourced(ID, name, price, stock, min, max, companyName);
        } // end if

        Stage stage;
        Parent root;
        stage=(Stage) partSave.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Your Part Was Saved!" + part.getName());
    } // end partSaveHandler

    @FXML
    void partCancelHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) partSave.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("You Canceled Adding a Part.");
    } // end partCancelHandler

    public void setPart(Part part) {
        this.part = part;
        partID.setText(Integer.toString(part.getPartID()));
        partName.setText(part.getName());
        partInventory.setText(Integer.toString(part.getInStock()));
        partPrice.setText(Double.toString(part.getPrice()));
        partSourceName.setText("IN PROGRESS");
        partMin.setText(Integer.toString(part.getMin()));
        partMax.setText(Integer.toString(part.getMax()));
    }
}
