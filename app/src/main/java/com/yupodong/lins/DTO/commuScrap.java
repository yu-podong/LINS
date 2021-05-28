package com.yupodong.lins.DTO;

public class commuScrap {
    private Integer writingID;
    private String writingCate;
    private String userID;

    // 생성자
    public commuScrap() {
        this.writingID = 0;
        this.writingCate = "";
    }
    // 접근자함수
    public commuScrap(String userID, Integer writingID, String writingCate) {
        this.userID = userID;
        this.writingID = writingID;
        this.writingCate = writingCate;
    }

    // Set함수
    public void setUserID(String userID) { this.userID = userID; }
    public void setWritingID(Integer writingID) {
        this.writingID = writingID;
    }
    public void setWritingCate(String writingCate) { this.writingCate = writingCate; }

    // Get함수
    public String getUserID() { return this.userID; }
    public Integer getWritingID() {
        return this.writingID;
    }
    public String getWritingCate() { return this.writingCate; }
}
