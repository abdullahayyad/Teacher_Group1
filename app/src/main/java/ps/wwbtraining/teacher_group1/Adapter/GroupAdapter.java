//package ps.wwbtraining.teacher_group1.Adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import java.util.List;
//
//import ps.wwbtraining.teacher_group1.R;
//import ps.wwbtraining.teacher_group1.WebService.User;
//
///**
// * Created by osama on 19/10/2017.
// */
//
//public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
//
//    private final List<User> userManageItems;
//    int positionItem ;
//
//    Context context;
//
//    public GroupAdapter(Context context, List<User> userManageItems , int positionSpinner) {
//        this.context = context;
//        this.userManageItems = userManageItems;
//    }
//
//
//
//
//    @Override
//    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(android., parent, false);
//        return new GroupAdapter.ViewHolder(view);
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mItem = userManageItems.get(position);
//        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup,  int i) {
//
//                // positionItem =i;
//
//                // holder.radioButtonn.setChecked(true);
//
//            }
//        });
//
//        holder.student_name.setText(userManageItems.get(position).getUserName());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return userManageItems.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView student_name;
//
//        public User mItem;
//
//        public ViewHolder(View view) {
//
//            super(view);
//
//            mView = view;
//            student_name = (TextView) view.findViewById(R.id.student_name);
//
//
//
//        }
//    }
//}
