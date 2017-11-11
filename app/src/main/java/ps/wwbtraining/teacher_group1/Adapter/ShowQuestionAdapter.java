package ps.wwbtraining.teacher_group1.Adapter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.Answer;
import ps.wwbtraining.teacher_group1.Model.QuesItem;
import ps.wwbtraining.teacher_group1.Model.ShowAnswerModel;
import ps.wwbtraining.teacher_group1.Model.ShowQuesModel;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hanan Dawod on 31/10/2017.
 */

public class ShowQuestionAdapter extends RecyclerView.Adapter<ShowQuestionAdapter.ViewHolder> {

    private final ArrayList<QuesItem> arrayList;
    Fragment context;
    TeacherApi teacherApi;
    ArrayList<Answer>answer = new ArrayList<>();
    ArrayList<QuesItem> correct=new ArrayList<>();
    public int index;


    public ShowQuestionAdapter(Fragment context, ArrayList<QuesItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        teacherApi = ApiTeacher.getAPIService();
        // array = new ArrayList<>();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = arrayList.get(position);
        holder.statement.setText(arrayList.get(position).getStatement());
        Log.d("statement",arrayList.get(position).getStatement());
        holder.question_id = arrayList.get(position).getQuestionId();
        holder.question_type = arrayList.get(position).getQuestion_type();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UpdateFlagQues(holder.question_id);

                                arrayList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });





        holder.edit.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               holder.question_id = arrayList.get(position).getQuestionId();
                                               final Dialog dialog = new Dialog(context.getActivity());
                                               dialog.setContentView(R.layout.answer_dialog);
                                               dialog.setTitle("Edit Answer");
                                               final EditText quesStatement = (EditText)dialog.findViewById(R.id.statement);
                                               final EditText ans1 = (EditText)dialog.findViewById(R.id.editAns1);
                                               final EditText ans2 = (EditText)dialog.findViewById(R.id.editAns2);
                                               final EditText ans3 = (EditText)dialog.findViewById(R.id.editAns3);
                                               final EditText ans4 = (EditText)dialog.findViewById(R.id.editAns4);

                                               final RadioGroup radioGroup1 = (RadioGroup)dialog.findViewById(R.id.trueQuestion);
                                               final RadioGroup radioGroup2 = (RadioGroup)dialog.findViewById(R.id.choiceQues);
                                               final LinearLayout layout = (LinearLayout)dialog.findViewById(R.id.layoutChooseQues);
                                               if(holder.question_type == 0){
                                                   layout.setVisibility(View.INVISIBLE);
                                                   radioGroup1.setVisibility(View.VISIBLE);
                                               }
                                               else if(holder.question_type == 1){
                                                   radioGroup1.setVisibility(View.INVISIBLE);
                                                   layout.setVisibility(View.VISIBLE);
                                               }
                                               Log.d("update",holder.question_id+"");
                                               teacherApi.showAnswer(holder.question_id).enqueue(new Callback<ShowAnswerModel>() {
                                                   @Override
                                                   public void onResponse(Call<ShowAnswerModel> call, Response<ShowAnswerModel> response) {
                                                       if(response.body().isResult()){
                                                           answer = response.body().getUser();
                                                           Log.d("showAnswer",answer.toString());
                                                           for(int i=0;i<answer.size();i++) {
                                                               ans1.setText(answer.get(i).getAns1());
                                                               ans2.setText(answer.get(i).getAns2());
                                                               ans3.setText(answer.get(i).getAns3());
                                                               ans4.setText(answer.get(i).getAns4());
                                                           }
                                                       }
                                                       else
                                                           Log.d("error","error");
                                                   }

                                                   @Override
                                                   public void onFailure(Call<ShowAnswerModel> call, Throwable t) {
                                                       Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

                                                   }
                                               });


                                               final RadioButton trueAns = (RadioButton) dialog.findViewById(R.id.trueAns);
                                               final RadioButton falseAns = (RadioButton) dialog.findViewById(R.id.falseAns);

                                               final RadioButton anss1 = (RadioButton) dialog.findViewById(R.id.ans1);
                                               final RadioButton anss2 = (RadioButton) dialog.findViewById(R.id.ans2);
                                               final RadioButton anss3 = (RadioButton) dialog.findViewById(R.id.ans3);
                                               final RadioButton anss4 = (RadioButton) dialog.findViewById(R.id.ans4);

//                                               teacherApi.showCorrectAns(holder.question_id).enqueue(new Callback<ShowQuesModel>() {
//                                                   @Override
//                                                   public void onResponse(Call<ShowQuesModel> call, Response<ShowQuesModel> response) {
//                                                       if(response.body().isResult())
//                                                           correct = response.body().getUser();
//                                                       Log.d("getCorrectAnswer()",correct.toString());
//
//                                                       for(int i=0;i<correct.size();i++){
//
//                                                           Log.d("getCorrectAnswer()",correct.get(i).getCorrectAnswer());
//
//                                                           if(correct.get(i).getCorrectAnswer().equals("true")){
//                                                               trueAns.setChecked(true);
//                                                           }
//
//                                                           else if(correct.get(i).getCorrectAnswer().equals("false")){
//                                                               falseAns.setChecked(true);
//                                                           }
//                                                           else if(correct.get(i).getCorrectAnswer().equals("1")){
//                                                               anss1.setChecked(true);
//                                                           }
//                                                           else if(correct.get(i).getCorrectAnswer().equals("2")){
//                                                               anss2.setChecked(true);
//                                                           }
//                                                           else if(correct.get(i).getCorrectAnswer().equals("3")){
//                                                               anss3.setChecked(true);
//                                                           }
//                                                           else if(correct.get(i).getCorrectAnswer().equals("4")){
//                                                               anss4.setChecked(true);
//                                                           }
//
//
//                                                       }
//                                                       Log.d("cccc",correct.toString());
//
//                                                   }
//
//                                                   @Override
//                                                   public void onFailure(Call<ShowQuesModel> call, Throwable t) {
//                                                       Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
//
//                                                   }
//                                               });


                                               trueAns.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(trueAns.isChecked()){
                                                           UpdateCorrectAns(holder.question_id,"true");
                                                           //update correct answer
                                                       }
                                                   }
                                               });

                                               falseAns.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(falseAns.isChecked()){
                                                           UpdateCorrectAns(holder.question_id,"false");
                                                           //update correct answer
                                                       }
                                                   }
                                               });

                                               anss1.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss1.isChecked()){
                                                           UpdateCorrectAns(holder.question_id,"1");
                                                           //update correct answer
                                                       }
                                                   }
                                               });
                                               anss2.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss2.isChecked()){
                                                           UpdateCorrectAns(holder.question_id,"2");
                                                           //update correct answer
                                                       }
                                                   }
                                               });
                                               anss3.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss3.isChecked()){
                                                           UpdateCorrectAns(holder.question_id,"3");

                                                           //update correct answer
                                                       }
                                                   }
                                               });
                                               anss4.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if(anss4.isChecked()){
                                                           UpdateCorrectAns(holder.question_id,"4");
                                                           //update correct answer
                                                       }
                                                   }
                                               });


                                               quesStatement.setText(holder.statement.getText().toString());
                                               Button save = (Button) dialog.findViewById(R.id.save);
                                               save.setOnClickListener(new View.OnClickListener() {

                                                   @Override
                                                   public void onClick(View view) {
                                                       UpdateStatement(holder.question_id, quesStatement.getText().toString());
                                                       UpdateAns1(holder.question_id,ans1.getText().toString());
                                                       UpdateAns2(holder.question_id,ans2.getText().toString());
                                                       UpdateAns3(holder.question_id,ans3.getText().toString());
                                                       UpdateAns4(holder.question_id,ans4.getText().toString());
                                                       arrayList.get(position).setStatement(quesStatement.getText().toString());
                                                       notifyDataSetChanged();

                                                       //notifyDataSetChanged();
                                                       dialog.cancel();
                                                       notifyDataSetChanged();

                                                   }

                                               });
                                               dialog.show();
                                               // notifyDataSetChanged();
                                           }

                                       }

        );
    }

    int size = 0;

    @Override
    public int getItemCount() {
        try {
            size = arrayList.size();

        } catch (Exception e) {

        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView statement;
        public final ImageButton edit;
        public final ImageButton delete;
        public int question_id;
        public int question_type;

        public QuesItem mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            statement = (TextView) view.findViewById(R.id.statement);
            edit = (ImageButton) view.findViewById(R.id.editQues);
            delete = (ImageButton) view.findViewById(R.id.deleteQues);


        }

    }
    public void UpdateStatement(int question_id, String statement) {
        teacherApi.updateStatement(question_id, statement).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "insert");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void UpdateCorrectAns(int question_id, String correct_ans) {
        teacherApi.updateCorrectAns(question_id, correct_ans).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "correct");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void UpdateAns1(int question_id, String ans) {
        teacherApi.updateAns1(question_id, ans).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "correct");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UpdateAns2(int question_id, String ans) {
        teacherApi.updateAns2(question_id, ans).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "correct");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UpdateAns3(int question_id, String ans) {
        teacherApi.updateAns3(question_id, ans).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "correct");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UpdateAns4(int question_id, String ans) {
        teacherApi.updateAns4(question_id, ans).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "correct");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UpdateFlagQues(int question_id) {
        teacherApi.updateFlagQues(question_id, 0).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "insert");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }

}