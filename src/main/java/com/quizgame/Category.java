package com.quizgame;
public class Category {
    int categoryID;
    String categoryName;
    String iconImage;
    public Category(){

    }
    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }public String getIconImage() {
        return iconImage;
    }
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public int getCategoryID() {
        return categoryID;
    }public String getCategoryName() {
        return categoryName;
    }
    
}
