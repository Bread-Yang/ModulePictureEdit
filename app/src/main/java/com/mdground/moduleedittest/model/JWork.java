package com.mdground.moduleedittest.model;

import java.util.List;

public class JWork {
    private String edit_w;
    private String id;
    private int pageSize;
    private String page_h;
    private String page_w;
    private List<JPage> pages;

    public String toString() {
        StringBuffer buff = new StringBuffer("page_w" + this.page_w + ">>>page_h" + this.page_h + ">>>edit_w" + this.edit_w);
        for (JPage p : this.pages) {
            buff.append("\n" + p);
        }
        return buff.toString();
    }

    public JWork(String id, List<JPage> pages, int pageSize, String page_w, String page_h, String edit_w) {
        this.id = id;
        this.pages = pages;
        this.pageSize = pageSize;
        this.edit_w = edit_w;
        this.page_h = page_h;
        this.page_w = page_w;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage_w() {
        return this.page_w;
    }

    public void setPage_w(String page_w) {
        this.page_w = page_w;
    }

    public String getPage_h() {
        return this.page_h;
    }

    public void setPage_h(String page_h) {
        this.page_h = page_h;
    }

    public String getEdit_w() {
        return this.edit_w;
    }

    public void setEdit_w(String edit_w) {
        this.edit_w = edit_w;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getWork_id() {
        return this.id;
    }

    public void setWork_id(String work_id) {
        this.id = work_id;
    }

    public List<JPage> getPages() {
        return this.pages;
    }

    public void setPages(List<JPage> pages) {
        this.pages = pages;
    }
}
