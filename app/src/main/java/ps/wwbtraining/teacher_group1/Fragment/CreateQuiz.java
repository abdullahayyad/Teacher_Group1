package ps.wwbtraining.teacher_group1.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Class.AnwerQuestion;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupInsert;
import ps.wwbtraining.teacher_group1.Model.InsertInToQuiz;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.QuestionItem;
import ps.wwbtraining.teacher_group1.Model.QuizModel;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.NoInternetAlert;
import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;


public class CreateQuiz extends Fragment {
    private EditText tvnameQuize;
    private EditText tvDiscription;
    private EditText tvQuestion;
    private EditText tvDeadline;
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
    private View customView;


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
        customView = view.findViewById(R.id.creatQuiz);

        findViews(view);
        visibleTF.setVisibility(View.VISIBLE);
        visibleChoose.setVisibility(View.GONE);

        return view;
    }

    private void findViews(View view) {
        tvnameQuize = (EditText) view.findViewById(R.id.tvnameQuize);
        tvDiscription = (EditText) view.findViewById(R.id.tvDiscription);
        tvQuestion = (EditText) view.findViewById(R.id.tvQuestion);
        tvDeadline = (EditText) view.findViewById(R.id.tvdeadlineQuiz);
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
                String deadLine = tvDeadline.getText().toString();
                String questionName = tvQuestion.getText().toString();
                String ans1 = etch1.getText().toString();
                String ans2 = etch2.getText().toString();
                String ans3 = etch3.getText().toString();
                String ans4 = etch4.getText().toString();


                if (!(nameQuiz.isEmpty() || description.isEmpty() || questionName.isEmpty()||deadLine.isEmpty())) {
                    if (rgType.getCheckedRadioButtonId() == R.id.raChoose) {
                        if (!(ans1.isEmpty() || ans2.isEmpty() || ans3.isEmpty() || ans4.isEmpty()) &&
                                (rach1.isChecked() || rach2.isChecked() || rach3.isChecked() || rach4.isChecked())) {


                            tvnameQuize.setEnabled(false);
                            tvDiscription.setEnabled(false);
                            tvDeadline.setEnabled(false);

                            answerMap.put(index, new AnwerQuestion(ans1, ans2, ans3, ans4));
                            questionMap.put(index, questionName);
                            stateQuestion.put(index, 1);

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
                            tvDeadline.setEnabled(false);
                            rgType.setEnabled(false);

                            if (visibleTF.getCheckedRadioButtonId() == R.id.rbtrue)
                                correctMap.put(index, "true");
                            else
                                correctMap.put(index, "false");

                            questionMap.put(index, tvQuestion.getText().toString());
                            stateQuestion.put(index, 0);

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
                Log.d("answer",  answerMap.toString());
                Log.d("question",questionMap.toString());

                ArrayList array =new ArrayList();
                HashMap map = new HashMap();
                HashMap answer =new HashMap();
                for (int i = 0 ; i < questionMap.size() ; i++){
                    map.put("statement",questionMap.get(i));
                    map.put("correct",correctMap.get(i));
                    map.put("type",stateQuestion.get(i));

                    if (stateQuestion.get(i)==1){
                        answer.put("ans1",answerMap.get(i).getAns1());
                        answer.put("ans2",answerMap.get(i).getAns2());
                        answer.put("ans3",answerMap.get(i).getAns3());
                        answer.put("ans4",answerMap.get(i).getAns4());

                           map.put("answers",answer);
                    }

                    JSONObject object = new JSONObject(map);
                    array.add(object);
                    map.clear();
                }


                                        HashMap testMap = new HashMap<String, String>();
                                        testMap.put("name_quiz",tvnameQuize.getText().toString());
                                        testMap.put("description",tvDiscription.getText().toString());
                                        testMap.put("deadline",tvDeadline.getText().toString());
                                        testMap.put("questions",array);

                                        Log.d("testMap", testMap + " ");

                                        JSONObject jsonObject = new JSONObject(testMap);
                                        Log.d("jsonObject", jsonObject + " ");
                                        teacherApi.addArrayQuiz(
                                                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject)))
                                                .enqueue(new Callback<InsertInToQuiz>() {

                                                    @Override
                                            public void onResponse(Call <InsertInToQuiz>  call, Response <InsertInToQuiz> response) {

                                                if (response.isSuccessful()) {

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call <InsertInToQuiz>call, Throwable t) {
                                                Toast.makeText(getActivity(), "No Internet ", Toast.LENGTH_SHORT).show();
                                            }
                                        });





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
                    Intent intent = new Intent(getActivity(), TeacherActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                    return true;

                }
                return false;
            }
        });
    }

}
