package controller;

import BO.BOFactory;
import BO.Custom.SearchOrderBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import DTO.OrderDetailDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.BillTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class FinalBillFormController {

    public Label lblPayment;
    public AnchorPane billContext;
    public Label lblBillDate;
    public Label lblBillTime;
    public Label lblBillId;
    public TableView<BillTM> tblBillDetails;
    public TableColumn colItemCode;
    public TableColumn colUnitPrice;
    public TableColumn colQTY;
    public TableColumn colAmount;
    public Label lblNetAmount;
    public Label lblGrossAmount;

    private SearchOrderBO searchOrderBO = (SearchOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SEARCHORDER);

    public void initialize(){
        //to display data in table
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("sellQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    public void setData(String oId, String date, String time, double total,String payment,double grossAmount) throws SQLException, ClassNotFoundException {
        lblBillId.setText(oId);
        lblBillDate.setText(date);
        lblBillTime.setText(time);
        lblNetAmount.setText(String.valueOf(total));
        lblPayment.setText(payment);
        setData(oId);
        lblGrossAmount.setText(String.valueOf(grossAmount));
    }

    private void setData(String oId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> data = searchOrderBO.searchOrderDetails(oId);
        ObservableList<BillTM> obList = FXCollections.observableArrayList();
        data.forEach(e->{
            obList.add(new BillTM(e.getItemCode(),e.getOrderId(),e.getSellQty(),e.getPrice(),e.getAmount()));
        });
        tblBillDetails.setItems(obList);
    }

    public void openPlaceOrderForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"));
        Stage window = (Stage) billContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void printOnAction(ActionEvent event) {
        JasperDesign design = null;
        try {
            design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Report/Bill.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            String orderNo = lblBillId.getText();
            String date = lblBillDate.getText();
            String time = lblBillTime.getText();
            double grossAmount = Double.parseDouble(lblGrossAmount.getText());
            double netAmount = Double.parseDouble(lblNetAmount.getText());
            String payment = lblPayment.getText();

            HashMap map = new HashMap();
            map.put("orderNo",orderNo);
            map.put("orderDate",date);
            map.put("orderTime",time);
            map.put("grossAmount",grossAmount);
            map.put("netAmount",netAmount);
            map.put("payment",payment);

            ObservableList<BillTM> items = tblBillDetails.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map, new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
