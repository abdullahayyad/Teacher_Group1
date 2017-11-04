package ps.wwbtraining.teacher_group1.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ps.wwbtraining.teacher_group1.R;
import ps.wwbtraining.teacher_group1.WebService.SharedPrefUtil;

import static ps.wwbtraining.teacher_group1.Class.Utils.EMAIL_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.MOBIL_SHARED_PREF;
import static ps.wwbtraining.teacher_group1.Class.Utils.NAME_SHARED_PREF;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    SharedPrefUtil sharedPrefUtil;
    private TextView name;
    private TextView email;
    private TextView mobile;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        sharedPrefUtil = new SharedPrefUtil(getActivity());

        // Inflate the layout for this fragment
        String nameSaredPref = sharedPrefUtil.getString(NAME_SHARED_PREF);
        String mobileSaredPref = sharedPrefUtil.getString(MOBIL_SHARED_PREF);
        String emailSaredPref = sharedPrefUtil.getString(EMAIL_SHARED_PREF);
        name=(TextView)view.findViewById(R.id.name);
        mobile=(TextView)view.findViewById(R.id.mobile);
        email=(TextView)view.findViewById(R.id.email);

        name.setText(nameSaredPref);
        mobile.setText(mobileSaredPref);
        email.setText(emailSaredPref);

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
                    getActivity().finish();
                    return true;

                }
                return false;
            }
        });
    }
}
