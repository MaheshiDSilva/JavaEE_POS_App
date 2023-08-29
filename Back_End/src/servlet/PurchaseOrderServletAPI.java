package servlet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/pages/order"})
public class PurchaseOrderServletAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String orderID = jsonObject.getString("id");
        String date = jsonObject.getString("date");
        String cusId =jsonObject.getString("cusId");
        String itemCode = jsonObject.getString("itemCode");
        String qty = jsonObject.getString("qty");
        String unitPrice = jsonObject.getString("unitPrice");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            connection.setAutoCommit(false);

            PreparedStatement pstm = connection.prepareStatement("insert into orders values(?,?,?)");
            pstm.setObject(1,orderID);
            pstm.setObject(2, date);
            pstm.setObject(3, cusId);

            PreparedStatement pstm1 = connection.prepareStatement("insert into orderdetail values(?,?,?,?)");
            pstm1.setObject(1,orderID);
            pstm1.setObject(2, itemCode);
            pstm1.setObject(3, qty);
            pstm1.setObject(4, unitPrice);

            resp.addHeader("Content-Type", "application/json");
            resp.addHeader("Access-Control-Allow-Origin","*");
            resp.addHeader("Access-Control-Allow-Headers","content-type");
            if (pstm.executeUpdate() > 0) {
                if (pstm1.executeUpdate() > 0) {
                    connection.commit();
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    objectBuilder.add("state:", "OK");
                    objectBuilder.add("message:", "Order Success...");
                    objectBuilder.add("data", "");
                    resp.getWriter().print(objectBuilder.build());
                }
            }else {
                connection.rollback();
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state:", "Error");
            objectBuilder.add("message:", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }finally {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

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
        resp.addHeader("Access-Control-Allow-Origin","*");
//        resp.addHeader("Access-Control-Allow-Methods","PUT,DELETE");
        resp.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
