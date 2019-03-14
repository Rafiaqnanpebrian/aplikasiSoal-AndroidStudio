package id.techarea.ujiantnipolri;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.util.Map;

import id.techarea.ujiantnipolri.helper.AnswerRecordClass;
import id.techarea.ujiantnipolri.models.Jawaban;
import id.techarea.ujiantnipolri.models.JawabanUser;
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
    int id_exam;
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
                id_exam = intent.getIntExtra("sim", 0);

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

                        if (id_exam == 1) {
                            mExamplePagerAdapter = new AdapterContentSoal(soalList, SoalActivity.this, 1);

                            mViewPager = (ViewPager) findViewById(R.id.view_pager);
                            mViewPager.setAdapter(mExamplePagerAdapter);

                            initMagicIndicator();
                        } else if (id_exam == 2) {
                            mExamplePagerAdapter = new AdapterContentSoal(soalList, SoalActivity.this, 2);

                            mViewPager = (ViewPager) findViewById(R.id.view_pager);
                            mViewPager.setAdapter(mExamplePagerAdapter);

                            initMagicIndicator();
                        } else if (id_exam == 3) {
                            mExamplePagerAdapter = new AdapterContentSoal(soalList, SoalActivity.this, 3);

                            mViewPager = (ViewPager) findViewById(R.id.view_pager);
                            mViewPager.setAdapter(mExamplePagerAdapter);

                            initMagicIndicator();
                        }


                    }
                });
                loading.dismiss();

            }
        };
        thread.start();

        finishLatihan =(Button) findViewById(R.id.btn_finish_soal);
        finishLatihan.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                AnswerRecordClass.listJawabanSoal = mExamplePagerAdapter.getListJwbUser();
                try {
                    if (AnswerRecordClass.listJawabanSoal.size() >0)
                    {
                        AlertDialog(1);
                    } else {
                        AlertDialog(0);
                    }
                }catch (Exception e)
                {

                }
            }
        });

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

    public int jawabanBenar(Map<Integer, JawabanUser> jawabanUser)
    {
        int jumlahBenar = 0;
        for (int i= 0; i< jawabanUser.size();i++)
        {
            if(jawabanUser.get(i) !=null)
            {
                if(jawabanUser.get(i).getMark() == 1)
                {
                    jumlahBenar += 1;
                }
            }
        }
        return jumlahBenar;
    }

    public double getScore(double jmlhBenar, double jmlSoal){
        //return (int) Math.round((41.83/100)*jmlBenar;
        return (jmlhBenar)*1;

    }

    public void finishUjian(Map<Integer, JawabanUser>listJwbUser)
    {
        int jmlhBenarBahasaInggris, jmlhSalahBahasaInggris, jmlhKosongBahasaInggris;
        int jmlhBenarBahasaIndonesia, jmlhSalahBahasaIndonesia, jmlhKosongBahasaIndonesia;
        int jmlhBenarPengetahuanUmum, jmlhSalahPengetahuanUmum, jmlhKosongPengetahuanUmum;
        int jmlhBenarAkumulasi, jmlhSalahAkumulasi, jmlhKosongAkumulasi;
        int jmlhSoalAkumulasi;
        double scoreBahasaInggris, scoreBahasaIndonesia, scorePengetahuanUmum, scoreAkumulasi;

        List<Soal> soalListBahasaInggris = db.getDataSoal(2);
        List<Soal> soalListBahasaIndonesia = db.getDataSoal(1);
        List<Soal> soalListPengetahuanUmum = db.getDataSoal(3);

        jmlhSoalAkumulasi = soalListBahasaInggris.size()+soalListBahasaIndonesia.size()+soalListPengetahuanUmum.size();

        jmlhBenarBahasaInggris = jawabanBenar(AnswerRecordClass.listJawabanSoal);
        jmlhSalahBahasaInggris = AnswerRecordClass.listJawabanSoal.size() - jmlhBenarBahasaInggris;
        jmlhKosongBahasaInggris = soalListBahasaInggris.size() - AnswerRecordClass.listJawabanSoal.size();
        scoreBahasaInggris = getScore(jmlhBenarBahasaInggris, soalListBahasaInggris.size());

        jmlhBenarBahasaIndonesia = jawabanBenar(AnswerRecordClass.listJawabanSoal);
        jmlhSalahBahasaIndonesia = AnswerRecordClass.listJawabanSoal.size() - jmlhBenarBahasaIndonesia;
        jmlhKosongBahasaIndonesia = soalListBahasaIndonesia.size() - AnswerRecordClass.listJawabanSoal.size();
        scoreBahasaIndonesia = getScore(jmlhBenarBahasaIndonesia, soalListBahasaIndonesia.size());

        jmlhBenarPengetahuanUmum = jawabanBenar(AnswerRecordClass.listJawabanSoal);
        jmlhSalahPengetahuanUmum = AnswerRecordClass.listJawabanSoal.size() - jmlhBenarPengetahuanUmum;
        jmlhKosongPengetahuanUmum = soalListPengetahuanUmum.size() - AnswerRecordClass.listJawabanSoal.size();
        scorePengetahuanUmum = getScore(jmlhBenarPengetahuanUmum, soalListPengetahuanUmum.size());

        jmlhBenarAkumulasi = jmlhBenarBahasaInggris+jmlhBenarBahasaIndonesia+jmlhBenarPengetahuanUmum;
        jmlhSalahAkumulasi = jmlhSalahBahasaInggris+jmlhSalahBahasaIndonesia+jmlhSalahPengetahuanUmum;
        jmlhKosongAkumulasi = jmlhKosongBahasaInggris+jmlhKosongBahasaIndonesia+jmlhKosongPengetahuanUmum;
        scoreAkumulasi = getScore(jmlhBenarAkumulasi,jmlhSoalAkumulasi);

        Intent newIntent = new Intent(SoalActivity.this, HasilActivity.class );
        newIntent.putExtra("jmlhBenar",jmlhBenarAkumulasi);
        newIntent.putExtra("jmlhSalah",jmlhSalahAkumulasi);
        newIntent.putExtra("jmlhKosong",jmlhKosongAkumulasi);
        newIntent.putExtra("scoreBahasaInggris", String.valueOf(scoreBahasaInggris));
        newIntent.putExtra("scoreBahasaIndonesia", String.valueOf(scoreBahasaInggris));
        newIntent.putExtra("scorePengetahuanUmum", String.valueOf(scorePengetahuanUmum));
        newIntent.putExtra("scoreAkumulasi",String.valueOf(scoreAkumulasi));
        newIntent.putExtra("ujian", id_exam);
        newIntent.putExtras(getIntent().getExtras());

        startActivity(newIntent);
        finish();
    }
    public void AlertDialog(final int code){
        final Dialog alert1 = new Dialog(SoalActivity.this, android.R.style.Theme_Black_NoTitleBar);
        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        alert1.setContentView(R.layout.exit_dialog);

        Button cancelBtn = (Button) alert1.findViewById(R.id.tidak);
        Button nextBtn = (Button) alert1.findViewById(R.id.iya);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.cancel();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code == 1){
                    finishUjian(mExamplePagerAdapter.getListJwbUser());
                }
                else if(code == 0){
                    Intent intent = new Intent(SoalActivity.this, PilihUjianActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);
                    alert1.cancel();
                    finish();
                }
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
                doubleBackToExitPressedOnce = false;
            }
        }, 500);
    }

}

