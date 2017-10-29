package ps.wwbtraining.teacher_group1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Adapter.ShowGroupAdapter;
import ps.wwbtraining.teacher_group1.Adapter.ShowQuizAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
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

        teacherApi.showQuiz().enqueue(new Callback<QuizModel>() {
            @Override

            public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {

                        array = response.body().getGroup();
                        showQuizAdapter = new ShowQuizAdapter(ShowQuizFragment.this, array);
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
            getActivity().getMenuInflater().inflate(R.menu.menu, menu);
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

                    mode.finish();
                    break;

                case R.id.update:

                    mode.finish();
                    break;

            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionmode = null;
        }
    };
}
