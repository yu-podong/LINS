package com.yupodong.lins.License;

import java.util.Date;

public class licenselist {

    private String license_day; // 시험날짜
    private String license_number; // 시험 회차
    private String license_line; // 구분선
    private String license_place; // 시험 장소
    private int license_scrap; // 스크랩 버튼

    public licenselist(String license_day, String license_number, String license_line, String license_place, int license_scrap) {
        this.license_day = license_day;
        this.license_number = license_number;
        this.license_line = license_line;
        this.license_place = license_place;
        this.license_scrap = license_scrap;
    }

    public String getLicense_day() {
        return license_day;
    }

    public void setLicense_day(String license_day) {
        this.license_day = license_day;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public String getLicense_line() {
        return license_line;
    }

    public void setLicense_line(String license_line) {
        this.license_line = license_line;
    }

    public String getLicense_place() {
        return license_place;
    }

    public void setLicense_place(String license_place) {
        this.license_place = license_place;
    }

    public int getLicense_scrap() {
        return license_scrap;
    }

    public void setLicense_scrap(int license_scrap) {
        this.license_scrap = license_scrap;
    }
}
