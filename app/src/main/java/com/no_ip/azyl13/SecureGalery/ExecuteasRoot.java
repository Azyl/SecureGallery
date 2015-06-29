package com.no_ip.azyl13.SecureGalery;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by AndreiTataru on 6/26/2015.
 */
public class ExecuteasRoot extends ExecuteAsRootBase{

    public ExecuteasRoot(){
        super.execute();


        Process p;
        try {
            // Preform su to get root privledges
            p = Runtime.getRuntime().exec("su");

            // Attempt to write a file to a root-only
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n");

            // Close the terminal
            os.writeBytes("exit\n");
            os.flush();
            try {
                p.waitFor();
                if (p.exitValue() != 255) {
                    // TODO Code to run on success
                    Log.d("azyl13", "root");
                }
                else {
                    // TODO Code to run on unsuccessful
                    Log.d("azyl13", "not root");
                }
            } catch (InterruptedException e) {
                // TODO Code to run in interrupted exception
                Log.d("azyl13", "not root");
            }
        } catch (IOException e) {
            // TODO Code to run in input/output exception
            Log.d("azyl13","not root");
        }

    }


    @Override
    protected ArrayList<String> getCommandsToExecute() {
        String com1 = "/system/bin/ping -c 8 8.8.8.8";
        ArrayList<String> comands = new ArrayList<>();
        comands.add(com1);
        return comands;
    }


}
