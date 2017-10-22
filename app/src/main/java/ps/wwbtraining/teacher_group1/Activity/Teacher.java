package ps.wwbtraining.teacher_group1.Activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ps.wwbtraining.teacher_group1.Fragment.Teacher_Fragment;
import ps.wwbtraining.teacher_group1.R;

public class Teacher extends AppCompatActivity {


    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

       //     getSupportFragmentManager().beginTransaction().replace(R.id.frameTeacher,new Teacher_Fragment()).commit();

    }

}
