package controller;

import BO.BOFactory;
import BO.Custom.CustomerBO;
import ValidationUtil.Validation;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import DTO.CustomerDTO;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateCustomerFormController {
    public TextField txtId;
    public TextField txtTitle;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtPostalCode;
    public TextField txtCity;
    public ComboBox<String> cmbProvince;
    public JFXButton btnEdit;

    LinkedHashMap<TextField, Pattern> mapName = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapTitle = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapAddress = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapCity = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapPostalCode = new LinkedHashMap<>();

    Pattern compile_CustomerName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_CustomerTitle = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_CustomerAddress = Pattern.compile("^[A-z .,/0-9]{4,50}$");
    Pattern compile_CustomerCity = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_CustomerPostalCode = Pattern.compile("^[0-9]{1,6}$");

    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        btnEdit.setDisable(true);

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
        mapName.put(txtName,compile_CustomerName);
        mapTitle.put(txtTitle,compile_CustomerTitle);
        mapAddress.put(txtAddress,compile_CustomerAddress);
        mapCity.put(txtCity,compile_CustomerCity);
        mapPostalCode.put(txtPostalCode,compile_CustomerPostalCode);
    }

    public void customerName_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapName, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void customerTitle_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapTitle, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void customerAddress_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapAddress, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void city_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapCity, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void postalCode_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapPostalCode, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void selectCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //search relevant customer
        CustomerDTO c1 = customerBO.searchCustomer(txtId.getText());
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set.").show();
        } else {
            //if passed data didn't null pass data to setData method
            setData(c1);
        }
    }

    //set customer's details to textfields
    void setData(CustomerDTO c) {
        txtId.setText(c.getId());
        txtTitle.setText(c.getTitle());
        txtName.setText(c.getName());
        txtAddress.setText(c.getAddress());
        txtCity.setText(c.getCity());
        cmbProvince.setValue(c.getProvince());
        txtPostalCode.setText(c.getPostalCode());
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = new CustomerDTO(
                txtId.getText(),
                txtTitle.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                cmbProvince.getValue(),
                txtPostalCode.getText()
        );

        //data of textFields pass to updateCustomer method to update and show alert if it updated or not
        if (customerBO.updateCustomer(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").showAndWait();
            txtId.clear();
            txtTitle.clear();
            cmbProvince.getSelectionModel().clearSelection();
            txtName.clear();
            txtAddress.clear();
            txtCity.clear();
            txtPostalCode.clear();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again.").show();
        }
    }

}
