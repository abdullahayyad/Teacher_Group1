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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.AdapterAddGroup;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupInsert;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.NoInternetAlert;
import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

public class CreateGroupFragment extends Fragment {
    private TeacherApi teacherApi;
    private ArrayList<User> array = new ArrayList<>();
    private ArrayList<Integer> array1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterAddGroup userManagementAdapter;
    private EditText name, description;
    private int group_id = 0;
    private int user_id = 0;
    private TextView back;
    private static Animation shakeAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherApi = ApiTeacher.getAPIService();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_group, null, false);

        name = (EditText) view.findViewById(R.id.tvnameGroup);
        description = (EditText) view.findViewById(R.id.tvDiscription);
        recyclerView = view.findViewById(R.id.list_student);
        recyclerView.setHasFixedSize(true);
        back = (TextView) view.findViewById(R.id.buCancel);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_student);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        if (isOnline(getActivity())) {
            getStudant(view);
        } else {
            reloadData(view);
        }

        // recyclerView.setLayoutManager(RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userManagementAdapter);

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
                    if (checkValidation()) {
                        teacherApi
                                .addGroup(nameg, des)
                                .enqueue(new Callback<GroupInsert>() {
                                    @Override
                                    public void onResponse(Call<GroupInsert> call, Response<GroupInsert> response) {
                                        final ProgressDialog pd = new ProgressDialog(getActivity());
                                        pd.setMessage("Saving Group ....");
                                        pd.setCancelable(false);
                                        pd.show();
                                        if (response.isSuccessful()) {
                                            if (response.body().isResult()) {
                                                group_id = response.body().getId();
//                                        Log.d("group_id", group_id + "");
                                                array1.addAll(userManagementAdapter.getArray().values());
//                                        Log.d("size0", array + " ");
                                                HashMap testMap = new HashMap<String, String>();
                                                testMap.put("group_id", group_id);
                                                testMap.put("user_id", array1);
                                                Log.d("testMap", testMap + " ");
                                                JSONObject jsonObject = new JSONObject(testMap);
                                                Log.d("jsonObject", jsonObject + " ");
                                                //RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj.toString());
                                                teacherApi.addArrayUserGroup(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject))).enqueue(new Callback<InsertIntoGroup>() {
                                                    @Override
                                                    public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
                                                        if (response.isSuccessful()) {
//                                                    Log.d("//////", response.body().toString());
                                                            if (pd != null && pd.isShowing())
                                                                pd.dismiss();
                                                        } else {
//                                                    Log.d("//////", response.body().toString());
//                                                        // userManagementAdapter.notifyS);
                                                            if (pd != null && pd.isShowing())
                                                                pd.dismiss();
                                                            customSnackBare(view, "No Internet Connection ....");
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                                                        Log.d("//////", t.toString());
                                                        if (pd != null && pd.isShowing())
                                                            pd.dismiss();
                                                        NoInternetAlert(getActivity());
                                                    }
                                                });
                                            } else
                                                customSnackBare(view, "No Internet Connection ....");
                                            // userManagementAdapter.notifyS);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GroupInsert> call, Throwable t) {
                                        customSnackBare(view, "No Internet Connection ....");
                                    }
                                });
                    }

                } catch (Exception e) {
                    customSnackBare(view, "Something Error ....");
                }
                name.setText("");
                description.setText("");
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
    }

    public void getStudant(final View view) {
        teacherApi
                .getStudName(2)
                .enqueue(new Callback<StudentModel>() {
                    @Override
                    public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isResult()) {
                                try {
                                    array = response.body().getUser();
                                    ArrayList<String> array_id = new ArrayList<String>();
                                    userManagementAdapter = new AdapterAddGroup(getActivity(), array, array_id);
                                    recyclerView.setAdapter(userManagementAdapter);
//                                Log.d("arrayyy", array.toString());
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                } catch (Exception e) {
                                    customSnackBare(view, "Something Error");
                                }
                            } else
                                reloadData(view);
                            // userManagementAdapter.notifyS);
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentModel> call, Throwable t) {
                        reloadData(view);
                    }
                });
    }

    public void reloadData(View view) {
        final Snackbar snackbar;
        snackbar = Snackbar.make(view, "No Internet Connection:( ", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public boolean checkValidation() {
        String nameg = name.getText().toString();
        String des = description.getText().toString();
        if (nameg.isEmpty()) {
            name.setError("Enter a valid Group Name");
            name.startAnimation(shakeAnimation);
            return false;
        } else if (des.isEmpty()) {
            description.setError("Enter a valid Description");
            description.startAnimation(shakeAnimation);
            return false;
        }
        return true;
    }
}