package DTO;

public class TableDTO {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String orderTime;
    private String itemCode;
    private int sellQty;
    private double UPrice;
    private double discount;
    private double cost;

    public TableDTO() {
    }

    public TableDTO(String orderId, String customerId, String orderDate, String orderTime, String itemCode, int sellQty, double UPrice, double discount, double cost) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setItemCode(itemCode);
        this.setSellQty(sellQty);
        this.setUPrice(UPrice);
        this.setDiscount(discount);
        this.setCost(cost);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getSellQty() {
        return sellQty;
    }

    public void setSellQty(int sellQty) {
        this.sellQty = sellQty;
    }

    public double getUPrice() {
        return UPrice;
    }

    public void setUPrice(double UPrice) {
        this.UPrice = UPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Table{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", sellQty=" + sellQty +
                ", UPrice=" + UPrice +
                ", discount=" + discount +
                ", cost=" + cost +
                '}';
    }
}
