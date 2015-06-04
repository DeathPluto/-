package com.zyxf.workdivision.bean.response;

import com.zyxf.workdivision.bean.Project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DeathPluto on 2015/6/3.
 */
public class ResProject implements Serializable {
    public int current_page;
    public List<Project> data;
    public int from;
    public int last_page;
    public String next_page_url;
    public int per_page;
    public String prev_page_url;
    public int to;
    public int total;

    @Override
    public String toString() {
        return "ResProject{" +
                "current_page=" + current_page +
                ", data=" + data +
                ", from=" + from +
                ", last_page=" + last_page +
                ", next_page_url='" + next_page_url + '\'' +
                ", per_page=" + per_page +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", to=" + to +
                ", total=" + total +
                '}';
    }
}
