package com.yupodong.lins.License;

import java.util.Date;

public class licenselist {
    private String licenseName;
    private Integer licenseID;
    private String license_day; // 시험날짜
    private String license_number; // 시험 회차
    private int license_scrap; // 스크랩 버튼

    public licenselist(String licenseName, Integer licenseID, String license_day, String license_number, int license_scrap) {
        this.licenseName = licenseName;
        this.licenseID = licenseID;
        this.license_day = license_day;
        this.license_number = license_number;
        this.license_scrap = license_scrap;
    }
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicenseName() {
        return this.licenseName;
    }
    public void setLicenseID(Integer licenseID) {
        this.licenseID = licenseID;
    }

    public Integer getLicenseID() {
        return this.licenseID;
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

    public int getLicense_scrap() {
        return license_scrap;
    }

    public void setLicense_scrap(int license_scrap) {
        this.license_scrap = license_scrap;
    }
}
