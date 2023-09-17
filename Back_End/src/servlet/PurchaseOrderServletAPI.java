package servlet;

import bo.BOFactory;
import bo.custom.PurchaseOrderBO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import util.ResponseUtil;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/order")
public class PurchaseOrderServletAPI extends HttpServlet {

    PurchaseOrderBO purchaseOrderBO= (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        PrintWriter writer = resp.getWriter();
        try (Connection connection=pool.getConnection()){

            JsonArrayBuilder allOrders = Json.createArrayBuilder();
            ArrayList<OrderDTO> all = purchaseOrderBO.getAllOrders(connection);

            for (OrderDTO orderDTO:all){
                JsonObjectBuilder order = Json.createObjectBuilder();
                order.add("oId",orderDTO.getOrderId());
                order.add("date", String.valueOf(orderDTO.getDate()));
                order.add("cusID",orderDTO.getCustomerId());

                allOrders.add(order).build();


            }
            writer.println(allOrders.build());

        } catch (ClassNotFoundException e) {
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String oid = jsonObject.getString("oId");
        String date = jsonObject.getString("date");
        String cusId = jsonObject.getString("cusID");
        String code = jsonObject.getString("ItemID");
        String ItemName = jsonObject.getString("ItemName");
        String UnitPrice = jsonObject.getString("UnitPrice");
        String Qty = jsonObject.getString("Qty");
        String QtyOnHnd =jsonObject.getString("QtyOnHnd");
        JsonArray CartItems = jsonObject.getJsonArray("CartItems");

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection= pool.getConnection()){



            ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

            for (int i = 0; i < CartItems.size(); i++) {
                String itemId= CartItems.getJsonArray(i).getString(0);
                int qty= Integer.parseInt(CartItems.getJsonArray(i).getString(3));
                double unitPrice= Double.parseDouble(CartItems.getJsonArray(i).getString(2));

                System.out.println(itemId);
                System.out.println(qty);
                System.out.println(unitPrice);
                orderDetailsDTOS.add(new OrderDetailsDTO(itemId,oid,qty,unitPrice));
            }

            OrderDTO orderDTO = new OrderDTO(oid, Date.valueOf(date),cusId,orderDetailsDTOS);
            if (purchaseOrderBO.purchaseOrder(connection,orderDTO)){
                resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Added !"));
            }


        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String orderID = jsonObject.getString("id");
//        String date = jsonObject.getString("date");
//        String cusId =jsonObject.getString("cusId");
//        String itemCode = jsonObject.getString("ItemCode");
//        String qty = jsonObject.getString("qty");
//        String unitPrice = jsonObject.getString("unitPrice");
//
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
//
//            connection.setAutoCommit(false);
//
//            PreparedStatement pstm = connection.prepareStatement("insert into orders values(?,?,?)");
//            pstm.setObject(1,orderID);
//            pstm.setObject(2, date);
//            pstm.setObject(3, cusId);
//
//            PreparedStatement pstm1 = connection.prepareStatement("insert into orderdetail values(?,?,?,?)");
//            pstm1.setObject(1,orderID);
//            pstm1.setObject(2, itemCode);
//            pstm1.setObject(3, qty);
//            pstm1.setObject(4, unitPrice);
//
//            resp.addHeader("Content-Type", "application/json");
//            resp.addHeader("Access-Control-Allow-Origin","*");
//            resp.addHeader("Access-Control-Allow-Headers","content-type");
//            if (pstm.executeUpdate() > 0) {
//                if (pstm1.executeUpdate() > 0) {
//                    connection.commit();
//                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//                    objectBuilder.add("state:", "OK");
//                    objectBuilder.add("message:", "Order Success...");
//                    objectBuilder.add("data", "");
//                    resp.getWriter().print(objectBuilder.build());
//                }
//            }else {
//                connection.rollback();
//            }
//
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            objectBuilder.add("state:", "Error");
//            objectBuilder.add("message:", e.getMessage());
//            objectBuilder.add("data", "");
//            resp.setStatus(400);
//            resp.getWriter().print(objectBuilder.build());
//        }finally {
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
//                connection.setAutoCommit(true);
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin","*");
//        resp.addHeader("Access-Control-Allow-Methods","PUT,DELETE");
        resp.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
