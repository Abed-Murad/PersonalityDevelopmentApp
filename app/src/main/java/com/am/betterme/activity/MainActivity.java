package com.am.betterme.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.am.betterme.R;
import com.am.betterme.databinding.ActivityMainBinding;
import com.am.betterme.databinding.ContentMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.marcoscg.ratedialog.RateDialog;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import static com.am.betterme.util.FUNC.startAboutActivity;
import static com.am.betterme.util.FUNC.startBugReportActivity;
import static com.am.betterme.util.FUNC.startRateUsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RateDialog.with(this, 2, 4); // daysUntilPrompt, launchesUntilPrompt
        mLayout = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ContentMainBinding mCoordinatorLayout = mLayout.includeLayout;
        setSupportActionBar(mCoordinatorLayout.toolbar);
        setupDrawer(mCoordinatorLayout.toolbar);
        Navigation.findNavController(this, R.id.navHostFragment).setGraph(R.navigation.nav_graph, null);
    }

    private void setupDrawer(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this
                , mLayout.drawerLayout
                , toolbar
                , R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        mLayout.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mLayout.navView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (mLayout.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mLayout.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("hello", "world");
        startSearch(null, false, appData, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            onSearchRequested();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {
            case R.id.navAll:
                //TODO: Send Arg to Posts Fragment
                break;
            case R.id.navArticles:
                //TODO: Send Arg to Posts Fragment
                break;
            case R.id.navVideos:
                //TODO: Send Arg to Posts Fragment
                break;
            case R.id.navRateUs:
                startRateUsActivity(this);
                break;
            case R.id.navAbout:
                startAboutActivity(this);
                break;
                case R.id.navBugReport:
                    startBugReportActivity(this);
                break;
        }
        mLayout.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
