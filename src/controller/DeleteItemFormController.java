package controller;

import BO.BOFactory;
import BO.Custom.ItemBO;
import SendData.loadStock;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import DTO.ItemDTO;

import java.sql.SQLException;

public class DeleteItemFormController {

    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtqtyOnHand;
    public TextField txtDiscount;

    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private loadStock send;

    public void selectItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        //search relevant item
        ItemDTO i1 = itemBO.searchItem(txtCode.getText());
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set.").show();
        } else {
            //if passed data didn't null ,pass data to setData method
            setData(i1);
        }
    }

    //set item's details to textfields
    void setData(ItemDTO i) {
        txtCode.setText(i.getCode());
        txtDescription.setText(i.getDescription());
        txtPackSize.setText(i.getPackSize());
        txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
        txtDiscount.setText(String.valueOf(i.getDiscount()));
        txtqtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //delete item
        if (itemBO.deleteItem(txtCode.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted.").showAndWait();
            txtCode.clear();
            txtDescription.clear();
            txtPackSize.clear();
            txtUnitPrice.clear();
            txtqtyOnHand.clear();
            txtDiscount.clear();
            send.loadData();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again.").show();
        }
    }

    public void setData(loadStock send){
        this.send = send;
    }
}
