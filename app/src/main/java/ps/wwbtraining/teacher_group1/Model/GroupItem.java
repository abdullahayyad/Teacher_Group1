
/**
 * Created by Hanan Dawod on 22/10/17.
 */

package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class GroupItem{

	@SerializedName("group_id")
	private int group_id;

	@SerializedName("group_name")
	private String group_name;

	@SerializedName("description")
	private String description;

	public GroupItem(int group_id, String group_name, String description) {
		this.group_id = group_id;
		this.group_name = group_name;
		this.description = description;
	}

	public void setGroup_id(int group_id){
		this.group_id = group_id;
	}

	public int getGroup_id(){
		return group_id;
	}

	public void setgroup_name(String group_name){
		this.group_name = group_name;
	}

	public String getgroup_name(){
		return group_name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	@Override
	public String toString(){
		return
				"UserItem{" +
						"group_id = '" + group_id + '\'' +
						",group_name = '" + group_name + '\'' +
						",description = '" + description + '\'' +
						"}";
	}}
