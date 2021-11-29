package controller;

import BO.BOFactory;
import BO.Custom.ItemBO;
import BO.Custom.PlaceOrderBO;
import BO.Custom.SearchOrderBO;
import ValidationUtil.Validation;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import DTO.*;
import view.tm.OrderDetailsTM;
import view.tm.TableTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SearchOrderFormController {

    public TableView<OrderDetailsTM> tblOrderDetails;
    public TableColumn colCode;
    public TableColumn colPrice;
    public TableColumn colQTY;
    public TableColumn colAmount;
    public TableColumn colOId;
    public TableColumn colCId;
    public TableColumn colOTime;
    public TableColumn colODate;
    public TableView<TableTM> tblItemDetails;
    public TableColumn colUpdate;
    public TableColumn colDelete;
    public TableColumn colCost;
    public TextField txtSearch;
    public TableColumn colDeleteOrder;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtqtyOnHand;
    public TextField txtQTY;
    public ComboBox<String > cmbItemCode;
    public JFXButton btnAdd;

    int index = -1;

    ObservableList<TableTM> observableList = null;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern compile_Qty = Pattern.compile("^[0-9]{1,10}$");

    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    private PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);
    private SearchOrderBO searchOrderBO = (SearchOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SEARCHORDER);

    public void initialize() throws SQLException, ClassNotFoundException {
        btnAdd.setDisable(true);

        colOId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colODate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDeleteOrder.setCellValueFactory(new PropertyValueFactory<>("delete"));

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("UPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("sellQty"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        setOrderDetailsToTable();

        setValidation();

        loadItemIds();

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                try {

                    observableList = loadDataToItemTable(newValue.getOrderId());

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        tblItemDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                index = (int) newValue;
        });

        tblItemDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){

                cmbItemCode.setValue(newValue.getItemCode());
                txtQTY.setText(String.valueOf(newValue.getSellQty()));

                ItemDTO item = null;
                try {
                    item = itemBO.searchItem(cmbItemCode.getValue());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                txtDescription.setText(item.getDescription());
                txtDescription.setDisable(true);
                txtUnitPrice.setText(String.valueOf(newValue.getUPrice()));
                txtUnitPrice.setDisable(true);
                txtqtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
                txtqtyOnHand.setDisable(true);
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                try {

                    setItemData(newValue);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {

                    search(newValue);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setValidation() {
        map.put(txtQTY,compile_Qty);
    }

    public void qty_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<DetailsDTO> search = searchOrderBO.searchData(value);
        ObservableList<OrderDetailsTM> tableData = FXCollections.observableArrayList();
        for (DetailsDTO details : search) {
            setDBtn();
            tableData.add(new OrderDetailsTM(
                    details.getOrderId(),
                    details.getCustomerId(),
                    details.getOrderDate(),
                    details.getOrderTime(),
                    details.getCost(),
                    buttonDelete
            ));
            deleteOrder(details.getOrderId());
        }
        tblOrderDetails.setItems(tableData);
    }

    public void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = searchOrderBO.getItemIds();
        cmbItemCode.getItems().addAll(itemIds);
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = searchOrderBO.getItem(itemCode);
        if (i1 == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set.");
        }else {
            txtDescription.setText(i1.getDescription());
            txtDescription.setDisable(true);
            txtqtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtqtyOnHand.setDisable(true);
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtUnitPrice.setDisable(true);
        }
    }

    private void setOrderDetailsToTable() throws SQLException, ClassNotFoundException {
        ArrayList<DetailsDTO> items = searchOrderBO.getOrderDetailsToSearch();
        ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
        items.forEach(e->{
            setDBtn();
            obList.add(new OrderDetailsTM(e.getOrderId(),e.getCustomerId(),e.getOrderDate(),e.getOrderTime(),e.getCost(),buttonDelete));

            try {

                deleteOrder(e.getOrderId());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        tblOrderDetails.setItems(obList);
    }

    JFXButton buttonDelete;
    public void setDBtn(){
        buttonDelete = new JFXButton();
        buttonDelete.setStyle("-fx-border-color: red");
        Image img = new Image("assets\\icons8-delete-64.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(27);
        view.setFitWidth(36);
        buttonDelete.setGraphic(view);
    }

    JFXButton buttonUpdate;
    public void setUBtn(){
        buttonUpdate = new JFXButton();
        buttonUpdate.setStyle("-fx-border-color: blueviolet");
        Image img = new Image("assets\\icons8-edit-48.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(27);
        view.setFitWidth(36);
        buttonUpdate.setGraphic(view);
    }

    public void deleteOrder(String oid) throws SQLException, ClassNotFoundException {

        ArrayList<OrderDetailDTO> items = searchOrderBO.searchOrderDetails(oid);

        buttonDelete.setOnAction((event) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Order?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (placeOrderBO.deleteOrder(oid,items)) {
                        new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again.").showAndWait();
                    }

                    setOrderDetailsToTable();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                tblItemDetails.getItems().clear();

            }else{
            }
        });
    }

    public void setOnActionForDelete(String itemCode, String oid) throws SQLException, ClassNotFoundException {

        ArrayList<OrderDetailDTO> items = searchOrderBO.searchOrderDetails(oid);

        buttonDelete.setOnAction((event) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Item Details?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (placeOrderBO.deleteOrderDetail(oid,itemCode,items)) {
                        new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again.").showAndWait();
                    }

                    loadDataToItemTable(oid);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }else{
            }
        });
    }

    public void updateOnAction(){
        buttonUpdate.setOnAction((event) -> {

            if (index == -1) {
                new Alert(Alert.AlertType.WARNING, "Please Select A Raw.").show();
            } else {

            }
        });
    }

    private ObservableList<TableTM> loadDataToItemTable(String oid) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> data = searchOrderBO.searchOrderDetails(oid);
        ObservableList<TableTM> obList = FXCollections.observableArrayList();
        data.forEach(e->{
            setDBtn();
            setUBtn();
            obList.add(new TableTM(e.getItemCode(),e.getSellQty(),e.getPrice(),e.getAmount(),buttonUpdate,buttonDelete));

            try {
                setOnActionForDelete(e.getItemCode(),oid);

                updateOnAction();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

        });
        tblItemDetails.setItems(obList);
        return obList;
    }

    private int isExists(TableTM tm){
        for (int i = 0; i < observableList.size(); i++) {
            //if selected item code in row equals to next item code,return relevant index
            if (tm.getItemCode().equals(observableList.get(i).getItemCode())){
                return i;
            }
        }//if else return -1
        return -1;
    }

    void calculateCost(){
        double cost = 0;

        for (TableTM tm : observableList) {
            cost+=tm.getAmount();
        }
    }

    JFXButton Delete;
    public void setDelete(){
        Delete = new JFXButton();
        Delete.setStyle("-fx-border-color: red");
        Image img = new Image("assets\\icons8-delete-64.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(27);
        view.setFitWidth(36);
        Delete.setGraphic(view);
    }

    public void clearOnAction(TableTM tm) {
        Delete.setOnAction(e -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Item Details?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                observableList.remove(tm);
                calculateCost();
                tblItemDetails.refresh();

            }else{
            }

        });
    }

    public void addToTableOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        ItemDTO item = itemBO.searchItem(cmbItemCode.getValue());

        int qtyOnHand = Integer.parseInt(txtqtyOnHand.getText());
        double unitPrice = item.getUnitPrice();
        double discount = item.getDiscount();
        int qtyForCustomer =  Integer.parseInt(txtQTY.getText());
        double save = (unitPrice / 100 * discount);
        double total = qtyForCustomer * (unitPrice - save);//total of taken amount

        if(qtyOnHand < qtyForCustomer || qtyForCustomer < 0){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY.").show();
            return;
        }

        setDelete();
        setUBtn();

        TableTM tm = new TableTM(
                cmbItemCode.getValue(),
                qtyForCustomer,
                unitPrice,
                total,
                buttonUpdate,
                Delete
        );

        int rowNumber = isExists(tm);

        if(rowNumber == -1){//-1 means , no one
            observableList.add(tm);

            clearOnAction(tm);
            updateOnAction();

        }else {
            //update
            TableTM temp = observableList.get(rowNumber);

            TableTM newTm = new TableTM(
                    temp.getItemCode(),
                    qtyForCustomer,
                    unitPrice,
                    total,
                    buttonUpdate,
                    buttonDelete
            );

            if ((qtyOnHand - qtyForCustomer) < temp.getSellQty()){
                new Alert(Alert.AlertType.WARNING,"Invalid QTY.").show();
                return;
            }

            observableList.remove(rowNumber);
            observableList.add(newTm);
        }

        tblItemDetails.setItems(observableList);
        calculateCost();
    }

    public void updateOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (cmbItemCode.getValue()==null || txtQTY.getText().isEmpty() || tblItemDetails.getItems().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"All Fields Are Required.").show();
        }else {

            ArrayList<OrderDetailDTO> items = new ArrayList<>();//create arraylist to put items

            OrderDetailsTM selectedItem = tblOrderDetails.getSelectionModel().getSelectedItem();

            double ttotal = 0;

            for (TableTM tempTm : observableList) {
                ttotal += tempTm.getAmount();

                //add all table details to arraylist
                items.add(new OrderDetailDTO(
                        tempTm.getItemCode(),
                        selectedItem.getOrderId(),
                        tempTm.getSellQty(),
                        tempTm.getUPrice(),
                        tempTm.getAmount()
                ));
            }

            OrderDTO order = new OrderDTO(
                    selectedItem.getOrderId(),
                    selectedItem.getCustomerId(),
                    selectedItem.getOrderDate(),
                    selectedItem.getOrderTime(),
                    ttotal,
                    items
            );

            if (placeOrderBO.updateOrder(order)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success.").showAndWait();
                setOrderDetailsToTable();
                tblItemDetails.getItems().clear();
                cmbItemCode.getSelectionModel().clearSelection();
                txtDescription.clear();
                txtqtyOnHand.clear();
                txtUnitPrice.clear();
                txtQTY.clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }
    }

}
