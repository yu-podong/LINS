package com.yupodong.lins.DTO;

// DTO = Data Transfer Object
public class user {
    private String id;
    private String password;
    private String name;
    private String nickName;
    private String phoneNum;
    private String email;
    private Integer accessLevel; // 접근권한 레벨, 관리자 : 10, 사용자 : 1(사용자와 관리자의 접근권한을 달리 부여하기 위함)

    // 생성자
    public user(){
        this.id = "";
        this.password = "";
        this.name = "";
        this.nickName = "";
        this.phoneNum = "";
        this.email = "";
        this.accessLevel = 0;
    }
    public user(String id, String password, String name, String nickName, String phoneNum, String email, Integer accessLevel) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.accessLevel = accessLevel;
    }

    // 접근자 함수
    // Set함수
    public void setId(String id){
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    // Get함수
    public String getId() {
        return this.id;
    }
    public String getPassword() {
        return this.password;
    }
    public String getName() {
        return this.name;
    }
    public String getNickName() {
        return this.nickName;
    }
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public String getEmail(){
        return this.email;
    }
    public Integer getAccessLevel() {
        return this.accessLevel;
    }
}
