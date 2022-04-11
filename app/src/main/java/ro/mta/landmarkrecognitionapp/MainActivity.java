package ro.mta.landmarkrecognitionapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;

import ro.mta.landmarkrecognitionapp.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    ViewPager2 viewPager;
    TabLayout tabs;
    SectionsPagerAdapter sectionsPagerAdapter;
    private String unrecognizedImagesDirLocation;
    private String recognizedImagesDirLocation;
    boolean pauseFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unrecognizedImagesDirLocation = getFilesDir().getPath() + "/Unrecognized Images";
        recognizedImagesDirLocation = getFilesDir().getPath() + "/Recognized Images";
        File unrecognizedImagesDir = new File(unrecognizedImagesDirLocation);
        File recognizedImagesDir = new File(recognizedImagesDirLocation);
        if (!unrecognizedImagesDir.exists())
            unrecognizedImagesDir.mkdirs();
        if (!recognizedImagesDir.exists())
            recognizedImagesDir.mkdirs();
        sectionsPagerAdapter = new SectionsPagerAdapter(this);
        viewPager = findViewById(R.id.view_pager_galleries);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();
        FloatingActionButton fab = findViewById(R.id.fab_camera);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pauseFlag) {
            recreate();
            pauseFlag = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseFlag = true;
    }
}