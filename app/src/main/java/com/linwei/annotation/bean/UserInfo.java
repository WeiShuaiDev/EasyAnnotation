package com.linwei.annotation.bean;

import java.io.Serializable;

/**
 * @Author: WS
 * @Time: 2020/5/9
 * @Description: 用户数据
 */
public class UserInfo implements Serializable {
    public String username;
    public String password;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
