package ps.wwbtraining.teacher_group1.Interface;


import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group1.Model.AnswerAddModel;
import ps.wwbtraining.teacher_group1.Model.CountStudentModel;
import ps.wwbtraining.teacher_group1.Model.GradeModel;
import ps.wwbtraining.teacher_group1.Model.GroupModel;
import ps.wwbtraining.teacher_group1.Model.InsertInToQuiz;
import ps.wwbtraining.teacher_group1.Model.InsertIntoGroup;
import ps.wwbtraining.teacher_group1.Model.QuesInsertModel;
import ps.wwbtraining.teacher_group1.Model.QuizModel;
import ps.wwbtraining.teacher_group1.Model.ShowAnswerModel;
import ps.wwbtraining.teacher_group1.Model.ShowQuesModel;
import ps.wwbtraining.teacher_group1.Model.StudentModel;
import ps.wwbtraining.teacher_group1.Model.UpdateStatus;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.Model.UserFromGroupModel;
import ps.wwbtraining.teacher_group1.Model.UserHistoryModel;
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

    @GET("showGroupHistory.php")
    Call<GroupModel> showGroupHistory();

    @POST("showUserGradeHistory.php")
    @FormUrlEncoded
    Call<UserHistoryModel> showUserHistory(@Field("quiz_id") int quiz_id,
                                           @Field("group_id") int group_id);


    @POST("showQuizHistory.php")
    @FormUrlEncoded
    Call<QuizModel> showQuizHistory(@Field("group_id") int group_id);

    @POST("addUserGroup.php")
    Call<InsertIntoGroup> addArrayUserGroup(@Body RequestBody requestBody);


    @POST("UpdateGroupFromUsers.php")
    Call<InsertInToQuiz> UpdateGroupFromUser(@Body RequestBody requestBody);

//    @POST("sendQuiz.php")
//    Call<InsertIntoGroup> sendQuiz(@Body RequestBody requestBody);

    @POST("insert_quiz.php")
    Call <InsertInToQuiz>addArrayQuiz(@Body RequestBody requestBody);

    @POST("manageUser.php")
    Call <InsertInToQuiz> updateStatusUser(@Body RequestBody requestBody);


    @POST("UserFromGroup.php")
    @FormUrlEncoded
    Call<UserFromGroupModel> userFromGroup(@Field("group_id") int group_id);



    @POST("UpdateInfoQuiz.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateQuiz(@Field("quiz_id") int id,
            @Field("quiz_name") String name,
                                    @Field("discription") String description,
                                    @Field("deadline")String deadline
    );
    @POST("recreat.php")
    @FormUrlEncoded
    Call <UpdateStatus> recreat(@Field("quiz_id") int quiz_id);

    @POST("UpdateGroup.php")
    @FormUrlEncoded
    Call<InsertIntoGroup> updateGroup(@Field("group_id") int group_id,
                                      @Field("group_name") String group_name,
                                      @Field("description") String description);


    @GET("showQuiz.php")
    Call<QuizModel> showQuiz();


    @POST("insertQuiz.php")
    @FormUrlEncoded
    Call<QuizModel> addQuiz(@Field("quiz_name") String nameQuiz,
                            @Field("discription") String descriptionQuiz);

    @POST("addQuestion.php")
    @FormUrlEncoded
    Call<QuesInsertModel> addQuestion(@Field("statement") String statementQuestion,
                                    @Field("quiz_id") int idQuiz,
                                    @Field("question_type") int questionType,
                                    @Field("correct_answer") String correctAnswer);

    @POST("addAnswer.php")
    @FormUrlEncoded
    Call<AnswerAddModel> addAnswer  (@Field("ans1") String ans1,
                                     @Field("ans2") String ans2,
                                     @Field("ans3") String ans3,
                                     @Field("ans4") String ans4,
                                     @Field("question_id")int questionId
                                );

    @POST("insertUserIntoGroup.php")
    @FormUrlEncoded
    Call<InsertIntoGroup> insertUserIntoGroup(@Field("group_id") int group_id,
                                              @Field("user_id") int user_id);

    @POST("deleteUserFromGroup.php")
    @FormUrlEncoded
    Call<InsertIntoGroup> deleteUserFromGroup(@Field("group_id") int group_id,
                                              @Field("user_id") int user_id);


    @POST("countStudent.php")
    @FormUrlEncoded
    Call<CountStudentModel> getCount(@Field("group_id") int group_id);

    @POST("countQuiz.php")
    @FormUrlEncoded
    Call<CountStudentModel> getCountQuiz(@Field("group_id") int group_id);

    @POST("showQues.php")
    @FormUrlEncoded
    Call<ShowQuesModel> showQues(@Field("quiz_id") int quiz_id);

    @POST("showQuestion.php")
    @FormUrlEncoded
    Call<ShowQuesModel> showQuestion(@Field("quiz_id") int quiz_id);

    @POST("showAnswer.php")
    @FormUrlEncoded
    Call<ShowAnswerModel> showAnswer(@Field("question_id") int question_id);

    @POST("showCorrectAns.php")
    @FormUrlEncoded
    Call<ShowQuesModel> showCorrectAns(@Field("question_id") int question_id);

    @POST("updateStatement.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateStatement(@Field("question_id") int question_id,
                                       @Field("statement") String statement);


    @POST("updateCorrectAns.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateCorrectAns(@Field("question_id") int question_id,
                                        @Field("correct_answer") String statement);

    @POST("updateFlagQuiz.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateFlagQuiz(@Field("quiz_id") int quiz_id,
                                      @Field("flag") int flag);

    @POST("updateFlagGroup.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateFlagGroup(@Field("group_id") int group_id,
                                       @Field("flag") int flag);

    @POST("updateFlagQues.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateFlagQues(@Field("question_id") int question_id,
                                      @Field("flag") int flag);

    @POST("updateAns.php")
    @FormUrlEncoded
    Call<UpdateStatus> updateAns(@Field("question_id") int question_id,
                                  @Field("ans1") String ans1,
                                  @Field("ans2") String ans2,
                                  @Field("ans3") String ans3,
                                  @Field("ans4") String ans4);


    @POST("insertQues.php")
    @FormUrlEncoded
    Call<QuesInsertModel> insertQues(@Field("question_type") int question_type,
                                     @Field("statement") String statement,
                                     @Field("quiz_id") int quiz_id,
                                     @Field("correct_answer") String correct_answer
    );
    @POST("insertAns.php")
    @FormUrlEncoded


    Call<UpdateStatus> insertAns(@Field("question_id") int question_id,
                                     @Field("ans1") String ans1,
                                     @Field("ans2") String ans2,
                                     @Field("ans3") String ans3,
                                    @Field("ans4") String ans4
    );


    @POST("updateTeacherProfile.php")
    @FormUrlEncoded
    Call<User> updateProfile(@Field("user_id") String user_id,
                             @Field("user_name") String user_name,
                             @Field("user_email") String user_email,
                             @Field("user_mobile") String user_mobile,
                             @Field("old_password") String old_password,
                             @Field("new_password") String new_password);

    @POST("showQuizGroup.php")
    @FormUrlEncoded
    Call<GroupModel> showQuizGroup(@Field("quiz_id") int quiz_id);

    @POST("apiNotification.php")
    Call<InsertIntoGroup> sendQuizToGroup(@Body RequestBody requestBody);


    @POST("checkQuiz.php")
    @FormUrlEncoded
    Call<UpdateStatus> checkQuiz(@Field("quiz_id") int quiz_id);

    @POST("gradeStudent.php")
    @FormUrlEncoded
    Call<GradeModel> gradeStudent(@Field("user_id") int user_id,
                                  @Field("quiz_id") int quiz_id);


}
