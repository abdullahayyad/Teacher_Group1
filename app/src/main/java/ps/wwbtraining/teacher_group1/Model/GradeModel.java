package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

/**
 * Created by Hanan Dawod on 15/11/17.
 */
@Generated("com.robohorse.robopojogenerator")

public class GradeModel {
    @SerializedName("result")
    private boolean result;

    @SerializedName("User")
    private ArrayList<GradeItem> user;



    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setUser(ArrayList<GradeItem> user) {
        this.user = user;
    }

    public ArrayList<GradeItem> getUser() {
        return user;
    }

    @Override
    public String toString() {
        return
                "QuizModel{" +
                        "result = '" + result + '\'' +
                        ",user = '" + user + '\'' +
                        "}";
    }
    public class GradeItem{
        @SerializedName("grade")
        private int gradee;

        public int getGradee() {
            return gradee;
        }

        public void setGradee(int gradee) {
            this.gradee = gradee;
        }
        @Override
        public String toString() {
            return
                    "{" +
                            "gradee = '" + gradee + '\'' +

                            "}";}
    }
}
