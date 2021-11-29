package DTO;

public class ItemDetailsDTO {
    private String itemCode;
    private double unitPrice;
    private double amount;
    private int qtyForSell;

    public ItemDetailsDTO(String s, String s1) {
    }

    public ItemDetailsDTO() {
    }

    public ItemDetailsDTO(String itemCode, double unitPrice, double amount, int qtyForSell) {
        this.setItemCode(itemCode);
        this.setUnitPrice(unitPrice);
        this.setAmount(amount);
        this.setQtyForSell(qtyForSell);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", amount=" + amount +
                ", qtyForSell=" + qtyForSell +
                '}';
    }
}
