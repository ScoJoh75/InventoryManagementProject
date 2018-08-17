package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private int index;

    /**
     * Initializes the controller class and sets up the AutoID for new parts.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID.setText(String.valueOf(inventory.getPartID()));
    } // end initialize

    /**
     * radioSourceHandler detects when the user clicks on a Radio button to set the type of part they are creating.
     * Based on the button clicked, the Label for the MachineID/CompanyName field is changed to match.
     * It then sets a boolean that will determine, when save is clicked, which part type is created.
     * @param event the fxid of the radio button clicked.
     */
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

    /**
     * partSaveHandler captures the data in the fields, and then, based on the radioButton selected
     * will create a new part.
     * On save it will check information entered as well as verify various constraints. If everything passes
     * the part will be saved and the user returned to the MainScreen.
     * If everything does not pass the checks, the user will be informed of the problems and then be allowed to correct
     * the issues before trying to save again.
     */
    @FXML
    void partSaveHandler() throws IOException {
        // TODO: Implement Error checking and handling for data captured from the form fields.
        int ID = Integer.parseInt(partID.getText());
        String name = partName.getText();
        double price = Double.parseDouble(partPrice.getText());
        int stock = Integer.parseInt(partInventory.getText());
        int min = Integer.parseInt(partMin.getText());
        int max = Integer.parseInt(partMax.getText());

        if (partInHouse) {
            int machineID = Integer.parseInt(partSourceName.getText());
            part = new Inhouse(ID, name, price, stock, min, max, machineID);
        } else {
            String companyName = partSourceName.getText();
            part = new Outsourced(ID, name, price, stock, min, max, companyName);
        } // end if

        /* If statement below runs the check to make sure min, max, and current stock levels meet system requirements
            before going ahead and allowing the save/update to happen.
            If successful, the save/update occurs and the user is returned to the MainScreen.
            If unsuccessful, the user is returned to the AddModPart screen so they can correct the issues.
         */
        if (inventory.checkStock(min, max, stock)) {
            if (modifying) {
                inventory.updatePart(index, part);
            } else {
                inventory.addPart(part);
            } // end if

            Stage stage;
            Parent root;
            stage = (Stage) partSave.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end if
    } // end partSaveHandler

    /**
     * partCancelHandler brings up a confirmation for the user to let them know that proceeded will result in the
     * loss of any entered information. If the user proceeds they are returned to the MainScreen, otherwise they are
     * returned to the AddModPart window to make additional changes and save their part.
     */
    @FXML
    void partCancelHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Dialog!");
        alert.setHeaderText(null);
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
        // Lines below set the on screen fields with the data in the passed product.
        partTitle.setText("Modify Part");
        partID.setText(Integer.toString(part.getPartID()));
        partName.setText(part.getName());
        partInventory.setText(Integer.toString(part.getInStock()));
        partPrice.setText(Double.toString(part.getPrice()));
        /* if below will set the Radio buttons correctly based upon the part type passed.
            It also sets the Appropriate field label and part type variable for saving.
         */
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
        index = inventory.getAllParts().indexOf(part);
    } // end setPart
} // end AddModPart
