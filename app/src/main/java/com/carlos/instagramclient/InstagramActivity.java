package com.carlos.instagramclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.carlos.instagramclient.adapter.PhotoViewAdapter;
import com.carlos.instagramclient.asset.Assets;
import com.carlos.instagramclient.fragment.CommentList;
import com.carlos.instagramclient.fragment.PhotoViewList;

public class InstagramActivity extends AppCompatActivity implements PhotoViewAdapter.ViewCommentsListener {

    public static final String TITLE = " InstaClient";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);

        toolbarCreation();
        switchToPhotoViewer();
    }

    private void switchToPhotoViewer() {
        PhotoViewList photoViewList = new PhotoViewList();
        photoViewList.setViewCommentListener(this);
        switchFragment(photoViewList);
    }

    private void switchToCommentList(String id) {
        setHomeButtonVisibility(true);
        CommentList commentList = CommentList.newInstance(id);
        switchFragment(commentList);
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setHomeButtonVisibility(boolean visibility) {
        getSupportActionBar().setHomeButtonEnabled(visibility);
        getSupportActionBar().setDisplayHomeAsUpEnabled(visibility);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instagram, menu);
        return true;
    }

    private void toolbarCreation() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.getMenu().clear();
        setSupportActionBar(toolbar);
        setToolbarTitle(TITLE);
    }

    private void setToolbarTitle(String title) {
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            Assets.setInstagramTitleFont(toolbarTitle, this);
            toolbarTitle.setText(title);
        }
    }

    @Override
    public void viewComments(String id) {
        switchToCommentList(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            getFragmentManager().popBackStack();
        }

        setHomeButtonVisibility(false);
        return super.onOptionsItemSelected(item);
    }
}
