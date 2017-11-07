package ps.wwbtraining.teacher_group1.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ps.wwbtraining.teacher_group1.Fragment.BlankFragment;
import ps.wwbtraining.teacher_group1.Fragment.ShowGroupFragment;
import ps.wwbtraining.teacher_group1.Fragment.ShowQuizFragment;
import ps.wwbtraining.teacher_group1.Fragment.Teacher_Fragment;
import ps.wwbtraining.teacher_group1.R;
import ps.wwbtraining.teacher_group1.WebService.SharedPrefUtil;

public class TeacherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher2);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameTeacher, new Teacher_Fragment()).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teacher, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameTeacher, new Teacher_Fragment()).commit();
        } else if (id == R.id.profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameTeacher, new BlankFragment()).commit();

        } else if (id == R.id.group) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameTeacher, new ShowGroupFragment()).commit();


        } else if (id == R.id.quiz) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameTeacher, new ShowQuizFragment()).commit();
        } else if (id == R.id.log_out) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to logout?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
                            sharedPrefUtil.clear();
                            //Starting login activity
                            Intent intent = new Intent(TeacherActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            //Showing the alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
//
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
