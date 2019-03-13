package id.techarea.ujiantnipolri;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import id.techarea.ujiantnipolri.models.Jawaban;
import id.techarea.ujiantnipolri.models.Soal;
import id.techarea.ujiantnipolri.modul.DBHandler;

public class SoalActivity extends AppCompatActivity {

    //private ArrayList<DaftarSoal> soal;
    private ArrayList<Integer> urutanSoal;
    private ArrayList<String> jawab;
    private AdapterContentSoal mExamplePagerAdapter;

    public ViewPager mViewPager;

    private MagicIndicator magicIndicator;
    private CommonNavigator commonNavigator;

    DBHandler db;
    List<Soal> soalList;
    int id_exam = 1;
    Intent intent;
    Button finishLatihan;

    Boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_soal);
        final ProgressDialog loading = ProgressDialog.show(SoalActivity.this, "", "Menyiapkan Soal..",
                true);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                db = new DBHandler(SoalActivity.this);
                intent = getIntent();
                id_exam = intent.getIntExtra("sim", 1);

                soalList = db.getDataSoal(id_exam);

                /*Soal soal = new Soal();
                soal.setQuestion("pertanyaan 1");
                Jawaban jawaban1 = new Jawaban();
                jawaban1.setAnswer("jawaban1");
                jawaban1.setOrder(1);
                Jawaban jawaban2 = new Jawaban();
                jawaban2.setOrder(2);
                jawaban2.setAnswer("jawaban2");
                Jawaban jawaban3 = new Jawaban();
                jawaban3.setOrder(3);
                jawaban3.setAnswer("jawaban3");
                Jawaban jawaban4 = new Jawaban();
                jawaban4.setOrder(4);
                jawaban4.setAnswer("jawaban4");


                List<Jawaban> jawabanList = new ArrayList<>();

                jawabanList.add(jawaban1);
                jawabanList.add(jawaban2);
                jawabanList.add(jawaban3);
                jawabanList.add(jawaban4);
                soal.setListJawaban(jawabanList);

                soalList = new ArrayList<>();
                soalList.add(soal);*/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mExamplePagerAdapter = new AdapterContentSoal(soalList, SoalActivity.this, 1);

                        mViewPager = (ViewPager) findViewById(R.id.view_pager);
                        mViewPager.setAdapter(mExamplePagerAdapter);

                        initMagicIndicator();
                    }
                });
                loading.dismiss();

            }
        };
        thread.start();

        /*finishLatihan =(Button) findViewById(R.id.btn_finish_soal);
        finishLatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    private void initMagicIndicator() {
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);

        commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return soalList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(String.valueOf(index + 1));
                simplePagerTitleView.setGravity(Gravity.CENTER);
                simplePagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#3abaff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#ffffff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
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
                doubleBackToExitPressedOnce = false;
            }
        }, 500);
    }

}

