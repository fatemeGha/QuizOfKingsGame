package com.quizgame.Functions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp {


    public static boolean checkEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return  true ;//matcher.matches();
    }
    
    public static boolean checkPass(String pass){
        boolean hasLetter = false;
        boolean hasNumber = false;
        for(char c : pass.toCharArray()){
            if(Character.isLetter(c)){
                hasLetter = true;
            }
            else if(Character.isDigit(c)){
                hasNumber = true;
            }
        }
        return true ;//pass.matches("[a-zA-Z0-9]") && hasLetter && hasNumber;
    }

    public static boolean checkRepeatPass(String pass , String repeatPass){
        return true;//(pass.compareTo(repeatPass) == 0);
    }
    
    public static boolean checkUserName(String userName){
        return true;//(userName.length() >= 6);
    }
    public static boolean checkPhonrNumber(String phonrNumber){
        return true;//(PhonrNumber.length() == 11);
    }
    
}
