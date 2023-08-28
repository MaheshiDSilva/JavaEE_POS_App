package servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/pages/item")
public class ItemServletAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();


            resp.addHeader("Content-Type", "application/json");
            resp.addHeader("Access-Control-Allow-Origin","*");
            JsonArrayBuilder allItems = Json.createArrayBuilder();
            while (rst.next()) {
                String code = rst.getString(1);
                String name = rst.getString(2);
                int qtyOnHand = rst.getInt(3);
                double unitPrice = rst.getDouble(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("code", code);
                customerObject.add("description", name);
                customerObject.add("qty", qtyOnHand);
                customerObject.add("unitPrice", unitPrice);
                allItems.add(customerObject.build());
            }

            JsonObjectBuilder response = Json.createObjectBuilder();//create object
            response.add("state", "OK");
            response.add("message", "Successfully Loaded....!");
            response.add("data", allItems.build());
            resp.getWriter().print(response.build());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        String description = req.getParameter("description");
        String qty = req.getParameter("qty");
        String unitPrice = req.getParameter("unitPrice");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");
            pstm.setObject(1, code);
            pstm.setObject(2, description);
            pstm.setObject(3, qty);
            pstm.setObject(4, unitPrice);

            resp.addHeader("Access-Control-Allow-Origin", "*");
            if (pstm.executeUpdate() > 0) {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("state:", "OK");
                objectBuilder.add("message:", "Successfully Added...");
                objectBuilder.add("data", "");
                resp.getWriter().print(objectBuilder.build());
            }


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state:", "Error");
            objectBuilder.add("message:", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            String code = req.getParameter("code");
            PreparedStatement pstm2 = connection.prepareStatement("delete from Item where code=?");
            pstm2.setObject(1, code);
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");
            if (pstm2.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("state:", "OK");
                objectBuilder.add("message:", "Successfully Deleted...");
                objectBuilder.add("data", "");
                resp.getWriter().print(objectBuilder.build());
            }

            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            String i_code = jsonObject.getString("code");
            String i_name = jsonObject.getString("description");
            String i_qty = jsonObject.getString("qty");
            String i_price = jsonObject.getString("unitPrice");

            System.out.println("DO DELETE " + i_code + " " + i_name + " " + i_qty + " " + i_price);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            String code = req.getParameter("code");
            String description = req.getParameter("description");
            String qty = req.getParameter("qty");
            String unitPrice = req.getParameter("unitPrice");

            resp.addHeader("Access-Control-Allow-Origin","*");

            PreparedStatement pstm3 = connection.prepareStatement("update Item set description=?,qtyOnHand=?,unitPrice=? where code=?");
            pstm3.setObject(4, code);
            pstm3.setObject(1, description);
            pstm3.setObject(2, qty);
            pstm3.setObject(3, unitPrice);
            resp.addHeader("Content-Type", "application/json");
            if (pstm3.executeUpdate() > 0) {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("state:", "OK");
                objectBuilder.add("message:", "Successfully Updated...");
                objectBuilder.add("data", "");
                resp.getWriter().print(objectBuilder.build());
            }

            JsonReader reader = Json.createReader(req.getReader());
            JsonArray jsonValues = reader.readArray();

            for(JsonValue jsonValue:jsonValues){
                JsonObject jsonObject = jsonValue.asJsonObject();
                String oid = jsonObject.getString("oid");
                String date = jsonObject.getString("date");
                JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");

                System.out.println(oid + ": " +date );

                for(JsonValue orderDetail:orderDetails){
                    JsonObject odObject = orderDetail.asJsonObject();
                    String code1 = odObject.getString("code");
                    System.out.println(code1);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Access-Control-Allow-Methods","PUT,DELETE");
        resp.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
