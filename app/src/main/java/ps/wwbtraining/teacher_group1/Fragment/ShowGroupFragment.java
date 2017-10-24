package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Adapter.ShowGroupAdapter;
import ps.wwbtraining.teacher_group1.Adapter.UserManagementAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowGroupFragment extends Fragment {

    TeacherApi teacherApi;
    ArrayList<GroupItem>array = new ArrayList<>();
    ShowGroupAdapter showGroupAdapter;
    RecyclerView list_group;
    FloatingActionButton addGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_group, container, false);
        teacherApi = ApiTeacher.getAPIService();
        list_group = (RecyclerView) view.findViewById(R.id.list_group);
        addGroup = (FloatingActionButton)view.findViewById(R.id.addGroup);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.show_group, new CreateGroupFragment()
                        ).commit();

            }
        });

        //array = showGroup();
        teacherApi.showGroup().enqueue(new Callback<GroupModel>() {
            @Override

            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {

                        array = response.body().getGroup();
                        Toast.makeText(getActivity(), array + "  1233", Toast.LENGTH_SHORT).show();
                        showGroupAdapter = new ShowGroupAdapter(getParentFragment(), array);
                        list_group.setAdapter(showGroupAdapter);
                        list_group.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else
                        Toast.makeText(getActivity(), "error123", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<GroupModel> call, Throwable t) {
                Toast.makeText(getContext(), "faaa", Toast.LENGTH_SHORT).show();
                Log.d("ffff", "fff");
            }
        });


        return view;
    }


}