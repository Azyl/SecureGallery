package com.no_ip.azyl13.SecureGalery;

/**
 * Created by AndreiTataru on 6/25/2015.
 */
public class User {

    int _id;
    String _email;
    String _passwd;


    // dummy constructor
    public User(){

    }


    // constructor
    public User(int id,String email,String passwd){
        this._id = id;
        this._email = email;
        this._passwd = passwd;
    }

    // constructor
    public User(String email,String passwd){
        this._email = email;
        this._passwd = passwd;
    }


    // get id
    public int getId(){
        return this._id;
    }

    // set id
    public void setId(int id){
        this._id=id;
    }


    // get email
    public String getEmail(){
        return this._email;
    }

    // set email
    public void setEmail(String email){
        this._email=email;
    }

    // get passwd
    public String getPasswd(){
        return this._passwd;
    }

    // set passwd
    public void setPasswd(String passwd){
        this._passwd=passwd;
    }


}
