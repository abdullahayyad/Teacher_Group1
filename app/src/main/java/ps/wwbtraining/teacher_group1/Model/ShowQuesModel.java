package ps.wwbtraining.teacher_group1.Model;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ShowQuesModel{

	@SerializedName("result")
	private boolean result;

	@SerializedName("quizStudent")
	private ArrayList<QuesItem> user;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setUser(ArrayList<QuesItem> user){
		this.user = user;
	}

	public ArrayList<QuesItem> getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"ShowQuesModel{" + 
			"result = '" + result + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}