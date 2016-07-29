package com.mdground.moduleedittest.model;

import java.util.List;

public class JPage {
    private String base_img;
    private String bg_color;
    private String bg_img;
    private int id;
    private int mouldSize;
    private List<JMould> moulds;
    private int page;
    private String pageName;
    private String pic;
    private String pid;
    private String shading;
    private int temp_id;
    private String work_id;

    public String toString() {
        StringBuffer buff = new StringBuffer("page_id" + this.id + ">>>color" + this.bg_color + ">>>shading" + this.shading + ">>>background" + this.bg_img);
        for (JMould m : this.moulds) {
            buff.append("\n" + m);
        }
        return buff.toString();
    }

    public JPage(int page_id, String color, String shading, String background, List<JMould> moulds, int mouldSize, String base_img, int temp_id, int page, String work_id, String pageName, String pid) {
        this.id = page_id;
        this.bg_color = color;
        this.shading = shading;
        this.bg_img = background;
        this.moulds = moulds;
        this.mouldSize = mouldSize;
        this.base_img = base_img;
        this.temp_id = temp_id;
        this.page = page;
        this.work_id = work_id;
        this.pageName = pageName;
        this.pid = pid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getTemp_id() {
        return this.temp_id;
    }

    public String getWork_id() {
        return this.work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public void setTemp_id(int temp_id) {
        this.temp_id = temp_id;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getBase_img() {
        return this.base_img;
    }

    public void setBase_img(String base_img) {
        this.base_img = base_img;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBg_color() {
        return this.bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getBg_img() {
        return this.bg_img;
    }

    public void setBg_img(String bg_img) {
        this.bg_img = bg_img;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public int getMouldSize() {
        return this.mouldSize;
    }

    public void setMouldSize(int mouldSize) {
        this.mouldSize = mouldSize;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPage_id() {
        return this.id;
    }

    public void setPage_id(int page_id) {
        this.id = page_id;
    }

    public String getColor() {
        return this.bg_color;
    }

    public void setColor(String color) {
        this.bg_color = color;
    }

    public String getShading() {
        return this.shading;
    }

    public void setShading(String shading) {
        this.shading = shading;
    }

    public String getBackground() {
        return this.bg_img;
    }

    public void setBackground(String background) {
        this.bg_img = background;
    }

    public List<JMould> getMoulds() {
        return this.moulds;
    }

    public void setMoulds(List<JMould> moulds) {
        this.moulds = moulds;
    }
}
