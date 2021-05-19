package com.yupodong.lins.Scheduler;

import java.util.Date;

public class list_item {
    private String schelist;
    private String scheDate;
    private int color_image;

    public list_item(String schelist, String scheDate, int color_image) {
        this.schelist = schelist;
        this.scheDate = scheDate;
        this.color_image = color_image;
    }

    public String getSchelist() {
        return schelist;
    }

    public void setSchelist(String schelist) {
        this.schelist = schelist;
    }

    public String getScheDate() {
        return scheDate;
    }

    public void setScheDate(String scheDate) {
        this.scheDate = scheDate;
    }

    public int getColor_image() {
        return color_image;
    }

    public void setColor_image(int color_image) {
        this.color_image = color_image;
    }
}