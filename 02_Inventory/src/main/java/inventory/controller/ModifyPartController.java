
package inventory.controller;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.service.InventoryService;
import inventory.validator.PartValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static inventory.controller.MainScreenController.getModifyPartIndex;

public class ModifyPartController implements Initializable, Controller {
    
    // Declare field
    private int partIndex= getModifyPartIndex();
    private boolean isOutsourced;

    private InventoryService service;
    
    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;
    
    @FXML
    private Label modifyPartDynamicLbl;
    
    @FXML
    private TextField modifyPartDynamicTxt;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    public void setService(InventoryService service){
        this.service=service;
        fillWithData();
    }

    private void fillWithData(){
        Part part = service.getAllParts().get(partIndex);

        int partId = service.getAllParts().get(partIndex).getPartId();
        partIdTxt.setText(Integer.toString(part.getPartId()));
        nameTxt.setText(part.getName());
        inventoryTxt.setText(Integer.toString(part.getInStock()));
        priceTxt.setText(Double.toString(part.getPrice()));
        maxTxt.setText(Integer.toString(part.getMax()));
        minTxt.setText(Integer.toString(part.getMin()));

        if(part instanceof InhousePart) {
            modifyPartDynamicTxt.setText(Integer.toString(((InhousePart) service.getAllParts().get(partIndex)).getMachineId()));
            modifyPartDynamicLbl.setText("Machine ID");
            inhouseRBtn.setSelected(true);
            isOutsourced = false;
        } else {
            modifyPartDynamicTxt.setText(((OutsourcedPart) service.getAllParts().get(partIndex)).getCompanyName());
            modifyPartDynamicLbl.setText("Company Name");
            outsourcedRBtn.setSelected(true);
            isOutsourced = true;
        }
    }

    /**
     * Method to add to button handler to switch to scene passed as source
     * @param event
     * @param source
     * @throws IOException
     */
    @FXML
    private void displayScene(ActionEvent event, String source) throws IOException {
        Parent scene;
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource(source));
        scene = loader.load();
        Controller ctrl=loader.getController();
        ctrl.setService(service);
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * If in-house radio button is selected set isOutsourced boolean
     * to false and modify dynamic label to Machine ID
     * @param event 
     */
    @FXML
    void handleInhouseRBtn(ActionEvent event) {
        isOutsourced = false;
        modifyPartDynamicLbl.setText("Machine ID");
    }
    
    /**
     * If outsourced radio button is selected set isOutsourced boolean
     * to true and modify dynamic label to Company Name
     * @param event 
     */
    @FXML
    void handleOutsourcedRBtn(ActionEvent event) {
        isOutsourced = true;
        modifyPartDynamicLbl.setText("Company Name");
    }

    /**
     * Seek user confirmation before canceling modifications and
     * switching scene to MainScreen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleModifyPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Cancellation");
        alert.setContentText("Are you sure you want to cancel modifying part " + nameTxt.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Ok selected. Part modification cancelled.");
            displayScene(event, "/fxml/MainScreen.fxml");
        } else {
            System.out.println("Cancel clicked. Please complete part modification.");
        }
    }

    /**
     * Validate part attributes and save modifications to chosen
     * Part object then switch scene to MainScreen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleModifyPartSave(ActionEvent event) throws IOException {
        String errorMessage = "";
        String partId = partIdTxt.getText();
        String name = nameTxt.getText();
        String price = priceTxt.getText();
        String inStock = inventoryTxt.getText();
        String min = minTxt.getText();
        String max = maxTxt.getText();
        String partDynamicValue = modifyPartDynamicTxt.getText();
        errorMessage = "";
        
        try {
            errorMessage = PartValidator.isValidPart(name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), errorMessage);
            if(errorMessage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part!");
                alert.setHeaderText("Error!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
                if(isOutsourced) {
                    OutsourcedPart outsourcedPart = new OutsourcedPart(Integer.parseInt(partId), name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), partDynamicValue);
                    service.updateOutsourcedPart(partIndex, outsourcedPart);
                } else {
                    InhousePart inhousePart = new InhousePart(Integer.parseInt(partId), name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(partDynamicValue));
                    service.updateInhousePart(partIndex, inhousePart);
                }
                displayScene(event, "/fxml/MainScreen.fxml");
            }

        } catch (NumberFormatException e) {
            System.out.println("Blank Fields");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Error");
            alert.setContentText("Form contains blank field.");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}