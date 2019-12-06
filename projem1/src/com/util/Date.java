package com.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public class Date {
    public void DateSave() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=beyza");
            String d = "10-11-2006";
            PreparedStatement myPrepStmt = con.prepareStatement("update ogrenci set girisTarih=? where ogrenciID=1");
            java.util.Date date = new SimpleDateFormat("dd-MMM-yyyy").parse(d);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            myPrepStmt.setDate(1, sqlDate);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}