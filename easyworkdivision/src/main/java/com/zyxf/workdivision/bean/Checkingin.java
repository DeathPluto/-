package com.zyxf.workdivision.bean;

import java.io.Serializable;

/**
 * Created by DeathPluto on 2015/5/23.
 */
public class Checkingin implements Serializable {
    public String attendance_timestamp;
    public String device_id;
    public String id;
    public String id_string;
    public String department;
    public String name;
    public String comment;
    public String serial_number;

    @Override
    public String toString() {
        return "Checkingin{" +
                "attendance_timestamp='" + attendance_timestamp + '\'' +
                ", device_id='" + device_id + '\'' +
                ", id='" + id + '\'' +
                ", id_string='" + id_string + '\'' +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", serial_number='" + serial_number + '\'' +
                '}';
    }
}
