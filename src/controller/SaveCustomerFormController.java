package controller;

import BO.BOFactory;
import BO.Custom.CustomerBO;
import ValidationUtil.Validation;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import DTO.CustomerDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SaveCustomerFormController implements Initializable {

    public TextField txtCustomerId;
    public TextField txtCustomerTitle;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtPostalCode;
    public ComboBox<String> cmbProvince;
    public TextField txtCity;
    public JFXButton btnSave;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern compile_CustomerId = Pattern.compile("^[C-c]{1}(0){2}[-][0-9]{4}$");
    Pattern compile_CustomerName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_CustomerTitle = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_CustomerAddress = Pattern.compile("^[A-z .,/0-9]{4,50}$");
    Pattern compile_CustomerCity = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_CustomerPostalCode = Pattern.compile("^[0-9]{1,6}$");

    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);

        ObservableList<String> obList = FXCollections.observableArrayList(
                "Western Province",
                "Central Province",
                "Southern Province",
                "Eastern Province",
                "Uva Province",
                "Sabaragamuwa Province",
                "North Western Province",
                "North Central Province",
                "Nothern Province"
        );
        cmbProvince.setItems(obList);

        setValidation();

    }

    private void setValidation() {
        map.put(txtCustomerId,compile_CustomerId);
        map.put(txtCustomerName,compile_CustomerName);
        map.put(txtCustomerTitle,compile_CustomerTitle);
        map.put(txtCustomerAddress,compile_CustomerAddress);
        map.put(txtCity,compile_CustomerCity);
        map.put(txtPostalCode,compile_CustomerPostalCode);
    }

    public void customer_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    //save new customer
    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (txtCustomerId.getText().isEmpty() || txtCustomerName.getText().isEmpty() || txtCustomerTitle.getText().isEmpty() || txtCustomerAddress.getText().isEmpty() || txtCity.getText().isEmpty() || txtPostalCode.getText().isEmpty() || cmbProvince.getValue()==null){
            new Alert(Alert.AlertType.WARNING, "All Fields Are Required..").show();
        }else {
            if (customerBO.ifCustomerExists(txtCustomerId.getText())) {
                new Alert(Alert.AlertType.WARNING, "Already Exists..").show();
            }

            CustomerDTO c1 = new CustomerDTO(
                    txtCustomerId.getText(),
                    txtCustomerTitle.getText(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    txtCity.getText(),
                    cmbProvince.getValue(),
                    txtPostalCode.getText()
            );

            if (customerBO.saveCustomer(c1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").showAndWait();
                txtCustomerId.clear();
                txtCustomerTitle.clear();
                txtCustomerName.clear();
                txtCustomerAddress.clear();
                txtCity.clear();
                cmbProvince.getSelectionModel().clearSelection();
                txtPostalCode.clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }
    }

}
