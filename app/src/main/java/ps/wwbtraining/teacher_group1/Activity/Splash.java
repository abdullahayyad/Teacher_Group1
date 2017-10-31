package ps.wwbtraining.teacher_group1.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ps.wwbtraining.teacher_group1.R;
import ps.wwbtraining.teacher_group1.WebService.SharedPrefUtil;

import static ps.wwbtraining.teacher_group1.Class.Utils.RESULT_SHARED_PREF;

public class Splash extends AppCompatActivity {
    SharedPrefUtil sharedPrefUtil;
    private TextView tvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvSplash=(TextView)findViewById(R.id.tvSplash);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Swiftel_Base.ttf");
        tvSplash.setTypeface(type);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TeacherActivity login = new TeacherActivity();
                sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
                if (sharedPrefUtil.getBoolean(RESULT_SHARED_PREF)) {
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
