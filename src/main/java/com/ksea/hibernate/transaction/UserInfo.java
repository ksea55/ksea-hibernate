package com.ksea.hibernate.transaction;

/**
 * Created by ksea on 2017/7/3.
 */
public class UserInfo {

    private Integer uid;
    //版本号,这是用于乐观锁加锁
    private int uversion;

    private String userName;
    private String email;

    public UserInfo() {
    }

    public UserInfo(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public int getUversion() {
        return uversion;
    }

    public void setUversion(int uversion) {
        this.uversion = uversion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", uversion=" + uversion +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
