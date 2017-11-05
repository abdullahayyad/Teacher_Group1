package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class QuesItem{

    @SerializedName("statement")
    private String statement;

    @SerializedName("question_id")
    private int questionId;

    public int getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(int question_type) {
        this.question_type = question_type;
    }

    @SerializedName("question_type")
    private int question_type;


    @SerializedName("quiz_id")
    private int quiz_id;

    @SerializedName("correct_answer")
    private String correctAnswer;

    @SerializedName("flag")
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }




    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setStatement(String statement){
        this.statement = statement;
    }

    public String getStatement(){
        return statement;
    }


    public void setQuestionId(int questionId){
        this.questionId = questionId;
    }

    public int getQuestionId(){
        return questionId;
    }


    @Override
    public String toString(){
        return
                "UserItem{" +
                        "statement = '" + statement + '\'' +
                        ",question_id = '" + questionId + '\'' +
                        ",question_type = '" + question_type + '\'' +

                        ",correct = '" + correctAnswer + '\'' +
                        "}";
    }
}