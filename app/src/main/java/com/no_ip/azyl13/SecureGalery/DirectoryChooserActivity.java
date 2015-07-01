package com.no_ip.azyl13.SecureGalery;

/**
 * Created by AndreiTataru on 6/27/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DirectoryChooserActivity extends Activity
{

    public void onUseDefault(View view){
        setContentView(R.layout.activity_fulscreen_view);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        Utils utils = new Utils(getApplicationContext());
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        FullScreenImageAdapter adapter = new FullScreenImageAdapter(DirectoryChooserActivity.this,
                utils.getFilePaths());
        viewPager.setAdapter(adapter);
        // displaying selected image first
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onResume(){
        DatabaseHandler db = new DatabaseHandler(this);


        TextView topTextV = (TextView) findViewById(R.id.textView_topD);


        Folder UserDefaultFolder = db.getFolderDefault(Utils.loggedUser);

        EditText DefDir = (EditText) findViewById(R.id.editText_default_Path);
        DefDir.setText(UserDefaultFolder.getPath());

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_chooser);


        DatabaseHandler db = new DatabaseHandler(this);


        TextView topTextV = (TextView) findViewById(R.id.textView_topD);
        topTextV.setText(Utils.loggedUser.getEmail()); //set the user

        Folder UserDefaultFolder = db.getFolderDefault(Utils.loggedUser);

        EditText DefDir = (EditText) findViewById(R.id.editText_default_Path);
        DefDir.setText(UserDefaultFolder.getPath());

        Utils.PHOTO_ALBUM = UserDefaultFolder.getPath();

        Button dirChooserButton = (Button) findViewById(R.id.chooseDirButton);
        dirChooserButton.setOnClickListener(new OnClickListener()
        {
            private String m_chosenDir = "";
            private boolean m_newFolderEnabled = true;

            @Override
            public void onClick(View v)
            {
                // Create DirectoryChooserDialog and register a callback
                DirectoryChooserDialog directoryChooserDialog =
                        new DirectoryChooserDialog(DirectoryChooserActivity.this,
                                new DirectoryChooserDialog.ChosenDirectoryListener()
                                {
                                    @Override
                                    public void onChosenDir(String chosenDir)
                                    {
                                        m_chosenDir = chosenDir;
                                        Toast.makeText(
                                                DirectoryChooserActivity.this, "Chosen directory: " +
                                                        chosenDir, Toast.LENGTH_LONG).show();

                                        Utils.PHOTO_ALBUM = chosenDir;
                                        Log.d("azyl13", chosenDir);
                                        //
                                        EditText DefDir = (EditText) findViewById(R.id.editText_default_Path);
                                        DefDir.setText(Utils.PHOTO_ALBUM);





                                        //

                                    }
                                });
                // Toggle new folder button enabling
                directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
                // Load directory chooser dialog for initial 'm_chosenDir' directory.
                // The registered callback will be called upon final directory selection.
                directoryChooserDialog.chooseDirectory(m_chosenDir);
                m_newFolderEnabled = ! m_newFolderEnabled;
            }




        });




    }
}