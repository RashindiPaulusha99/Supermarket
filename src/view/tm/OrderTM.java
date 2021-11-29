package view.tm;

import DTO.ItemDetailsDTO;

import java.util.ArrayList;

public class OrderTM {
        private String orderId;
        private String customerId;
        private String orderDate;
        private String orderTime;
        private double cost;
        private ArrayList<ItemDetailsDTO> items;//because one order has many itemdetails

        public OrderTM(String orderId, String customerId, String orderDate, String orderTime, double cost) {
        }

        public OrderTM(String orderId, String customerId, String orderDate, String orderTime, double cost, ArrayList<ItemDetailsDTO> items) {
            this.setOrderId(orderId);
            this.setCustomerId(customerId);
            this.setOrderDate(orderDate);
            this.setOrderTime(orderTime);
            this.setCost(cost);
            this.setItems(items);
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

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public ArrayList<ItemDetailsDTO> getItems() {
            return items;
        }

        public void setItems(ArrayList<ItemDetailsDTO> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "OrderTM{" +
                    "orderId='" + orderId + '\'' +
                    ", customerId='" + customerId + '\'' +
                    ", orderDate='" + orderDate + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    ", cost=" + cost +
                    ", items=" + items +
                    '}';
        }
}
