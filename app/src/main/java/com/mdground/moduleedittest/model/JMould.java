package com.mdground.moduleedittest.model;

public class JMould {
    public static final String DEFAULT_COLOR = "#555555";
    public static final String DEFAULT_FONT = "";
    public static final int DEFAULT_SIZE = 20;
    public static final String DEFAULT_TEXT = "点击此处编辑文字";

    private String color;
    private String font;
    private String font_direct;
    private String h;
    private String matrix;
    private String model_name;
    private String mouldPic;
    private String mouldid;
    private String photo;
    private float scale;
    private String size;
    private String text;
    private String type;
    private String w;
    private String x;
    private String y;

    public String toString() {
        return "mouldid" + this.mouldid + ">>>mould" + this.mouldPic + ">>>image" + this.photo + ">>>text" + this.text + ">>>textColor" + this.color + ">>>textSize" + this.size + ">>>textFont" + this.font + ">>>type" + this.type + ">>>matrix" + this.matrix + ">>>mouldX" + this.x + ">>> mouldY" + this.y + ">>>mouldW" + this.w + ">>>mouldH" + this.h;
    }

    public JMould(String mouldid, String mould, String image, String text, String textColor, String textSize, String textFont, String type, String matrix, String mouldX, String mouldY, String mouldW, String mouldH, float scale, String font_direct, String model_name) {
        setMouldInfo(mouldid, mould, type).setMouldSize(mouldX, mouldY, mouldW, mouldH).setMouldImage(image, matrix).setMouldText(text, textColor, textSize, textFont);
        this.scale = scale;
        this.font_direct = font_direct;
        this.model_name = model_name;
    }

    public JMould setMouldInfo(String mouldid, String mould, String type) {
        this.mouldid = mouldid;
        this.mouldPic = mould;
        this.type = type;
        return this;
    }

    public String getModel_name() {
        return this.model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getFont_direct() {
        return this.font_direct;
    }

    public void setFont_direct(String font_direct) {
        this.font_direct = font_direct;
    }

    public String getMouldPic() {
        return this.mouldPic;
    }

    public void setMouldPic(String mouldPic) {
        this.mouldPic = mouldPic;
    }

    public String getPhoto() {
        return this.photo;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public JMould setMouldSize(String mouldX, String mouldY, String mouldW, String mouldH) {
        this.x = mouldX;
        this.y = mouldY;
        this.w = mouldW;
        this.h = mouldH;
        return this;
    }

    public JMould setMouldImage(String image, String matrix) {
        this.photo = image;
        this.matrix = matrix;
        return this;
    }

    public JMould setMouldText(String text, String textColor, String textSize, String textFont) {
        this.text = text;
        this.color = textColor;
        this.font = textFont;
        this.size = textSize;
        return this;
    }

    public String getMouldid() {
        return this.mouldid;
    }

    public void setMouldid(String mouldid) {
        this.mouldid = mouldid;
    }

    public String getMould() {
        return this.mouldPic;
    }

    public void setMould(String mould) {
        this.mouldPic = mould;
    }

    public String getImage() {
        return this.photo;
    }

    public void setImage(String image) {
        this.photo = image;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatrix() {
        return this.matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getW() {
        return this.w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getH() {
        return this.h;
    }

    public void setH(String h) {
        this.h = h;
    }
}
