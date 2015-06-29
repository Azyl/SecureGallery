package com.no_ip.azyl13.SecureGalery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;



public class MainActivity extends ActionBarActivity {

    // db initialization!




    ExecuteasRoot Excutor = new ExecuteasRoot();

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.abc_btn_radio_material : R.drawable.notification_template_icon_bg); //success / fail

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    //    Loggin Button action
    public void doLoggin(View view) {




        EditText Email_addr_et = (EditText) findViewById(R.id.editText_email_adress);
        String Email_addr = Email_addr_et.getText().toString();

        EditText Passwd_et = (EditText) findViewById(R.id.editText_password);
        String Passwd = Passwd_et.getText().toString();

        if (Email_addr.length()==0){
            TextView toptv = (TextView) findViewById(R.id.textView_bot);
            toptv.setText("Email cannot be empty");
        }
        else {
            if (Passwd.length()==0){
                TextView toptv = (TextView) findViewById(R.id.textView_bot);
                toptv.setText("Password cannot be empty");
            }
            else {
                //intent.putExtra(MainActivity., Email_addr);

                // login check to implement

                DatabaseHandler db = new DatabaseHandler(this);

                //db.onUpgrade(db.getWritableDatabase(),1,1);

                // Inserting Contacts
                //Log.d("azyl13", "Inserting ..");
                //db.addUser(new User("t", "aasdfg"));
                //db.addUser(new User("Andrei", "qwert"));
                //db.addUser(new User("Ramona", "919"));
                //db.addUser(new User("Maria", "952"));
                //db.addUser(new User("Ioana", "953"));

                // Reading all contacts
                Log.d("azyl13", "Reading all Users..");
                List<User> users = db.getAllUsers();

                for (User cn : users) {
                    String log = "Id: " + cn.getId() + " ,Email: " + cn.getEmail() + " ,Passwd: " + cn.getPasswd();
                    // Writing Contacts to log
                    Log.d("azyl13", log);
                }


                try{

                    String Userid = String.valueOf(db.getUser(Email_addr).getId());
                    Log.d("azyl13", "The userid is " + Userid);

                    String Userpwd = String.valueOf(db.getUser(Email_addr).getPasswd());
                    Log.d("azyl13",  "The userpaswd is " + Passwd);





                    TextView toptv = (TextView) findViewById(R.id.textView_bot);

                    if (Passwd.equals(Userpwd)){


                        Utils.loggedUser = new User(db.getUser(Email_addr).getId(),db.getUser(Email_addr).getEmail(),db.getUser(Email_addr).getPasswd());
                        toptv.setText("Login Success! Welcome back ...");
                        //Intent intent = new Intent(getApplicationContext(),DatabaseConnectionsActivity.class);
                        //startActivity(intent);



                        // start the galery in the fullscreenactivity
                   /*     setContentView(R.layout.activity_fulscreen_view);
                        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                        Utils utils = new Utils(getApplicationContext());
                        Intent i = getIntent();
                        int position = i.getIntExtra("position", 0);
                        FullScreenImageAdapter adapter = new FullScreenImageAdapter(MainActivity.this,
                                utils.getFilePaths());
                        viewPager.setAdapter(adapter);
                        // displaying selected image first
                        viewPager.setCurrentItem(position);*/

                        //Intent intent = new Intent(getApplicationContext(),AfterLogin.class);
                        //startActivity(intent);


                        Intent intent = new Intent(getApplicationContext(),DirectoryChooserActivity.class);
                        startActivity(intent);


                    }
                    else {
                        toptv.setText("Login Failed! Incorrect password please try again.");
                    }


                }
                catch (IndexOutOfBoundsException e) {
                    Log.d("azyl13"," User not found ... in the db "+e);
                    TextView toptv = (TextView) findViewById(R.id.textView_bot);
                    toptv.setText("Invalid Credentials! If you are a new user please Sign-up");
                }

            }
        }


    }

    //    Signup Button action
    public void doSignup(View view) {

        DatabaseHandler db = new DatabaseHandler(this);
        //db.onUpgrade(db.getWritableDatabase(), 1, 1);

        EditText Email_addr_et = (EditText) findViewById(R.id.editText_email_adress);
        String Email_addr = Email_addr_et.getText().toString();

        EditText Passwd_et = (EditText) findViewById(R.id.editText_password);
        String Passwd = Passwd_et.getText().toString();


        if (Email_addr.length()==0){
            TextView toptv = (TextView) findViewById(R.id.textView_bot);
            toptv.setText("Email cannot be empty");
        }
        else {
            if (Passwd.length()==0){
                TextView toptv = (TextView) findViewById(R.id.textView_bot);
                toptv.setText("Password cannot be empty");
            }
            else {
                Log.d("azyl13","Checking if the user exists");

                try {
                    String Userid = String.valueOf(db.getUser(Email_addr).getId());
                    Log.d("azyl13", "The userid is " + Userid);
                    Log.d("azyl13","User exists in the database");

                    TextView toptv = (TextView) findViewById(R.id.textView_bot);
                    toptv.setText("This account is already defined");
                }
                catch (IndexOutOfBoundsException e){
                    Log.d("azyl13", "Inserting new user "+e);
                    db.addUser(new User(Email_addr, Passwd));
                    Log.d("azyl13", "New user created");

                    String Userid = String.valueOf(db.getUser(Email_addr).getId());
                    Log.d("azyl13", "The userid is " + Userid);


                    Log.d("azyl13", "Inserting the default folder");
                    db.addFolder(new Folder(Userid,android.os.Environment.getExternalStorageDirectory()+"/Pictures/9GAG","F","Y"));


                    TextView toptv = (TextView) findViewById(R.id.textView_bot);
                    toptv.setText("Signup success!");
                }

                /*
                INSERT INTO memos(id,text)
                SELECT 5, 'text to insert'
                WHERE NOT EXISTS(SELECT 1 FROM memos WHERE id = 5 AND text = 'text to insert');
                 */



            }
        }




    }


        @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // Check internet Status
            // flag for Internet connection status
            Boolean isInternetPresent = false;

            // Connection detector class
            ConnectionDetector cd;

            // creating connection detector class instance
            cd = new ConnectionDetector(getApplicationContext());

            // get Internet status
            isInternetPresent = cd.isConnectingToInternet();

            // check for Internet status
            if (isInternetPresent) {
                // Internet Connection is Present
                // make HTTP requests
                showAlertDialog(MainActivity.this, "Internet Connection", "You have internet connection", true);
            } else {
                // Internet connection is not present
                // Ask user to connect to Internet
                showAlertDialog(MainActivity.this, "No Internet Connection",
                        "You don't have internet connection.", false);
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
