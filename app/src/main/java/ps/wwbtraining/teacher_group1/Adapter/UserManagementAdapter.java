package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;

public class UserManagementAdapter extends RecyclerView.Adapter<UserManagementAdapter.ViewHolder> {

    private final ArrayList<User> userManageItems;
    private int positionItem;
    private int positionRadio;
    private Context context;
    private HashMap map = new HashMap();
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public UserManagementAdapter(Context context, ArrayList<User> userManageItems, int positionSpinner) {
        this.context = context;
        this.userManageItems = userManageItems;
        positionItem = positionSpinner;
        mDrawableBuilder = TextDrawable.builder()
                .round();
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
        User item=userManageItems.get(position);
        updateCheckedState(holder, item);
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int cc = holder.radioGroup.getCheckedRadioButtonId();
                if (i == R.id.approved) {
                    userManageItems.get(position).setStatusId(2 + "");
                } else if (i == R.id.blocked) {
                    userManageItems.get(position).setStatusId(5 + "");
                } else if (i == R.id.notApproved) {
                    userManageItems.get(position).setStatusId(4 + "");
                } else {
                    userManageItems.get(position).setStatusId(3 + "");
                }
                map.put(position, cc);
            }
        });
//       holder.image.setImageDrawable(drawable);


        if (positionItem == 2) {
            holder.radioGroup.check(R.id.approved);
        } else if (positionItem == 3) {
            holder.radioGroup.check(R.id.rejected);
        } else if (positionItem == 4) {
            holder.radioGroup.check(R.id.notApproved);
        } else {
            holder.radioGroup.check(R.id.blocked);
        }
        holder.student_name.setText(userManageItems.get(position).
                getUserName());
    }

    public void removeItem(int i) {
        userManageItems.remove(i);
        notifyDataSetChanged();
    }

    public void SelectAll(int i) {
        positionItem = i;
        notifyDataSetChanged();
    }

    public ArrayList<User> arrayUser() {
        return userManageItems;
    }

    @Override
    public int getItemCount() {
        return userManageItems.size();
    }

    private void updateCheckedState(ViewHolder holder, User item) {

        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.getUserName().charAt(0)), mColorGenerator.getColor(item.getUserName()));
        holder.image.setImageDrawable(drawable);
        holder.view.setBackgroundColor(Color.TRANSPARENT);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView student_name;
        public final RadioGroup radioGroup;
        public final RadioButton radioButtonn;
        public final ImageView image;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            student_name = (TextView) view.findViewById(R.id.student_name);
            radioGroup = (RadioGroup) view.findViewById(R.id.groupStu);
            radioButtonn = (RadioButton) view.findViewById(positionItem);
            image = (ImageView) view.findViewById(R.id.image_view);


        }


    }
}
