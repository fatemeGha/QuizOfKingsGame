package com.quizgame;
import java.sql.Date;
import java.util.Set;

public class Player {
    int ID ;
    String userName;
    String AvatarURL;
    String password;
    Date registrationDate;
    Date birthday;
    String phoneNumber;
    Set<Match> Compotations;
    String email;
    Access access;
    Data data;
    int level;
    int score;

    public Player(){

    }
    public int getScore() {
        return score;
    }
    public int getLevel() {
        return level;
    }
    public int getID() {
        return ID;
    }
    public Date getBirthday() {
        return birthday;
    }public String getPhoneNumber() {
        return phoneNumber;
    }
    public Access getAccess() {
        return access;
    }public String getPassword() {
        return password;
    }public Set<Match> getCompotations() {
        return Compotations;
    }public Data getData() {
        return data;
    }public String getEmail() {
        return email;
    }public String getAvatarURL() {
        return "QuizAvatar.jpg";
        //return AvatarURL;
    }public Date getRegistrationDate() {
        return registrationDate;
    }public String getUserName() {
        if(this.userName != null)
        return userName;
        else
        return "Gest";
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setAccess(Access access) {
        this.access = access;
    }
    public void setCompotations(Set<Match> compotations) {
        Compotations = compotations;
    }public void setData(Data data) {
        this.data = data;
    }public void setEmail(String email) {
        this.email = email;
    }public void setID(int iD) {
        ID = iD;
    }public void setPassword(String password) {
        this.password = password;
    }public void setAvatarURL(String AvatarURL) {
        this.AvatarURL = AvatarURL;
    }public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }public void setUserName(String userName) {
        this.userName = userName;
    }public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }public void setScore(int score) {
        this.score = score;
    }
    

}