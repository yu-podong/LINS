package com.yupodong.lins.Community;

public class CommuList {

    private String commu_title; // 글 제목
    private String commu_nickname; // 닉네임
    private String line; // 구분선
    private String commu_date; // 작성날짜
    private int view_icon; // 조회수 아이콘
    private Integer viewnum; // 조회수
    private int comment_icon; // 댓글 아이콘
    private Integer commenttnum; // 댓글수


    public CommuList(String commu_title, String commu_nickname, String line, String commu_date, int view_icon, Integer viewnum, int comment_icon,Integer commenttnum) {
        this.commu_title = commu_title;
        this.commu_nickname = commu_nickname;
        this.line = line;
        this.commu_date = commu_date;
        this.view_icon = view_icon;
        this.viewnum = viewnum;
        this.comment_icon = comment_icon;
        this.commenttnum = commenttnum;

    }


    public String getCommu_title() {
        return commu_title;
    }

    public void setCommu_title(String commu_title) {
        this.commu_title = commu_title;
    }

    public String getCommu_nickname() {
        return commu_nickname;
    }

    public void setCommu_nickname(String commu_nickname) {
        this.commu_nickname = commu_nickname;
    }

    public String getCommu_date() {
        return commu_date;
    }

    public void setCommu_date(String commu_date) {
        this.commu_date = commu_date;
    }

    public Integer getViewnum() {
        return viewnum;
    }

    public void setViewnum(Integer viewnum) {
        this.viewnum = viewnum;
    }

    public Integer getCommenttnum() {
        return commenttnum;
    }

    public void setCommenttnum(Integer commenttnum) {
        this.commenttnum = commenttnum;
    }

    public int getView_icon() {
        return view_icon;
    }

    public void setView_icon(int view_icon) {
        this.view_icon = view_icon;
    }

    public int getComment_icon() {
        return comment_icon;
    }

    public void setComment_icon(int comment_icon) {
        this.comment_icon = comment_icon;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}

