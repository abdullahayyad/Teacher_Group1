package ps.wwbtraining.teacher_group1.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.ShowGroupAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;


public class ShowGroupFragment extends Fragment {

    private TeacherApi teacherApi;
    private ArrayList<GroupItem> array = new ArrayList<>();
    private ShowGroupAdapter showGroupAdapter;
    private RecyclerView list_group;
    private FloatingActionButton addGroup;
    private RecyclerView recyclerView;
    private View view;
    private RelativeLayout customView;
    private ProgressBar progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_group, container, false);
        customView=(RelativeLayout)view.findViewById(R.id.show_group);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#c0392b"), android.graphics.PorterDuff.Mode.MULTIPLY);

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
                    Intent intent = new Intent(getActivity(), TeacherActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });


        if (!isOnline(getActivity())) {

            list_group.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            reloadData();
        } else {
            progress.setVisibility(View.VISIBLE);
            getGroup(view);
        }
    }

    private void getGroup(final View view) {
        progress.setVisibility(View.VISIBLE);
        try {
            teacherApi.showGroup().enqueue(new Callback<GroupModel>() {
                @Override
                public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isResult()) {
                            progress.setVisibility(View.GONE);
                            array = response.body().getGroup();
                            showGroupAdapter = new ShowGroupAdapter(ShowGroupFragment.this, array);
                            list_group.setAdapter(showGroupAdapter);
                            list_group.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } else {
                            progress.setVisibility(View.GONE);
                            customSnackBare(view, "Something Error ....");
                        }
                    }else {
                        reloadData();
                        progress.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<GroupModel> call, Throwable t) {
                    if(getView() != null)
                        reloadData();
                }
            });
        } catch (Exception e) {
            customSnackBare(view, "Something Error ....");
        }
    }

    private void reloadData() {
        final Snackbar snackbar;
        snackbar = Snackbar.make(customView, "No Internet Connection:( ", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGroup(view);
                snackbar.dismiss();
            }
        })
//                .setAction("Dissmis", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                snackbar.dismiss();
//            }
//        })
                .setActionTextColor(Color.WHITE);
        snackbar.show();
    }

}