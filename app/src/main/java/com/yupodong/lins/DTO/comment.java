package com.yupodong.lins.DTO;

public class comment {
    private Integer commentID;  // 작성된 댓글ID
    private Integer writingID;  // 해당 댓글이 작성된 게시글ID
    private String writingCate; // 해당 댓글이 작성된 게시글의 커뮤니티 이름
    private String nickName;    // 댓글을 작성한 사용자의 nickname
    private String content;     // 작성한 댓글 내용
    private String commentDate; // 댓글 작성 시간

    public comment() {
        this.commentID = 0;
        this.writingID = 0;
        this.writingCate = "";
        this.nickName = "";
        this.content = "";
        this.commentDate = "";
    }
    public comment(Integer commentID, Integer writingID, String writingCate, String nickName, String content, String commentDate) {
        this.commentID = commentID;
        this.writingID = writingID;
        this.writingCate = writingCate;
        this.nickName = nickName;
        this.content = content;
        this.commentDate = commentDate;
    }

    // 접근자 함수
    // Set 함수
    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }
    public void setWritingID(Integer writingID) {
        this.writingID = writingID;
    }
    public void setWritingCate(String writingCate) {
        this.writingCate = writingCate;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    // Get 함수
    public Integer getCommentID() {
        return this.commentID;
    }
    public Integer getWritingID() {
        return this.writingID;
    }
    public String getWritingCate() {
        return this.writingCate;
    }
    public String getNickName() {
        return this.nickName;
    }
    public String getContent() {
        return this.content;
    }
    public String getCommentDate() {
        return this.commentDate;
    }
}
