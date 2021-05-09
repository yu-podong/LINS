package com.yupodong.lins.DTO;

// test table (firebase랑 연동이 되어있는지 확인하기 위해)
// DTO = Data Transfer Object
public class userDTO {
    public String id;
    public String password;

    // 생성자
    public userDTO (){

    }
    public userDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // 접근자 함수
    public void setId(String id){
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getId(String id) {
        return this.id;
    }
    public String getPassword(String password) {
        return this.password;
    }
}
