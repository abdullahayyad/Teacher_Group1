package ps.wwbtraining.teacher_group1.Class;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public static final String USERID_SHARED_PREF="user_id";



    public static final int NO_INTERNET_CONNECTION = 0;
    public static final int REQUEST_OK = 200 | 201;
    public static final int OTHER = 400 | 401 | 403 | 404;


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

    public static void customSnackBare(View view, String message/*,String action*/) {
        final Snackbar snackbar;
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static boolean hasActiveInternetConnection(Context context) {
        if (isOnline(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(15000);
                urlc.connect();
                switch (urlc.getResponseCode()) {
                    case NO_INTERNET_CONNECTION:
                        return false;
                    case REQUEST_OK:
                        return true;
                    case OTHER:
                        return true;
                }
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    public static boolean hasInternetAccess(Context context) {
        if (isOnline(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(15000);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e("///", "Error checking internet connection", e);
            }
        } else {
            Log.d("///", "No network available!");
        }
        return false;
    }
}

