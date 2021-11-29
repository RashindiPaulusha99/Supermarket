package controller;

import BO.BOFactory;
import BO.Custom.CustomerBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import DTO.CustomerDTO;
import view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class DisplayAllCustomerFormController {

    public TableView tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerTitle;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;

    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        try {
            //to display data in table
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCustomerTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
            colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

            //call setCustomersToTable() method passing data
            setCustomersToTable(customerBO.getAllCustomer());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //set data to table
    private void setCustomersToTable(ArrayList<CustomerDTO> customers) {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        customers.forEach(e->{
            obList.add(new CustomerTM(e.getId(),e.getTitle(),e.getName(),e.getAddress(),e.getCity(),e.getProvince(),e.getPostalCode()));
        });
        tblCustomer.setItems(obList);
    }
}
