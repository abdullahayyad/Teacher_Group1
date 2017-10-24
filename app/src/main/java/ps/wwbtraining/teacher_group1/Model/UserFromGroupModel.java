package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class UserFromGroupModel{
	@SerializedName("User")
	private ArrayList<UserItem> user;

	public void setUser(ArrayList<UserItem> user){
		this.user = user;

	}

	public ArrayList<UserItem> getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"UserFromGroupModel{" + 
			"user = '" + user + '\'' + 
			"}";
		}
}