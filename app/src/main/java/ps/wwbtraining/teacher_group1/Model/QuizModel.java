package ps.wwbtraining.teacher_group1.Model;

/**
 * Created by Hanan Dawod on 22/10/17.
 */


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class QuizModel{

    @SerializedName("result")
    private boolean result;

    @SerializedName("User")
    private ArrayList<QuizItem> user;

    public void setResult(boolean result){
        this.result = result;
    }

    public boolean isResult(){
        return result;
    }

    public void setGroup(ArrayList<QuizItem> user){
        this.user = user;
    }

    public ArrayList<QuizItem> getGroup(){
        return user;
    }

    @Override
    public String toString(){
        return
                "QuizModel{" +
                        "result = '" + result + '\'' +
                        ",user = '" + user + '\'' +
                        "}";
    }
}
/*
{
    "result": true,
    "User": [
        {
            "user_name": "lubna",
            "user_id": "2",
            "group_id": "1",
            "quiz_id": "350"
        }
 */