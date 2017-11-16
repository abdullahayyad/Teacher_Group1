package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

/**
 * Created by Hanan Dawod on 15/11/17.
 */
@Generated("com.robohorse.robopojogenerator")

public class UserHistoryModel {


        @SerializedName("result")
        private boolean result;

        @SerializedName("User")
        private ArrayList<UserHistoryItem> user;



        public void setResult(boolean result) {
            this.result = result;
        }

        public boolean isResult() {
            return result;
        }

        public void setGroup(ArrayList<UserHistoryItem> user) {
            this.user = user;
        }

        public ArrayList<UserHistoryItem> getUser() {
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
    }
