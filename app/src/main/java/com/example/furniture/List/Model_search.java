package com.example.furniture.List;


import android.graphics.Bitmap;

public class Model_search {

    private String title, price, disc_price, disc_per, id, company;

    private Bitmap bitmap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setTile(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setDisc_price(String disc_price)
    {
        this.disc_price = disc_price;
    }

    public String getDisc_price()
    {
        return disc_price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getPrice()
    {
        return price;
    }

    public void setDisc_per(String disc_per)
    {
        this.disc_per = disc_per;
    }

    public String getDisc_per()
    {
        return disc_per;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCompany()
    {
        return company;
    }
}

