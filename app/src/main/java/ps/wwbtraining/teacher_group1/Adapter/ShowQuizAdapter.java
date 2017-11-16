package ps.wwbtraining.teacher_group1.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Fragment.ShowQuizFragment;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.QuizItem;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

public class ShowQuizAdapter extends RecyclerView.Adapter<ShowQuizAdapter.ViewHolder> {

    private final ArrayList<QuizItem> arrayList;
    Fragment context;
    TeacherApi teacherApi;
    ArrayList<GroupItem> array;
    ArrayList<GroupItem> arraySendId = new ArrayList<>();
    ArrayList<Integer>sendGroupId = new ArrayList<>();
    ArrayList<Integer>arrayId = new ArrayList<>();
    private ActionMode mActionmode;
    OnItemLongClickListener listener;
    public  int post ;
    public  int index ;
    int quiz_id;
    String[] groupName;
    HashMap hashmap = new HashMap<>();
    boolean[] checkedGroup;
    ProgressDialog pd;

    ShowQuizFragment showQuizFragment = new  ShowQuizFragment();
    private TeacherApi techerApi;

    public ShowQuizAdapter(Fragment context, ArrayList<QuizItem> arrayList, OnItemLongClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quize_list, parent, false);
        teacherApi = ApiTeacher.getAPIService();
        array = new ArrayList<>();
        teacherApi= ApiTeacher.getAPIService();
        pd = new ProgressDialog(context.getActivity());

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
                teacherApi.showGroup().enqueue(new Callback<GroupModel>() {
                    @Override

                    public void onResponse(final Call<GroupModel> call, Response<GroupModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isResult()) {
                                array = response.body().getGroup();
                                groupName = new String[array.size()];
                                checkedGroup = new boolean[array.size()];
                                for (int i = 0; i < array.size(); i++) {
                                    groupName[i] = array.get(i).getgroup_name();
                                    final ArrayList<Integer>sendId = new ArrayList<>();
                                     for (int j=0;j<sendId.size();j++) {
                                        if (sendId.get(j).equals(array.get(j).getGroup_id())) {
                                            checkedGroup[i] = true;
                                        } else
                                            checkedGroup[i] = false;
                                    }
                                }
                                final List<String> groupList = Arrays.asList(groupName);

                                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                                arrayId=new ArrayList();
                                builder.setMultiChoiceItems(groupName, checkedGroup, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        checkedGroup[which] = isChecked;
                                        String currentItem = groupList.get(which);

                                        arrayId.add(array.get(which).getGroup_id());
                                        Log.d("arrayId",arrayId.toString());


                                    }
                                });
                                builder.setCancelable(false);
                                builder.setTitle("Send Quiz");

                                builder.setPositiveButton("Send All", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                       //for (int i = 0; i < checkedGroup.length; i++) {
                                            //  boolean checked = checkedGroup[i];
                                            //checkedGroup[i] = true;
                                            //Log.d("checkedGroup[i]",checkedGroup[i]+"");

                                            HashMap testMap = new HashMap<String, String>();
                                           // arrayId=new ArrayList();
                                            for (int j = 0; j < array.size(); j++) {
                                                arrayId.add(array.get(j).getGroup_id());
                                            }
                                            testMap.put("quiz_id",  arrayList.get(position).getQuiz_id());
                                            testMap.put("group_id", arrayId);

                                            Log.d("testMap", arrayId + " "+ arrayList.get(position).getQuiz_id());
                                            final JSONObject jsonObject = new JSONObject(testMap);
                                            Log.d("tttt", jsonObject.toString());
                                            if (isOnline(context.getActivity())) {
                                                pd.setMessage("Send To All....");
                                                pd.setCancelable(false);
                                                pd.show();
                                            teacherApi.sendQuizToGroup(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())).enqueue(new Callback<InsertIntoGroup>() {
                                                @Override
                                                public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
                                                    Log.d("jsonnn",jsonObject.toString());
                                                    //if (response.isSuccessful()) {
                                                        if (pd != null && pd.isShowing()) {
                                                            pd.dismiss();

                                                        }
                                                      //  Log.d("jsonnn",jsonObject.toString());
                                                         dialog.cancel();

                                            //        }
                                            }

                                                @Override
                                                public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                                                   // Toast.makeText(context.getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                                                    if (pd != null && pd.isShowing()) {
                                                        pd.dismiss();

                                                    }
                                                }
                                            });
                                        }
                                    }
                                });


                                builder.setNegativeButton("Send", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                      //  for (int i = 0; i < checkedGroup.length; i++) {

                                            if(arrayId.size() != 0){
                                            Log.d("sendarray",checkedGroup.toString());

                                            HashMap testMap = new HashMap<String, String>();
                                            testMap.put("quiz_id",  arrayList.get(position).getQuiz_id());
                                            testMap.put("group_id", arrayId);

                                            Log.d("testMap", arrayId + " "+ arrayList.get(position).getQuiz_id());
                                            final JSONObject jsonObject = new JSONObject(testMap);
                                            Log.d("tttt", jsonObject.toString());
                                            if (isOnline(context.getActivity())) {
                                                pd.setMessage("Send To All....");
                                                pd.setCancelable(false);
                                                pd.show();
                                            teacherApi.sendQuizToGroup(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())).enqueue(new Callback<InsertIntoGroup>() {
                                                @Override
                                                public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
                                                    Log.d("jsonnn",jsonObject.toString());
                                                   // if (response.isSuccessful()) {
                                                        Log.d("jsonnn",jsonObject.toString());
                                                          if (pd != null && pd.isShowing()) {
                                                            pd.dismiss();

                                                        }
                                                        dialog.cancel();

                                                    //}
                                                }

                                                @Override
                                                public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                                                   // Toast.makeText(context.getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                                                    if (pd != null && pd.isShowing()) {
                                                        pd.dismiss();
                                                    }
                                                }
                                            });}
                                        }
                                            else
                                                Toast.makeText(context.getActivity(), "Choose at least one group id", Toast.LENGTH_SHORT).show();




                                         }
                                });
                                builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                // Display the alert dialog on interface
                                dialog.show();


                            } }
                    }

                    @Override
                    public void onFailure(Call<GroupModel> call, Throwable t) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();

                        }
                        Log.d("ffff", "fff");
                    }
                });
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
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();

                }
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
        private final CardView cr;
        public QuizItem mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            quiz_name = (TextView) view.findViewById(R.id.QuizeName);
            description = (TextView) view.findViewById(R.id.description);
            send = (ImageButton) view.findViewById(R.id.sendQuiz);
            del = (ImageButton) view.findViewById(R.id.deletQuiz);
            cr = (CardView) view.findViewById(R.id.cardView_quize);
            quiz_id = 0;
        }

    }
    public ArrayList<Integer> ShowQuizGroup(int quiz_id) {
        techerApi.showQuizGroup(quiz_id).enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                if (response.body().isResult()) {
                    arraySendId = response.body().getGroup();
                    for (int i = 0 ; i < arraySendId.size() ; i++){
                        sendGroupId.add(arraySendId.get(i).getGroup_id());
                    }
                    Log.d("sendGroupId", sendGroupId.toString());
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();

                }
            }
        });
        return sendGroupId;
    }
}