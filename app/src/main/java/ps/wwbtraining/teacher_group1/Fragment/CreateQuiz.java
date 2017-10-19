package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import ps.wwbtraining.teacher_group1.R;


public class CreateQuiz extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_create_quiz, container, false);
        RadioGroup radioGroup =view.findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.d("item",radioGroup.getCheckedRadioButtonId()+"");

            }
        });

        // Inflate the layout for this fragment
        return  view ;
    }

}
