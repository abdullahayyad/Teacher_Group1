package ps.wwbtraining.teacher_group1.Adapter;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Fragment.EditGroupFragment;
import ps.wwbtraining.teacher_group1.Fragment.ShowQuizHistory;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.CountStudentModel;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Hanan Dawod on 14/11/17.
 */

public class ShowGroupHistory  extends RecyclerView.Adapter<ShowGroupHistory.ViewHolder> {

    private final ArrayList<GroupItem> arrayList;
    int positionItem ;
    Fragment context;
    ShowQuizHistory newFragment;
    TeacherApi teacherApi;
    int group_id;
    int count = 0;
    int size =0;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ShowGroupHistory(Fragment context, ArrayList<GroupItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("arraygrouppp",arrayList.toString());
        mDrawableBuilder = TextDrawable.builder()
                .round();
        teacherApi= ApiTeacher.getAPIService();


    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = arrayList.get(position);
      holder.delete.setVisibility(View.GONE);
        holder.group_name.setText(arrayList.get(position).getgroup_name());
        holder.description.setText(arrayList.get(position).getDescription());
       GroupItem item=arrayList.get(position);
        updateCheckedState(holder, item);

        holder.llgroub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                group_id = arrayList.get(position).getGroup_id();
                args.putString("group_name",holder.group_name.getText().toString());
                Log.d("nameee",holder.group_name.getText().toString());
                Log.d("description",holder.description.getText().toString());
                args.putString("group_description",holder.description.getText().toString());
                args.putInt("group_id",group_id);
                FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
                newFragment = new ShowQuizHistory();
                newFragment.setArguments(args);
                transaction.replace(R.id.show_group, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


    @Override
    public int getItemCount() {
Log.d("bbbb",arrayList.size()+"");
        return arrayList.size();
    }

    private void updateCheckedState(final ViewHolder holder, final GroupItem item) {
        teacherApi.getCountQuiz(item.getGroup_id()).enqueue(new Callback<CountStudentModel>() {
            @Override
            public void onResponse(Call<CountStudentModel> call, Response<CountStudentModel> response) {
                count = response.body().getCountStudent() ;
                Log.d("countGroup", count + "  ");
                Log.d("ccccc",""+count);
                TextDrawable drawable = mDrawableBuilder.build(count+"", mColorGenerator.getColor(item.getgroup_name()));
                holder.image.setImageDrawable(drawable);
                holder.view.setBackgroundColor(Color.TRANSPARENT);


            }

            @Override
            public void onFailure(Call<CountStudentModel> call, Throwable t) {
try {
    Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();
}catch (Exception e){}
            }
        });


    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView group_name;
        public final TextView description;
        public final ImageView delete;
public final LinearLayout llgroub;
        public GroupItem mItem;
        public final ImageView image;
        public ViewHolder(View view) {

            super(view);
            this.view = view;
            group_name = (TextView) view.findViewById(R.id.groupName);
            description = (TextView) view.findViewById(R.id.description);
           image = (ImageView) view.findViewById(R.id.image_view);
            delete = view.findViewById(R.id.delete);
             llgroub = (LinearLayout) view.findViewById(R.id.llgroub);
        }
    }





}
