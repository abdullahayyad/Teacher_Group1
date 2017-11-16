package ps.wwbtraining.teacher_group1.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class QuestionItem implements Parcelable {

    @SerializedName("ans3")
    private String ans3;

    @SerializedName("ans2")
    private String ans2;

    @SerializedName("quiz_id")
    private String quizId;

    @SerializedName("ans4")
    private String ans4;

    @SerializedName("correct")
    private String correct;

    @SerializedName("question_type")
    private String questionType;

    @SerializedName("ans1")
    private String ans1;

    @SerializedName("statement")
    private String statement;

    @SerializedName("question_id")
    private String questionId;

    protected QuestionItem(Parcel in) {
        ans3 = in.readString();
        ans2 = in.readString();
        quizId = in.readString();
        ans4 = in.readString();
        correct = in.readString();
        questionType = in.readString();
        ans1 = in.readString();
        statement = in.readString();
        questionId = in.readString();
    }

    public static final Creator<QuestionItem> CREATOR = new Creator<QuestionItem>() {
        @Override
        public QuestionItem createFromParcel(Parcel in) {
            return new QuestionItem(in);
        }

        @Override
        public QuestionItem[] newArray(int size) {
            return new QuestionItem[size];
        }
    };

    public void setAns3(String ans3){
        this.ans3 = ans3;
    }

    public String getAns3(){
        return ans3;
    }

    public void setAns2(String ans2){
        this.ans2 = ans2;
    }

    public String getAns2(){
        return ans2;
    }

    public void setQuizId(String quizId){
        this.quizId = quizId;
    }

    public String getQuizId(){
        return quizId;
    }

    public void setAns4(String ans4){
        this.ans4 = ans4;
    }

    public String getAns4(){
        return ans4;
    }

    public void setCorrect(String correct){
        this.correct = correct;
    }

    public String getCorrect(){
        return correct;
    }

    public void setQuestionType(String questionType){
        this.questionType = questionType;
    }

    public String getQuestionType(){
        return questionType;
    }

    public void setAns1(String ans1){
        this.ans1 = ans1;
    }

    public String getAns1(){
        return ans1;
    }

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
                "QuestionItem{" +
                        "ans3 = '" + ans3 + '\'' +
                        ",ans2 = '" + ans2 + '\'' +
                        ",quiz_id = '" + quizId + '\'' +
                        ",ans4 = '" + ans4 + '\'' +
                        ",correct = '" + correct + '\'' +
                        ",question_type = '" + questionType + '\'' +
                        ",ans1 = '" + ans1 + '\'' +
                        ",statement = '" + statement + '\'' +
                        ",question_id = '" + questionId + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ans3);
        parcel.writeString(ans2);
        parcel.writeString(quizId);
        parcel.writeString(ans4);
        parcel.writeString(correct);
        parcel.writeString(questionType);
        parcel.writeString(ans1);
        parcel.writeString(statement);
        parcel.writeString(questionId);
    }
}

