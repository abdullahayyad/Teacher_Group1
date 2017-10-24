package ps.wwbtraining.teacher_group1.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Fragment.EditGroupFragment;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.R;



public class ShowGroupAdapter  extends RecyclerView.Adapter<ShowGroupAdapter.ViewHolder> {

    private final ArrayList<GroupItem> arrayList;
    int positionItem ;
    Fragment context;
    EditGroupFragment newFragment;

    public ShowGroupAdapter(Fragment context, ArrayList<GroupItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_item, parent, false);
        return new ShowGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = arrayList.get(position);
        holder.group_name.setText(arrayList.get(position).getgroup_name());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("group_name",holder.group_name.getText().toString());
                args.putString("group_description",holder.description.getText().toString());
                FragmentTransaction transaction = context.getFragmentManager().beginTransaction();

                newFragment = new EditGroupFragment();
                newFragment.setArguments(args);
                transaction.add(R.id.show_group, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
}
