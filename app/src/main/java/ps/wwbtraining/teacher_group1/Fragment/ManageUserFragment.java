package ps.wwbtraining.teacher_group1.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Adapter.UserManagementAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

public class ManageUserFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<User> array = new ArrayList<>();
    private String r;
    private UserManagementAdapter userManagementAdapter;
    private Spinner spinner;
    private TeacherApi teacherApi;
    private ArrayAdapter<CharSequence> adapter;
    private Snackbar snackbar;
    private TextView tvNoItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_manage_user, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.user_status, R.layout.textview_with_font_change);
        tvNoItems = (TextView) view.findViewById(R.id.tvNoItems);
        tvNoItems.setVisibility(View.GONE);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        teacherApi = ApiTeacher.getAPIService();
        recyclerView = view.findViewById(R.id.list_user);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                        if (!isOnline(getActivity())) {
                            recyclerView.setVisibility(View.GONE);
                            reloadData(view);
                        } else getData(view);
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
                    public void onClick(View v) {
                        final ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setMessage("Saving Group ....");
                        pd.setCancelable(false);
                        pd.show();
//                        final ArrayList<User> arrayList = userManagementAdapter.arrayUser();
                        for (int i = 0; i < array.size(); i++) {
                            final int finalI = i;
                            teacherApi
                                    .updateStatus(array.get(i).getUserId(), array.get(i).getStatusId())
                                    .enqueue(new Callback<UpdateStatus>() {
                                        @Override
                                        public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {

                                            if (response.isSuccessful()) {
                                                if (pd != null && pd.isShowing())
                                                    pd.dismiss();
//                                                customSnackBare(view,"Saving Data ....","Dismiss");
                                                // userManagementAdapter(getActivity(),array,)
//                                                    getData(view);
//                                                array.remove(finalI);
                                                userManagementAdapter.notifyDataSetChanged();
                                            } else {
                                                if (pd != null && pd.isShowing())
                                                    pd.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UpdateStatus> call, Throwable t) {
                                            if (pd != null && pd.isShowing())
                                                pd.dismiss();
                                            reloadData(view);
                                        }
                                    });
                        }
                    }
                }
        );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
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
                        getFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                .replace(R.id.frameTeacher, new Teacher_Fragment()).commit();

                    return true;

                }
                return false;
            }
        });
    }


    private void getData(final View view) {

        teacherApi.getStudName(spinner.getSelectedItemPosition() + 2).enqueue(new Callback<StudentModel>() {
            @Override
            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {
                        getUser(response);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvNoItems.setVisibility(View.VISIBLE);
//                            customSnackBareReload(view, response, "Something Error :(");
                    }
                }
            }

            @Override
            public void onFailure(Call<StudentModel> call, Throwable t) {
                reloadData(view);
            }
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
        ;
        snackbar.show();
    }

    private void reloadData(View view) {
        final Snackbar snackbar;
        snackbar = Snackbar.make(view, "No Internet Connection:( ", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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