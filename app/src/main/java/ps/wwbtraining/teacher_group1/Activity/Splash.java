package ps.wwbtraining.teacher_group1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import ps.wwbtraining.teacher_group1.R;
import ps.wwbtraining.teacher_group1.WebService.SharedPrefUtil;

import static ps.wwbtraining.teacher_group1.Class.Utils.STATUS_SHARED_PREF;

public class Splash extends AppCompatActivity {
    SharedPrefUtil sharedPrefUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TeacherActivity login = new TeacherActivity();
                sharedPrefUtil = new SharedPrefUtil(getApplicationContext());

                if (sharedPrefUtil.getBoolean(STATUS_SHARED_PREF)) {
                    startActivity(new Intent(Splash.this, TeacherActivity.class));
                    finish();
                } else {

                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);
    }
}
