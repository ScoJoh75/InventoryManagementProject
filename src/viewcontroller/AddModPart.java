package viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Inhouse;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static viewcontroller.MainScreen.inventory;

public class AddModPart implements Initializable {

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
    private TextField partMax;

    @FXML
    private RadioButton radioInHouse;

    @FXML
    private TextField partName;

    @FXML
    private RadioButton radioOutsourced;

    private boolean partInHouse = true;
    private boolean modifying;
    private Part part;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID.setText(String.valueOf(inventory.getAllParts().size() + 1));
    } // end initialize

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
    void partSaveHandler() throws IOException {
        // TODO: Implement Error checking and handling for data captured from the form fields.
        int ID = Integer.parseInt(partID.getText());
        String name = partName.getText();
        double price = Double.parseDouble(partPrice.getText());
        int stock = Integer.parseInt(partInventory.getText());
        int min = Integer.parseInt(partMin.getText());
        int max = Integer.parseInt(partMax.getText());

        if(partInHouse){
            int machineID = Integer.parseInt(partSourceName.getText());
            part = new Inhouse(ID, name, price, stock, min, max, machineID);
        } else {
            String companyName = partSourceName.getText();
            part = new Outsourced(ID, name, price, stock, min, max, companyName);
        } // end if

        if(modifying){
            inventory.updatePart(ID - 1, part);
        } else {
            inventory.addPart(part);
        }

        Stage stage;
        Parent root;
        stage=(Stage) partSave.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end partSaveHandler

    @FXML
    void partCancelHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Dialog!");
        String s = "Click OK to cancel and return to Main Screen. \n" +
                "Any information entered will be lost.";
        alert.setContentText(s);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) partSave.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    } // end partCancelHandler

    /**
     * setPart is called when a user decides to modify an existing part.
     * The part selected in the part table will be passed into this method, that part
     * will then be used to determine the status of the part type radio buttons, the
     * values of the fields as they pertain to the part selected, and it will also set the
     * 'modifying' variable which determines the behavior of the save button.
     * @param part the part selected in the part table when modify was clicked.
     */
    public void setPart(Part part) {
        this.part = part;
        modifying = true;
        partTitle.setText("Modify Part");
        partID.setText(Integer.toString(part.getPartID()));
        partName.setText(part.getName());
        partInventory.setText(Integer.toString(part.getInStock()));
        partPrice.setText(Double.toString(part.getPrice()));
        if(part instanceof Outsourced) {
            partSourceName.setText(((Outsourced) part).getCompanyName());
            radioOutsourced.setSelected(true);
            partInHouse = false;
        } else {
            partSourceName.setText(Integer.toString(((Inhouse) part).getMachineID()));
            radioInHouse.setSelected(true);
            partInHouse = true;
        }
        partMin.setText(Integer.toString(part.getMin()));
        partMax.setText(Integer.toString(part.getMax()));
    } // end setPart
} // end AddModPart
