package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
/**
 * Created by مركز الخبراء on 10/29/2017.
 */
@Generated("com.robohorse.robopojogenerator")

public class ItemAnswerAdd {


    @SerializedName("result")
    private boolean result;

    @SerializedName("ans1")
    private String ans1;

    @SerializedName("ans2")
    private String ans2;

    @SerializedName("ans3")
    private String ans3;

    @SerializedName("ans4")
    private String ans4;

    @SerializedName("question_id")
    private int questionId;


    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
