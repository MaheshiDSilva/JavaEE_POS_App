package entity;

public class OrderDetails {
    private String code;
    private String orderId;
    private int qtyOnHand;
    private double unitPrice;

    public OrderDetails() {
    }

    public OrderDetails(String code, String orderId, int qtyOnHand, double unitPrice) {
        this.code = code;
        this.orderId = orderId;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailsDTO{" +
                "code='" + code + '\'' +
                ", orderId='" + orderId + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
