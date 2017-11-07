package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import javax.annotation.Generated;

/**
 * Created by مركز الخبراء on 11/05/2017.
 */
@Generated("com.robohorse.robopojogenerator")

public class InsertInToQuiz{

    @SerializedName("result")
    private boolean result;

    @SerializedName("goo")
    private int jsonObject;

    public void setResult(boolean result){
        this.result = result;
    }

    public boolean isResult(){
        return result;
    }

    public int getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(int jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String toString(){
        return
                "InsertIntoQuiz{" +
                        "result = '" + result + '\'' +
                        "}";
    }
}