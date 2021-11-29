package controller;

import BO.BOFactory;
import BO.Custom.SearchOrderBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import DTO.DetailsDTO;
import view.tm.DetailsTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportsFormController {

    public Label lblTotal;
    public TableView<DetailsTM> tblIncomeDetails;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colOrderTime;
    public TableColumn colCost;
    public Label lblDate;
    public AnchorPane reportsContext;

    private SearchOrderBO searchOrderBO = (SearchOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SEARCHORDER);

    public void initialize()  {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        loadDate();

        try {

            setCost(lblDate.getText());
            setData(lblDate.getText());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDate() {
        /*load date*/
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    private void setCost(String  date) throws SQLException, ClassNotFoundException {
        double cost = searchOrderBO.findCost(String.valueOf(date));
        lblTotal.setText(cost+" /=");
    }

    private void setData(String date) throws SQLException, ClassNotFoundException {
       ArrayList<DetailsDTO> items = searchOrderBO.setTodayData(String.valueOf(date));
        ObservableList<DetailsTM> obList = FXCollections.observableArrayList();
        items.forEach(e->{
            obList.add(new DetailsTM(e.getOrderId(),e.getCustomerId(),e.getOrderDate(),e.getOrderTime(),e.getCost()));
        });
        tblIncomeDetails.setItems(obList);
    }

    public void MonthlyIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MonthlyReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }

    public void AnnuallyIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AnnuallyReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }

    public void CustomerWiseIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/CustomerWiseReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }
}
