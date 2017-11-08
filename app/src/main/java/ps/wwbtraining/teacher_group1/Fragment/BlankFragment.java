package ps.wwbtraining.teacher_group1.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import ps.wwbtraining.teacher_group1.Class.ApiTeacher;
import ps.wwbtraining.teacher_group1.Interface.TeacherApi;
import ps.wwbtraining.teacher_group1.Model.User;
import ps.wwbtraining.teacher_group1.R;
import ps.wwbtraining.teacher_group1.WebService.SharedPrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group1.Class.Utils.EMAIL_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.MOBIL_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.NAME_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.USERID_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.isOnline;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    SharedPrefUtil sharedPrefUtil;
    private EditText name;
    private EditText email;
    private EditText mobile;
    private TextView pluse;
    private static final int GALLERY_PICK = 1;
    private ProgressDialog mProgressDialog;
    private CircleImageView mDisplayImage;
    private static String IMAGE_DATA = "image_data";
    private ImageButton edit;
    private EditText oldpass;
    private EditText newpass;
    private EditText conf_pass;
    private Button savedata;
    private LinearLayout change_pass;
    private TeacherApi teacherApi;
    private String idSaredPref;
    private String txtname;
    private String txtmobile;
    private String txtemail;
    private String txtoldpass;
    private String txtnewpass;
    private String txtconf_pass;
    private Button canceldata;
    private Bitmap bitmap;
    private boolean check = true;
    private String ServerUploadPath = "http://group1.wwbtraining.website/group1/api/img.php";
    private ProgressDialog progressDialog;
    private String IMAGE_NAME = "image_name";
    private String IMAGE_PATH = "image_path";
    private View viewHedar;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        sharedPrefUtil = new SharedPrefUtil(getActivity());
        teacherApi = ApiTeacher.getAPIService();

        // Inflate the layout for this fragment
        final String nameSaredPref = sharedPrefUtil.getString(NAME_SHARED_PREF);
        final String mobileSaredPref = sharedPrefUtil.getString(MOBIL_SHARED_PREF);
        final String emailSaredPref = sharedPrefUtil.getString(EMAIL_SHARED_PREF);
        name = (EditText) view.findViewById(R.id.name);
        mobile = (EditText) view.findViewById(R.id.mobile);
        email = (EditText) view.findViewById(R.id.email);
        edit = (ImageButton) view.findViewById(R.id.edit);
        mDisplayImage = (CircleImageView) view.findViewById(R.id.circleView);

        change_pass = (LinearLayout) view.findViewById(R.id.change_pass);
        oldpass = (EditText) view.findViewById(R.id.oldpass);
        newpass = (EditText) view.findViewById(R.id.newpass);
        conf_pass = (EditText) view.findViewById(R.id.conf_pass);
        savedata = (Button) view.findViewById(R.id.savedata);
        canceldata = (Button) view.findViewById(R.id.canceldata);

        name.setText(nameSaredPref);
        mobile.setText(mobileSaredPref);
        email.setText(emailSaredPref);
        viewHedar = LayoutInflater.from(getContext())
                .inflate(R.layout.nav_header_teacher,null, false);

        String img = sharedPrefUtil.getString(IMAGE_DATA);
        if (img != null ||! (img.isEmpty())) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Uri.parse(img));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mDisplayImage.setImageBitmap(bitmap);
           // ((CircleImageView)viewHedar.findViewById(R.id.circleView)).setImageBitmap(bitmap);

        }
        else {
            mDisplayImage.setImageResource(R.drawable.blank_profile_picture);
        }
        pluse = (TextView) view.findViewById(R.id.pluse);

//        putImage();
        pluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), 1);


            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                mobile.setEnabled(true);
                email.setEnabled(true);
                change_pass.setVisibility(View.VISIBLE);
            }
        });
        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idSaredPref = sharedPrefUtil.getString(USERID_SHARED_PREF);
                txtname = name.getText().toString();
                txtmobile = mobile.getText().toString();
                txtemail = email.getText().toString();

                txtoldpass = oldpass.getText().toString();
                txtnewpass = newpass.getText().toString();
                txtconf_pass = conf_pass.getText().toString();
                if (isOnline(getActivity())) {
                    if (checkValidation()) {
                        editProfile();
                    } else {
                        Toast.makeText(getActivity(), "Not Available to Edit", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
        canceldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name.setEnabled(false);
                mobile.setEnabled(false);
                email.setEnabled(false);
                name.setText(nameSaredPref);
                mobile.setText(mobileSaredPref);
                email.setText(emailSaredPref);
                change_pass.setVisibility(View.GONE);

            }
        });
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    DrawerLayout navigationView = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    if (navigationView.isDrawerOpen(GravityCompat.START))
                        navigationView.closeDrawers();
                    else
                        getFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                .replace(R.id.frameTeacher, new Teacher_Fragment()).commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                sharedPrefUtil.saveString(IMAGE_DATA, uri.toString());
                mDisplayImage.setImageBitmap(bitmap);
                ((CircleImageView)viewHedar.findViewById(R.id.circleView)).setImageBitmap(bitmap);
//                ImageUploadToServerFunction();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

//        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
//
//            Uri imageUri = data.getData();
//
//            CropImage.activity(imageUri)
//                    .setAspectRatio(1, 1)
//                    .setMinCropWindowSize(500, 500)
//                    .start(getActivity());
//            //Toast.makeText(SettingsActivity.this, imageUri, Toast.LENGTH_LONG).show();
//        }
//
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            Log.d("///result", result + "");
//            Log.d("///resultCode", resultCode + "");
//
//            if (resultCode == RESULT_OK) {
//
//                Log.d("resultCode", resultCode + "");
//                mProgressDialog = new ProgressDialog(getActivity());
//                mProgressDialog.setTitle("Uploading Image...");
//                mProgressDialog.setMessage("Please wait while we upload and process the image.");
//                mProgressDialog.setCanceledOnTouchOutside(false);
//                mProgressDialog.show();
//
//                Uri resultUri = result.getUri();
//                File thumb_filePath = new File(resultUri.getPath());
////                String current_user_id = mCurrentUser.getUid();
//
//
//                Bitmap thumb_bitmap = new Compressor(getActivity())
//                        .setMaxWidth(200)
//                        .setMaxHeight(200)
//                        .setQuality(75)
//                        .compressToBitmap(thumb_filePath);
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                final byte[] thumb_byte = baos.toByteArray();
//
//                String encodedImage = Base64.encodeToString(thumb_byte, Base64.DEFAULT);
//
//
////                textEncode.setText(encodedImage);
//                sharedPrefUtil.saveString(IMAGE_DATA, encodedImage);
//                putImage();
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//
//                Toast.makeText(getActivity(), "........Error...........", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    private void editProfile() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Saving Data ....");
        pd.setCancelable(false);
        pd.show();
        teacherApi.updateProfile(idSaredPref, txtname, txtemail, txtmobile, txtoldpass, txtnewpass).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(getActivity(), response.body() + "", Toast.LENGTH_SHORT).show();
                    if (response.body().isResult()) {
                        if (pd != null && pd.isShowing())
                            pd.dismiss();
//                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        name.setEnabled(false);
                        mobile.setEnabled(false);
                        email.setEnabled(false);
                        oldpass.setText("");
                        newpass.setText("");
                        conf_pass.setText("");

                        sharedPrefUtil.saveString(NAME_SHARED_PREF, txtname);
                        sharedPrefUtil.saveString(MOBIL_SHARED_PREF, txtmobile);
                        sharedPrefUtil.saveString(EMAIL_SHARED_PREF, txtemail);
                        change_pass.setVisibility(View.GONE);

                    } else {
                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(getContext(), "Not Available to Edit", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (pd != null && pd.isShowing())
                    pd.dismiss();
                Toast.makeText(getContext(), "Sorry..internet disconnect", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean checkValidation() {
        if ((!(txtname.isEmpty()) || txtname.length() != 0) && (!(txtmobile.isEmpty()) || txtmobile.length() != 0)
                && (!(txtemail.isEmpty()) || txtemail.length() != 0) ) {
            if ((txtnewpass.equals(txtconf_pass))) {
                return true;
            }
        }
        return false;
    }

//    public void ImageUploadToServerFunction() {
//
//        ByteArrayOutputStream byteArrayOutputStreamObject;
//        byteArrayOutputStreamObject = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
//        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
//        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
//
//        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
//
//            @Override
//            protected void onPreExecute() {
//
//                super.onPreExecute();
//                progressDialog = ProgressDialog.show(getActivity(), "Image is Uploading", "Please Wait", false, false);
//            }
//
//            @Override
//            protected void onPostExecute(String string1) {
//                super.onPostExecute(string1);
//                // Dismiss the progress dialog after done uploading.
//                progressDialog.dismiss();
//                // Printing uploading success message coming from server on android app.
//                Toast.makeText(getActivity(), "......", Toast.LENGTH_LONG).show();
//                // Setting image as transparent after done uploading.
//
//
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//
//                ImageProcessClass imageProcessClass = new ImageProcessClass();
//                HashMap<String, String> HashMapParams = new HashMap<String, String>();
//                HashMapParams.put(IMAGE_NAME, "User_" + sharedPrefUtil.getString(USERID_SHARED_PREF));
//                HashMapParams.put(IMAGE_PATH, ConvertImage);
//                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);
//                return FinalData;
//            }
//        }
//        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
//        AsyncTaskUploadClassOBJ.execute();
//    }

//    public class ImageProcessClass {
//
//        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {
//            StringBuilder stringBuilder = new StringBuilder();
//            try {
//
//                URL url;
//                HttpURLConnection httpURLConnectionObject;
//                OutputStream OutPutStream;
//                BufferedWriter bufferedWriterObject;
//                BufferedReader bufferedReaderObject;
//                int RC;
//                url = new URL(requestURL);
//                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
//                httpURLConnectionObject.setReadTimeout(19000);
//                httpURLConnectionObject.setConnectTimeout(19000);
//                httpURLConnectionObject.setRequestMethod("POST");
//                httpURLConnectionObject.setDoInput(true);
//                httpURLConnectionObject.setDoOutput(true);
//                OutPutStream = httpURLConnectionObject.getOutputStream();
//
//                bufferedWriterObject = new BufferedWriter(
//                        new OutputStreamWriter(OutPutStream, "UTF-8"));
//
//                bufferedWriterObject.write(bufferedWriterDataFN(PData));
//                bufferedWriterObject.flush();
//                bufferedWriterObject.close();
//                OutPutStream.close();
//
//                RC = httpURLConnectionObject.getResponseCode();
//                if (RC == HttpsURLConnection.HTTP_OK) {
//                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
//                    stringBuilder = new StringBuilder();
//                    String RC2;
//                    while ((RC2 = bufferedReaderObject.readLine()) != null) {
//                        stringBuilder.append(RC2);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return stringBuilder.toString();
//        }
//
//        @NonNull
//        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
//            StringBuilder stringBuilderObject;
//            stringBuilderObject = new StringBuilder();
//
//            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
//
//                if (check)
//
//                    check = false;
//                else
//                    stringBuilderObject.append("&");
//
//                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
//
//                stringBuilderObject.append("=");
//
//                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
//            }
//
//            return stringBuilderObject.toString();
//        }
//
//    }

//    private void putImage() {
//        String previouslyEncodedImage = sharedPrefUtil.getString(IMAGE_DATA, "");
//
//        if (!previouslyEncodedImage.equalsIgnoreCase("")) {
//            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//            mDisplayImage.setImageBitmap(bitmap);
//        } else {
//            Toast.makeText(getActivity(), "....No Image....", Toast.LENGTH_SHORT).show();
//            mDisplayImage.setImageResource(R.drawable.blank_profile_picture);
//        }
//    }

}
