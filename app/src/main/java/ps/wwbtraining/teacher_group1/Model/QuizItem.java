
/**
 * Created by Hanan Dawod on 22/10/17.
 */

package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class QuizItem{

    @SerializedName("quiz_id")
    private int quiz_id;

    @SerializedName("flag")
    private int flag;

    @SerializedName("quiz_name")
    private String quiz_name;

    @SerializedName("discription")
    private String discription;

    @SerializedName("deadline")
    private String deadline;

    public QuizItem(int quiz_id, String quiz_name, String discription,String deadline) {
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.discription = discription;
        this.deadline =deadline;
    }

    public void setQuiz_id(int quiz_id){
        this.quiz_id = quiz_id;
    }

    public int getQuiz_id(){
        return quiz_id;
    }

    public void setQuiz_name(String quiz_name){
        this.quiz_name = quiz_name;
    }

    public String getQuiz_name(){
        return quiz_name;
    }

    public void setDescription(String discription){
        this.discription = discription;
    }

    public String getDescription(){
        return discription;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString(){
        return
                "UserItem{" +
                        "quiz_id = '" + quiz_id + '\'' +
                        ",quiz_name = '" + quiz_name + '\'' +
                        ",description = '" + discription + '\'' +
                        "}";
    }}
