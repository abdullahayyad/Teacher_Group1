package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ps.wwbtraining.teacher_group1.R;


public class CreateQuiz extends Fragment {
    private EditText tvnameQuize;
    private EditText tvDiscription;
    private EditText tvQuestion;
    private RadioButton raObtion;
    private RadioButton raChoose;
    private FrameLayout frameQuestion;
    private RadioButton rach1;
    private RadioButton rach2;
    private RadioButton rach3;
    private RadioButton rach4;
    private EditText etch1;
    private EditText etch2;
    private EditText etch3;
    private EditText etch4;
    private RadioButton rbtrue;
    private RadioButton rbfalse;
    private TextView buAdd;
    private TextView buSubmit;
    private View view;
    private LinearLayout visibleChoose;
    private RadioGroup visibleTF;
    private RadioGroup rgChoose;
    private RadioGroup rgType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_quiz, container, false);
        findViews(view);
        visibleTF.setVisibility(View.VISIBLE);
        visibleChoose.setVisibility(View.GONE);
        //enabelView();

        return view;
    }

    private void findViews(View view) {
        tvnameQuize = (EditText) view.findViewById(R.id.tvnameQuize);
        tvDiscription = (EditText) view.findViewById(R.id.tvDiscription);
        tvQuestion = (EditText) view.findViewById(R.id.tvQuestion);
        raObtion = (RadioButton) view.findViewById(R.id.raObtion);
        raChoose = (RadioButton) view.findViewById(R.id.raChoose);
        frameQuestion = (FrameLayout) view.findViewById(R.id.frameQuestion);
        visibleTF = (RadioGroup) view.findViewById(R.id.visibleTF);
        rgType = (RadioGroup) view.findViewById(R.id.rgType);
        rgChoose = (RadioGroup) view.findViewById(R.id.rgChoose);
        visibleChoose = (LinearLayout) view.findViewById(R.id.visibleChoose);
        rach1 = (RadioButton) view.findViewById(R.id.rach1);
        rach2 = (RadioButton) view.findViewById(R.id.rach2);
        rach3 = (RadioButton) view.findViewById(R.id.rach3);
        rach4 = (RadioButton) view.findViewById(R.id.rach4);
        etch1 = (EditText) view.findViewById(R.id.etch1);
        etch2 = (EditText) view.findViewById(R.id.etch2);
        etch3 = (EditText) view.findViewById(R.id.etch3);
        etch4 = (EditText) view.findViewById(R.id.etch4);
        rbtrue = (RadioButton) view.findViewById(R.id.rbtrue);
        rbfalse = (RadioButton) view.findViewById(R.id.rbfalse);
        buAdd = (TextView) view.findViewById(R.id.buAdd);
        buSubmit = (TextView) view.findViewById(R.id.buSubmit);

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.raObtion) {
                    visibleTF.setVisibility(View.VISIBLE);
                    visibleChoose.setVisibility(View.GONE);

                } else if (checkedId == R.id.raChoose) {
                    visibleTF.setVisibility(View.GONE);
                    visibleChoose.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
