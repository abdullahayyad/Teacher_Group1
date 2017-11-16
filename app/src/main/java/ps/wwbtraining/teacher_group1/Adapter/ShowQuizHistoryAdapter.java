package ps.wwbtraining.teacher_group1.Adapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Fragment.ShowQuizFragment;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GroupItem;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.QuizItem;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

/**
 * Created by Hanan Dawod on 14/11/17.
 */

public class ShowQuizHistoryAdapter extends RecyclerView.Adapter<ShowQuizHistoryAdapter.ViewHolder> {

    private final ArrayList<QuizItem> arrayList;
    Fragment context;
    TeacherApi teacherApi;
    ArrayList<GroupItem> array;
    OnItemLongClickListener listener;
    public  int post ;
    public  int index ;

    ProgressDialog pd;


    public ShowQuizHistoryAdapter(Fragment context, ArrayList<QuizItem> arrayList, OnItemLongClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quize_list, parent, false);
        teacherApi = ApiTeacher.getAPIService();
        array = new ArrayList<>();
        teacherApi= ApiTeacher.getAPIService();
        pd = new ProgressDialog(context.getActivity());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = arrayList.get(position);
        holder.quiz_name.setText(arrayList.get(position).getQuiz_name());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.quiz_id = arrayList.get(position).getQuiz_id();
       holder.send.setVisibility(View.GONE);
        holder.del.setVisibility(View.GONE);
        teacherApi.checkQuiz(holder.quiz_id).enqueue(new Callback<UpdateStatus>() {
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {

                        holder.completed.setVisibility(View.VISIBLE);
                        holder.notCompleted.setVisibility(View.GONE);
                        holder.cr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                listener.onItemLongClicked(position);
                                post = arrayList.get(position).getQuiz_id();
                                index = position;
                            }

                        });
                    }
                    else{
                        holder.completed.setVisibility(View.GONE);
                    holder.notCompleted.setVisibility(View.VISIBLE);
                }}
            }

            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Toast.makeText(context.getActivity(), "No Internet ", Toast.LENGTH_SHORT).show();
            }

        });


    }

    int size = 0;

    @Override
    public int getItemCount() {
        try {
            size = arrayList.size();

        } catch (Exception e) {

        }
        return size;
    }






    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView quiz_name;
        public final ImageButton del;
        int quiz_id ;
        public final TextView description;
        public final ImageButton send;
        private final CardView cr;
        public QuizItem mItem;
        public final ImageView completed;
        public final ImageView notCompleted;
        public ViewHolder(View view) {

            super(view);

            mView = view;
            quiz_name = (TextView) view.findViewById(R.id.QuizeName);
            description = (TextView) view.findViewById(R.id.description);
            send = (ImageButton) view.findViewById(R.id.sendQuiz);
            del = (ImageButton) view.findViewById(R.id.deletQuiz);
            cr = (CardView) view.findViewById(R.id.cardView_quize);
            quiz_id = 0;
            completed = view.findViewById(R.id.completed);
            notCompleted = view.findViewById(R.id.notCompleted);
        }

    }

}
