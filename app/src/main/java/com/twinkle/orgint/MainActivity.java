package com.twinkle.orgint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.twinkle.orgint.adapter.AddingTaskBottomSheetAdapter;
import com.twinkle.orgint.adapter.TabPagerFragmentAdapter;
import com.twinkle.orgint.fragments.LinksFragment;
import com.twinkle.orgint.fragments.PlaningFragment;
import com.twinkle.orgint.helpers.ActivityDataCommunicator;
import com.twinkle.orgint.helpers.AdapterCommunicator;
import com.twinkle.orgint.helpers.Constants;
import com.twinkle.orgint.pages.AddingActivity;
import com.twinkle.orgint.pages.EventActivity;
import com.twinkle.orgint.pages.InterestsActivity;
import com.twinkle.orgint.pages.SettingsActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private static final int LAYOUT = R.layout.activity_main;
    public static String TAB_POSITION = "POSITION";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    private GridView bottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private Integer[] bottomItems = {R.drawable.ic_playlist_add, R.drawable.ic_build, R.drawable.ic_today, R.drawable.ic_card_giftcard};
    private ArrayAdapter<Integer> bottomSheetAdapter;

    public AdapterCommunicator adapterCommunicator;
    public ActivityDataCommunicator fragmentCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       // setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();
        initFabs();
        initTabs();
        initTabIcons();
        initBottomSheet();
    }

    //get - select tab position (current page)
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(TAB_POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(TAB_POSITION));
    }
    //

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
        {
            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
        {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else
        {
            super.onBackPressed();
        }
    }

    //Toolbar menu implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    /*    if (id == R.id.sort)
        {
            return true;
        }

        if (id == R.id.today)
        {
            return true;
        }*/

        switch(item.getItemId())
        {
            case R.id.sort:

            case R.id.today:

            case R.id.interests:
                Intent intent = new Intent(this, InterestsActivity.class);
                intent.putExtra("red_code", "From main activity");
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Navigation view menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        //int id = item.getItemId();

        switch(item.getItemId())
        {
            case R.id.nav_home:
                // Handle the camera action
            case R.id.nav_notifications:

            case R.id.nav_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.todo_page:
                intent = new Intent(this, EventActivity.class);
                intent.putExtra("calling", "From Main Activity");
                intent.putExtra("type", "ToDo");
                startActivity(intent);
                return true;

            case R.id.work_tasks_page:
                intent = new Intent(this, EventActivity.class);
                intent.putExtra("calling", "From Main Activity");
                intent.putExtra("type", "Work Task");
                startActivity(intent);
                return true;

            case R.id.birthday_page:
                intent = new Intent(this, EventActivity.class);
                intent.putExtra("calling", "From Main Activity");
                intent.putExtra("type", "Birthday");
                startActivity(intent);
                return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == Constants.REQUEST_CODE_ADDING_SCHEDULE)
            {
                sendDataToScheduleFragment(data);

                int position = 0;

                switch (data.getStringExtra("day"))
                {
                    case "Monday":
                         position = 0;
                        break;

                    case "Tuesday":
                        position = 1;
                        break;

                    case "Wednesday":
                        position = 2;
                        break;

                    case "Thursday":
                        position = 3;
                        break;

                    case "Friday":
                        position = 4;
                        break;

                    case "Saturday":
                        position = 5;
                        break;

                    case "Sunday":
                        position = 6;
                        break;
                }

                updateScheduleAdapterItems(position);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error! Different result codes", Toast.LENGTH_SHORT).show();
        }
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //init Navigation Drawer
    private void initNavigationView()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    //Initialize Tabs
    private void initTabs()
    {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        if (viewPager != null)
        {
            viewPager.setAdapter(adapter);

            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //  ((SchedulesFragment)adapter.getItem(Constants.TAB_ONE)).addShedule();
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    fab.hide();
                }
            });

            fab2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ((LinksFragment)adapter.getItem(Constants.TAB_TWO)).addLink();
                }
            });

            fab3.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ((PlaningFragment)adapter.getItem(Constants.TAB_THREE)).addPlan();
                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position)
                {
                    switch (position)
                    {
                        case Constants.TAB_ONE:

                            fab.show();
                            fab2.hide();
                            fab3.hide();

                            break;

                        case Constants.TAB_TWO:

                            fab.hide();
                            fab2.show();
                            fab3.hide();

                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                            break;

                        case Constants.TAB_THREE:

                            fab.hide();
                            fab2.hide();
                            fab3.show();

                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                            break;

                        default:

                            fab.show();
                            fab2.hide();
                            fab3.hide();

                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if (tabLayout != null)
        {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void initTabIcons()
    {
        TabLayout.Tab tabShedules = tabLayout.getTabAt(Constants.TAB_ONE);
        TabLayout.Tab tabLinks = tabLayout.getTabAt(Constants.TAB_TWO);
        TabLayout.Tab tabPlaning = tabLayout.getTabAt(Constants.TAB_THREE);

        if (tabShedules != null)
        {
            tabShedules.setIcon(Constants.TAB_ONE_ICON);
        }

        if (tabLinks != null)
        {
            tabLinks.setIcon(Constants.TAB_TWO_ICON);
        }

        if (tabPlaning != null)
        {
            tabPlaning.setIcon(Constants.TAB_THREE_ICON);
        }
    }
    //

    //Initialize action buttons
    private void initFabs()
    {
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
    }

    //Initialize bottom sheet, add items and events
    private void initBottomSheet()
    {
        //set bottom sheet(GridView) adapter
        bottomSheetAdapter = new AddingTaskBottomSheetAdapter(this, R.layout.adding_item, bottomItems);

        bottomSheet = (GridView) findViewById(R.id.bottom_sheet);
        if (bottomSheet != null)
        {
            bottomSheet.setAdapter(bottomSheetAdapter);
        }

        bottomSheet.setTranslationY(getStatusBarHeight());
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            boolean first = true;

            @Override
            public void onStateChanged(View bottomSheet, int newState)
            {
                Log.d("MainActivity", "onStateChanged: " + newState);
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset)
            {
                Log.d("MainActivity", "onSlide: ");
                if (first)
                {
                    first = false;
                    bottomSheet.setTranslationY(0);
                }

                if (slideOffset == 0)
                {
                    fab.show();
                }


            }
        });


        //bottom sheet item click event
        bottomSheet.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {

                Intent intent;

                switch (position)
                {
                    case 0:
                        //ToDo: make forResult().
                        intent = new Intent(getApplicationContext(), AddingActivity.class);
                        intent.putExtra("type", "ToDo");
                        startActivity(intent);

                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;

                    case 1:
                        intent = new Intent(getApplicationContext(), AddingActivity.class);
                        intent.putExtra("type", "Work Task");
                        startActivity(intent);

                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;

                    case 2:
                        intent = new Intent(getApplicationContext(), AddingActivity.class);
                        intent.putExtra("type", "Schedule");
                        startActivityForResult(intent, Constants.REQUEST_CODE_ADDING_SCHEDULE);

                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;

                    case 3:
                        intent = new Intent(getApplicationContext(), AddingActivity.class);
                        intent.putExtra("type", "Birthday");
                        startActivity(intent);

                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                }
            }
        });
    }

    private int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void updateScheduleAdapterItems(int position)
    {
        if(adapterCommunicator != null)
        {
            adapterCommunicator.updateItem(position);
        }
    }

    private void sendDataToScheduleFragment(Intent data)
    {
        if(fragmentCommunicator != null)
        {
            fragmentCommunicator.passDataToFragment(data);
        }
    }
}
