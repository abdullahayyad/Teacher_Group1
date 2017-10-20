package ps.wwbtraining.teacher_group1.WebService;


import ps.wwbtraining.teacher_group1.Model.StudentModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Hanan Dawod on 18/10/17.
 */

public interface TeacherApi {

    @POST("getStudant.php")
    @FormUrlEncoded
    Call<StudentModel> getStudName(@Field("status_id") int status_id);




}
