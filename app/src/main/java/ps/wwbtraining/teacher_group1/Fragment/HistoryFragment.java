package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ps.wwbtraining.teacher_group1.R;

public class HistoryFragment extends Fragment {
    private View view;

    public HistoryFragment() {
        // Required empty public constructor
    }

      public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
       return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_history, container, false);



        return view;

    }
  }
