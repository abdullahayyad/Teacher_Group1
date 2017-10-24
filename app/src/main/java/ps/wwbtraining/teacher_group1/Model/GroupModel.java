package ps.wwbtraining.teacher_group1.Model;

/**
 * Created by Hanan Dawod on 22/10/17.
 */


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class GroupModel{

	@SerializedName("result")
	private boolean result;

	@SerializedName("User")
	private ArrayList<GroupItem> user;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setGroup(ArrayList<GroupItem> user){
		this.user = user;
	}

	public ArrayList<GroupItem> getGroup(){
		return user;
	}

	@Override
	public String toString(){
		return
				"GroupModel{" +
						"result = '" + result + '\'' +
						",user = '" + user + '\'' +
						"}";
	}
}