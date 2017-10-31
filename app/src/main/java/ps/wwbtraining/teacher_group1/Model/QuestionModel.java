package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by مركز الخبراء on 10/29/2017.
 */

public class QuestionModel {

    @SerializedName("result")
    private boolean result;

    @SerializedName("question")
    private ArrayList<QuestionItem> question;

    public void setResult(boolean result){
        this.result = result;
    }

    public boolean isResult(){
        return result;
    }

    public ArrayList<QuestionItem> getQuestion() {
        return question;
    }

    public void setQuestion(ArrayList<QuestionItem> question) {
        this.question = question;
    }
}
