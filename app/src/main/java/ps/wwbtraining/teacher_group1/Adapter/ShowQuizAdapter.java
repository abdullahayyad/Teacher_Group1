package ps.wwbtraining.teacher_group1.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Arrays;

import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Class.Utils;
import ps.wwbtraining.teacher_group1.Fragment.EditGroupFragment;
import ps.wwbtraining.teacher_group1.Fragment.ShowGroupFragment;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.QuizItem;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by مركز الخبراء on 10/26/2017.
 */

public class ShowQuizAdapter extends RecyclerView.Adapter<ShowQuizAdapter.ViewHolder> {

    private final ArrayList<QuizItem> arrayList;
    Fragment context;
    TeacherApi teacherApi;
    ArrayList<GroupItem> array;
    ArrayList<String> groupName = new ArrayList<>();

    public ShowQuizAdapter(Fragment context, ArrayList<QuizItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        final int quiz_id = arrayList.get(position).getQuiz_id();
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                teacherApi.showGroup().enqueue(new Callback<GroupModel>() {
                    @Override

                    public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isResult()) {


                                array = response.body().getGroup();
                                for (int i = 0; i < array.size(); i++) {
                                    groupName.add(array.get(i).getgroup_name());

                                }
                                ArrayAdapter arrayAdapter = new ArrayAdapter(context.getActivity(), android.R.layout.simple_list_item_checked, groupName);


                                final Dialog dialog = new Dialog(context.getActivity());
                                dialog.setContentView(R.layout.group_dialog);
                                dialog.setTitle("Group");
                                ListView listView = (ListView) dialog.findViewById(R.id.listGroupDialog);
                                listView.setAdapter(arrayAdapter);

                                listView.setTextFilterEnabled(true);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        CheckedTextView checkedTextView = (CheckedTextView) view;
                                        checkedTextView.setChecked(!checkedTextView.isChecked());

                                    }
                                });
                                Button quizSend = (Button) dialog.findViewById(R.id.send);
                                quizSend.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        dialog.cancel();

                                    }
                                });

                                Button quizSendAll = (Button) dialog.findViewById(R.id.sendAll);
                                quizSendAll.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        dialog.cancel();
                                    }
                                });

                                dialog.show();

                            } else
                                Toast.makeText(context.getActivity(), "error123", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupModel> call, Throwable t) {
                        Toast.makeText(context.getActivity(), "faaa", Toast.LENGTH_SHORT).show();
                        Log.d("ffff", "fff");
                    }
                });
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
        public final TextView description;

        public final ImageButton send;
        public QuizItem mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            quiz_name = (TextView) view.findViewById(R.id.QuizeName);
            description = (TextView) view.findViewById(R.id.description);
            send = (ImageButton) view.findViewById(R.id.sendQuiz);

        }
    }
}

