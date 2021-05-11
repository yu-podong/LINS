package com.yupodong.lins.DTO;

public class licenseScrap {
    private Integer scrapID;
    private Integer licenseID;
    private String licenseName;

    // 생성자
    public licenseScrap() {
        this.scrapID = 0;
        this.licenseID = 0;
        this.licenseName = "";
    }
    public licenseScrap(Integer scrapID, Integer licenseID, String licenseName) {
        this.scrapID = scrapID;
        this.licenseID = licenseID;
        this.licenseName = licenseName;
    }

    // 접근자함수
    // Set함수
    public void setScrapID(Integer scrapID) {
        this.scrapID = scrapID;
    }
    public void setLicenseID(Integer licenseID) {
        this.licenseID = licenseID;
    }
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    // Get함수
    public Integer getScrapID() {
        return this.scrapID;
    }
    public Integer getLicenseID() {
        return this.licenseID;
    }
    public String getLicenseName() {
        return this.licenseName;
    }
}
