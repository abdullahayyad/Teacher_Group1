package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.ShowQuizAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.QuizItem;
import ps.wwbtraining.teacher_group1.Model.QuizModel;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by مركز الخبراء on 10/26/2017.
 */

public class ShowQuizFragment extends Fragment {

    TeacherApi teacherApi;
    RecyclerView list_quiz;
    FloatingActionButton addQuiz;
    ArrayList<QuizItem> array = new ArrayList<>();
    ShowQuizAdapter showQuizAdapter;
    private ActionMode mActionmode;
    private int myPosition;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_quiz, null, false);
        teacherApi = ApiTeacher.getAPIService();
        list_quiz = (RecyclerView) view.findViewById(R.id.list_quiz);
        addQuiz = (FloatingActionButton) view.findViewById(R.id.addQuiz);

        addQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.show_quiz, new CreateQuiz()).commit();

            }
        });
        list_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hanan", Toast.LENGTH_SHORT).show();
                //mActionmode = getActivity().startActionMode(mActionModeCallback);
            }
        });

        teacherApi.showQuiz().enqueue(new Callback<QuizModel>() {
            @Override

            public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {

                        array = response.body().getGroup();
                        showQuizAdapter = new ShowQuizAdapter(ShowQuizFragment.this, array, new OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClicked(int position) {
                                myPosition = position;
                                mActionmode = getActivity().startActionMode(mActionModeCallback);
                                return true;
                            }
                        });
                        list_quiz.setAdapter(showQuizAdapter);
                        list_quiz.setLayoutManager(new LinearLayoutManager(getActivity()));

                    } else
                        Toast.makeText(getActivity(), "error123", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<QuizModel> call, Throwable t) {
                Toast.makeText(getContext(), "faaa", Toast.LENGTH_SHORT).show();

            }

        });

        return view;
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getActivity().getMenuInflater().inflate(R.menu.menu_quize, menu);
            TeacherActivity.toolbar.setVisibility(View.GONE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:


//                    if (del ==1) {
//
//                        mode.finish();
//                    }
//
//                    else {
//
                    mode.finish();
                    TeacherActivity.toolbar.setVisibility(View.VISIBLE);

//                    }
                    break;
                case R.id.update:

                    mode.finish();
                    TeacherActivity.toolbar.setVisibility(View.VISIBLE);
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionmode = null;
        }
    };

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


}
