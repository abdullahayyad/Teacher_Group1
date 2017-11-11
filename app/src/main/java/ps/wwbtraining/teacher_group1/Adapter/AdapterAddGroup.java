package ps.wwbtraining.teacher_group1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;


public class AdapterAddGroup extends RecyclerView.Adapter<AdapterAddGroup.ViewHolder> {

    private final ArrayList<User> usersAddToGroup;
    private final ArrayList<String> array_id;
    Context context;
    HashMap<Integer, Integer> map = new HashMap<>();
    HashMap<Integer, Integer> checkMap = new HashMap<>();
    boolean checkboxAddBoolean ;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;



    public AdapterAddGroup(Context context, ArrayList<User> usersAddToGroup, ArrayList<String> array_id) {
        this.usersAddToGroup = usersAddToGroup;
        this.context = context;
        this.array_id = array_id;
        this.checkboxAddBoolean=false;
        mDrawableBuilder=TextDrawable.builder().round();

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
        User item = usersAddToGroup.get(position);
        updateCheckedState(holder, item);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkboxAddBoolean = false;
                try {
                    if (isChecked) {
                        map.put(position, Integer.parseInt(usersAddToGroup.get(position).getUserId()));
                        checkMap.put(position, Integer.parseInt(usersAddToGroup.get(position).getUserId()));

                    } else
                        map.remove(position);
                    checkMap.remove(position);


                } catch (Exception e) {

                }
            }
        });

        if (checkboxAddBoolean) {

            holder.checkBox.setChecked(false);
            checkMap.remove(position);

        } else {
            Log.d("bbbbbb",map.toString());

            if (checkMap.containsKey(position)) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
        }

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

    private void updateCheckedState(ViewHolder holder, User item) {
        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.getUserName().charAt(0)), mColorGenerator.getColor(item.getUserName()));
        holder.image.setImageDrawable(drawable);
        holder.mView.setBackgroundColor(Color.TRANSPARENT);
    }

    public HashMap getArray() {
        return map;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView student_name;
        public final CheckBox checkBox;
        public User mItem;
        public final ImageView image;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            student_name = (TextView) view.findViewById(R.id.nameStudentAddGroup);
            checkBox = (CheckBox) view.findViewById(R.id.checkboxStudentAddGroup);
            image = (ImageView) view.findViewById(R.id.image_view);
        }
    }

    public void check() {

        this.checkboxAddBoolean = true;
        notifyDataSetChanged();
    }
}
