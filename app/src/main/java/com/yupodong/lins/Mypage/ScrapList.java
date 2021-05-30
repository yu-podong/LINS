package com.yupodong.lins.Mypage;

public class ScrapList {
    private String s_day; // 날짜
    private String s_applydate; // 접수일정
    private int s_icon; //스크랩 아이콘
    private String writingInfo;  // 작성 정보
    private Integer ID;

    public ScrapList(String s_day, String s_applydate, int s_icon, String writingInfo, Integer ID) {
        this.s_day = s_day;
        this.s_applydate = s_applydate;
        this.s_icon = s_icon;
        this.writingInfo= writingInfo;
        this.ID = ID;
    }

    public String getS_day() {
        return s_day;
    }

    public void setS_day(String s_day) {
        this.s_day = s_day;
    }

    public String getS_applydate() {
        return s_applydate;
    }

    public void setS_applydate(String s_applydate) {
        this.s_applydate = s_applydate;
    }

    public int getS_icon() {
        return s_icon;
    }

    public void setS_icon(int s_icon) {
        this.s_icon = s_icon;
    }

    public String getWritingInfo() { return this.writingInfo; }

    public void setWritingInfo(String writingInfo) { this.writingInfo = writingInfo; }

    public Integer getID() { return this.ID; }

    public void setID(Integer ID) { this.ID = ID;}
}
