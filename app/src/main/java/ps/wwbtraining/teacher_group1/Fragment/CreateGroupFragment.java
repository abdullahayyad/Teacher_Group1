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
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

        import ps.wwbtraining.teacher_group1.Adapter.AdapterAddGroup;
        import ps.wwbtraining.teacher_group1.Adapter.UserManagementAdapter;
        import ps.wwbtraining.teacher_group1.ApiTeacher;
        import ps.wwbtraining.teacher_group1.Model.StudentModel;
        import ps.wwbtraining.teacher_group1.Model.User;
        import ps.wwbtraining.teacher_group1.R;
        import ps.wwbtraining.teacher_group1.WebService.TeacherApi;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class CreateGroupFragment extends Fragment {
    TeacherApi teacherApi;
    ArrayList<User> array=new ArrayList<>();
    AdapterAddGroup  userManagementAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherApi = ApiTeacher.getAPIService();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_group, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.listStudentAddGroup);
        recyclerView.setHasFixedSize(true);
        teacherApi.getStudName(2).enqueue(new Callback<StudentModel>() {

            @Override
            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                array.clear();
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {

                        List<User> list =response.body().getUser();
                        array.addAll(list);
                        Log.d("rrr",array.toString());
                        Log.d("gg", array + "");
                        userManagementAdapter = new AdapterAddGroup(getActivity(), array);
                        Log.d("gg", "ff");
                        recyclerView.setAdapter(userManagementAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        Log.d("gg", "hhh");

                        Toast.makeText(getActivity(),array.toString()+ "", Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(getActivity(), "error123", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StudentModel> call, Throwable t) {
                Toast.makeText(getActivity(), "faaa", Toast.LENGTH_SHORT).show();
                Log.d("ffff", "fff");
            }
        });
        Toast.makeText(getActivity(),array+ "ll", Toast.LENGTH_SHORT).show();
       try {

}catch (Exception e){

}

        Toast.makeText(getActivity(),array.toString()+ "nnnn", Toast.LENGTH_SHORT).show();

        return view;
    }
}
