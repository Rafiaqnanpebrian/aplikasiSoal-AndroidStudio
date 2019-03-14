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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class HasilActivity extends AppCompatActivity {

    private TextView skorBahasaInggris, skorPengetahuanUmum, skorBahasaIndonesia, skorAkhir, skorBenar, skorSalah, skorPass, nama;
    private int benar, salah, kosong, i;
    private ImageButton menu;
    private CustomGauge gauge, gaugeR, gaugeL, gaugeW;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private String name;
    private String scoreInggris,scoreUmum, scoreIndo, scoreAkumulasi;

    LinearLayout exit, share, repeat;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        menu = (ImageButton) findViewById(R.id.btn_menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
                Bitmap bitmap = setViewToBitmapImage(linearLayout);
                SaveImage(bitmap);
            }
        });

        HitungNilai();
        TampilNilai();
        gauge();

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

        exit.setOnClickListener(new View.OnClickListener() {
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
        });

    }

    private void HitungNilai() {
        scoreInggris = getIntent().getExtras().getString("scoreBahasaInggris");
        scoreUmum = getIntent().getExtras().getString("scorePengetahuanUmum");
        scoreIndo = getIntent().getExtras().getString("scoreBahasaIndonesia");
        scoreAkumulasi = getIntent().getExtras().getString("scoreAkumulasi");

        benar = getIntent().getExtras().getInt("jmlhBenar",0);
        salah = getIntent().getExtras().getInt("jmlhSalah",0);
        kosong = getIntent().getExtras().getInt("jmlhKosong", 0);
        name = getIntent().getExtras().getString("nama","none");

    }

    private void gauge() {
        new Thread() {
            public void run() {
                for (i=0;i<=100;i++) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (i< Double.valueOf(scoreInggris)){
                                    gaugeR.setValue(i);
                                }
                                if(i< Double.valueOf(scoreUmum)){
                                    gaugeL.setValue(i);
                                }
                                if(i< Double.valueOf(scoreIndo)){
                                    gaugeW.setValue(i);
                                }
                                if(i< Double.valueOf(scoreAkumulasi)){
                                    gauge.setValue(i*2);
                                }
                                /*skorReading.setText(String.valueOf(i));
                                skorWriting.setText(String.valueOf(i));
                                skorListening.setText(String.valueOf(i));
                                skorAkhir.setText(String.valueOf(i));
                                skorBenar.setText(String.valueOf(i));
                                skorSalah.setText(String.valueOf(i));
                                skorPass.setText(String.valueOf(i));
                                nama.setText(name);*/

                            }
                        });
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    private void TampilNilai() {
        skorBahasaInggris.setText(String.format("%.02f", Double.valueOf(scoreInggris)));
        skorPengetahuanUmum.setText(String.format("%.02f", Double.valueOf(scoreUmum)));
        skorBahasaIndonesia.setText(String.format("%.02f", Double.valueOf(scoreIndo)) );
        skorAkhir.setText(String.format("%.02f", Double.valueOf(scoreAkumulasi)) );

        skorBenar.setText(String.valueOf(benar));
        skorSalah.setText(String.valueOf(salah));
        skorPass.setText(String.valueOf(kosong));
        nama.setText(name);

        // gauge();
    }

    public static Bitmap setViewToBitmapImage(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    public void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/SimulasiToefl/skor");
        myDir.mkdirs();
        /*Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);*/

        String fname = "skor.jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void exitDialog() {
        final Dialog alert1 = new Dialog(HasilActivity.this, android.R.style.Theme_Black_NoTitleBar);
        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0060baff")));
        alert1.setContentView(R.layout.exit_dialog);

        Button tidak = (Button) alert1.findViewById(R.id.tidak);
        Button ya = (Button) alert1.findViewById(R.id.iya);
        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.cancel();
            }
        });

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keluar = new Intent(Intent.ACTION_MAIN);
                keluar.addCategory(Intent.CATEGORY_HOME);
                keluar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(keluar);
                alert1.cancel();
            }
        });
        alert1.setCancelable(false);
        alert1.show();
    }

    public void repeatdialog(){
        final Dialog alert1 = new Dialog(HasilActivity.this, android.R.style.Theme_Black_NoTitleBar);
        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0060baff")));
        alert1.setContentView(R.layout.exit_dialog);

        TextView textView = (TextView) alert1.findViewById(R.id.text_info);
        Button tidak = (Button) alert1.findViewById(R.id.tidak);
        Button ya = (Button) alert1.findViewById(R.id.iya);

        textView.setText("Apakah Anda ingin mengulang simulasi?");
        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.cancel();
            }
        });

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        alert1.setCancelable(false);
        alert1.show();
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
