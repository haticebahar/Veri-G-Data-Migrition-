package com.util;

import com.mysql.jdbc.RowData;
import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ConnectionConfiguration {


    ResultSet resultSet = null; //bir sorgudan dönen sonucları ıcerır

    public static String semaMySQL;
    public static String ipMySQL;
    public static Connection getConnection() {
        Connection connection = null;

       /* Scanner scanner3 = new Scanner(System.in);
        System.out.println("Kaynak MySQL IP adresini giriniz:");
        ipMySQL=scanner3.nextLine();

        Scanner scanner4 = new Scanner(System.in);
        System.out.println("MySQL şema adını giriniz:");
        semaMySQL=scanner4.nextLine();

        Scanner scanner5 = new Scanner(System.in);
        System.out.println("Kullanıcı adınızı giriniz:");
        String kullaniciAdi=scanner5.nextLine();

        Scanner scanner6 = new Scanner(System.in);
        System.out.println("Parolanızı giriniz:");
        String parola=scanner6.nextLine();*/
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //get a connection to database

            //connection = DriverManager.getConnection("jdbc:mysql://"+ipMySQL+":3306/"+semaMySQL+"",kullaniciAdi,parola);
            connection = DriverManager.getConnection("jdbc:mysql://172.21.76.133:3306/employees","beyza","151002047");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    Connection connection = getConnection();
    DatabaseMetaData databaseMetaData;

    {
        try {
            databaseMetaData = connection.getMetaData();// get MetaData
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void schemasMethod() {
        //List Schemas
        System.out.println("Veritabanları : ");
        try {
            resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString("TABLE_CAT"));
                List<String> schemaList = new ArrayList<>();
                schemaList.add(resultSet.getString("TABLE_CAT"));
                System.out.println(schemaList);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    List<String> tableList = new ArrayList<>();

    public List<String> tablesMethod(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        //List tables
        System.out.println("Tablolar : ");
        //List<String> tableList = new ArrayList<>();
        try {
            resultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);

            while (resultSet.next()) {
                //System.out.println(resultSet.getString("TABLE_NAME"));

                tableList.add(resultSet.getString("TABLE_NAME"));
            }
//            mN.mongoCollection(tableNamePattern);
            System.out.println(tableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }



    public List<String> columnsMethod(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) {
        List<String> columnList = new ArrayList<>();
        //List columns
        System.out.println("Alanlar :");
        //List<String> columnList = new ArrayList<>();
        try {
            resultSet = databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
            while ((resultSet.next())) {
                //System.out.println(resultSet.getString("COLUMN_NAME"));
                columnList.add(resultSet.getString("COLUMN_NAME"));
            }
//            mN.mongoColumn(columnList);
            System.out.println(columnList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }

    PreparedStatement statement = null;

    public List<HashMap<String, Object>> veriCekHepsi(String tableName) {

        //alan sayısı
        int a = columnsMethod(semaMySQL, null, tableName, null).size();
        // List<String> columnList = columnsMethod(null, semaMySQL, tableName, null);
        List<String> columnList = columnsMethod(null, "employees", tableName, null);

        List<HashMap<String, Object>> liste = new ArrayList<>();

        //String myVeriler = "SELECT * FROM "+semaMySQL+"." + tableName;
        String myVeriler = "SELECT * FROM employees"+"." + tableName;

        try {
            statement = connection.prepareStatement(myVeriler);
            resultSet = statement.executeQuery();

            HashMap<String, Object> map = new HashMap<>();

            while (resultSet.next()) {
                for(int i = 0; i < a; i++) {
                    String anahtar = columnList.get(i);
                    String deger = resultSet.getString(anahtar);
                    map.put(anahtar, deger);
                }
                liste.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return liste;
    }
}
