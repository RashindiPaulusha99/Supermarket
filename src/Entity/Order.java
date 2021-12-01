package Entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Order {
    @Id
    private String orderId;
    private String cId;
    
    @ManyToOne
    private Customer customer;

    private LocalDate orderDate;
    private String ordertime;
    private double cost;

    public Order() {
    }

    public Order(String orderId, String cId, Customer customer, LocalDate orderDate, String ordertime, double cost) {
        this.orderId = orderId;
        this.cId = cId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.ordertime = ordertime;
        this.cost = cost;
    }

    public Order(String orderId, Customer customer, LocalDate orderDate, String ordertime, double cost) {
        this.setOrderId(orderId);
        this.setCustomer(customer);
        this.setOrderDate(orderDate);
        this.setOrdertime(ordertime);
        this.setCost(cost);
    }

    public Order(String string, String string1, LocalDate parse, String string2, double aDouble) {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                "cId='" + cId + '\'' +
                ", customer='" + customer + '\'' +
                ", orderDate=" + orderDate +
                ", ordertime='" + ordertime + '\'' +
                ", cost=" + cost +
                '}';
    }
}
