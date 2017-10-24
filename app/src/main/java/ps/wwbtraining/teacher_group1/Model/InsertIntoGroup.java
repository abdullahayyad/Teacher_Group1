package ps.wwbtraining.teacher_group1.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class InsertIntoGroup{

	@SerializedName("result")
	private boolean result;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"InsertIntoGroup{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}