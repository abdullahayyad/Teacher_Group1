package ps.wwbtraining.teacher_group1;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * Created by Hanan Dawod on 18/10/17.
 */

public interface TeacherApi {
    @GET("check.php")
    @FormUrlEncoded

    Call<User> checkTeacher(@Field("user_password") String user_password,
                            @Field("user_email") String user_email);
//@POST("Quiz.php")
//    Call InsertQuiz(@Field()
//                    @Field());


}
