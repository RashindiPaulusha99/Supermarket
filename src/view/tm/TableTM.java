package view.tm;

import javafx.scene.control.Button;

public class TableTM {
    private String itemCode;
    private int sellQty;
    private double UPrice;
    private double amount;
    private Button update;
    private Button delete;

    public TableTM() {
    }

    public TableTM(String itemCode, int sellQty, double UPrice, double amount, Button update, Button delete) {
        this.setItemCode(itemCode);
        this.setSellQty(sellQty);
        this.setUPrice(UPrice);
        this.setAmount(amount);
        this.setUpdate(update);
        this.setDelete(delete);
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "TableTM{" +
                "itemCode='" + itemCode + '\'' +
                ", sellQty=" + sellQty +
                ", UPrice=" + UPrice +
                ", amount=" + amount +
                ", update=" + update +
                ", delete=" + delete +
                '}';
    }
}
