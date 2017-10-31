package ps.wwbtraining.teacher_group1.Fragment;

/**
 * Created by osama on 17/10/2017.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

// nameGroup = getArguments().getString("group_name");
//         description = getArguments().getString("group_description");


public class CreateGroupFragment extends Fragment {
    TeacherApi teacherApi;
    ArrayList<User> array = new ArrayList<>();
    ArrayList<Integer> array1 = new ArrayList<>();
    private RecyclerView recyclerView;
    AdapterAddGroup userManagementAdapter;
    EditText name, description;
    int group_id = 0;
    int user_id = 0;
    private TextView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherApi = ApiTeacher.getAPIService();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_group, null, false);
        name = (EditText) view.findViewById(R.id.tvnameGroup);
        description = (EditText) view.findViewById(R.id.tvDiscription);
        recyclerView = view.findViewById(R.id.list_student);
        recyclerView.setHasFixedSize(true);
        try {
            teacherApi.getStudName(2).enqueue(new Callback<StudentModel>() {
                @Override

                public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isResult()) {
                            try {
                                array = response.body().getUser();
                                ArrayList<String> array_id = new ArrayList<String>();
                                userManagementAdapter = new AdapterAddGroup(getActivity(), array, array_id);
                                recyclerView.setAdapter(userManagementAdapter);
                                Log.d("arrayyy", array.toString());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            } catch (Exception e) {
                            }
                        } else
                            Toast.makeText(getActivity(), "error123", Toast.LENGTH_SHORT).show();
                        // userManagementAdapter.notifyS);
                    }
                }

                @Override
                public void onFailure(Call<StudentModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "NO Enternt Connection", Toast.LENGTH_SHORT).show();


                }
            });
        } catch (Exception e) {
        }

        // recyclerView.setLayoutManager(RecyclerView);

        back = (TextView) view.findViewById(R.id.buCancel);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_student);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userManagementAdapter);

        back = (TextView) view.findViewById(R.id.buCancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  getFragmentManager().popBackStack();
                Intent intent =new Intent(getActivity(), TeacherActivity.class);
                startActivity(intent);

            }
        });
        view.findViewById(R.id.buAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nameg = name.getText().toString();
                    String des = description.getText().toString();
                    if (!nameg.isEmpty() && !des.isEmpty()) {
                 //try{

                        teacherApi.addGroup(nameg, des).enqueue(new Callback<GroupInsert>() {

                            @Override

                            public void onResponse(Call<GroupInsert> call, Response<GroupInsert> response) {
                                final ProgressDialog pd = new ProgressDialog(getActivity());
                                pd.setMessage("Saving Group ....");
                                pd.setCancelable(false);
                                pd.show();
                                if (response.isSuccessful()) {
                                    if (response.body().isResult()) {
                                        group_id = response.body().getId();
                                        Log.d("group_id", group_id + "");
                                        array1.addAll(userManagementAdapter.getArray().values());
                                        Log.d("size0", array1.size() + " ");
                                        HashMap testMap = new HashMap<String, String>();
                                        testMap.put("group_id", group_id);
                                        //
                                        testMap.put("user_id", array1);
                                        Log.d("testMap", testMap + " ");
                                        JSONObject jsonObject = new JSONObject(testMap);
                                        Log.d("jsonObject", jsonObject + " ");
                                        //RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj.toString());
                                        teacherApi.addArrayUserGroup(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject)))
                                                .enqueue(new Callback<InsertIntoGroup>() {
                                            @Override
                                            public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
                                                if (response.isSuccessful()) {
                                                    Log.d("//////", response.body().toString());
                                                    if (pd != null && pd.isShowing())
                                                        pd.dismiss();
                                                } else {
                                                    Log.d("//////", response.body().toString());
//                                                        // userManagementAdapter.notifyS);
                                                    if (pd != null && pd.isShowing())
                                                        pd.dismiss();
                                                    NoInternetAlert(getActivity());
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                                                Log.d("//////", t.toString());

                                            }
                                        });

//                                        for (int i = 0; i < array1.size(); i++) {
//                                            Log.d("list0 ", array1.toString());
//                                            teacherApi.addUserGroup(group_id , Integer.parseInt(array1.get(i).getUserId())).enqueue(new Callback<InsertIntoGroup>() {
//                                                @Override
//
//                                                public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
//                                                    if (response.isSuccessful()) {
//                                                        if (response.body().isResult()) {
//                                                            Toast.makeText(getContext(), array1.toString(), Toast.LENGTH_SHORT).show();
//
//                                                        } else
//                                                            Toast.makeText(getContext(), "error123", Toast.LENGTH_SHORT).show();
//                                                        // userManagementAdapter.notifyS);
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
//                                                    Toast.makeText(getContext(), "faaa", Toast.LENGTH_SHORT).show();
//
//                                                }
//                                            });
//
//                                        }
                                    } else
                                        Toast.makeText(getContext(), "error123", Toast.LENGTH_SHORT).show();
                                    // userManagementAdapter.notifyS);
                                }
                            }

                            @Override
                            public void onFailure(Call<GroupInsert> call, Throwable t) {
                                Toast.makeText(getActivity(), "NO Enternt Connection", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }

                    // Toast.makeText(getActivity(), userManagementAdapter.getArray().values().toString(), Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    Toast.makeText(getActivity(), "NO Enternt Connection", Toast.LENGTH_SHORT).show();


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
                    Intent intent =new Intent(getActivity(), TeacherActivity.class);
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
}