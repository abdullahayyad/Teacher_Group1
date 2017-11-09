package ps.wwbtraining.teacher_group1.Adapter;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Fragment.ShowQuizFragment;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.QuizItem;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowQuizAdapter extends RecyclerView.Adapter<ShowQuizAdapter.ViewHolder> {

    private final ArrayList<QuizItem> arrayList;
    Fragment context;
    TeacherApi teacherApi;
    ArrayList<GroupItem> array;
    ArrayList<String> groupName = new ArrayList<>();
    ArrayList<Integer>arrayId = new ArrayList<>();
    private ActionMode mActionmode;
    OnItemLongClickListener listener;
    public  int post ;
    public  int index ;
    int quiz_id;
    HashMap hashmap = new HashMap<>();

    ShowQuizFragment showQuizFragment = new  ShowQuizFragment();
//, OnItemLongClickListener listener
    public ShowQuizAdapter(Fragment context, ArrayList<QuizItem> arrayList, OnItemLongClickListener onItemLongClickListener) {
        this.context = context;
        this.arrayList = arrayList;
//        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quize_list, parent, false);
        teacherApi = ApiTeacher.getAPIService();


        array = new ArrayList<>();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = arrayList.get(position);
        holder.quiz_name.setText(arrayList.get(position).getQuiz_name());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.quiz_id = arrayList.get(position).getQuiz_id();


        holder.cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemLongClicked(position);
                post = arrayList.get(position).getQuiz_id();
                index = position;
            }

        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                listener.onItemDelete(position);
                quiz_id = arrayList.get(position).getQuiz_id();
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UpdateFlagQuiz(quiz_id);
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

        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//
//                                final Dialog dialog = new Dialog(context.getActivity());
//                                dialog.setContentView(R.layout.group_dialog);
//                                dialog.setTitle("Group");
//                                ListView listView = (ListView) dialog.findViewById(R.id.listGroupDialog);
//                                listView.setAdapter(arrayAdapter);
//
//                                listView.setTextFilterEnabled(true);
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                        CheckedTextView checkedTextView = (CheckedTextView) view;
//                                          //  checkedTextView.setChecked(!);
//                                        checkedTextView.setChecked(!checkedTextView.isChecked());
//
//
//                                    }
//                                });
//
//                                Button quizSend = (Button) dialog.findViewById(R.id.send);
//                                quizSend.setOnClickListener(new View.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View view) {
//
//                                    }
//                                });
//
//                                Button quizSendAll = (Button) dialog.findViewById(R.id.sendAll);
//                                quizSendAll.setOnClickListener(new View.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View view) {
//                                        HashMap testMap = new HashMap<String, String>();
//                                        arrayId=new ArrayList();
//                                        for (int i = 0; i < array.size(); i++) {
//                                            arrayId.add(array.get(i).getGroup_id());
//                                        }
//                                        testMap.put("quiz_id",  arrayList.get(position).getQuiz_id());
//                                        testMap.put("group_id", arrayId);
//
//                                        Log.d("testMap", arrayId + " "+ arrayList.get(position).getQuiz_id());
//                                        final JSONObject jsonObject = new JSONObject(testMap);
//                                        Log.d("tttt", jsonObject.toString());
//
//                                        teacherApi.sendQuiz(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())).enqueue(new Callback<InsertIntoGroup>() {
//                                            @Override
//                                            public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
//                                                // Log.d("jsonnn",jsonObject.toString());
//                                                if (response.isSuccessful()) {
//                                                    Log.d("jsonnn",jsonObject.toString());
//                                                    Toast.makeText(context.getActivity(), "Send Quiz", Toast.LENGTH_SHORT).show();
////                                                    Log.d("//////", response.body().toString());
////                                                    if (pd != null && pd.isShowing())
////                                                        pd.dismiss();
////                                                } else {
//////                                                    Log.d("//////", response.body().toString());
//////                                                        // userManagementAdapter.notifyS);
////                                                    if (pd != null && pd.isShowing())
////                                                        pd.dismiss();
////                                                    customSnackBare(view, "No Internet Connection ....");
////                                                }
//                                                }}
//
//                                            @Override
//                                            public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
//                                                Toast.makeText(context.getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
//                                                //  Log.d("//////", t.toString());
////                                                if (pd != null && pd.isShowing())
////                                                    pd.dismiss();
////                                                NoInternetAlert(getActivity());
//                                            }
//                                        });
//                                        dialog.cancel();
//
//                                    }
//                                });
//
//                                dialog.show();
//
//                            } else
//                                Toast.makeText(context.getActivity(), "error123", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<GroupModel> call, Throwable t) {
//                        Toast.makeText(context.getActivity(), "faaa", Toast.LENGTH_SHORT).show();
//                        Log.d("ffff", "fff");
//                    }
//                });
            }
        });


    }
    public void UpdateFlagQuiz(int quiz_id) {
        teacherApi.updateFlagQuiz(quiz_id, 0).enqueue(new Callback<UpdateStatus>() {
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
        public final TextView quiz_name;
        public final ImageButton del;
        int quiz_id ;
        public final TextView description;
        public final ImageButton send;
        private final LinearLayout cr;
        public QuizItem mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            quiz_name = (TextView) view.findViewById(R.id.QuizeName);
            description = (TextView) view.findViewById(R.id.description);
            send = (ImageButton) view.findViewById(R.id.sendQuiz);
            del = (ImageButton) view.findViewById(R.id.deletQuiz);
            cr = (LinearLayout) view.findViewById(R.id.cardView_quize);
            quiz_id = 0;
        }

    }

}