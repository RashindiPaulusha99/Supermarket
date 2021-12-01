package BO.Custom;

import BO.SuperBO;
import DTO.DetailsDTO;
import DTO.ItemDTO;
import DTO.OrderDetailDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface SearchOrderBO extends SuperBO {

    ArrayList<OrderDetailDTO> searchOrderDetails(String oid) throws SQLException, ClassNotFoundException;

    double findCost(String date) throws SQLException, ClassNotFoundException;

    ArrayList<DetailsDTO> setTodayData(String date) throws SQLException, ClassNotFoundException;

    int findQty(String code) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    String findDescription(String code) throws SQLException, ClassNotFoundException;

    String mostMovableItems() throws SQLException, ClassNotFoundException;

    String leastMovableItems() throws SQLException, ClassNotFoundException;

    ItemDTO getItem(String code) throws SQLException, ClassNotFoundException;

    List<String> getItemIds() throws SQLException, ClassNotFoundException;

    ArrayList<DetailsDTO> searchData(String value) throws SQLException, ClassNotFoundException;
    
    ArrayList<DetailsDTO> getOrderDetailsToSearch() throws SQLException, ClassNotFoundException;

    ArrayList<DetailsDTO> searchOrder(String id) throws SQLException, ClassNotFoundException;

    double findCostForCustomer(String id) throws SQLException, ClassNotFoundException;
}
