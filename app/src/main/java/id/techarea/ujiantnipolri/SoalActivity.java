package id.techarea.ujiantnipolri;

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
                AnswerRecordClass.listJawabanBahasaInggris = mExamplePagerAdapter.getListJwbUser();
                AnswerRecordClass.listJawabanPengetahuanUmum = mExamplePagerAdapter.getListJwbUser();
                AnswerRecordClass.listJawabanBahasaIndonesia = mExamplePagerAdapter.getListJwbUser();
                try{
                    if (AnswerRecordClass.listJawabanBahasaInggris.size() >0 &&
                            AnswerRecordClass.listJawabanPengetahuanUmum.size()>0&&
                            AnswerRecordClass.listJawabanBahasaIndonesia.size()>0){
                        AlertDialog(1);
                    }else{
                        AlertDialog(0);
                    }
                }
                catch (Exception e){

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

    public void AlertDialog(final int code){
        final Dialog alert1 = new Dialog(SoalActivity.this, android.R.style.Theme_Black_NoTitleBar);
        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        alert1.setContentView(R.layout.finish_dialog);

        Button cancelBtn = (Button) alert1.findViewById(R.id.batal);
        Button nextBtn = (Button) alert1.findViewById(R.id.edit_data);
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

    public void finishUjian(Map<Integer, JawabanUser> listJwbUser){


        int jmlhBenarBinggris, jmlhSalahBinggris, jmlKosongBinggris;
        int jmlhBenarPengetahuanUmum, jmlhSalahPengetahuanUmum, jmlKosongPengetahuanUmum;
        int jmlhBenarBahasaIndonesia, jmlhSalahBahasaIndonesia, jmlKosongBahasaIndonesia;
        int jmlhBenarAkumulasi, jmlhSalahAkumulasi, jmlKosongAkumulasi;
        int jmlhSoalAkumulasi;
        double scoreBahasaInggris, scorePengetahuanUmum, scoreBahasaIndonesia, scoreAkumulasi;


        List<Soal> soalListBahasaInggris = db.getDataSoal(2);
        List<Soal> soalListPengetahuanUmum = db.getDataSoal(3);
        List<Soal> soalListBahasaIndonesia = db.getDataSoal(1);

       jmlhSoalAkumulasi = soalListBahasaInggris.size()+soalListPengetahuanUmum.size()+soalListBahasaIndonesia.size();

        jmlhBenarBinggris = jawabanBenar(AnswerRecordClass.listJawabanBahasaInggris);
        jmlhSalahBinggris = AnswerRecordClass.listJawabanBahasaInggris.size() - jmlhBenarBinggris;
        jmlKosongBinggris = soalListBahasaInggris.size() - AnswerRecordClass.listJawabanBahasaInggris.size();
        scoreBahasaInggris = getScore(jmlhBenarBinggris,soalListBahasaInggris.size());

        jmlhBenarPengetahuanUmum = jawabanBenar(AnswerRecordClass.listJawabanPengetahuanUmum);
        jmlhSalahPengetahuanUmum = AnswerRecordClass.listJawabanPengetahuanUmum.size() - jmlhBenarPengetahuanUmum;
        jmlKosongPengetahuanUmum = soalListPengetahuanUmum.size() - AnswerRecordClass.listJawabanPengetahuanUmum.size();
        scorePengetahuanUmum = getScore(jmlhBenarPengetahuanUmum,soalListPengetahuanUmum.size());

        jmlhBenarBahasaIndonesia = jawabanBenar(AnswerRecordClass.listJawabanBahasaIndonesia);
        jmlhSalahBahasaIndonesia = AnswerRecordClass.listJawabanBahasaIndonesia.size() - jmlhBenarBahasaIndonesia;
        jmlKosongBahasaIndonesia = soalListBahasaIndonesia.size() - AnswerRecordClass.listJawabanBahasaIndonesia.size();
        scoreBahasaIndonesia = getScore(jmlhBenarBahasaIndonesia,soalListBahasaIndonesia.size());



        jmlhBenarAkumulasi = jmlhBenarBinggris+jmlhBenarPengetahuanUmum+jmlhBenarBahasaIndonesia;
        jmlhSalahAkumulasi = jmlhSalahBinggris+jmlhSalahPengetahuanUmum+jmlhSalahBahasaIndonesia;
        jmlKosongAkumulasi = jmlKosongBinggris+jmlKosongPengetahuanUmum+jmlKosongBahasaIndonesia;
        scoreAkumulasi = getScore(jmlhBenarAkumulasi,jmlhSoalAkumulasi);

        Log.i("score","reading: "+scoreBahasaInggris+"\n"+"listeing: "+scorePengetahuanUmum+"\n"+"Writing: "+scoreBahasaIndonesia+"\n");


        Intent newIntent = new Intent(SoalActivity.this, HasilActivity.class);
        newIntent.putExtra("jmlhBenar",jmlhBenarAkumulasi);
        newIntent.putExtra("jmlhSalah",  jmlhSalahAkumulasi);
        newIntent.putExtra("jmlhKosong", jmlKosongAkumulasi);
        newIntent.putExtra("scoreBahasaInggris", String.valueOf(scoreBahasaInggris));
        newIntent.putExtra("scorePengetahuanUmum", String.valueOf(scorePengetahuanUmum));
        newIntent.putExtra("scoreBahasaIndonesia", String.valueOf(scoreBahasaIndonesia));
        newIntent.putExtra("scoreAkumulasi",String.valueOf(scoreAkumulasi));
        newIntent.putExtra("simulasi", id_exam);
        newIntent.putExtras(getIntent().getExtras());
        //newIntent.putExtra("listJwbUser", (Serializable) listJwbUser);
        // newIntent.putExtra("name", intent.getStringExtra("name").toString());
        startActivity(newIntent);
        finish();
    }

    public int jawabanBenar(Map<Integer, JawabanUser> jawabanUser){

        int jumlahBenar = 0;

        for(int i= 0; i< jawabanUser.size();i++){
            if(jawabanUser.get(i) != null){
                if(jawabanUser.get(i).getMark() == 1){
                    jumlahBenar += 1;
                }
            }
        }
        return jumlahBenar;
    }

    public double getScore(double jmlBenar, double jmlSoal){
        //return (int) Math.round((41.83/100)*jmlBenar);
        return (jmlBenar/jmlSoal)*100;
    }

}

