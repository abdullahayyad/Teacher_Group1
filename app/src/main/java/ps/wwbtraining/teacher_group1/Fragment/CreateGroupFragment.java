package ps.wwbtraining.teacher_group1.Fragment;

/**
 * Created by osama on 17/10/2017.
 */


        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ps.wwbtraining.teacher_group1.R;

public class CreateGroupFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_group, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.student_name);
       // ArrayList<User> arrayList=new ArrayList<>();
       // ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_checked,arrayList);

       // recyclerView.setAdapter(arrayAdapter);

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().beginTransaction()  .setCustomAnimations(R.anim.left_enter, R.anim.right_out).
                            replace(R.id.frameTeacher, new Teacher_Fragment()).addToBackStack(null).commit();
                    return true;

                }
                return false;
            }
        });
    }

}
