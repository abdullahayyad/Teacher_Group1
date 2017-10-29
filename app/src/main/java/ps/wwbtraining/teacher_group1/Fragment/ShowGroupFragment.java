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
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
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
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_group, null, false);
        teacherApi = ApiTeacher.getAPIService();
        list_group = (RecyclerView) view.findViewById(R.id.list_group);
        addGroup = (FloatingActionButton)view.findViewById(R.id.addGroup);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.show_group, new CreateGroupFragment()
                        ).commit();

            }
        });

        //array = showGroup();y
        try {


            teacherApi.showGroup().enqueue(new Callback<GroupModel>() {
                @Override

                public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isResult()) {

                            array = response.body().getGroup();
                            showGroupAdapter = new ShowGroupAdapter(ShowGroupFragment.this, array);
                            list_group.setAdapter(showGroupAdapter);
                            list_group.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } else
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override

                public void onFailure(Call<GroupModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "NO Enternt Connection", Toast.LENGTH_SHORT).show();
                    Log.d("ffff", "fff");
                }
            });
        }catch (Exception e)
        {

            Toast.makeText(getContext(), "NO Enternt Connection", Toast.LENGTH_SHORT).show();

        }

        return view;
    }


}