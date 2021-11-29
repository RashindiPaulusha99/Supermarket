package controller;

import BO.BOFactory;
import BO.Custom.ItemBO;
import SendData.loadStock;
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
import DTO.ItemDTO;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateItemFormController {
    public TextField txtCode;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtqtyOnHand;
    public TextField txtDiscount;
    public ComboBox<String> cmbDescription;
    public JFXButton btnEdit;

    LinkedHashMap<TextField, Pattern> mapPackSize = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapUnitPrice = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapDiscount = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapQty = new LinkedHashMap<>();

    Pattern compile_PackSize = Pattern.compile("^[0-9]{1,10}$");
    Pattern compile_UnitPrice = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern compile_Discount = Pattern.compile("^[0-9]{1,3}$");
    Pattern compile_Qty = Pattern.compile("^[0-9]{1,10}$");

    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private loadStock send;

    public void initialize(){
        btnEdit.setDisable(true);

        ObservableList<String> obList = FXCollections.observableArrayList(
                "Fruits & Vegetables",
                "Rice &Other Grains",
                "Spices",
                "All Flours",
                "Dry Fruits",
                "Edible Oils",
                "Beverages",
                "Snacks & Branded foods",
                "Bakery items",
                "Sweet & Desserts",
                "Personal care & Cosmetics",
                "Baby care",
                "Diary Products",
                "Medical Health",
                "Household needs",
                "Office & Stationary",
                "Electrical & Electronics products"
        );
        cmbDescription.setItems(obList);

        setValidation();
    }

    private void setValidation() {
        mapPackSize.put(txtPackSize,compile_PackSize);
        mapUnitPrice.put(txtUnitPrice,compile_UnitPrice);
        mapDiscount.put(txtDiscount,compile_Discount);
        mapQty.put(txtqtyOnHand,compile_Qty);
    }

    public void packSize_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapPackSize, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void unitPrice_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapUnitPrice, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void discount_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapDiscount, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void qty_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(mapQty, btnEdit);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void selectItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //search relevant item
        ItemDTO i1 = itemBO.searchItem(txtCode.getText());
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set.").showAndWait();
        } else {
            //if passed data didn't null ,pass data to setData method
            setData(i1);
        }
    }

    //set item's details to textfields
    void setData(ItemDTO i) {
        txtCode.setText(i.getCode());
        cmbDescription.setValue(i.getDescription());
        txtPackSize.setText(i.getPackSize());
        txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
        txtDiscount.setText(String.valueOf(i.getDiscount()));
        txtqtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = new ItemDTO(
                txtCode.getText(),
                cmbDescription.getValue(),
                Integer.parseInt(txtqtyOnHand.getText()),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Double.parseDouble(txtDiscount.getText())
        );

        //data of textFields pass to updateItem method to update and show alert if it updated or not
        if (itemBO.updateItem(i1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").showAndWait();
            txtCode.clear();
            cmbDescription.getSelectionModel().clearSelection();
            txtPackSize.clear();
            txtUnitPrice.clear();
            txtDiscount.clear();
            txtqtyOnHand.clear();
            send.loadData();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again.").show();
        }
    }

    public void setData(loadStock send){
        this.send = send;
    }

}
