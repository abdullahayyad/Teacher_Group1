package ps.wwbtraining.teacher_group1.Fragment;


import android.app.ProgressDialog;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Adapter.EditGroupAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.InsertInToQuiz;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.Model.UserFromGroupModel;
import ps.wwbtraining.teacher_group1.Model.UserItem;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

public class EditGroupFragment extends Fragment {
    String nameGroup, group_description;
    TeacherApi teacherApi;
    ArrayList<User> array = new ArrayList<>();
    ArrayList<UserItem> array1 = new ArrayList<>();
    ArrayList<Integer>checkedArray = new ArrayList<>();
    private RecyclerView recyclerView;
    EditGroupAdapter userManagementAdapter;
    EditText name, description;
    int group_id;
    ArrayList<String> arrayId = new ArrayList<>();
    ArrayList <UserItem>arrayStatus;
    ProgressDialog pd;
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
        pd = new ProgressDialog(getActivity());
        arrayStatus = new ArrayList();

        try {

            Log.d("tttt",group_id+"");

            if (isOnline(getActivity())) {
                pd.setMessage("Upload User in Group ....");
                pd.setCancelable(false);
                pd.show();
            teacherApi.userFromGroup(group_id).enqueue(new Callback<UserFromGroupModel>() {
                @Override

                public void onResponse(Call<UserFromGroupModel> call, Response<UserFromGroupModel> response) {
                    if (response.isSuccessful()) {
                        try {
                            array1 = response.body().getUser();
                            arrayStatus =response.body().getUserStatus();
                            Log.d("ccccc",response.body().getUser().toString()+" gghh");
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();

                            }
                            for (int i = 0; i < array1.size(); i++) {
                                arrayId.add(array1.get(i).getUserId());
                            }

                            userManagementAdapter = new EditGroupAdapter(getActivity(), arrayStatus, arrayId, group_id);
                            recyclerView.setAdapter(userManagementAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        } catch (Exception e) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserFromGroupModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "no Internet...", Toast.LENGTH_SHORT).show();
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();

                    }

                }
            });}

        } catch (Exception e) {
        }
        view.findViewById(R.id.buCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.relayout, new ShowGroupFragment()
                        ).commit();

                //relayout
            }});

        view.findViewById(R.id.buAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nameg = name.getText().toString();
                    String des = description.getText().toString();
                    if (!nameg.isEmpty() && !des.isEmpty()) {
                        if (isOnline(getActivity())) {
                            pd.setMessage("Upload User in Group ....");
                            pd.setCancelable(false);
                            pd.show();
                            teacherApi.updateGroup(group_id, nameg, des).enqueue(new Callback<InsertIntoGroup>() {
                                @Override

                                public void onResponse(Call<InsertIntoGroup> call, Response<InsertIntoGroup> response) {

                                    if (response.isSuccessful()) {
                                        if (response.body().isResult()) {
                                            if (pd != null && pd.isShowing()) {
                                                pd.dismiss();

                                            }
                                            update();
                                            //Toast.makeText(getActivity(), "Edit group", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertIntoGroup> call, Throwable t) {
                                    Toast.makeText(getContext(), "No Internet ...", Toast.LENGTH_SHORT).show();
                                    if (pd != null && pd.isShowing()) {
                                        pd.dismiss();

                                    }
                                }
                            });
                        }         }
                } catch (Exception e) {
                }

            }
        });


        return view;
    }

    public void update (){
        ArrayList <Integer>dddd =new ArrayList<>(userManagementAdapter.getArray().values());
        HashMap map = new HashMap();
        map.put("group_id",group_id);
        map.put("users",dddd);

        JSONObject object =new JSONObject(map);
        Log.d("vvvv",object.toString());

        try {
            teacherApi.UpdateGroupFromUser(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(object))).enqueue(new Callback<InsertInToQuiz>() {
                @Override
                public void onResponse(Call<InsertInToQuiz> call, Response<InsertInToQuiz> response) {
                    if (response.isSuccessful()) {

                    }
                }

                @Override
                public void onFailure(Call<InsertInToQuiz> call, Throwable t) {
                    Log.d("//////", t.toString());

                }
            });

        } catch (Exception e) {

        }
    }
}