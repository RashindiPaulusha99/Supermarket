package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import DTO.OrderDTO;

import java.io.IOException;
import java.sql.SQLException;

public class PaymentOptionFormController {

    public AnchorPane paymentContext;
    public Label lblOrderId;
    public Label lblCustomerId;
    public Label lblOrderDate;
    public Label lblOrderTime;
    public Label lblTotal;
    public Label lblFinalTotal;

    double amount = 0;

    public void setData(OrderDTO order, double grossAmount) {
        lblOrderId.setText(order.getOrderId());
        lblCustomerId.setText(order.getCustomerId());
        lblOrderDate.setText(order.getOrderDate());
        lblOrderTime.setText(order.getOrderTime());
        lblTotal.setText(String.valueOf(order.getCost()));
        lblFinalTotal.setText(String.valueOf(order.getCost()));
        amount = grossAmount;
    }

    String payment = null;

    public void cashPaymentOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION,"Add Cash Payment.").showAndWait();
        payment = "Cash";
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (payment==null) {
            new Alert(Alert.AlertType.WARNING,"Select Payment Option.").show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Payment Proceeded.").showAndWait();
        }
    }

}
