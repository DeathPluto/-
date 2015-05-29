package com.zyxf.workdivision.bean;

import java.io.Serializable;

/**
 * Sample Response: 一个 project 的各个字段
 * <p/>
 * address: "Shinar"
 * chinese_name: "通天塔"
 * city: "Babylon"
 * country: "Ancient Babylon"
 * english_name: "Tower of Babel"
 * fax: "123456"
 * id: "1"
 * leader_id: "1"
 * phone_number_1: "789123"
 * phone_number_2: ""
 * supervisor: "Noah"
 * zip_code: "456789"
 */
public class Project implements Serializable {

    public String address;
    public String chinese_name;
    public String city;
    public String country;
    public String english_name;
    public String fax;
    public String id;
    public String leader_id;
    public String phone_number_1;
    public String phone_number_2;
    public String supervisor;
    public String zip_code;


    @Override
    public String toString() {
        return "Project{" +
                "address='" + address + '\'' +
                ", chinese_name='" + chinese_name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", english_name='" + english_name + '\'' +
                ", fax='" + fax + '\'' +
                ", id='" + id + '\'' +
                ", leader_id='" + leader_id + '\'' +
                ", phone_number_1='" + phone_number_1 + '\'' +
                ", phone_number_2='" + phone_number_2 + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", zip_code='" + zip_code + '\'' +
                '}';
    }
}
