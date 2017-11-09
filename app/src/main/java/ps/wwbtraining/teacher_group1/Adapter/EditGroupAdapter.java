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
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.Model.UserItem;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hanan Dawod on 29/10/17.
 */

public class EditGroupAdapter extends RecyclerView.Adapter<EditGroupAdapter.ViewHolder> {

    private final ArrayList<UserItem> usersAddToGroup;
    private final ArrayList<String> array_id;
    Context context;
    int group_id;
    HashMap indexMap ;

    public EditGroupAdapter(Context context, ArrayList<UserItem> usersAddToGroup, ArrayList<String> array_id, int group_id) {
        this.usersAddToGroup = usersAddToGroup;
        this.context = context;
        this.array_id = array_id;
        this.group_id = group_id;
        indexMap=new HashMap();
        Log.d("array_id", array_id.toString());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custem_item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = usersAddToGroup.get(position);
        holder.student_name.setText(usersAddToGroup.get(position).getUserName());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    Integer ff = Integer.parseInt(usersAddToGroup.get(position).getUserId());
                    if (isChecked) {
                        indexMap.put(ff,ff);
                        Log.d("aaaa",ff+"");

                    } else {
                         indexMap.remove(ff);
                         Log.d("bbbb",ff+"");

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

                        Integer ff = Integer.parseInt(usersAddToGroup.get(position).getUserId());
                        Log.d("nnmmm",ff+"");
                        holder.checkBox.setChecked(true);

                    }}}

        } catch (Exception e) {
        }
    }
    public HashMap getArray() {
        Log.d("zzzz",indexMap.toString()+"  hkj  ");//+map.size());
        return indexMap;
    }


    @Override
    public int getItemCount() {
        return usersAddToGroup.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView student_name;
        public final CheckBox checkBox;
        public UserItem mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            student_name = (TextView) view.findViewById(R.id.nameStudentAddGroup);
            checkBox = (CheckBox) view.findViewById(R.id.checkboxStudentAddGroup);


        }
    }
    }

