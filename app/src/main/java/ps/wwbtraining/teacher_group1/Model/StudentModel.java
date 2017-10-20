package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class StudentModel{

	@SerializedName("result")
	private boolean result;

	@SerializedName("User")
	private ArrayList<User> user;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setUser(ArrayList<User> user){
		this.user = user;
	}

	public ArrayList<User> getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"StudentModel{" + 
			"result = '" + result + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}