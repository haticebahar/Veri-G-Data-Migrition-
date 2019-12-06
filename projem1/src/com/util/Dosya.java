package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

public class Dosya {

    public void FileSave() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/kys");

            File dosya = new File("C:\\Users\\nur\\Desktop\\Wallpaper\\tumblr_p677cfVH1W1shmtoyo8_1280.png");
            FileInputStream fis = new FileInputStream(dosya);

            PreparedStatement ps = con.prepareStatement("insert into ogrenci (fotograf) values(file)");
            int index = 1;
            ps.setString(index, "image 1");
            ps.setBinaryStream(index + 1, fis, (int) dosya.length());
            ps.executeUpdate();

            ps.close();
            fis.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FileRetrieve() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/kys");


            File dosya = new File("E:\\image1.png");
            FileOutputStream fos = new FileOutputStream(String.valueOf(dosya));
            byte b[];
            Blob blob;

            PreparedStatement ps = con.prepareStatement("select fotograf from kys.ogrenci");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                blob = rs.getBlob("image");
                b = blob.getBytes(1, (int) blob.length());
                fos.write(b);
            }

            ps.close();
            fos.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}