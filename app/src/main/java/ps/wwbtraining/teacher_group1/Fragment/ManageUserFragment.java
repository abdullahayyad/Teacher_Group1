package ps.wwbtraining.teacher_group1.Fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.UserManagementAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.InsertInToQuiz;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

public class ManageUserFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<User> array = new ArrayList<>();
    private UserManagementAdapter userManagementAdapter;
    private Spinner spinner;
    private TeacherApi teacherApi;
    private ArrayAdapter<CharSequence> adapter;
    private TextView tvNoItems;
    private View view;
    private ProgressBar progress;
    private RelativeLayout customView;
    private int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manage_user, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        customView=(RelativeLayout)view.findViewById(R.id.rlayout);

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.user_status, R.layout.textview_with_font_change);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#c0392b"), android.graphics.PorterDuff.Mode.MULTIPLY);

        tvNoItems = (TextView) view.findViewById(R.id.tvNoItems);
        tvNoItems.setVisibility(View.GONE);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        teacherApi = ApiTeacher.getAPIService();
        recyclerView = view.findViewById(R.id.list_user);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, final View v, int i, long l) {
                        pos = spinner.getSelectedItemPosition();
                        if (isOnline(getActivity())) {
                            recyclerView.setVisibility(View.GONE);
                            getData(view);
                        } else{
                            recyclerView.setVisibility(View.GONE);
                            reloadData();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                }
        );
        TextView save = (TextView) view.findViewById(R.id.buAdd);
        TextView back = (TextView) view.findViewById(R.id.buCancel);

        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if(isOnline(getActivity())) {
                            final ProgressDialog pd = new ProgressDialog(getActivity());
                            pd.setMessage("Saving Group ....");
                            pd.setCancelable(false);
                            pd.show();

                            HashMap map1 = new HashMap();
                            final ArrayList arrayList =new ArrayList();
                            final HashMap hashMap =new HashMap();

                            for (int i = 0 ; i < array.size() ; i++){
                                if (!array.get(i).getStatusId().contains(pos+2+"")){
                                map1.put("user_id",array.get(i).getUserId()) ;
                                map1.put("status_id",array.get(i).getStatusId());
                                JSONObject object = new JSONObject(map1);
                                arrayList.add(object);
                                map1.clear();

                                }else {
                                    hashMap.put(array.get(i),array.get(i));
                                    Log.d("jjjj",hashMap.size()+"");
                                }
                            }

                            HashMap map =new HashMap();
                            map.put("manage",arrayList);
                            JSONObject object = new JSONObject(map);
                            teacherApi.updateStatusUser(
                                    RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(object)))
                                    .enqueue(new Callback<InsertInToQuiz>() {

                                        @Override
                                        public void onResponse(Call <InsertInToQuiz> call, Response <InsertInToQuiz>response) {

                                            if (response.isSuccessful()) {
                                                array.clear();
                                                array.addAll(hashMap.values());
                                                userManagementAdapter.notifyDataSetChanged();

                                                if (pd != null && pd.isShowing()) {
                                                    pd.dismiss();

                                            }
                                            } else {
                                                if (pd != null && pd.isShowing())
                                                    pd.dismiss();
                                                    customSnackBare(customView,"Somethin Error...");
                                            }
                                            }
                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            Toast.makeText(getActivity(), "No Internet ", Toast.LENGTH_SHORT).show();

                                            if (pd != null && pd.isShowing())

                                               pd.dismiss();

                                            if(getView() != null)
                                                reloadData();
                                        }

                                    });

                        }else customSnackBare(customView,"No Internet Connection...");
                    }
                }
        );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeacherActivity.class);
                startActivity(intent);
                getActivity().finish();            }
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
//                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_enter, R.anim.right_out).
//                            replace(R.id.frameTeacher, new Teacher_Fragment()).addToBackStack(null).commit();
                    return true;

                }
                return false;
            }
        });
    }


    private void getData(final View view) {
        progress.setVisibility(View.VISIBLE);
        teacherApi.getStudName(spinner.getSelectedItemPosition() + 2).enqueue(new Callback<StudentModel>() {
            @Override
            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                if (response.isSuccessful()) {
                    progress.setVisibility(View.GONE);

                    if (response.body().isResult()) {
                        getUser(response);
                    } else {
                        progress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        tvNoItems.setVisibility(View.VISIBLE);
                    }
                }else {
                    progress.setVisibility(View.VISIBLE);
                    reloadData();
                }
            }

            @Override
            public void onFailure(Call<StudentModel> call, Throwable t) {
                if(getView() != null)
                    reloadData();            }
        });

    }

    private void getUser(Response<StudentModel> response) {
        array = response.body().getUser();
        if (array != null && !(array.isEmpty()) && array.size() != 0) {
            recyclerView.setVisibility(View.VISIBLE);
            userManagementAdapter = new UserManagementAdapter(getActivity(), array, spinner.getSelectedItemPosition() + 2);
            recyclerView.setAdapter(userManagementAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            userManagementAdapter.SelectAll(spinner.getSelectedItemPosition() + 2);
            tvNoItems.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoItems.setVisibility(View.VISIBLE);
        }
    }

    private void customSnackBareReload(View view, final Response<StudentModel> response, String message) {
        final Snackbar snackbar;
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser(response);
                snackbar.dismiss();
            }
        }).setActionTextColor(Color.WHITE);

        snackbar.show();
    }

    private void reloadData() {

        progress.setVisibility(View.GONE);
        final Snackbar snackbar;
        snackbar = Snackbar.make(customView, "No Internet Connection:( ", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(view);
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