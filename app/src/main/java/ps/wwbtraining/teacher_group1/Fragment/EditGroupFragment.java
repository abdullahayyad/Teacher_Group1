package ps.wwbtraining.teacher_group1.Fragment;

/**
 * Created by osama on 17/10/2017.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.EditGroupAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.Model.UserFromGroupModel;
import ps.wwbtraining.teacher_group1.Model.UserItem;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.NoInternetAlert;
import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

public class EditGroupFragment extends Fragment {
    String nameGroup, group_description;
    TeacherApi teacherApi;
    ArrayList<User> array = new ArrayList<>();
    ArrayList<UserItem> array1 = new ArrayList<>();
    ArrayList<Integer> checkedArray = new ArrayList<>();
    private RecyclerView recyclerView;
    EditGroupAdapter userManagementAdapter;
    EditText name, description;
    int group_id;
    private View view;
    private RelativeLayout customView;
    private ProgressBar progress;
    ArrayList<String> arrayId = new ArrayList<>();
    private TextView back;
    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherApi = ApiTeacher.getAPIService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_group, null, false);

        customView = (RelativeLayout) view.findViewById(R.id.relayout);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        progress.getIndeterminateDrawable().setColorFilter
                (Color.parseColor("#c0392b"), android.graphics.PorterDuff.Mode.MULTIPLY);
        nameGroup = getArguments().getString("group_name");
        group_description = getArguments().getString("group_description");
        group_id = getArguments().getInt("group_id");
        name = (EditText) view.findViewById(R.id.tvnameGroup);
        description = (EditText) view.findViewById(R.id.tvDiscription);
        recyclerView = view.findViewById(R.id.list_student);
        back = (TextView) view.findViewById(R.id.buCancel);
        recyclerView.setHasFixedSize(true);
        name.setText(nameGroup);
        description.setText(group_description);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userManagementAdapter);
//////////////////////////////////////////////user in group
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  getFragmentManager().popBackStack();
                Intent intent = new Intent(getActivity(), TeacherActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.buAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                try {
                    String nameg = name.getText().toString();
                    String des = description.getText().toString();
                    if (!nameg.isEmpty() && !des.isEmpty()) {
                        if (isOnline(getActivity())) {
                            final ProgressDialog pd = new ProgressDialog(getActivity());
                            pd.setMessage("Saving Edit ....");
                            pd.setCancelable(false);
                            pd.show();
                            teacherApi.updateGroup(group_id, nameg, des).enqueue(new Callback<InsertIntoGroup>() {


                                @Override

                                public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {

                                    if (response.isSuccessful()) {
                                        if (response.body().isResult()) {

                                            if (pd != null && pd.isShowing())
                                                pd.dismiss();

                                            userManagementAdapter.setCheck();


                                        } else {
//                                                    Log.d("//////", response.body().toString());
//                                                        // userManagementAdapter.notifyS);
                                            if (pd != null && pd.isShowing())
                                                pd.dismiss();
                                            customSnackBare(view, "No Internet Connection ....");
                                        }
                                    } else {
//
                                        if (pd != null && pd.isShowing())
                                            pd.dismiss();
                                        customSnackBare(view, "No Internet Connection ....");
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                                    if (getView() != null) {
                                        if (pd != null && pd.isShowing())
                                            pd.dismiss();
                                        NoInternetAlert(getActivity());
                                    }
                                }

                            });
                        } else customSnackBare(customView, "No Internet Connection...");
                    } else customSnackBare(customView, "No Student...");
                } catch (Exception e) {
                    customSnackBare(view, "Something Error ....");
                }

            }
        });


        return view;
    }

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
//                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_enter, R.anim.right_out).
//                            replace(R.id.frameTeacher, new Teacher_Fragment()).addToBackStack(null).commit();
                    return true;

                }
                return false;
            }
        });
        if (isOnline(getActivity())) {
            recyclerView.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            getStudant(view);
        } else {
            recyclerView.setVisibility(View.GONE);
           reloadData(customView);
        }
    }

    public void getStudant(final View view) {
        progress.setVisibility(View.VISIBLE);
        try {
            teacherApi.getStudName(2).enqueue(new Callback<StudentModel>() {
                @Override
                public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                    if (response.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        if (response.body().isResult()) {
                            try {
                                progress.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                array = response.body().getUser();
                                arrayId = new ArrayList<String>();
                                userManagementAdapter = new EditGroupAdapter(getActivity(), array, arrayId, group_id);
                                recyclerView.setAdapter(userManagementAdapter);
                                Log.d("arrayyy", array.toString());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                try {
                                    teacherApi.userFromGroup(group_id).enqueue(new Callback<UserFromGroupModel>() {
                                        @Override
                                        public void onResponse(Call<UserFromGroupModel> call, Response<UserFromGroupModel> response) {
                                            if (response.isSuccessful()) {
                                                try {
                                                    progress.setVisibility(View.GONE);
                                                    recyclerView.setVisibility(View.VISIBLE);
                                                    array1 = response.body().getUser();
                                                    for (int i = 0; i < array1.size(); i++) {
                                                        arrayId.add(array1.get(i).getUserId());
                                                        Log.d("arr", array1.toString());
                                                    }
                                                    userManagementAdapter = new EditGroupAdapter(getActivity(), array, arrayId, group_id);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                                    Log.d("arr1", arrayId.toString());
                                                } catch (Exception e) {
                                                    customSnackBare(
                                                            view, "Something Error");
                                                }


                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserFromGroupModel> call, Throwable t) {
                                            if (getView() != null) {
                                                reloadData(view);
                                                progress.setVisibility(View.VISIBLE);
                                                recyclerView.setVisibility(View.GONE);
                                            }
                                        }

                                    });

                                } catch (Exception e) {
                                    customSnackBare(
                                            view, "Something Error");
                                }
                            } catch (Exception e) {
                                customSnackBare(
                                        view, "Something Error");
                            }
                        } else

                            progress.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        if (getView() != null) {
                            reloadData(customView);
                        }
                    } else

                        progress.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    if (getView() != null) {
                        reloadData(customView);
                    }
                }

                @Override
                public void onFailure(Call<StudentModel> call, Throwable t) {
                    if (getView() != null) {
                        reloadData(view);
                        progress.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            customSnackBare(
                    view, "Something Error");
            Log.d("err", array1.toString());
        }


    }

    public void reloadData(final View view) {
        final Snackbar snackbar;
        snackbar = Snackbar.make(view, "No Internet Connection:( ", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStudant(view);
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