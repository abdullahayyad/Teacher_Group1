package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;

/**
 * Created by مركز الخبراء on 10/20/2017.
 */

public class AdapterAddGroup extends RecyclerView.Adapter<AdapterAddGroup.ViewHolder> {

    private final ArrayList<User> usersAddToGroup;
    Context context;

    public AdapterAddGroup(Context context, ArrayList<User> usersAddToGroup ) {

        this.context = context;
        this.usersAddToGroup = usersAddToGroup;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custem_item_group, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mItem = usersAddToGroup.get(position);
        holder.student_name.setText(usersAddToGroup.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return usersAddToGroup.size();
    }

   public  class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView student_name;
        public final CheckBox checkBox;
        public User mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            student_name = (TextView) view.findViewById(R.id.student_name);
            checkBox = (CheckBox) view.findViewById(R.id.checkboxStudentAddGroup);


        }
}}
