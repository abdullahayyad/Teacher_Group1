package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class UserItem{

	@SerializedName("result")
	private boolean result;

	@SerializedName("user_email")
	private String userEmail;

	@SerializedName("user_password")
	private String userPassword;

	@SerializedName("status_id")
	private String statusId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_mobile")
	private String userMobile;

	@SerializedName("group_id")
	private String groupId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("token")
	private Object token;

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

	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}

	public String getUserPassword(){
		return userPassword;
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

	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	public String getGroupId(){
		return groupId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setToken(Object token){
		this.token = token;
	}

	public Object getToken(){
		return token;
	}

	@Override
	public String toString(){
		return
				"UserItem{" +
						"result = '" + result + '\'' +
						",user_email = '" + userEmail + '\'' +
						",user_password = '" + userPassword + '\'' +
						",status_id = '" + statusId + '\'' +
						",user_id = '" + userId + '\'' +
						",user_mobile = '" + userMobile + '\'' +
						",group_id = '" + groupId + '\'' +
						",user_name = '" + userName + '\'' +
						",token = '" + token + '\'' +
						"}";
	}
}