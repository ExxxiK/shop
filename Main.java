package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String req = in.nextLine();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        ArrayList<String> Quantity = new ArrayList<>();
        ArrayList<String> Price = new ArrayList<>();
        ArrayList<String> Name = new ArrayList<>();

        HashSet<String> product_id = new HashSet<>();

        String url = "jdbc:mysql://195.19.44.146:3306/user24?serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url,"user24", "user24");
            System.out.println("Connected...");

            statement =  connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM shop WHERE name LIKE '" + req + "'");
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                resultSet = statement.executeQuery("SELECT * FROM pur WHERE shop_id LIKE '" + id + "'");
                while (resultSet.next()){
                    String quantity = resultSet.getString("quantity");
                    String price = resultSet.getString("price");
                    String productid = resultSet.getString("product_id");
                    Quantity.add(quantity);
                    Price.add(price);
                    product_id.add(productid);
                }
                for (String e : product_id){
                    resultSet = statement.executeQuery("SELECT * FROM product WHERE id LIKE '" + e + "'");
                    if (resultSet.next()) {
                        Name.add(resultSet.getString("name"));
                    }
                }
                System.out.println(Name);
                System.out.println(Price);
                System.out.println(Quantity);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}