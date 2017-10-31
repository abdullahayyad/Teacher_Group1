package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

/**
 * Created by مركز الخبراء on 10/29/2017.
 */
@Generated("com.robohorse.robopojogenerator")

public class QuestionItem {

    @SerializedName("result")
    private boolean result;

    @SerializedName("quiz_id")
    private int quizId;

    @SerializedName("question_type")
    private int questionType;

    @SerializedName("statement")
    private String statement;

    @SerializedName("correct_answer")
    private String correctAnswer;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}














