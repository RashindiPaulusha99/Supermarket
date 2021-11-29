package controller;

import BO.BOFactory;
import BO.Custom.SearchOrderBO;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import DTO.DetailsDTO;
import view.tm.DetailsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AnnuallyReportsFormController implements Initializable {

    public AnchorPane reportsContext;
    public TableView<DetailsTM> tblIncomeDetails;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colOrderTime;
    public TableColumn colCost;
    public Label lblTotal;
    public JFXDatePicker startDatePicker;
    public JFXDatePicker endDatePicker;

    LocalDate endDate = null;
    LocalDate startDate=null;

    private SearchOrderBO searchOrderBO = (SearchOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SEARCHORDER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        startDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startDate= startDatePicker.getValue();
            }
        });

        endDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endDate = endDatePicker.getValue();

                try {

                    loadDataToTable(startDate,endDate);

                    setCost(startDate,endDate);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setCost(LocalDate startDay, LocalDate endDay) throws SQLException, ClassNotFoundException {
        double cost = searchOrderBO.findCostForMoAn(startDay,endDay);
        lblTotal.setText(cost+" /=");
    }

    public void loadDataToTable(LocalDate startDay, LocalDate endDay) throws SQLException, ClassNotFoundException {
        ArrayList<DetailsDTO> items = searchOrderBO.setMonthlyAnnuallyData(startDay,endDay);
        ObservableList<DetailsTM> obList = FXCollections.observableArrayList();
        items.forEach(e->{
            obList.add(new DetailsTM(e.getOrderId(),e.getCustomerId(),e.getOrderDate(),e.getOrderTime(),e.getCost()));
        });
        tblIncomeDetails.setItems(obList);
    }

    public void dailyIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }

    public void customerWiseIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/CustomerWiseReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }

    public void monthlyIncomeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MonthlyReportsForm.fxml"));
        AnchorPane pane = loader.load();
        reportsContext.getChildren().setAll(pane);
    }
}
