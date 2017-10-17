package ps.wwbtraining.teacher_group1;

/**
 * Created by Hanan Dawod on 17/10/17.
 */

public class UserManageItem {
    String UserName;
    int status;

    public UserManageItem() {
    }

    public UserManageItem(String userName, int status) {
        UserName = userName;
        this.status = status;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}