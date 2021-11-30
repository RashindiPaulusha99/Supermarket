package Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Order {
    @Id
    private String orderId;
    private String cId;
    private LocalDate orderDate;
    private String ordertime;
    private double cost;

    public Order() {
    }

    public Order(String orderId, String cId, LocalDate orderDate, String ordertime, double cost) {
        this.setOrderId(orderId);
        this.setcId(cId);
        this.setOrderDate(orderDate);
        this.setOrdertime(ordertime);
        this.setCost(cost);
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
                ", cId='" + cId + '\'' +
                ", orderDate=" + orderDate +
                ", ordertime='" + ordertime + '\'' +
                ", cost=" + cost +
                '}';
    }
}
