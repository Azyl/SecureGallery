package com.no_ip.azyl13.SecureGalery;

/**
 * Created by AndreiTataru on 6/27/2015.
 */
public class Folder {


    String _id;
    String _permission;
    String _path;
    String _default;


    // dummy constructor
    public Folder(){

    }


    // constructor
    public Folder(String id,String permission,String path,String Default){
        this._id = id;
        this._permission = permission;
        this._path = path;
        this._default = Default;
    }




    // get id
    public String getId(){
        return this._id;
    }

    // set id
    public void setId(String id){
        this._id=id;
    }


    // get Permission
    public String getPermission(){
        return this._permission;
    }

    // set Permission
    public void setPermission(String permission){
        this._permission=permission;
    }

    // get Path
    public String getPath(){
        return this._path;
    }

    // set path
    public void setPath(String path){
        this._path=path;
    }


    // get Default
    public String getDefault(){
        return this._default;
    }

    // set Default
    public void setDefault(String Default){
        this._default=Default;
    }








}
