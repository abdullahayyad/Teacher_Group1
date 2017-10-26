package ps.wwbtraining.teacher_group1.Fragment;

/**
 * Created by osama on 17/10/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Adapter.AdapterAddGroup;
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

// nameGroup = getArguments().getString("group_name");
//         group_description = getArguments().getString("group_description");


public class EditGroupFragment extends Fragment {
    String nameGroup, group_description;
    TeacherApi teacherApi;
    ArrayList<User> array = new ArrayList<>();
    ArrayList<UserItem> array1 = new ArrayList<>();
    private RecyclerView recyclerView;
    AdapterAddGroup userManagementAdapter;
    EditText name, description;
    int group_id;

    ArrayList<String> arrayId = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameGroup = getArguments().getString("group_name");
        group_description = getArguments().getString("group_description");
        group_id = getArguments().getInt("group_id");

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
        name.setText(nameGroup);
        description.setText(group_description);

        try {
            teacherApi.getStudName(2).enqueue(new Callback<StudentModel>() {
                @Override

                public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isResult()) {
                            try {
                                array = response.body().getUser();
                                arrayId = new ArrayList<String>();
                                userManagementAdapter = new AdapterAddGroup(getActivity(), array,arrayId);
                                recyclerView.setAdapter(userManagementAdapter);
                                Log.d("arrayyy", array.toString());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                                try {
                                    teacherApi.userFromGroup(group_id).enqueue(new Callback<UserFromGroupModel>() {
                                        @Override

                                        public void onResponse(Call<UserFromGroupModel> call, Response<UserFromGroupModel> response) {
                                            if (response.isSuccessful()) {


                                                try {
                                                    array1 = response.body().getUser();
                                                    for (int i = 0; i < array1.size(); i++) {
                                                        arrayId.add(array1.get(i).getUserId());
                                                    }
                                                    userManagementAdapter = new AdapterAddGroup(getActivity(), array, arrayId);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                                } catch (Exception e) {
                                                }


                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserFromGroupModel> call, Throwable t) {
                                            Toast.makeText(getContext(), "faaa", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                } catch (Exception e) {
                                }
                            } catch (Exception e) {
                            }
                        } else
                            Toast.makeText(getContext(), "error123", Toast.LENGTH_SHORT).show();
                        // userManagementAdapter.notifyS);
                    }
                }

                @Override
                public void onFailure(Call<StudentModel> call, Throwable t) {
                    Toast.makeText(getContext(), "faaa", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
        }

        // recyclerView.setLayoutManager(RecyclerView);


        recyclerView = (RecyclerView) view.findViewById(R.id.list_student);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userManagementAdapter);
//////////////////////////////////////////////user in group
        view.findViewById(R.id.buAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nameg = name.getText().toString();
                    String des = description.getText().toString();
                    if (!nameg.isEmpty() && !des.isEmpty()) {
                        teacherApi.updateGroup(group_id,nameg, des).enqueue(new Callback<InsertIntoGroup>() {
                            @Override

                            public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {

                                if (response.isSuccessful()) {
                                    if (response.body().isResult()) {
                                        Log.d("size0", array1.size() + " ");
                                        deleteUser(group_id);
//                                       for (int i = 0; i < array1.size(); i++) {
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
                                        Toast.makeText(getContext(), "Edit group", Toast.LENGTH_SHORT).show();
                                        array.clear();
                                        userManagementAdapter.notifyDataSetChanged();

                                    } else
                                        Toast.makeText(getContext(), "error123", Toast.LENGTH_SHORT).show();
                                    // userManagementAdapter.notifyS);
                                }
                            }

                            @Override
                            public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }



                } catch (Exception e) {


                }

            }
        });


        return view;
    }
    public void deleteUser(final int group_id) {
        teacherApi.deleteGroupUser(group_id).enqueue(new Callback<InsertIntoGroup>() {
            @Override
            public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {
                if (response.body().isResult()) {
                    Log.d("delete","sucess");
                }

            }

            @Override
            public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }}