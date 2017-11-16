package ps.wwbtraining.teacher_group1.Adapter;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.OnItemLongClickListener;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.GradeModel;
import ps.wwbtraining.teacher_group1.Model.UserHistoryItem;
import ps.wwbtraining.teacher_group1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ShowUserHistoryAdapter  extends RecyclerView.Adapter<ShowUserHistoryAdapter.ViewHolder> {

    private final ArrayList<UserHistoryItem> arrayList;
    Fragment context;
    TeacherApi teacherApi;
    ArrayList<GradeModel.GradeItem>gradeModels =  new ArrayList<>();;
    OnItemLongClickListener listener;
    int grade = 0;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    ProgressDialog pd;
    double sum = 0 ;


    public ShowUserHistoryAdapter(Fragment context, ArrayList<UserHistoryItem> arrayList, OnItemLongClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("array",arrayList.toString());
        this.listener = listener;

        mDrawableBuilder = TextDrawable.builder()
                .round();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_history_item, parent, false);
        teacherApi = ApiTeacher.getAPIService();
        pd = new ProgressDialog(context.getActivity());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = arrayList.get(position);
        Log.d("gradee",arrayList.toString()+" "+arrayList.size());
        holder.stud_name.setText(arrayList.get(position).getUser_name());
        holder.quiz_id = arrayList.get(position).getQuiz_id();
        holder.user_id = arrayList.get(position).getUser_id();

        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(arrayList.get(position).getUser_name().charAt(0)),
                mColorGenerator.getColor(arrayList.get(position).getUser_name()));
        holder.image.setImageDrawable(drawable);
        holder.mView.setBackgroundColor(Color.TRANSPARENT);

        teacherApi.gradeStudent(holder.user_id,holder.quiz_id).enqueue(new Callback<GradeModel>(){

            @Override
            public void onResponse(Call<GradeModel> call, Response<GradeModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResult()) {
                        gradeModels = response.body().getUser();
                        Log.d("gradeModel",gradeModels.toString()+"");
                        for(int i =0;i<gradeModels.size();i++){
                            grade = gradeModels.get(i).getGradee();
                            sum += grade;
                            Log.d("grade = ", grade + "");
                            if(grade>=90){
                                holder.stud_name.append("          "+grade+"%");
                                holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#0a7105"),android.graphics.PorterDuff.Mode.SRC_IN);
                            }
                            else if(grade>=80) {
                                holder.stud_name.append("          " + grade + "%");
                                holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#76f211"), android.graphics.PorterDuff.Mode.SRC_IN);
                            }

                            else if(grade>=70) {
                                holder.stud_name.append("          " + grade + "%");
                                holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#c9f211"),android.graphics.PorterDuff.Mode.SRC_IN);
                            }
                            else if(grade>=60) {
                                holder.stud_name.append("          " + grade + "%");
                                holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#f26f11"),android.graphics.PorterDuff.Mode.SRC_IN);
                            }

                            else{
                                holder.stud_name.append("          " + grade + "%");
                                holder.progressBar.getProgressDrawable().setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN);
                            }
                            holder.progressBar.setProgress(grade);
                        }

                    }}
                else{
                    grade = 0;
                    holder.stud_name.append("          " + grade + "%");
                    holder.progressBar.getProgressDrawable().setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN);
                }

                holder.progressBar.setProgress(grade);
//                Log.d("gradee",sum+"   222 ");


                TextView avg = context.getActivity().findViewById(R.id.average);
                avg.setText(sum/arrayList.size() + " %");
                Log.d("average",""+sum +"  mmm "+ sum/arrayList.size());

//


            }

            @Override
            public void onFailure(Call<GradeModel> call, Throwable t) {
                Toast.makeText(context.getActivity(), "error", Toast.LENGTH_SHORT).show();
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
        public final TextView stud_name;
        public final ProgressBar progressBar;
        public final ImageView image;
        UserHistoryItem mItem;
        int quiz_id;
        int user_id;


        public ViewHolder(View view) {

            super(view);

            mView = view;
            stud_name = view.findViewById(R.id.student_name);
            progressBar = view.findViewById(R.id.progressbar);
            image = view.findViewById(R.id.image_view);
        }

    }

}
