package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ShowAnswerModel{

	@SerializedName("result")
	private boolean result;

	@SerializedName("User")
	private ArrayList<Answer> user;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setUser(ArrayList<Answer> user){
		this.user = user;
	}

	public ArrayList<Answer> getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"ShowAnswerModel{" + 
			"result = '" + result + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}