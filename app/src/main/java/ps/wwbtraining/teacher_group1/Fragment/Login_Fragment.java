package ps.wwbtraining.teacher_group1.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ps.wwbtraining.teacher_group1.Activity.TeacherActivity;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Class.CustomToast;
import ps.wwbtraining.teacher_group1.Class.Utils;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.Users;
import ps.wwbtraining.teacher_group1.R;
import ps.wwbtraining.teacher_group1.WebService.SharedPrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.EMAIL_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.MOBIL_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.NAME_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.RESULT_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.STATUS_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.USERID_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

public class Login_Fragment extends Fragment implements View.OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    TeacherApi teacherApi;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        teacherApi = ApiTeacher.getAPIService();

        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);
            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {

        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {
                        if (isChecked) {
                            show_hide_password.setText(R.string.hide_pwd);// change
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:

                String getEmailId = emailid.getText().toString().trim();
                String getPassword = password.getText().toString().trim();
                if (checkValidation() && isOnline(getActivity())) {

                    checkLogin(getEmailId, getPassword);
                } else {
                    loginLayout.startAnimation(shakeAnimation);
                    new CustomToast().Show_Toast(getActivity(), view,
                            "No Internet Connection");
                }
                break;
            case R.id.forgot_password:
                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                Utils.ForgotPassword_Fragment).commit();
                break;
        }
    }

    // Check Validation before login
    private boolean checkValidation() {
        boolean valid = true;
        // Get email id and password
        String getEmailId = emailid.getText().toString().trim();
        String getPassword = password.getText().toString().trim();

        // Check for both field is empty or not
        if (getEmailId.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {
            emailid.setError("Enter a valid email address");
            loginLayout.startAnimation(shakeAnimation);
            valid = false;
        } else {
            emailid.setError(null);
        }
        if (getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            password.setError("Enter your Password");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }

    private void checkLogin(final String user_email, final String user_password) {
        if(isOnline(getActivity())){
            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setMessage("Login....");
            pd.setCancelable(false);
            pd.show();

        try {
            teacherApi.checkLogin(user_email, user_password).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Users users = response.body();
                    if (users.getStatuse().equals("1")) {

                        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getActivity());
                        sharedPrefUtil.saveString(NAME_SHARED_PREF, users.getUser().getUserName());
                        sharedPrefUtil.saveString(STATUS_SHARED_PREF, users.getUser().getStatusId());
                        sharedPrefUtil.saveString(MOBIL_SHARED_PREF, users.getUser().getUserMobile());
                        sharedPrefUtil.saveBoolean(RESULT_SHARED_PREF, users.getUser().isResult());
                        sharedPrefUtil.saveString(EMAIL_SHARED_PREF, users.getUser().getUserEmail());
                        sharedPrefUtil.saveString(USERID_SHARED_PREF, users.getUser().getUserId());
                        Intent intent = new Intent(getActivity(), TeacherActivity.class);
                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Not Allow to login");
                        loginLayout.startAnimation(shakeAnimation);
                    }
//                Log.i("sucessfull", response.body() + "");
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    if (pd != null && pd.isShowing())
                        pd.dismiss();
                    new CustomToast().Show_Toast(getActivity(), view,
                            "No Internet Connection");
                    loginLayout.startAnimation(shakeAnimation);
                }
            });
        } catch (Exception e) {
            if (pd != null && pd.isShowing())
                pd.dismiss();
        }
        }else {
            new CustomToast().Show_Toast(getActivity(), view,
                    "No Internet Connection");
            loginLayout.startAnimation(shakeAnimation);
        }
    }
}