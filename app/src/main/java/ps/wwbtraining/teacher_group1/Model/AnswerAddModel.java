package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import javax.annotation.Generated;

/**
 * Created by مركز الخبراء on 10/29/2017.
 */
@Generated("com.robohorse.robopojogenerator")

public class AnswerAddModel {


        @SerializedName("result")
        private boolean result;

        @SerializedName("answer")
        private ArrayList<ItemAnswerAdd> answer;

        public void setResult(boolean result){
            this.result = result;
        }

        public boolean isResult(){
            return result;
        }

        public void setAnswer(ArrayList<ItemAnswerAdd> answer){
            this.answer = answer;
        }

        public ArrayList<ItemAnswerAdd> getAnswer(){
            return answer;
        }

}
