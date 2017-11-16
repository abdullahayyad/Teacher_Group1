package ps.wwbtraining.teacher_group1.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.ShowUserHistoryAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.UserHistoryItem;
import ps.wwbtraining.teacher_group1.Model.UserHistoryModel;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;


public class ShowUserHistory extends Fragment {

    private TeacherApi teacherApi;
    private RecyclerView list_quiz;
    private FloatingActionButton addQuiz;
    private ArrayList<UserHistoryItem> array = new ArrayList<>();
    private ShowUserHistoryAdapter showUserAdapter;
    private ActionMode mActionmode;
    private int myPosition;
    private View view;
    ProgressDialog pd;
    private RelativeLayout customView;
    private ProgressBar progress;
    int quiz_id;
    int group_id;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quiz_id = getArguments().getInt("quiz_id");
        group_id = getArguments().getInt("group_id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.user_history, container, false);
        teacherApi = ApiTeacher.getAPIService();
        list_quiz = view.findViewById(R.id.list_user);
        progress = view.findViewById(R.id.progress);

        progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#c0392b"), android.graphics.PorterDuff.Mode.MULTIPLY);
        TeacherActivity.toolbar.setVisibility(View.VISIBLE);
        pd = new ProgressDialog(getActivity());

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
            progress.setVisibility(View.VISIBLE);
            list_quiz.setVisibility(View.GONE);
            reloadData();
        } else {
            getUserInQuizHistory(quiz_id,group_id);


        }

    }
    private void getUserInQuizHistory(int quizId,int groupId) {
        list_quiz.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        try {
            teacherApi.showUserHistory(quizId,groupId).enqueue(new Callback<UserHistoryModel>() {
                @Override
                public void onResponse(Call<UserHistoryModel> call, Response<UserHistoryModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isResult()) {
                            list_quiz.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            array = response.body().getUser();

                            Log.d("hhhhhhh",response.body().getUser().toString());

                            showUserAdapter = new ShowUserHistoryAdapter(ShowUserHistory.this, array,
                                    new OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClicked(int position) {
                                            myPosition = position;
                                            return true;
                                        }
                                    });
                            list_quiz.setAdapter(showUserAdapter);
                            list_quiz.setLayoutManager(new LinearLayoutManager(getActivity()));

                        } else{
                            customSnackBare(view, "Something Error ....");
                            list_quiz.setVisibility(View.GONE);
                            progress.setVisibility(View.VISIBLE);
                        }
                    } else{
                        list_quiz.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        reloadData();
                    }
                }

                @Override
                public void onFailure(Call<UserHistoryModel> call, Throwable t) {
                    if(getView() != null){
                        list_quiz.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        if(getView()!=null)
                            reloadData();}
                }
            });

        } catch (Exception e) {
            customSnackBare(view, "Something Error ....");
        }


    }

    private void reloadData() {
        final Snackbar snackbar;
        try{
            snackbar = Snackbar.make(customView, "No Internet Connection:( ", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Reload", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getUserInQuizHistory(quiz_id,group_id);
                    snackbar.dismiss();
                }
            }).setActionTextColor(Color.WHITE);
            snackbar.show();
        }catch(Exception e){}
    }

}