package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class QuesItem{

    @SerializedName("statement")
    private String statement;

    @SerializedName("question_id")
    private String questionId;

    public void setStatement(String statement){
        this.statement = statement;
    }

    public String getStatement(){
        return statement;
    }

    public void setQuestionId(String questionId){
        this.questionId = questionId;
    }

    public String getQuestionId(){
        return questionId;
    }

    @Override
    public String toString(){
        return
                "UserItem{" +
                        "statement = '" + statement + '\'' +
                        ",question_id = '" + questionId + '\'' +
                        "}";
    }
}