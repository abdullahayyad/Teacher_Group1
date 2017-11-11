package ps.wwbtraining.teacher_group1.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Adapter.ShowQuestionAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.InsertInToQuiz;
import ps.wwbtraining.teacher_group1.Model.QuesInsertModel;
import ps.wwbtraining.teacher_group1.Model.QuesItem;
import ps.wwbtraining.teacher_group1.Model.ShowQuesModel;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hanan Dawod on 31/10/17.
 */

public class ShowQuestionFragment  extends Fragment {

    TeacherApi teacherApi;
    RecyclerView list_question;
    TextView addQuestion;
    ArrayList<QuesItem> array = new ArrayList<>();
    ShowQuestionAdapter showQuestionAdapter;
    private int myPosition;
    int quiz_id;
    int quesType;
    String correctAns;
    private TextView tvCancel;
    private TextView tvRecreate;
    private EditText nameQuizEdit;
    private EditText descriptionQuizEdit;
    private TextView nameQuiz;
    private String quiz_name;
    private String quiz_description;
    private Button saveBt;
    private ImageButton editButton ;
    private LinearLayout layoutEdit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quiz_id=getArguments().getInt("quiz_id");
        quiz_name = getArguments().getString("quiz_name");
        quiz_description = getArguments().getString("quiz_description");
        Log.d("quiz",""+quiz_id);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.show_question, null, false);
        teacherApi = ApiTeacher.getAPIService();
        list_question = (RecyclerView) view.findViewById(R.id.list_question);
        addQuestion = (TextView) view.findViewById(R.id.addQuestion);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        nameQuizEdit=(EditText)view.findViewById(R.id.nameQuizEdit);
        descriptionQuizEdit=(EditText)view.findViewById(R.id.descriptionQuizEdit);
        nameQuiz =(TextView)view.findViewById(R.id.nameQuiz);
        saveBt =(Button)view.findViewById(R.id.saveEdit);
        editButton =(ImageButton)view.findViewById(R.id.editButton);
        layoutEdit= (LinearLayout)view.findViewById(R.id.layoutEdit);

        nameQuizEdit.setText(quiz_name);
        descriptionQuizEdit.setText(quiz_description);
        nameQuiz.setText(quiz_name);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutEdit.setVisibility(View.VISIBLE);

            }
        });

        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherApi.updateQuiz(nameQuizEdit.getText().toString()
                        ,descriptionQuizEdit.getText().toString(),quiz_id).enqueue(new Callback<InsertInToQuiz>() {
                    @Override

                    public void onResponse(Call<InsertInToQuiz> call, Response<InsertInToQuiz> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isResult()) {
                                layoutEdit.setVisibility(View.GONE);
                                nameQuiz.setText(nameQuizEdit.getText().toString());

                            }
                        }
                    }

                    @Override

                    public void onFailure(Call<InsertInToQuiz> call, Throwable t) {
                        Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();

                    }

                });



            }
        });


         tvRecreate = (TextView) view.findViewById(R.id.tvRecreate);
         tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.show_qestion, new ShowQuizFragment ()
                        ).commit();

            }
        });
        tvRecreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction().addToBackStack(null)
//                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
//                        .replace(R.id.show_qestion, new E ()
//                        ).commit();

            }
        });

        teacherApi.showQues(quiz_id).enqueue(new Callback<ShowQuesModel>() {
            @Override

            public void onResponse(Call<ShowQuesModel> call, Response<ShowQuesModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {
                        array = response.body().getUser();

                        Log.d("arrrrrrrr",array.toString());
                        showQuestionAdapter = new ShowQuestionAdapter(ShowQuestionFragment.this, array);
                        list_question.setAdapter(showQuestionAdapter);
                        list_question.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }
//                    else
//                        Toast.makeText(getActivity(), "error123", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<ShowQuesModel> call, Throwable t) {
                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();

            }

        });

        addQuestion.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               final Dialog dialog = new Dialog(getActivity());
                                               dialog.setContentView(R.layout.add_question_dialog);

                                               dialog.setTitle("Add Question");
                                               final EditText quesStatement = (EditText)dialog.findViewById(R.id.statement);
                                               final EditText ans1 = (EditText)dialog.findViewById(R.id.editAns1);
                                               final EditText ans2 = (EditText)dialog.findViewById(R.id.editAns2);
                                               final EditText ans3 = (EditText)dialog.findViewById(R.id.editAns3);
                                               final EditText ans4 = (EditText)dialog.findViewById(R.id.editAns4);

                                               final RadioGroup radioGroup1 = (RadioGroup)dialog.findViewById(R.id.trueQuestion);
                                               final RadioGroup radioGroup2 = (RadioGroup)dialog.findViewById(R.id.choiceQues);
                                               final LinearLayout layout = (LinearLayout)dialog.findViewById(R.id.layoutChooseQues);
                                               final RadioButton trueFalse = (RadioButton)dialog.findViewById(R.id.raObtion);
                                               final RadioButton chooseQues = (RadioButton)dialog.findViewById(R.id.raChoose);
                                               trueFalse.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       layout.setVisibility(View.INVISIBLE);
                                                       radioGroup1.setVisibility(View.VISIBLE);
                                                       quesType = 0;

                                                   }
                                               });
                                               chooseQues.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       radioGroup1.setVisibility(View.INVISIBLE);
                                                       layout.setVisibility(View.VISIBLE);
                                                       quesType = 1;
                                                   }
                                               });

                                               final RadioButton trueAns = (RadioButton) dialog.findViewById(R.id.trueAns);
                                               final RadioButton falseAns = (RadioButton) dialog.findViewById(R.id.falseAns);

                                               final RadioButton anss1 = (RadioButton) dialog.findViewById(R.id.ans1);
                                               final RadioButton anss2 = (RadioButton) dialog.findViewById(R.id.ans2);
                                               final RadioButton anss3 = (RadioButton) dialog.findViewById(R.id.ans3);
                                               final RadioButton anss4 = (RadioButton) dialog.findViewById(R.id.ans4);

                                               trueAns.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(trueAns.isChecked()){
                                                           correctAns = "true";
                                                       }
                                                   }
                                               });

                                               falseAns.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(falseAns.isChecked()){
                                                           correctAns = "false";
                                                       }
                                                   }
                                               });

                                               anss1.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss1.isChecked()){
                                                           correctAns = "a";
                                                       }
                                                   }
                                               });
                                               anss2.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss2.isChecked()){
                                                           correctAns = "b";
                                                       }
                                                   }
                                               });
                                               anss3.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss3.isChecked()){
                                                           correctAns = "c";
                                                       }
                                                   }
                                               });
                                               anss4.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss4.isChecked()){
                                                           correctAns = "d";
                                                       }
                                                   }
                                               });

                                               Button save = (Button) dialog.findViewById(R.id.save);
                                               save.setOnClickListener(new View.OnClickListener() {

                                                   @Override
                                                   public void onClick(View view) {
                                                       if(!(quesStatement.getText().toString().isEmpty())){

                                                           if(quesType == 0){
                                                               if(trueAns.isChecked()||(falseAns.isChecked())) {
                                                                   teacherApi.insertQues(0, quesStatement.getText().toString(), quiz_id, correctAns).
                                                                           enqueue(new Callback<QuesInsertModel>() {
                                                                               @Override
                                                                               public void onResponse(Call<QuesInsertModel> call, Response<QuesInsertModel> response) {

                                                                                   Log.d("Question_id", "555" + response.body().getQuestion_id() + "");
                                                                                   Toast.makeText(getActivity(), "Question was added", Toast.LENGTH_SHORT).show();
//
                                                                                   dialog.cancel();


                                                                               }

                                                                               @Override
                                                                               public void onFailure(Call<QuesInsertModel> call, Throwable t) {
                                                                                   Toast.makeText(getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

                                                                               }
                                                                           });
                                                               }
                                                               else
                                                                   Toast.makeText(getActivity(),"Choose Correct Answer",Toast.LENGTH_SHORT).show();


                                                           }
                                                           else if(quesType==1){
                                                               if(!(ans1.getText().toString().isEmpty())&&!(ans2.getText().toString().isEmpty())&&
                                                                       !(ans3.getText().toString().isEmpty())&&!(ans4.getText().toString().isEmpty())
                                                                       &&((anss1.isChecked())||(anss2.isChecked())||(anss3.isChecked())||(anss4.isChecked()))){
                                                                   teacherApi.insertQues(1,quesStatement.getText().toString(),quiz_id,correctAns).
                                                                           enqueue(new Callback<QuesInsertModel>() {
                                                                               @Override
                                                                               public void onResponse(Call<QuesInsertModel> call, Response<QuesInsertModel> response) {
                                                                                   Log.d("Question_id", response.body().getQuestion_id()+"");
                                                                                   Toast.makeText(getActivity(), "Question was added", Toast.LENGTH_SHORT).show();
                                                                                   dialog.cancel();

                                                                               }

                                                                               @Override
                                                                               public void onFailure(Call<QuesInsertModel> call, Throwable t) {
                                                                                   Toast.makeText(getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

                                                                               }
                                                                           });
                                                               }
                                                               else
                                                                   Toast.makeText(getActivity(), "Choose Correct Answer", Toast.LENGTH_SHORT).show();
                                                           }

                                                       }
                                                       else
                                                           Toast.makeText(getActivity(), "Write Question... ", Toast.LENGTH_SHORT).show();

                                                   }

                                               });
                                               dialog.show();

                                           }

                                       });

                                      return view;
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
                    DrawerLayout navigationView = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    if (navigationView.isDrawerOpen(GravityCompat.START))
                        navigationView.closeDrawers();
                    else {
                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                .replace(R.id.show_qestion, new ShowQuizFragment()
                                ).commit();
                    }
                    return true;

                }
                return false;
            }
        });

    }


}
