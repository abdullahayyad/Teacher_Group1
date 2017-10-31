package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Class.AnwerQuestion;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.QuestionItem;
import ps.wwbtraining.teacher_group1.Model.QuizModel;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    HashMap<Integer, AnwerQuestion> answerMap;
    HashMap<Integer, String> correctMap;
    HashMap<Integer, String> questionMap;
    HashMap<Integer, Integer> stateQuestion;
    HashMap<Integer, QuestionItem> questionItemHashMap;

    int index = 0;

    TeacherApi teacherApi;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        answerMap = new HashMap<>();
        correctMap = new HashMap<>();
        questionMap = new HashMap<>();
        stateQuestion = new HashMap<>();
        questionItemHashMap = new HashMap<>();
        teacherApi = ApiTeacher.getAPIService();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_quiz, container, false);
        findViews(view);
        visibleTF.setVisibility(View.VISIBLE);
        visibleChoose.setVisibility(View.GONE);

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

        rgType.check(R.id.raObtion);

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

        buAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameQuiz = tvnameQuize.getText().toString();
                String description = tvDiscription.getText().toString();
                String deadLine = "";
                String questionName = tvQuestion.getText().toString();
                String ans1 = etch1.getText().toString();
                String ans2 = etch2.getText().toString();
                String ans3 = etch3.getText().toString();
                String ans4 = etch4.getText().toString();


                if (!(nameQuiz.isEmpty() || description.isEmpty() || questionName.isEmpty())) {
                    if (rgType.getCheckedRadioButtonId() == R.id.raChoose) {
                        if (!(ans1.isEmpty() || ans2.isEmpty() || ans3.isEmpty() || ans4.isEmpty()) &&
                                (rach1.isChecked() || rach2.isChecked() || rach3.isChecked() || rach4.isChecked())) {

                            if (index == 0) {

                                teacherApi.addQuiz(nameQuiz, description).enqueue(new Callback<QuizModel>() {
                                    @Override
                                    public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getActivity(), response.body()+"", Toast.LENGTH_SHORT).show();

                                            if (response.body().isResult()) {
                                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<QuizModel> call, Throwable t) {
                                        Toast.makeText(getContext(), "Sorry..internet disconnect", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }

                            tvnameQuize.setEnabled(false);
                            tvDiscription.setEnabled(false);

                            answerMap.put(index, new AnwerQuestion(ans1, ans2, ans3, ans4));
                            questionMap.put(index, questionName);
                            stateQuestion.put(index, 0);

                            if (rgChoose.getCheckedRadioButtonId() == R.id.rach1)
                                correctMap.put(index, 1 + "");
                            else if (rgChoose.getCheckedRadioButtonId() == R.id.rach2)
                                correctMap.put(index, 2 + "");
                            else if (rgChoose.getCheckedRadioButtonId() == R.id.rach3)
                                correctMap.put(index, 3 + "");
                            else
                                correctMap.put(index, 4 + "");


                            tvQuestion.setText("");
                            etch1.setText("");
                            etch2.setText("");
                            etch3.setText("");
                            etch4.setText("");
                            rgChoose.clearCheck();
                            index++;

                        } else {
                            Toast.makeText(getActivity(), "Add all fields", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        if (rbtrue.isChecked() || rbfalse.isChecked()) {


                            tvnameQuize.setEnabled(false);
                            tvDiscription.setEnabled(false);
                            rgType.setEnabled(false);

                            if (visibleTF.getCheckedRadioButtonId() == R.id.rbtrue)
                                correctMap.put(index, "true");
                            else
                                correctMap.put(index, "false");

                            questionMap.put(index, tvQuestion.getText().toString());
                            stateQuestion.put(index, 1);

                            visibleTF.clearCheck();
                            tvQuestion.setText("");
                            index++;

                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "Sorry...write all :(", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("correct", correctMap.toString());
                Log.d("answer", answerMap.toString());
                Log.d("question", questionMap.toString());

                getFragmentManager().popBackStack();

//                for (int i = 0; i < questionMap.size(); i++) {
//                    teacherApi.addQuestion(questionMap.get(i), ).enqueue(new Callback<QuizModel>() {
//                        @Override
//                        public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
//
//                            if (response.isSuccessful()) {
//                                if (response.body().isResult()) {
//
//                                } else {
//                                    Toast.makeText(getContext(), "Sorry..internet disconnect", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<QuizModel> call, Throwable t) {
//
//                        }
//                    });
//
//                }

            }
        });
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    DrawerLayout navigationView =(DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
                    if (navigationView.isDrawerOpen(GravityCompat.START))
                        navigationView.closeDrawers();
                    else
                        getFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                .replace(R.id.frameTeacher, new Teacher_Fragment()).commit();

                    return true;

                }
                return false;
            }
        });

    }
}
