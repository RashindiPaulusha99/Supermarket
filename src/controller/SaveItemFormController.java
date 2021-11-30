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

public class SaveItemFormController{

    public TextField txtItemCode;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtqtyOnHand;
    public TextField txtDiscount;
    public ComboBox<String> cmbDescription;
    public JFXButton btnSave;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern compile_ItemCode = Pattern.compile("^[C-c]{1}(0){2}[-][0-9]{4}$");
    Pattern compile_PackSize = Pattern.compile("^[0-9]{1,10}$");
    Pattern compile_UnitPrice = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern compile_Discount = Pattern.compile("^[0-9]{1,3}$");
    Pattern compile_Qty = Pattern.compile("^[0-9]{1,10}$");

    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private loadStock send;

    public void initialize(){
        btnSave.setDisable(true);

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
        map.put(txtItemCode,compile_ItemCode);
        map.put(txtPackSize,compile_PackSize);
        map.put(txtUnitPrice,compile_UnitPrice);
        map.put(txtDiscount,compile_Discount);
        map.put(txtqtyOnHand,compile_Qty);
    }

    public void item_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    //save new Item
    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (txtItemCode.getText().isEmpty() || txtUnitPrice.getText().isEmpty() || txtDiscount.getText().isEmpty() || txtPackSize.getText().isEmpty() || txtqtyOnHand.getText().isEmpty() || cmbDescription.getValue()==null){
            new Alert(Alert.AlertType.WARNING, "All Fields Are Required..").show();
        }else {
            if (itemBO.searchExistsItem(txtItemCode.getText())) {
                new Alert(Alert.AlertType.WARNING, "Already Exists..").show();
            }else {
                ItemDTO item = new ItemDTO(
                        txtItemCode.getText(),
                        cmbDescription.getValue(),
                        Integer.parseInt(txtqtyOnHand.getText()),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Double.parseDouble(txtDiscount.getText())
                );

                if (itemBO.saveItem(item)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved..").showAndWait();
                    txtItemCode.clear();
                    cmbDescription.getSelectionModel().clearSelection();
                    txtPackSize.clear();
                    txtUnitPrice.clear();
                    txtqtyOnHand.clear();
                    txtDiscount.clear();
                    send.loadData();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            }
        }
    }

    public void setData(loadStock send){
        this.send = send;
    }

}
