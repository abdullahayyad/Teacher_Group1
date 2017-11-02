package ps.wwbtraining.teacher_group1.Class;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by osama on 17/10/2017.
 */

public class Utils {
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String EMAIL_SHARED_PREF = "user_email";
    public static final String STATUS_SHARED_PREF = "user_status";
    public static final String NAME_SHARED_PREF = "user_name";
    public static final String MOBIL_SHARED_PREF = "user_mobile";
    public static final String RESULT_SHARED_PREF = "user_result";

    public static void NoInternetAlert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Something went wrong");
        builder.setMessage("No Internet Connection");
        builder.setCancelable(true);
        final AlertDialog closedialog = builder.create();
        closedialog.show();
        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {

                closedialog.dismiss();
                timer2.cancel(); //this will cancel the timer of the system
            }
        }, 3000);

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public static void customSnackBare(View view,String message/*,String action*/){
        final Snackbar snackbar;
        snackbar= Snackbar.make(view,message,Snackbar.LENGTH_LONG);
//        snackbar.setAction(action, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                snackbar.dismiss();
//            }
//        }).setActionTextColor(Color.WHITE);
        snackbar.show();
   }


}

