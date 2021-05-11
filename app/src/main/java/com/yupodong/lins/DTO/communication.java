package com.yupodong.lins.DTO;

public class communication {
    private Integer writingID;  // 게시글 ID
    private String category;    // 커뮤니티 종류 (토익, 정보처리 기사..)
    private String title;       // 게시글 제목
    private String userID;      // 작성한 유저ID
    private String nickName;    // 작성한 유저 nickname
    private String writeDate;   // 작성된 날짜 
    private String content;     // 게시글 내용
    private String image;       // 게시글에 포함된 image (보류)
    private Integer scrapCount; // 해당 게시글을 스크랩한 유저 수
    private Integer viewCount;  // 해당 게시글의 조회수
    private Integer commentCount;   // 해당 게시글에 달린 댓글 수

    // 생성자
    public communication() {
        this.writingID = 0;
        this.category = "";
        this.title = "";
        this.userID = "";
        this.nickName = "";
        this.writeDate = "";
        this.content = "";
        this.image = "";
        this.scrapCount = 0;
        this.viewCount = 0;
        this.commentCount = 0;
    }
    public communication(Integer writingID, String category, String title, String userID, String nickName, String writeDate,
                         String content, String image, Integer scrapCount, Integer viewCount, Integer commentCount) {
        this.writingID = writingID;
        this.category = category;
        this.title = title;
        this.userID = userID;
        this.nickName = nickName;
        this.writeDate = writeDate;
        this.content = content;
        this.image = image;
        this.scrapCount = scrapCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
    }

    // 접근자 함수
    // Set햠수
    public void setWritingID(Integer writingID) {
        this.writingID = writingID;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setScrapCount(Integer scrapCount) {
        this.scrapCount = scrapCount;
    }
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    // Get 함수
    public Integer getWritingID() {
        return this.writingID;
    }
    public String getCategory() {
        return this.category;
    }
    public String getTitle() {
        return this.title;
    }
    public String getUserID() {
        return this.userID;
    }
    public String getNickName() {
        return this.nickName;
    }
    public String getWriteDate() {
        return this.writeDate;
    }
    public String getContent() {
        return this.content;
    }
    public String getImage() {
        return this.image;
    }
    public Integer getScrapCount() {
        return this.scrapCount;
    }
    public Integer getViewCount() {
        return this.viewCount;
    }
    public Integer getCommentCount() {
        return this.commentCount;
    }
}
