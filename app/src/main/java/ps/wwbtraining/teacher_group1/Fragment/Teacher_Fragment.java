package ps.wwbtraining.teacher_group1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ps.wwbtraining.teacher_group1.R;

public class Teacher_Fragment extends Fragment  {
    private static View view;
    Button manageUser;
    Button createGroup;
    Button createQuiz;
    Button history;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.teacher_layout, container, false);

        manageUser = (Button) view.findViewById(R.id.manager);
        createGroup = (Button) view.findViewById(R.id.group);
        createQuiz = (Button) view.findViewById(R.id.quiz);
        history = (Button) view.findViewById(R.id.hiQuiz);

        manageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                            .replace(R.id.frameTeacher, new ManageUserFragment()
                                   ).commit();
                }

            }
        );

        createGroup.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              getFragmentManager()
                                                      .beginTransaction()
                                                      .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                                      .replace(R.id.frameTeacher, new CreateGroupFragment()
                                                      ).commit();
                                          }

                                      }
        );
        createQuiz.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              getFragmentManager()
                                                      .beginTransaction()
                                                      .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                                      .replace(R.id.frameTeacher, new CreateQuiz()
                                                      ).commit();
                                          }

                                      }
        );
        history.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              getFragmentManager()
                                                      .beginTransaction()
                                                      .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                                      .replace(R.id.frameTeacher, new HistoryFragment()
                                                      ).commit();
                                          }

                                      }
        );
        return view;
    }
}
