package com.example.furniture.Similar;


import android.graphics.Bitmap;

public class Model {

    private String title, price, disc_price, price_desc, id, company, Disc_per;

    private Bitmap bitmap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice_desc() {
        return price_desc;
    }

    public void setPrice_desc(String price_desc) {
        this.price_desc = price_desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDisc_price() {
        return disc_price;
    }

    public void setDisc_price(String disc_price) {
        this.disc_price = disc_price;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCompany()
    {
        return company;
    }

    public void setDisc_per(String Disc_per)
    {
        this.Disc_per = Disc_per;
    }

    public String getDisc_per()
    {
        return Disc_per;
    }


}

