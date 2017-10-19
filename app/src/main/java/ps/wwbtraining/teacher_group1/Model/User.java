package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class User{

	@SerializedName("result")
	private boolean result;

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

	@SerializedName("token")
	private String token;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

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

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"result = '" + result + '\'' + 
			",user_email = '" + userEmail + '\'' + 
			",status_id = '" + statusId + '\'' + 
			",user_id = '" + userId + '\'' + 
			",user_mobile = '" + userMobile + '\'' + 
			",user_name = '" + userName + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}