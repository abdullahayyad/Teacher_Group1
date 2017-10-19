package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;

/**
 * Created by Hanan Dawod on 17/10/17.
 */

public class UserManagementAdapter extends RecyclerView.Adapter<UserManagementAdapter.ViewHolder> {

    private final  ArrayList<User> userManageItems;
    int positionItem ;

    Context context;

    public UserManagementAdapter(Context context, ArrayList<User> userManageItems , int positionSpinner) {
        this.context = context;
        this.userManageItems = userManageItems;
        positionItem = positionSpinner;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_manage_user_item, parent, false);
        Log.d("kkkkkkkk",userManageItems.get(0).getUserName());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = userManageItems.get(position);
//        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup,  int i) {
//
//               // positionItem =i;
//
//               // holder.radioButtonn.setChecked(true);
//
//            }
//        });

        holder.student_name.setText(userManageItems.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return userManageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView student_name;
        public final RadioGroup radioGroup;
        public final RadioButton radioButtonn;
        public User mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            student_name = (TextView) view.findViewById(R.id.student_name);
            radioGroup = (RadioGroup)view.findViewById(R.id.radio);
            radioButtonn =(RadioButton)view.findViewById(positionItem);


        }
    }
}
