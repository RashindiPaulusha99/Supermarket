package DTO;

public class OrderDetailDTO {
    private String itemCode;
    private String orderId;
    private int sellQty;
    private double price;
    private double amount;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String itemCode, String orderId, int sellQty, double price, double amount) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setSellQty(sellQty);
        this.setPrice(price);
        this.setAmount(amount);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSellQty() {
        return sellQty;
    }

    public void setSellQty(int sellQty) {
        this.sellQty = sellQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", sellQty=" + sellQty +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
