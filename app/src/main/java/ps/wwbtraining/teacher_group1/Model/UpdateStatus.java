package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

/**
 * Created by مركز الخبراء on 10/25/2017.
 */

public class UpdateStatus {
    @Generated("com.robohorse.robopojogenerator")

        @SerializedName("result")
        private boolean result;


        public void setResult(boolean result){
            this.result = result;
        }

        public boolean isResult(){
            return result;
        }

        @Override
        public String toString(){
            return
                    "StudentModel{" +
                            "result = '" + result + '\'' + "}";
        }
    }