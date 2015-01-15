package com.damirh.easypark;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.damirh.easypark.fragments.AddParkingFragment;
import com.damirh.easypark.fragments.EditParkingFragment;
import com.damirh.easypark.fragments.LoginFragment;
import com.damirh.easypark.fragments.MapFragment;
import com.damirh.easypark.fragments.ParkingDetailsFragment;
import com.damirh.easypark.fragments.RegLogFragment;

import connector.loginUserJSON;
import model.Parking;
import model.Person;


public class MapActivity extends Activity implements MapFragment.OnFragmentInteractionListener,
        RegLogFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        AddParkingFragment.OnFragmentInteractionListener,
        ParkingDetailsFragment.OnFragmentInteractionListener,
        EditParkingFragment.OnFragmentInteractionListener{

    public static final int POSITION_MAP = 0;
    public static final int POSITION_LOGIN = 1;
    public static final int POSITION_REGISTER = 2;
    public static final int POSITION_ADD_PARKING = 3;
    public static  final int POSITION_EDIT_PARKING = 4;
    private String ParkingID;
    private Parking selected_parking;
    private String[] mDrawerOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private int userid;
    private int regParkingPos;
    private Person Korisnik;

    @Override
    public void onFragmentInteraction(String id) {
        regParkingPos = Integer.parseInt(id);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void reserve(View v) {
        ListView lw = (ListView) findViewById(R.id.parkingDetailsList);
        Log.d("Parking", lw.getSelectedItemPosition()+"");
        Parking p = (Parking) lw.getItemAtPosition(regParkingPos);
        if(p!=null)
            Log.d("Parking", p.get_telefon());
        Toast.makeText(
                this,
                "ReserveMOFO", Toast.LENGTH_LONG).show();

    }
    public void loginUser(View v) {
        EditText mail = (EditText) findViewById(R.id.loginEmail);
        EditText pass = (EditText) findViewById(R.id.loginPass);
        String textMail = String.valueOf(mail.getText());
        String textPass = String.valueOf(pass.getText());
        if(textMail.equals("admin") && textPass.equals("admin")) {
            userid = 3;
            Toast.makeText(
                    this,
                    "Dobrodosli natrag!"+textMail+" "+textPass + "!", Toast.LENGTH_LONG).show();
        }
        loginUserJSON luj = new loginUserJSON(textMail,textPass,this);
        luj.execute();
    }
    public void loginPerson(Person p) {
        if(p.get_personID()!=0) {
            userid = p.get_personID();
            Korisnik = p;
            Toast.makeText(
                    this,
                    "Upisani ste kao "+p.get_firstname()+" "+p.get_lastname(), Toast.LENGTH_LONG).show();
            Button loginbtn = (Button) findViewById(R.id.btnLogin);
            Button logoutbtn = (Button) findViewById(R.id.Logout);
            loginbtn.setEnabled(false);
            logoutbtn.setEnabled(true);
        }

        else {
            Toast.makeText(
                    this,
                    "Pogrešni podaci!", Toast.LENGTH_LONG).show();
        }
    }
    public void logoutPerson(View v) {
        userid = 0;
        Korisnik = null;
        Button loginbtn = (Button) findViewById(R.id.btnLogin);
        Button logoutbtn = (Button) findViewById(R.id.Logout);
        loginbtn.setEnabled(true);
        logoutbtn.setEnabled(false);
        Toast.makeText(
                this,
                "Ispisali ste se!", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_map);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, MapFragment.newInstance(43.854892, 18.386757))
                    .commit();
        }

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerOptions = getResources().getStringArray(R.array.left_drawer_options);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerOptions));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setItemChecked(0, true);

        setDrawerToggle();
    }

    private void setDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {

        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = getFragmentBasedOnMenuChoice(position);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerOptions[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private Fragment getFragmentBasedOnMenuChoice(int pos) {
        switch(pos) {
            case POSITION_MAP:
                return MapFragment.newInstance(43.854892, 18.386757);
            case POSITION_LOGIN:
                return LoginFragment.newInstance("A","B");
            case POSITION_REGISTER:
                return RegLogFragment.newInstance(false);
            case POSITION_ADD_PARKING:
                return ParkingDetailsFragment.newInstance("A","B");
        }

        return null;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


}
