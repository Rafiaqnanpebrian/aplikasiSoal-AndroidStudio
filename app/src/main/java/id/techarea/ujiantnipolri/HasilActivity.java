package id.techarea.ujiantnipolri;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class HasilActivity extends AppCompatActivity {

    private TextView skorBahasaInggris, skorPengetahuanUmum, skorBahasaIndonesia, skorAkhir, skorBenar, skorSalah, skorPass, nama;
    private int benar, salah, kosong, i;
    private String scoreBahasaInggris, scorePengetahuanUmum, scoreBahasaIndonesia, scoreAkumulasi;
    private CustomGauge gauge, gaugeR, gaugeL, gaugeW;


    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

    LinearLayout exit, share, repeat;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        gauge = (CustomGauge) findViewById(R.id.gauge2);
        gaugeR = (CustomGauge) findViewById(R.id.gaugeR);
        gaugeW = (CustomGauge) findViewById(R.id.gaugeW);
        gaugeL = (CustomGauge) findViewById(R.id.gaugeL);
        skorBahasaInggris = (TextView) findViewById(R.id.NBahasaInggris);
        skorPengetahuanUmum = (TextView) findViewById(R.id.NPengetahuanUmum);
        skorBahasaIndonesia = (TextView) findViewById(R.id.NBahasaIndonesia);
        skorAkhir = (TextView) findViewById(R.id.NAkhir);
        skorBenar = (TextView) findViewById(R.id.Benar);
        skorSalah = (TextView) findViewById(R.id.Salah);
        skorPass = (TextView) findViewById(R.id.Kosong);
        nama = (TextView) findViewById(R.id.Nama);


        share = (LinearLayout) findViewById(R.id.share_button);
        exit = (LinearLayout) findViewById(R.id.exit_button);
        repeat = (LinearLayout) findViewById(R.id.repeat_button);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HasilActivity.this, ShareActivity.class);
                startActivity(i);
            }
        });

        /*exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog();
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatdialog();
            }
        });*/


    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                break;
            case R.id.nav_second_fragment:
                break;
            case R.id.nav_third_fragment:
                break;
            default:
        }

        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan tombol kembali 2x untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 500);
    }



}
