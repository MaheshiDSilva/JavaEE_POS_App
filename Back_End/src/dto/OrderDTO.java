package dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private String orderId;
    private Date date;
    private String customerId;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, Date date, String customerId) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
    }

    List<OrderDetailsDTO> orderDetailsDTOList;

    public OrderDTO(String oid, Date date, String cusId, ArrayList<OrderDetailsDTO> orderDetailsDTOS) {
    }

    public List<OrderDetailsDTO> getOrderDetailsDTOList() {
        return orderDetailsDTOList;
    }

    public void setOrderDetailsDTOList(List<OrderDetailsDTO> orderDetailsDTOList) {
        this.orderDetailsDTOList = orderDetailsDTOList;
    }

    public OrderDTO(String orderId, Date date, String customerId, List<OrderDetailsDTO> orderDetailsDTOList) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
        this.orderDetailsDTOList = orderDetailsDTOList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
