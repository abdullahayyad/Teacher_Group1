package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Answer{

    @SerializedName("ans3")
    private String ans3;

    @SerializedName("ans2")
    private String ans2;

    @SerializedName("ans4")
    private String ans4;

    @SerializedName("ans1")
    private String ans1;

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

    public void setAns4(String ans4){
        this.ans4 = ans4;
    }

    public String getAns4(){
        return ans4;
    }

    public void setAns1(String ans1){
        this.ans1 = ans1;
    }

    public String getAns1(){
        return ans1;
    }

    @Override
    public String toString(){
        return
                "UserItem{" +
                        "ans3 = '" + ans3 + '\'' +
                        ",ans2 = '" + ans2 + '\'' +
                        ",ans4 = '" + ans4 + '\'' +
                        ",ans1 = '" + ans1 + '\'' +
                        "}";
    }
}