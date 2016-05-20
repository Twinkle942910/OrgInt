package com.twinkle.orgint;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.twinkle.orgint.adapter.TabPagerFragmentAdapter;
import com.twinkle.orgint.fragments.LinksFragment;
import com.twinkle.orgint.fragments.PlaningFragment;
import com.twinkle.orgint.fragments.ShedulesFragment;
import com.twinkle.orgint.helpers.Constants;

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
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //

    private void showNotificationTab()
    {

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
                    ((ShedulesFragment)adapter.getItem(Constants.TAB_ONE)).addShedule();
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

                            break;

                        case Constants.TAB_THREE:

                                fab.hide();
                                fab2.hide();
                                fab3.show();

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

    private void initFabs()
    {
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
    }



}
