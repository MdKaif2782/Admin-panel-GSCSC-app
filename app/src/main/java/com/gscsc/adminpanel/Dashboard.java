package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gscsc.adminpanel.Fragments.HomeFragment;
import com.gscsc.adminpanel.Fragments.InviteFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Dashboard extends AppCompatActivity {
    private String TAG = "Dashboard";
    private int ID_HOME = 1;
    private int ID_INVITE = 3;
    private int ID_MEMBERS = 2;
    private int STATE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_space_dashboard_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_people_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_person_add_24));
        loadFragment(new HomeFragment());
        STATE = 1;
        bottomNavigation.show(ID_HOME, true);

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        //set status bar icons dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        int LastState = 0;
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment = new HomeFragment();
                switch (model.getId()) {
                    case 1:
                        if (!(STATE ==ID_HOME)) {
                            fragment = new HomeFragment();
                            STATE = ID_HOME;
                            loadFragment(fragment);
                        }
                        break;
                    case 2:
                        if (!(STATE == ID_MEMBERS)) {
                            fragment = new HomeFragment();
                            STATE = ID_MEMBERS;
                            loadFragment(fragment);
                        }
                        break;
                    case 3:
                        if (!(STATE == ID_INVITE)) {
                            fragment = new InviteFragment();
                            STATE = ID_INVITE;
                            Log.d(TAG, "invoke: Invite Fragment" );
                            loadFragment(fragment);
                        }
                        break;

                }


                return null;
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        //replace the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view,fragment, null)
                .commit();
    }
}
