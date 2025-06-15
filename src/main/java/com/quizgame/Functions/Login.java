package com.quizgame.Functions;

import com.quizgame.Player;
import com.quizgame.DAO.PlayersDAO;

public class Login {
    String userName;
    String pass;
    public boolean checkLogin;
    public Player player ;
    

    public Login(String userName , String pass){
        this.pass = pass;
        this.userName = userName;
        checkLogin = false;
        checkLogin();
    }


    void checkLogin(){

            Player player = PlayersDAO.getByUserName( userName);
            if(player != null){
                this.player = player;
                checkLogin = true;
            }
            else{
                checkLogin = false;
            }
        
    }

    
}
