//package ps.wwbtraining.teacher_group1;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.List;
//
///**
// * Created by Hanan Dawod on 17/10/17.
// */
//
//public class UserManagementAdapter extends RecyclerView.Adapter<UserManagementAdapter.ViewHolder> {
//
//    private final List<UserManageItem> userManageItems;
//
//    Context context;
//
//    public UserManagementAdapter(Context context, List<UserManageItem> userManageItems) {
//        this.context = context;
//        this.userManageItems = userManageItems;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_manage_user_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mItem = userManageItems.get(position);
////        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
////
////                RadioButton radioButton =
////
////            }
////        });
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
//        //        public final RadioGroup radioGroup;
////        public final RadioButton radioButtonn;
//        public UserManageItem mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            student_name = (TextView) view.findViewById(R.id.studentName);
////            radioGroup = (RadioGroup)view.findViewById(R.id.radio);
////            radioButtonn =(RadioButton)view
//
//
//        }
//    }
//}
