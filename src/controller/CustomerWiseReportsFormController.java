package controller;

import BO.BOFactory;
import BO.Custom.PlaceOrderBO;
import BO.Custom.SearchOrderBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import DTO.OrderDetailDTO;
import DTO.DetailsDTO;
import view.tm.BillTM;
import view.tm.DetailsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerWiseReportsFormController implements Initializable {

    public AnchorPane reportsContext;
    public TableView<DetailsTM> tblIncomeDetails;
    public TableColumn colOrderId;
    public TableColumn colOrderDate;
    public TableColumn colOrderTime;
    public TableColumn colCost;
    public Label lblTotal;
    public TableView<BillTM> tblItemDetails;
    public TableColumn colItemCode;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public ComboBox<String> cmbCustomerId;
    public TableColumn colAmount;

    private SearchOrderBO searchOrderBO = (SearchOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SEARCHORDER);
    private PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("sellQty"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {

            loadCustomerIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {

                loadDataToTable(newValue);
                setCost(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblIncomeDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

                try {
                    loadDataToItemTable(newValue.getOrderId());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

        });
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = placeOrderBO.getCustomerIds();
        cmbCustomerId.getItems().addAll(customerIds);
    }

    private void loadDataToTable(String id) throws SQLException, ClassNotFoundException {
        ArrayList<DetailsDTO> items = searchOrderBO.searchOrder(id);
        ObservableList<DetailsTM> obList = FXCollections.observableArrayList();
        items.forEach(e->{
            obList.add(new DetailsTM(e.getOrderId(),e.getCustomerId(),e.getOrderDate(),e.getOrderTime(),e.getCost()));
        });
        tblIncomeDetails.setItems(obList);
    }

    private void loadDataToItemTable(String id) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> data = searchOrderBO.searchOrderDetails(id);
        ObservableList<BillTM> obList = FXCollections.observableArrayList();
        data.forEach(e->{
            obList.add(new BillTM(e.getItemCode(),e.getOrderId(),e.getSellQty(),e.getPrice(),e.getAmount()));
        });
        tblItemDetails.setItems(obList);
    }

    private void setCost(String id) throws SQLException, ClassNotFoundException {
        double cost = searchOrderBO.findCostForCustomer(id);
        lblTotal.setText(cost+" /=");
    }

    public void dailyIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }

    public void MonthlyIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MonthlyReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }

    public void annuallyIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AnnuallyReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }

}
