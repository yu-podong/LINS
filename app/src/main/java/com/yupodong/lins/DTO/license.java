package com.yupodong.lins.DTO;

public class license {
    private Integer licenseID;
    private String licenseName;
    private String licenseDate;
    private String licenseTime;
    private String licenseURL;

    // 생성자
    public license() {
        this.licenseID = 0;
        this.licenseName = "";
        this.licenseDate = "";
        this.licenseTime = "";
        this.licenseURL = "";
    }
    public license(Integer licenseID, String licenseName, String licenseDate, String licenseTime, String licenseURL) {
        this.licenseID = licenseID;
        this.licenseName = licenseName;
        this.licenseDate = licenseDate;
        this.licenseTime = licenseTime;
        this.licenseURL = licenseURL;
    }

    // 접근자 함수
    // Set함수
    public void setLicenseID(Integer licenseID) {
        this.licenseID = licenseID;
    }
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }
    public void setLicenseDate(String licenseDate) {
        this.licenseDate = licenseDate;
    }
    public void setLicenseTime(String licenseTime) {
        this.licenseTime = licenseTime;
    }
    public void setLicenseURL(String licenseURL) {
        this.licenseURL = licenseURL;
    }

    // Get함수
    public Integer getLicenseID() {
        return this.licenseID;
    }
    public String getLicenseName() {
        return this.licenseName;
    }
    public String getLicenseDate() {
        return this.licenseDate;
    }
    public String getLicenseTime() {
        return this.licenseTime;
    }
    public String getLicenseURL() {
        return this.licenseURL;
    }
}
