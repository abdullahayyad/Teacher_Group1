package ps.wwbtraining.teacher_group1.Fragment;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Adapter.ShowQuizAdapter;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.QuizItem;
import ps.wwbtraining.teacher_group1.Model.QuizModel;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.customSnackBare;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

/**
 * Created by مركز الخبراء on 10/26/2017.
 */

public class ShowQuizFragment extends Fragment {

    private TeacherApi teacherApi;
    private RecyclerView list_quiz;
    private FloatingActionButton addQuiz;
    private ArrayList<QuizItem> array = new ArrayList<>();
    private ShowQuizAdapter showQuizAdapter;
    private ActionMode mActionmode;
    private int myPosition;
    private View view;
    private RelativeLayout customView;
    private ProgressBar progress;
    int quiz_id;

    private ActionMode.Callback mActionModeCallback1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_quiz, container, false);
        teacherApi = ApiTeacher.getAPIService();
        list_quiz = (RecyclerView) view.findViewById(R.id.list_quiz);
        addQuiz = (FloatingActionButton) view.findViewById(R.id.addQuiz);
        customView = (RelativeLayout)view.findViewById(R.id.show_quiz);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#c0392b"), android.graphics.PorterDuff.Mode.MULTIPLY);
        TeacherActivity.toolbar.setVisibility(View.VISIBLE);
        addQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.show_quiz, new CreateQuiz()).commit();
//                getActivity().finish();

            }
        });

//        list_quiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getActivity(), "Hanan", Toast.LENGTH_SHORT).show();
//                //mActionmode = getActivity().startActionMode(mActionModeCallback);
//            }
//        });


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
            getQuizzes();
        }

    }
    private void getQuizzes() {
        list_quiz.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        try {
            teacherApi.showQuiz().enqueue(new Callback<QuizModel>() {
                @Override
                public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isResult()) {
                            list_quiz.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            array = response.body().getGroup();
                            Log.d("hhhhhhh",response.body().getGroup().toString());
                            showQuizAdapter = new ShowQuizAdapter(ShowQuizFragment.this, array, new OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClicked(int position) {
                                    myPosition = position;
//                                    mActionmode = getActivity().startActionMode(mActionModeCallback);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    ShowQuestionFragment newFragment = new ShowQuestionFragment();
                                    quiz_id = showQuizAdapter.post;
                                    Bundle args = new Bundle();
                                    Log.d("quizId",quiz_id+"");
                                    args.putInt("quiz_id",quiz_id);
                                    args.putString("quiz_name", array.get(myPosition).getQuiz_name());
//                    Log.d("nameee",holder.group_name.getText().toString());
//                    Log.d("description",holder.description.getText().toString());
                                    args.putString("quiz_description",array.get(myPosition).getDescription());
                                    newFragment.setArguments(args);
                                    transaction.replace(R.id.show_quiz, newFragment);
                                    transaction.commit();
//                                    TeacherActivity.toolbar.setVisibility(View.VISIBLE);
                                    return true;
                                }
                                @Override
                                public void onItemDelete(int position) {
                                    quiz_id = showQuizAdapter.post;
                                    Log.d("hhh",quiz_id+"");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Do you want to remove?");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    UpdateFlagQuiz(quiz_id);
                                                    array.remove(showQuizAdapter.index);
                                                    showQuizAdapter.notifyDataSetChanged();
                                                }
                                            });
                                    builder.setNegativeButton("No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
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
                        reloadData();}
                }
            });
        } catch (Exception e) {
            customSnackBare(view, "Something Error ....");
        }
    }

    private void reloadData() {
        final Snackbar snackbar;
        snackbar = Snackbar.make(customView, "No Internet Connection:( ", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuizzes();
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


    // private ShowQuestionFragment newFragment;
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getActivity().getMenuInflater().inflate(R.menu.menu_quize, menu);
//            TeacherActivity.toolbar.setVisibility(View.GONE);
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
                    quiz_id = showQuizAdapter.post;
                    Log.d("hhh",quiz_id+"");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Do you want to remove?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    UpdateFlagQuiz(quiz_id);
                                    array.remove(showQuizAdapter.index);
                                    showQuizAdapter.notifyDataSetChanged();
                                }
                            });
                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();



                    mode.finish();
                    TeacherActivity.toolbar.setVisibility(View.VISIBLE);

                    break;
                case R.id.update:

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ShowQuestionFragment newFragment = new ShowQuestionFragment();
                    quiz_id = showQuizAdapter.post;
                    Bundle args = new Bundle();
                    Log.d("quizId",quiz_id+"");
                    args.putInt("quiz_id",quiz_id);
                    args.putString("quiz_name", array.get(myPosition).getQuiz_name());
//                    Log.d("nameee",holder.group_name.getText().toString());
//                    Log.d("description",holder.description.getText().toString());
                    args.putString("quiz_description",array.get(myPosition).getDescription());
                    newFragment.setArguments(args);
                    transaction.replace(R.id.show_quiz, newFragment);
                    transaction.commit();
                    TeacherActivity.toolbar.setVisibility(View.VISIBLE);
                    mode.finish();
                    // TeacherActivity.toolbar.setVisibility(View.VISIBLE);

                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionmode = null;
        }
    };
    public void UpdateFlagQuiz(int quiz_id) {
        teacherApi.updateFlagQuiz(quiz_id, 0).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.body().isResult()) {
                    Log.d("update", "insert");
                }
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }
        });
    }}
