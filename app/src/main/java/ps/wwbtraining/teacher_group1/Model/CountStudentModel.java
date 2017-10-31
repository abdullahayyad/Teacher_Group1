package ps.wwbtraining.teacher_group1.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CountStudentModel{

	@SerializedName("countStudent")
	private String countStudent;

	public void setCountStudent(String countStudent){
		this.countStudent = countStudent;
	}

	public String getCountStudent(){
		return countStudent;
	}

	@Override
 	public String toString(){
		return 
			"CountStudentModel{" + 
			"countStudent = '" + countStudent + '\'' + 
			"}";
		}
}