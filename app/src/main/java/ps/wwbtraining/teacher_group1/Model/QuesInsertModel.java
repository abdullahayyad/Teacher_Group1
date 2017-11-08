package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hanan Dawod on 02/11/17.
 */

public class QuesInsertModel {
    @SerializedName("result")
    private boolean result;

    @SerializedName("id")
    private int question_id;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setResult(boolean result){
        this.result = result;
    }

    public boolean isResult(){
        return result;
    }


    @Override
    public String toString(){
        return
                "QuizModel{" +
                        "result = '" + result + '\'' +
                        ",question_id = '" + question_id + '\'' +
                        "}";
    }
}




