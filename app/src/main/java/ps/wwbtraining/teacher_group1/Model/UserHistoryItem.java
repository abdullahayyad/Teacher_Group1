package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Hanan Dawod on 15/11/17.
 */
@Generated("com.robohorse.robopojogenerator")
public class UserHistoryItem {
    /*
     "user_name": "lubna",
            "user_id": "2",
            "group_id": "1",
            "quiz_id": "350"
     */
    @SerializedName("quiz_id")
    private int quiz_id;

    @SerializedName("group_id")
    private int group_id;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("user_id")
    private int user_id;

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserHistoryItem(int quiz_id, int group_id, String user_name, int user_id) {
        this.quiz_id = quiz_id;
        this.group_id = group_id;
        this.user_name = user_name;
        this.user_id =user_id;
    }
    @Override
    public String toString() {
        return
                "{" +
                        "quiz_id = '" + quiz_id + '\'' +
                        ",group_id = '" + group_id + '\'' +
                        ",userName = '" + user_name + '\'' +
                        ",userId = '" + user_id + '\'' +
                        "}";
    }

   }
