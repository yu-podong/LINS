package com.yupodong.lins.Login;

public class check_class {
    private String check="fail";
    public check_class(){
        String check = "fail";
    }

    public void set_check(){
        this.check = "success";
    }

    public String get_check(){
        return this.check;
    }
}
