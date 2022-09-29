package oeg.zgg.presto;

import java.sql.*;
import java.util.Properties;

public class ConnTest {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:presto://bigdata101:8881/hive/default";
        Connection connection = DriverManager.getConnection(url, "root", null);

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("select * from order_table_s");
        while(rs.next()){
            String product_name = rs.getString("product_name");
            int price = rs.getInt("price");
            System.out.println("product_name: " + product_name + " price:" + price);
        }

        statement.close();
        connection.close();

    }
}
