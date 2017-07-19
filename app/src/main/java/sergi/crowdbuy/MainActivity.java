package sergi.crowdbuy;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OffersFragment.OnFragmentInteractionListener,
        MyOffersFragment.OnFragmentInteractionListener, MyChatsFragment.OnFragmentInteractionListener{

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final List<String> permissionsList = new ArrayList<>();
        permissionsList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

        setTitle("Crowd Offers");

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = OffersFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.plusBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NewOfferActivity.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView emailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailTextView);
        emailTextView.setText(Crowdbuy.email);

    }

    public void onClick(View v ){
        switch (v.getId()){
            case R.id.button:
                Intent i = new Intent(this, ChatActivity.class);
                startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Log.e("Button", "back");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_camera) {
            setTitle("Crowd Offers");
            fragmentClass = OffersFragment.class;
        } else if (id == R.id.nav_gallery) {
            setTitle("My Offers");
            fragmentClass = MyOffersFragment.class;
        } else if (id == R.id.nav_slideshow) {
            setTitle("My Chats");
            fragmentClass = MyChatsFragment.class;
        } else if (id == R.id.nav_share) {
            fragmentClass = MyOffersFragment.class;
        } else if (id == R.id.nav_send) {
            fragmentClass = MyOffersFragment.class;
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
