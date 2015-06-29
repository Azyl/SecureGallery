package com.no_ip.azyl13.SecureGalery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class DatabaseConnectionsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_connections);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database_connections, menu);
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

    public String ping(String url) {
        String str = "";
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 8 " + url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));


            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());


            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();

            // body.append(output.toString()+"\n");
            str = output.toString();
            // Log.d(TAG, str);
        } catch (IOException e) {
            // body.append("Error\n");
            //e.printStackTrace()
            Log.d("azyl13","ping_error:");
            Log.d("azyl13", String.valueOf(e));
        }
        return str;
    }

    private boolean executeCommand(String url){
        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("su -c /system/bin/ping -c 1 " + url);
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();
            System.out.println(" Exception:"+ignore);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return false;
    }







    public void onPing (View view){

        ExecuteasRoot Excutor = new ExecuteasRoot();

        EditText editText_ServerAdr = (EditText) findViewById(R.id.editText_ping_url);
        String Url = editText_ServerAdr.getText().toString();
        Log.d("azyl13","Pinging ... "+Url);

        Log.d("azyl13",String.valueOf(executeCommand(Url)));


        String Ping_results = ping(Url);
        Log.d("azyl13",Ping_results);

    }
}
