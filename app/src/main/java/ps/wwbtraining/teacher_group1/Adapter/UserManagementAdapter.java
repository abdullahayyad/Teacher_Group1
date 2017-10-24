package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;

/**
 * Created by Hanan Dawod on 17/10/17.
 */

public class UserManagementAdapter extends RecyclerView.Adapter<UserManagementAdapter.ViewHolder> {

    private final  ArrayList<User> userManageItems;
    int positionItem ;
    int positionRadio;
    Context context;
    HashMap map =new HashMap();


    public UserManagementAdapter(Context context, ArrayList<User> userManageItems , int positionSpinner) {

        this.context = context;
        this.userManageItems = userManageItems;
        positionItem = positionSpinner;
        Log.d("kkkkkkkk",userManageItems.toString());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_manage_user_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = userManageItems.get(position);


        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,  int i) {
               int cc = holder.radioGroup.getCheckedRadioButtonId();
                if (i ==R.id.approved){
                    userManageItems.get(position).setStatusId(2+"");
                }else if (i ==R.id.blocked){
                    userManageItems.get(position).setStatusId(3+"");
                }else if (i ==R.id.notApproved){
                    userManageItems.get(position).setStatusId(4+"");
                }else {
                    userManageItems.get(position).setStatusId(5+"");
                }

                Toast.makeText(context,cc+ "", Toast.LENGTH_SHORT).show();
                map.put(position,cc);

            }
        });

      if (positionItem == 2){
          holder.radioGroup.check(R.id.approved);
      }else if (positionItem == 3){
          holder.radioGroup.check(R.id.rejected);
      }else if (positionItem == 4){
          holder.radioGroup.check(R.id.notApproved);
      }else{
          holder.radioGroup.check(R.id.blocked);
      }


        holder.student_name.setText(userManageItems.get(position).getUserName());

    }

    public void SelectAll(int i) {

       positionItem =i;
       Log.d("poooo",positionItem+"");
       notifyDataSetChanged();
    }


    public ArrayList<User> arrayUser (){
        return userManageItems;
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
            radioGroup = (RadioGroup)view.findViewById(R.id.groupStu);
            radioButtonn =(RadioButton)view.findViewById(positionItem);

        }}
}
