package com.zyxf.workdivision.bean;

import java.io.Serializable;

/**
 * serial_number，字符串，编号，而且在服务器中未被占用
 * id_string，字符串，身份证号，而且在服务器中未被占用
 * password，字符串，密码
 * ethnic，字符串，民族，
 * name，字符串，姓名
 * sex，字符串，性别
 * job，字符串，职务
 * office_phone，字符串，办公电话
 * mobile_phone，字符串，手机
 * department，字符串，部门
 * birthday，字符串，格式类似 1990-01-01
 * hire_date，字符串，格式雷士 2015-01-01
 * address，字符串，地址
 */
public class User implements Serializable {
    public String serial_number;
    public String id_string;
    public String password;
    public String ethnic;
    public String name;
    public String sex;
    public String job;
    public String office_phone;
    public String mobile_phone;
    public String department;
    public String birthday;
    public String hire_date;
    public String address;


    @Override
    public String toString() {
        return "User{" +
                "serial_number='" + serial_number + '\'' +
                ", id_string='" + id_string + '\'' +
                ", password='" + password + '\'' +
                ", ethnic='" + ethnic + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", job='" + job + '\'' +
                ", office_phone='" + office_phone + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", department='" + department + '\'' +
                ", birthday='" + birthday + '\'' +
                ", hire_date='" + hire_date + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
