package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

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
    ArrayList<GroupItem> array = new ArrayList<>();
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

        View view = inflater.inflate(R.layout.fragment_show_group, null, false);
        teacherApi = ApiTeacher.getAPIService();
        list_group = (RecyclerView) view.findViewById(R.id.list_group);
        addGroup = (FloatingActionButton) view.findViewById(R.id.addGroup);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.show_group, new CreateGroupFragment()
                        ).commit();

            }
        });

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    DrawerLayout navigationView = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    if (navigationView.isDrawerOpen(GravityCompat.START))
                        navigationView.closeDrawers();
                    else
                        getFragmentManager().beginTransaction().addToBackStack(null)
                                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                .replace(R.id.frameTeacher, new Teacher_Fragment()).commit();

                    return true;
                }
                return false;
            }
        });

    }


}