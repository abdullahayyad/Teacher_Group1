package ps.wwbtraining.teacher_group1.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.EditGroupAdapter;
import ps.wwbtraining.teacher_group1.Adapter.ShowQuizAdapter;
import ps.wwbtraining.teacher_group1.Adapter.ShowQuizHistoryAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.InsertInToQuiz;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.QuizItem;
import ps.wwbtraining.teacher_group1.Model.QuizModel;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.Model.UserFromGroupModel;
import ps.wwbtraining.teacher_group1.Model.UserItem;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

/**
 * Created by Hanan Dawod on 14/11/17.
 */

public class ShowQuizHistory extends Fragment {

    private TeacherApi teacherApi;
    private RecyclerView list_quiz;
    private FloatingActionButton addQuiz;
    private ArrayList<QuizItem> array = new ArrayList<>();
    private ShowQuizHistoryAdapter showQuizAdapter;
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
        group_id = getArguments().getInt("group_id");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_show_quiz, container, false);
        teacherApi = ApiTeacher.getAPIService();
        list_quiz = (RecyclerView) view.findViewById(R.id.list_quiz);
        addQuiz = (FloatingActionButton) view.findViewById(R.id.addQuiz);
        addQuiz.setVisibility(View.GONE);
        customView = (RelativeLayout)view.findViewById(R.id.show_quiz);
        progress = (ProgressBar) view.findViewById(R.id.progress);
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


//                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_enter, R.anim.right_out).
//                            replace(R.id.frameTeacher, new Teacher_Fragment()).addToBackStack(null).commit();
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
            getQuizzes(group_id);
        }

    }
    private void getQuizzes(final int groupId) {
        list_quiz.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        try {
            teacherApi.showQuizHistory(groupId).enqueue(new Callback<QuizModel>() {
                @Override
                public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isResult()) {
                            list_quiz.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            array = response.body().getGroup();

                            Log.d("hhhhhhh",response.body().getGroup().toString());
                            showQuizAdapter = new ShowQuizHistoryAdapter(ShowQuizHistory.this, array, new OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClicked(int position) {
                                    myPosition = position;
                                         FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    ShowUserHistory newFragment = new ShowUserHistory();
//
                                    quiz_id = array.get(myPosition).getQuiz_id();
                                    Bundle args = new Bundle();
                                    args.putInt("quiz_id",array.get(myPosition).getQuiz_id());
                                    args.putInt("group_id",group_id);
                                    newFragment.setArguments(args);
                                    transaction.replace(R.id.show_quiz, newFragment);
                                    transaction.commit();

                                    return true;
                                }

                            });
                            list_quiz.setAdapter(showQuizAdapter);
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
                public void onFailure(Call<QuizModel> call, Throwable t) {
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
                    getQuizzes(group_id);
                    snackbar.dismiss();
                }
            }).setActionTextColor(Color.WHITE);
            snackbar.show();
        }catch(Exception e){}


    }





    }