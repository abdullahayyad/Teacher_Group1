package ps.wwbtraining.teacher_group1.Adapter;

import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Fragment.EditGroupFragment;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
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

    public ShowGroupAdapter(Fragment context, ArrayList<GroupItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

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
        group_id = arrayList.get(position).getGroup_id();
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("group_name",holder.group_name.getText().toString());
                Log.d("nameee",holder.group_name.getText().toString());
                Log.d("description",holder.description.getText().toString());
                args.putString("group_description",holder.description.getText().toString());
                args.putInt("group_id",group_id);
                FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
                newFragment = new EditGroupFragment();
                newFragment.setArguments(args);
                transaction.add(R.id.show_group, newFragment);
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
                                UpdateFlagGroup(group_id);
                                arrayList.remove(position);
                                notifyDataSetChanged();
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
    int size =0;
    @Override
    public int getItemCount() {
        try{ size = arrayList.size();

        }
        catch (Exception e ){

        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView group_name;
        public final TextView description;
        public final ImageButton edit;
        public final ImageButton delete;
        public GroupItem mItem;

        public ViewHolder(View view) {

            super(view);

            mView = view;
            group_name = (TextView) view.findViewById(R.id.groupName);
            description = (TextView) view.findViewById(R.id.description);
            edit = (ImageButton)view.findViewById(R.id.edit);
            delete = (ImageButton)view.findViewById(R.id.delete);

        }
    }

    public void UpdateFlagGroup(int groupId) {
        teacherApi.updateFlagGroup(groupId, 0).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "insert");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
