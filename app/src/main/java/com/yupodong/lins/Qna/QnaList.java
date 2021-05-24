package com.yupodong.lins.Qna;

public class QnaList {
    private String q_title; // 제목
    private String q_day; // 작성날짜
    private int q_comment_icon; // 댓글 아이콘
    private String q_comment_num; // 댓글 수

    public QnaList(String q_title, String q_day, int q_comment_icon, String q_comment_num) {
        this.q_title = q_title;
        this.q_day = q_day;
        this.q_comment_icon = q_comment_icon;
        this.q_comment_num = q_comment_num;
    }

    public String getQ_title() {
        return q_title;
    }

    public void setQ_title(String q_title) {
        this.q_title = q_title;
    }

    public String getQ_day() {
        return q_day;
    }

    public void setQ_day(String q_day) {
        this.q_day = q_day;
    }

    public int getQ_comment_icon() {
        return q_comment_icon;
    }

    public void setQ_comment_icon(int q_comment_icon) {
        this.q_comment_icon = q_comment_icon;
    }

    public String getQ_comment_num() {
        return q_comment_num;
    }

    public void setQ_comment_num(String q_comment_num) {
        this.q_comment_num = q_comment_num;
    }
}
