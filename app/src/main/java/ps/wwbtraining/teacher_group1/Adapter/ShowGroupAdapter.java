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
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.CountStudentModel;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowGroupAdapter  extends RecyclerView.Adapter<ShowGroupAdapter.ViewHolder> {

    private final ArrayList<GroupItem> arrayList;
    int positionItem ;
    Fragment context;
    EditGroupFragment newFragment;
    TeacherApi teacherApi;
    int group_id;
    int count = 0;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ShowGroupAdapter(Fragment context, ArrayList<GroupItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        mDrawableBuilder = TextDrawable.builder()
                .round();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_item, parent, false);
        teacherApi= ApiTeacher.getAPIService();
        return new ShowGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = arrayList.get(position);
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
                newFragment = new EditGroupFragment();
                newFragment.setArguments(args);
                transaction.replace(R.id.show_group, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_id = arrayList.get(position).getGroup_id();
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UpdateFlagGroup(group_id,position);

                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

    }
    private void updateCheckedState(final ViewHolder holder, final GroupItem item) {
        teacherApi.getCount(item.getGroup_id()).enqueue(new Callback<CountStudentModel>() {
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
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });


    }
    int size =0;
    @Override
    public int getItemCount() {
        try{ size = arrayList.size();
            Log.d("arrayListSize","zz");

        }
        catch (Exception e ){

        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView group_name;
        public final TextView description;
        public final ImageButton edit;
        public final ImageView delete;
        public final LinearLayout llgroub;
        public GroupItem mItem;
        public final ImageView image;
        public ViewHolder(View view) {

            super(view);

            this.view = view;
            group_name = (TextView) view.findViewById(R.id.groupName);
            description = (TextView) view.findViewById(R.id.description);
            edit = (ImageButton)view.findViewById(R.id.edit);
            delete = (ImageView)view.findViewById(R.id.delete);
            image = (ImageView) view.findViewById(R.id.image_view);
            llgroub=(LinearLayout)view.findViewById(R.id.llgroub);
        }
    }

    public void UpdateFlagGroup(int groupId, final int position ) {
        teacherApi.updateFlagGroup(groupId, 0).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "insert");
                    arrayList.remove(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public int CountGroup(int groupId) {

        teacherApi.getCount(groupId).enqueue(new Callback<CountStudentModel>() {
            @Override
            public void onResponse(Call<CountStudentModel> call, Response<CountStudentModel> response) {
               count = response.body().getCountStudent() ;
                    Log.d("countGroup", count + "  ");


            }

            @Override
            public void onFailure(Call<CountStudentModel> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
        return count;
    }
}
