package ps.wwbtraining.teacher_group1;

import ps.wwbtraining.teacher_group1.WebService.RetrofitClient;
import ps.wwbtraining.teacher_group1.WebService.TeacherApi;

/**
 * Created by Hanan Dawod on 18/10/17.
 */

public class ApiTeacher {

 private ApiTeacher() {}

        public static final String BASE_URL = "http://group1.wwbtraining.website/group1/api/";

        public static TeacherApi getAPIService() {

            return RetrofitClient.getClient(BASE_URL).create(TeacherApi.class);
        }}
