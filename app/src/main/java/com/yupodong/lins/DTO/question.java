package com.yupodong.lins.DTO;

public class question {
    private Integer QID;        // 문의사항 ID
    private String nickName;    // 문의사항을 작성한 유저 닉네임
    private String title;       // 문의사항 제목
    private String content;     // 문의 내용
    private String image;       // 문의사항에 업로드한 image
    private String quesDate;    // 문의사항 작성 날짜
    private Boolean answer;     // 문의사항 답변 여부

    // 생성자
    public question() {
        this.QID = 0;
        this.nickName = "";
        this.title = "";
        this.content = "";
        this.image = "";
        this.quesDate = "";
        this.answer = false;
    }
    public question(Integer QID, String nickName, String title, String content, String image, String quesDate, Boolean answer) {
        this.QID = QID;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.image = image;
        this.quesDate = quesDate;
        this.answer = answer;
    }

    // 접근자 함수
    // Set 함수
    public void setQID(Integer QID) {
        this.QID = QID;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setQuesDate(String quesDate) {
        this.quesDate = quesDate;
    }
    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    // Get 함수
    public Integer getQID() {
        return this.QID;
    }
    public String getNickName() {
        return this.nickName;
    }
    public String getTitle() {
        return this.title;
    }
    public String getContent() {
        return this.content;
    }
    public String getImage() {
        return this.image;
    }
    public String getQuesDate() {
        return this.quesDate;
    }
    public Boolean getAnswer() {
        return this.answer;
    }
}
