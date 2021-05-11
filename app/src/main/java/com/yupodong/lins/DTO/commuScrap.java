package com.yupodong.lins.DTO;

public class commuScrap {
    private Integer scrapID;
    private Integer writingID;
    private String writingCate;

    // 생성자
    public commuScrap() {
        this.scrapID = 0;
        this.writingID = 0;
        this.writingCate = "";
    }
    public commuScrap(Integer scrapID, Integer writingID, String writingCate) {
        this.scrapID = scrapID;
        this.writingID = writingID;
        this.writingCate = writingCate;
    }

    // 접근자함수
    // Set함수
    public void setScrapID(Integer scrapID) {
        this.scrapID = scrapID;
    }
    public void setWritingID(Integer writingID) {
        this.writingID = writingID;
    }
    public void setWritingCate(String writingCate) { this.writingCate = writingCate; }

    // Get함수
    public Integer getScrapID() {
        return this.scrapID;
    }
    public Integer getWritingID() {
        return this.writingID;
    }
    public String getWritingCate() { return this.writingCate; }
}
