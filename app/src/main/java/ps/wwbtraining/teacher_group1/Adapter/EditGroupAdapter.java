package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.Model.UserFromGroupModel;
import ps.wwbtraining.teacher_group1.Model.UserItem;
import ps.wwbtraining.teacher_group1.R;

/**
 * Created by osama on 24/10/2017.
 */

public class EditGroupAdapter extends RecyclerView.Adapter<EditGroupAdapter.ViewHolder> {

    private final ArrayList<UserFromGroupModel>arrayId;
    private final ArrayList<User> usersAddToGroup;
    Context context;
    HashMap<Integer,User> map=new HashMap<>();
    public EditGroupAdapter(Context context, ArrayList<User> usersAddToGroup,ArrayList<UserItem>arrayId ) {

        this.context = context;
        this.usersAddToGroup = usersAddToGroup;
        this.arrayId = arrayId;

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
                if(isChecked){
                    map.put(position,usersAddToGroup.get(position));
                }
                else
                    map.remove(position);

                // usersAddToGroup.get(holder.getAdapterPosition()).setSelect(isChecked);
            }
        });

    }


    @Override
    public int getItemCount() {
        return usersAddToGroup.size();
    }


    public HashMap getArray (){
        return map;
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
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
    }}

