package darren.gcptts.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

import darren.gcptts.R;

public class firstPage extends AppCompatActivity {

    LinearLayout saved,upload,user;
    Context mContext;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        saved = findViewById(R.id.database);
        upload = findViewById(R.id.upload);
        user = findViewById(R.id.userNews);

        deleteCache(getApplicationContext());

        mPreferences = this.getSharedPreferences("Checker",MODE_PRIVATE);
        mEditor = mPreferences.edit();

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putString("Day1", "nono");
                mEditor.apply();
                Intent i = new Intent(firstPage.this, savedPosts.class);
                startActivity(i);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putString("Day1", "nono");
                mEditor.apply();
                Intent j = new Intent(firstPage.this, MainActivity.class);
                startActivity(j);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putString("Day1", "yesGo");
                mEditor.apply();
                Intent k = new Intent(firstPage.this, userUploads.class);
                startActivity(k);
            }
        });

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}