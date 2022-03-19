package com.example.att23.CartFiles;

import android.graphics.Bitmap;

public class model_cart {

    private String title, price, disc_price, off_per, id, qty, company;

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

    public String getOff_per() {
        return off_per;
    }

    public void setOff_per(String off_per) {
        this.off_per = off_per;
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

    public void setQty(String qty)
    {
        this.qty = qty;
    }

    public String getQty()
    {
        return qty;
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



