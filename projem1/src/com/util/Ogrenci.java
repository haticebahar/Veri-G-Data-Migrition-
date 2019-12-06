package com.util;

import java.util.List;

public  class Ogrenci {

    private  int id;
    private String adi;
    private String soyadi;

    public  Ogrenci(int idGelen, String adiGelen, String soyadiGelen){
        this.id=idGelen;
        this.adi=adiGelen;
        this.soyadi=soyadiGelen;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public int getId() {
        return this.id;
    }

    public String getAdi() {
        return this.adi;
    }

    public String getSoyadi() {
        return this.soyadi;
    }

}

