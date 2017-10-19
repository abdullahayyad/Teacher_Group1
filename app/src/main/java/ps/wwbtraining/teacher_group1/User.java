package ps.wwbtraining.teacher_group1;

/**
 * Created by Hanan Dawod on 18/10/17.
 */


import com.google.gson.annotations.SerializedName;


public class User{

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @SerializedName("result")
    private String result;

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("status_id")
    private String statusId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_mobile")
    private String userMobile;

    @SerializedName("user_name")
    private String userName;

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setStatusId(String statusId){
        this.statusId = statusId;
    }

    public String getStatusId(){
        return statusId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserMobile(String userMobile){
        this.userMobile = userMobile;
    }

    public String getUserMobile(){
        return userMobile;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

}
