package ps.wwbtraining.teacher_group1.Interface;


import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Model.CountStudentModel;
import ps.wwbtraining.teacher_group1.Model.GroupInsert;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
//import ps.wwbtraining.teacher_group1.Model.QuizModel;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.Model.UserFromGroupModel;
import ps.wwbtraining.teacher_group1.Model.Users;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TeacherApi {

    @POST("getStudant.php")
    @FormUrlEncoded
    Call<StudentModel> getStudName(@Field("status_id") int status_id);

    @POST("checkLogin.php")
    @FormUrlEncoded
    Call<Users> checkLogin(@Field("user_email") String user_email,
                           @Field("user_password") String user_password);


    @GET("showGroup.php")
    Call<GroupModel> showGroup();

    @POST("addGroup.php")
    @FormUrlEncoded
    Call<GroupInsert> addGroup(@Field("group_name") String group_name,
                               @Field("description") String description);

    //    @FormUrlEncoded
    @POST("addUserGroup.php")
    Call<InsertIntoGroup> addArrayUserGroup(@Body RequestBody requestBody);

    @POST("UserFromGroup.php")
    @FormUrlEncoded
    Call<UserFromGroupModel> userFromGroup(@Field("group_id") int group_id);

    @POST("UpdateGroup.php")
    @FormUrlEncoded
    Call<InsertIntoGroup> updateGroup(@Field("group_id") int group_id,
                                      @Field("group_name") String group_name,
                                      @Field("description") String description
    );

    @POST("insertUserIntoGroup.php")
    @FormUrlEncoded
    Call<InsertIntoGroup> insertUserIntoGroup(@Field("group_id") int group_id,
                                              @Field("user_id") int user_id);

    @POST("deleteUserFromGroup.php")
    @FormUrlEncoded
    Call<InsertIntoGroup> deleteUserFromGroup(@Field("group_id") int group_id,
                                              @Field("user_id") int user_id);

    @POST("updateStatus.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateStatus(@Field("user_id") String user_id,
                                    @Field("status_id") String status_id);

//    @GET("showQuiz.php")
//    Call<QuizModel> showQuiz();

    @POST("countStudent.php")
    @FormUrlEncoded
    Call<CountStudentModel> getCount(@Field("group_id") int group_id);
}
