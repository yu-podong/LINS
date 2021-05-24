package com.yupodong.lins.DTO;

public class licenseScrap {
    private Integer licenseID;
    private String licenseName;
    private String nickName;

    // 생성자
    public licenseScrap() {
        this.licenseID = 0;
        this.licenseName = "";
        this.nickName = "";
    }
    public licenseScrap( Integer licenseID, String licenseName, String nickName) {
        this.licenseID = licenseID;
        this.licenseName = licenseName;
        this.nickName = nickName;
    }

    // 접근자함수
    // Set함수
    public void setLicenseID(Integer licenseID) {
        this.licenseID = licenseID;
    }
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }
    public void setNickName(String nickName) { this.nickName = nickName; }

    // Get함수
    public Integer getLicenseID() {
        return this.licenseID;
    }
    public String getLicenseName() {
        return this.licenseName;
    }
    public String getNickName() { return this.nickName; }
}
