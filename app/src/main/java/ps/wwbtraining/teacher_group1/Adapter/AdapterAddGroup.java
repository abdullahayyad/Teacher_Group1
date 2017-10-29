package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;


public class AdapterAddGroup extends RecyclerView.Adapter<AdapterAddGroup.ViewHolder> {

    private final ArrayList<User> usersAddToGroup;
    private final ArrayList<String> array_id;
    Context context;
    HashMap<Integer, Integer> map = new HashMap<>();
    boolean check ;

    public AdapterAddGroup(Context context, ArrayList<User> usersAddToGroup, ArrayList<String> array_id) {
        this.usersAddToGroup = usersAddToGroup;
        this.context = context;
        this.array_id = array_id;
        Log.d("array_id", array_id.toString());
        check = true ;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custem_item_group, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterAddGroup.ViewHolder holder, final int position) {

        holder.mItem = usersAddToGroup.get(position);
        holder.student_name.setText(usersAddToGroup.get(position).getUserName());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check= true;
                try {
                    if (isChecked) {
                        map.put(position, Integer.parseInt(usersAddToGroup.get(position).getUserId()));
                    } else
                        map.remove(position);
                } catch (Exception e) {
                    Log.d("ggg", "error");
                }
                // usersAddToGroup.get(holder.getAdapterPosition()).setSelect(isChecked);
            }
        });

        if (check==false)
            holder.checkBox.setChecked(false);


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

        this.check = false;
        notifyDataSetChanged();
    }
}
