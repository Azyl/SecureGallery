package com.no_ip.azyl13.SecureGalery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreiTataru on 6/25/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager";

    // Table definitions
    private static final String TABLE_USERS = "users";
    private static final String TABLE_FOLDERS = "folders";

    //Table collumns definition
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWD = "passwd";

//    private static final String KEY_ID = "id";
    private static final String KEY_PATH = "path";
    private static final String KEY_PERMISSION = "permission";
    private static final String KEY_ISDEFAULT = "isdefault";





    public DatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Table create at database creation

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EMAIL + " TEXT, "
                + KEY_PASSWD + " TEXT " + ")";


        String CREATE_FOLDER_TABLE = "CREATE TABLE " + TABLE_FOLDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_PATH + " TEXT, "
                + KEY_PERMISSION + " TEXT, " + KEY_ISDEFAULT  + " TEXT" + ")";


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_FOLDER_TABLE);
        Log.d("azyl13", CREATE_FOLDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //drop older table if they exist
        String DROP_TABLE_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;
        String DROP_TABLE_FOLDERS = "DROP TABLE IF EXISTS " + TABLE_FOLDERS;
        db.execSQL(DROP_TABLE_USERS);
        db.execSQL(DROP_TABLE_FOLDERS);

        // recreate the tables
        onCreate(db);
    }


    // add a Folder to the Folder table
    public void addFolder(Folder folder){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,folder.getId()); // the user Id
        values.put(KEY_PATH, folder.getPath()); // path val
        values.put(KEY_PERMISSION, folder.getPermission()); // permissions val
        values.put(KEY_ISDEFAULT, folder.getDefault()); // default val

        db.insert(TABLE_FOLDERS, null, values);
        db.close();

    }



    //get the default folder
    public Folder getFolderDefault(User user){

        String selectQuery = "SELECT  * FROM " + TABLE_FOLDERS + " WHERE "+KEY_ID + " = " + user.getId() + " AND "+KEY_ISDEFAULT+ " = 'Y'";
        Log.d("azyl13",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        Folder defFolder = new Folder(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));


        return defFolder;

    }

    // Getting All User Folders by User id
    public List<Folder> getAllFolders(String Id) {
        List<Folder> folderList = new ArrayList<Folder>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOLDERS + " WHERE "+KEY_ID + " = " + Id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                Folder folder = new Folder();
                folder.setId(cursor.getString(0));
                folder.setPath(cursor.getString(1));
                folder.setPermission(cursor.getString(2));
                // Adding user to list
                folderList.add(folder);
            } while (cursor.moveToNext());
        }

        // return contact list
        return folderList;
    }





    // add a new user to the User table
    public void addUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL,user.getEmail()); // email address
        values.put(KEY_PASSWD,user.getPasswd()); // paswwd val


        db.insert(TABLE_USERS, null, values);
        db.close();

    }

    // get user by id
    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_EMAIL, KEY_PASSWD}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return the user
        return user;
    }


    // get user by Email
    public User getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_EMAIL, KEY_PASSWD}, KEY_EMAIL + "=?",
                new String[]{String.valueOf(email)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return the user
        return user;
    }



    // Getting All Users
    public List<User> getAllUsers() {
        List<User> usertList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setEmail(cursor.getString(1));
                user.setPasswd(cursor.getString(2));
                // Adding user to list
                usertList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return usertList;
    }


    // Updating single user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWD, user.getPasswd());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }


    // Deleting single user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


}
