package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class GroupInsert{

    @SerializedName("result")
    private boolean result;

    @SerializedName("id")
    private int id;

    public void setResult(boolean result){
        this.result = result;
    }

    public boolean isResult(){
        return result;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return
                "Response{" +
                        "result = '" + result + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}