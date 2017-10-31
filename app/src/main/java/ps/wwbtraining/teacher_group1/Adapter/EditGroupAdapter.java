package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hanan Dawod on 29/10/17.
 */

public class EditGroupAdapter extends RecyclerView.Adapter<EditGroupAdapter.ViewHolder> {

    private final ArrayList<User> usersAddToGroup;
    private final ArrayList<String> array_id;
    Context context;
    HashMap<Integer, Integer> map = new HashMap<>();
    int group_id;
    private TeacherApi techerApi;

    public EditGroupAdapter(Context context, ArrayList<User> usersAddToGroup, ArrayList<String> array_id, int group_id) {
        this.usersAddToGroup = usersAddToGroup;
        this.context = context;
        this.array_id = array_id;
        this.group_id = group_id;
        Log.d("group_id", group_id + "  ");

        Log.d("array_id", array_id.toString());
        // check = true ;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custem_item_group, parent, false);
        techerApi = ApiTeacher.getAPIService();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = usersAddToGroup.get(position);
        holder.student_name.setText(usersAddToGroup.get(position).getUserName());
        //DeleteUserFromGroup(group_id);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //  check= true;
                try {
                    if (isChecked) {
                        InsertUserIntoGroup(group_id, Integer.parseInt(usersAddToGroup.get(position).getUserId()));
                    } else {
                        DeleteUserGroup(group_id, Integer.parseInt(usersAddToGroup.get(position).getUserId()));
                    }

                } catch (Exception e) {
                    Log.d("ggg", "error");
                }
            }
        });

        try {
            if (!array_id.isEmpty()) {
                for (int i = 0; i < array_id.size(); i++) {
                    if (usersAddToGroup.get(position).getUserId().equals(array_id.get(i))) {
                        holder.checkBox.setChecked(true);
                    }
                }
            }
        } catch (Exception e) {
        }

    }


    @Override
    public int getItemCount() {
        return usersAddToGroup.size();
    }


    public HashMap getArray() {
        return map;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView student_name;
        public final CheckBox checkBox;
        public User mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            student_name = (TextView) view.findViewById(R.id.nameStudentAddGroup);
            checkBox = (CheckBox) view.findViewById(R.id.checkboxStudentAddGroup);


        }
    }

    public void setCheck() {
        notifyDataSetChanged();
    }



    public void InsertUserIntoGroup(int group_id, int user_id) {
        techerApi.insertUserIntoGroup(group_id, user_id).enqueue(new Callback<InsertIntoGroup>() {
            @Override
            public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
                if (response.body().isResult()) {
                    Log.d("insert", "insert");
                }
            }

            @Override
            public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                Toast.makeText(context, "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void DeleteUserGroup(int group_id, int user_id) {
        techerApi.deleteUserFromGroup(group_id, user_id).enqueue(new Callback<InsertIntoGroup>() {
            @Override
            public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
                if (response.body().isResult()) {
                    Log.d("insert", "insert");
                }
            }

            @Override
            public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                Toast.makeText(context, "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}