package ps.wwbtraining.teacher_group1;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * Created by Hanan Dawod on 18/10/17.
 */

public interface TeacherApi {
/*
 /* User user = new User();
    Boolean b = false;
    public boolean check(String user_password,String user_email) {
        teacherApi.checkTeacher(user_password,user_email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    //user.getResult().equals("true");
                    Toast.makeText(getActivity(), "sucess +  "+user.getResult(), Toast.LENGTH_SHORT).show();
                    b=true;
                   // new MainActivity().replaceLoginFragment();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to submit post to API.", Toast.LENGTH_SHORT).show();

            }


        });
        return b;
}*/

    @GET("check.php")
    @FormUrlEncoded
    Call<User> checkTeacher(@Field("user_password") String user_password,
                            @Field("user_email") String user_email);


}
