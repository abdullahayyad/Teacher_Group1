package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Adapter.UserManagementAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageUserFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<User> array = new ArrayList<>();
    String r;
    UserManagementAdapter userManagementAdapter;

    Spinner spinner;
    TeacherApi teacherApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manage_user, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.user_status, R.layout.textview_with_font_change);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        teacherApi = ApiTeacher.getAPIService();
        recyclerView = view.findViewById(R.id.list_user);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  try {

                                                      teacherApi.getStudName(spinner.getSelectedItemPosition() + 2).enqueue(new Callback<StudentModel>() {
                                                          @Override

                                                          public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                                                              if (response.isSuccessful()) {
                                                                  if (response.body().isResult()) {
                                                                      array = response.body().getUser();
                                                                      userManagementAdapter = new UserManagementAdapter(getActivity(), array, spinner.getSelectedItemPosition() + 2);
                                                                      recyclerView.setAdapter(userManagementAdapter);
                                                                      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                                                      userManagementAdapter.SelectAll(spinner.getSelectedItemPosition()+2);
                                                                      Log.d("rrr", array.toString());
                                                                  } else
                                                                      Toast.makeText(getActivity(), "error123", Toast.LENGTH_SHORT).show();
                                                              }
                                                          }

                                                          @Override

                                                          public void onFailure(Call<StudentModel> call, Throwable t) {
                                                              Toast.makeText(getActivity(), "faaa", Toast.LENGTH_SHORT).show();
                                                              Log.d("ffff", "fff");
                                                          }
                                                      });


                                                      // recyclerView.setLayoutManager(RecyclerView);

                                                  } catch (Exception e) {
                                                      Toast.makeText(getActivity(), "size 0", Toast.LENGTH_SHORT).show();
                                                  }
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {

                                              }
                                          }
        );
        TextView textView =(TextView)view.findViewById(R.id.buAdd);

        textView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           ArrayList <User> arrayList =userManagementAdapter.arrayUser();


       }
   });
        return view;
    }

//    public ArrayList<User> getUserName(int status) {
//
//        teacherApi.getStudName(status).enqueue(new Callback<StudentModel>() {
//            @Override
//            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().isResult()) {
//                        array =response.body().getUser();
//
//                        Log.d("rrr",array.toString());
//                    } else
//                        Toast.makeText(getActivity(), "error123", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//
//            public void onFailure(Call<StudentModel> call, Throwable t) {
//                Toast.makeText(getActivity(), "faaa", Toast.LENGTH_SHORT).show();
//                Log.d("ffff", "fff");
//            }
//        });
//        return array;
//    }
//    public ArrayList<User> getStudent(final int status) {
//        returnList = new ArrayList<>();
//
//        teacherApi.getStudName(status).enqueue(new Callback<UserName>() {
//            @Override
//            public void onResponse(Call<UserName> call, Response<UserName> response) {
////                Toast.makeText(getActivity(), "sucessfull   " + response.body().toString(), Toast.LENGTH_SHORT).show();
//                UserName users = response.body();
////                users.getStatuse();
//                if (users.isResult()) {
//                    Toast.makeText(getActivity(), "successful", Toast.LENGTH_SHORT).show();
//                    returnList.add(users.getUser());
//
//                }
//                else
//                    Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<UserName> call, Throwable t) {
//                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//        return returnList;
//    }

}