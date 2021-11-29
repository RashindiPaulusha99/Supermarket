package controller;

import BO.BOFactory;
import BO.Custom.SearchOrderBO;
import SendData.loadStock;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import DTO.DetailsDTO;
import DTO.ItemDTO;
import view.tm.DetailsTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class ManageItemFormController implements loadStock {

    public AnchorPane manageItemContext;
    public TextField txtMostItem;
    public TextField txtLessItem;
    public Label lblTotal;
    public TableView tblTodayIncome;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colOrderTime;
    public TableColumn colCost;
    public Label lblRole;
    public Label lblUsername;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnSearch;
    public JFXButton btnDelete;
    public JFXButton btnView;
    public JFXButton btnSearchReports;
    public Label lblLocalDate;
    public Label lblTime;
    public Label stockFV;
    public Label stockRice;
    public Label stockBevarages;
    public Label stockSnacks;
    public Label stockSpices;
    public Label stockOil;
    public Label stockFlour;
    public Label stockBackery;
    public Label stockSweet;
    public Label stockBaby;
    public Label stockCosmatic;
    public Label stockOffice;
    public Label stockDiary;
    public Label stockDry;
    public Label stockMedical;
    public Label stockElectronic;
    public Label stockHousehold;
    public Label lblFV;
    public Label lblRice;
    public Label lblBeverages;
    public Label lblSnacks;
    public Label lblSpices;
    public Label lblOil;
    public Label lblFlours;
    public Label lblBakery;
    public Label lblSweet;
    public Label lblBaby;
    public Label lblCosmetic;
    public Label lblOffice;
    public Label lblDiary;
    public Label lblDry;
    public Label lblMedical;
    public Label lblElectronic;
    public Label lblHouseHold;
    public Label lblMostMovableItem;
    public Label lblLeastMovableItem;
    public JFXButton btnSearchOrder;

    private SearchOrderBO searchOrderBO = (SearchOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SEARCHORDER);

    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        loadDateAndTime();

        try {

          setData();
          setCost();

          movableItem();

          displayStockQty();

      } catch (SQLException throwables) {
          throwables.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }

    }

    private void loadDateAndTime() {
        /*load date and time*/
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblLocalDate.setText(f.format(date));

        /*load time*/
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setCost() throws SQLException, ClassNotFoundException {
        double cost = searchOrderBO.findCost(lblLocalDate.getText());
        lblTotal.setText(cost+" /=");
    }

    private void setData() throws SQLException, ClassNotFoundException {
        ArrayList<DetailsDTO> items = searchOrderBO.setTodayData(lblLocalDate.getText());
        ObservableList<DetailsTM> obList = FXCollections.observableArrayList();
        items.forEach(e->{
            obList.add(new DetailsTM(e.getOrderId(),e.getCustomerId(),e.getOrderDate(),e.getOrderTime(),e.getCost()));
        });
        tblTodayIncome.setItems(obList);
    }

    public void setDetails(String role , String username){
        lblRole.setText(role);
        lblUsername.setText(username);
    }

    public void stock(String code,Label stock, Label Qty) throws SQLException, ClassNotFoundException {
        int qty = searchOrderBO.findQty(code);
        if (qty != 0) {
            stock.setText("Stock In");
            Qty.setText(String.valueOf(qty));
        } else {
            stock.setText("Stock Out");
            Qty.setText("0");
        }
    }

    public void displayStockQty() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> items = searchOrderBO.getAllItems();
        for (int i = 0; i < items.size(); i++) {

            if (items.get(i).getDescription().equals("Fruits & Vegetables")) {
                stock(items.get(i).getCode(),stockFV,lblFV);

            }else if (items.get(i).getDescription().equals("Rice &Other Grains")){
                stock(items.get(i).getCode(),stockRice,lblRice);

            }else if (items.get(i).getDescription().equals("Spices")){
                stock(items.get(i).getCode(),stockSpices,lblSpices);

            }else if (items.get(i).getDescription().equals("All Flours")){
                stock(items.get(i).getCode(),stockFlour,lblFlours);

            }else if (items.get(i).getDescription().equals("Beverages")){
                stock(items.get(i).getCode(),stockBevarages,lblBeverages);

            }else if (items.get(i).getDescription().equals("Snacks & Branded foods")){
                stock(items.get(i).getCode(),stockSnacks,lblSnacks);

            }else if (items.get(i).getDescription().equals("Edible Oils")){
                stock(items.get(i).getCode(),stockOil,lblOil);

            }else if (items.get(i).getDescription().equals("Bakery items")){
                stock(items.get(i).getCode(),stockBackery,lblBakery);

            }else if (items.get(i).getDescription().equals("Sweet & Desserts")){
                stock(items.get(i).getCode(),stockSweet,lblSweet);

            }else if (items.get(i).getDescription().equals("Baby care")){
                stock(items.get(i).getCode(),stockBaby,lblBaby);

            }else if (items.get(i).getDescription().equals("Personal care & Cosmetics")){
                stock(items.get(i).getCode(),stockCosmatic,lblCosmetic);

            }else if (items.get(i).getDescription().equals("Office & Stationary")){
                stock(items.get(i).getCode(),stockOffice,lblOffice);

            }else if (items.get(i).getDescription().equals("Diary Products")){
                stock(items.get(i).getCode(),stockDiary,lblDiary);

            }else if (items.get(i).getDescription().equals("Dry Fruits")){
                stock(items.get(i).getCode(),stockDry,lblDry);

            }else if (items.get(i).getDescription().equals("Medical Health")){
                stock(items.get(i).getCode(),stockMedical,lblMedical);

            }else if (items.get(i).getDescription().equals("Electrical & Electronics products")){
                stock(items.get(i).getCode(),stockElectronic,lblElectronic);

            }else if (items.get(i).getDescription().equals("Household needs")){
                stock(items.get(i).getCode(),stockHousehold,lblHouseHold);
            }
        }
    }

    public void movableItem() throws SQLException, ClassNotFoundException {
        String mostMovableItems = searchOrderBO.mostMovableItems();
        txtMostItem.setText(mostMovableItems);
        String description1 = searchOrderBO.findDescription(mostMovableItems);
        lblMostMovableItem.setText(description1);

        String leastMovableItems = searchOrderBO.leastMovableItems();
        txtLessItem.setText(leastMovableItems);
        String description2 = searchOrderBO.findDescription(leastMovableItems);
        lblLeastMovableItem.setText(description2);
    }

    public void openSaveItemFormOnAction(ActionEvent actionEvent) throws IOException {
        btnSave.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnSearchReports.setStyle("");
        btnSearchOrder.setStyle("");

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/SaveItemForm.fxml"));
        Parent load = loader.load();
        SaveItemFormController controller = loader.<SaveItemFormController>getController();
        controller.setData(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void openSearchItemFormOnAction(ActionEvent actionEvent) throws IOException {
        btnSearch.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSave.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnSearchReports.setStyle("");
        btnSearchOrder.setStyle("");
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/SearchItemForm.fxml"))));
        stage.show();
    }

    public void openUpdateItemFormOnAction(ActionEvent actionEvent) throws IOException {
        btnUpdate.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnSave.setStyle("");
        btnView.setStyle("");
        btnSearchReports.setStyle("");
        btnSearchOrder.setStyle("");

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdateItemForm.fxml"));
        Parent load = loader.load();
        UpdateItemFormController controller = loader.<UpdateItemFormController>getController();
        controller.setData(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void openDeleteItemFormOnAction(ActionEvent actionEvent) throws IOException {
        btnDelete.setStyle("-fx-background-color: lightcoral");
        btnSave.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnSearchReports.setStyle("");
        btnSearchOrder.setStyle("");

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DeleteItemForm.fxml"));
        Parent load = loader.load();
        DeleteItemFormController controller = loader.<DeleteItemFormController>getController();
        controller.setData(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void openDisplayAllItemFormOnAction(ActionEvent actionEvent) throws IOException {
        btnView.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnSave.setStyle("");
        btnSearchReports.setStyle("");
        btnSearchOrder.setStyle("");
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DisplayAllItemForm.fxml"))));
        stage.show();
    }

    public void openReportsFormOnAction(ActionEvent actionEvent) throws IOException {
        btnSearchReports.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnSave.setStyle("");
        btnSearchOrder.setStyle("");
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ReportsForm.fxml"))));
        stage.show();
    }

    public void openSearchOrderForm(ActionEvent event) throws IOException {
        btnSearchOrder.setStyle("-fx-background-color: lightcoral");
        btnSearchReports.setStyle("");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnSave.setStyle("");
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/SearchOrderForm.fxml"))));
        stage.show();
    }

    public void homePageOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        Stage window = (Stage) manageItemContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    @Override
    public void loadData() throws SQLException, ClassNotFoundException {
        displayStockQty();
    }
}
