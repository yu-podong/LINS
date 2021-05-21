package com.yupodong.lins.DTO;

public class crawling {
    private String licenseName;     // 크롤링한 자격증 일정
    private String licenseDate;     // 자격증 시험 일정
    private String applyPeriod;     // 해당 시험 접수기간
    private String licenseLink;     // 자격증 페이지 링크

    // 생성자
    public crawling() {
        this.licenseName = "";
        this.licenseDate = "";
        this.applyPeriod = "";
        this.licenseLink = "";
    }

    public crawling(String licenseName, String licenseDate, String applyPeriod, String licenseLink) {
        this.licenseName = licenseName;
        this.licenseDate = licenseDate;
        this.applyPeriod = applyPeriod;
        this.licenseLink = licenseLink;
    }

    // 접근자 함수
    // Set함수
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }
    public void setLicenseDate(String licenseDate) {
        this.licenseDate = licenseDate;
    }
    public void setApplyPeriod(String applyPeriod) {
        this.applyPeriod = applyPeriod;
    }
    public void setLicenseLink(String licenseLink) {
        this.licenseLink = licenseLink;
    }
    // Get함수
    public String getLicenseName() {
        return this.licenseName;
    }
    public String getLicenseDate() {
        return this.licenseDate;
    }
    public String getApplyPeriod() {
        return this.applyPeriod;
    }
    public String getLicenseLink() {
        return this.licenseLink;
    }
}
