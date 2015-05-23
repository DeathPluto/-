package com.zyxf.workdivision.bean.response;

import com.zyxf.workdivision.bean.User;

import java.io.Serializable;

/**
 * logined, 布尔值
 * type, 未登录为 null，已登录为字符串，可能为 leader 或者 worker
 * user, 未登录为 null，已登录为 JSON 对象，包括该用户的各种属性
 */
public class Check implements Serializable {
    public boolean logined;
    public String type;
    public User user;

    @Override
    public String toString() {
        return "Check{" +
                "logined=" + logined +
                ", type='" + type + '\'' +
                ", user=" + user +
                '}';
    }
}
