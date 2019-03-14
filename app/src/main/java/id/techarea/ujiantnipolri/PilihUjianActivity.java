package id.techarea.ujiantnipolri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import id.techarea.ujiantnipolri.helper.AnswerRecordClass;

public class PilihUjianActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout btn_bahasa_inggris, btn_bahasa_indonesia, btn_pengetahuan_umum, next_indo, next_ing, next_umum;

    String nama = "";

    ImageView img_indo, img_ing, img_umum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pilih_ujian);

        nama = getIntent().getExtras().getString("nama", "");

        btn_bahasa_inggris = (LinearLayout) findViewById(R.id.btn_bahasa_inggris);
        btn_bahasa_indonesia = (LinearLayout) findViewById(R.id.btn_bahasa_indonesia);
        btn_pengetahuan_umum = (LinearLayout) findViewById(R.id.btn_pengetahuan_umum);

        img_indo = (ImageView) findViewById(R.id.imageViewIndo);
        img_ing = (ImageView) findViewById(R.id.imageViewInggris);
        img_umum = (ImageView) findViewById(R.id.imageViewUmum);

        next_indo = (LinearLayout) findViewById(R.id.nextIndo);
        next_ing = (LinearLayout) findViewById(R.id.nextIng);
        next_umum = (LinearLayout) findViewById(R.id.nextUmum);

        if (AnswerRecordClass.listJawabanBahasaIndonesia.size() > 0) {
            img_indo.setImageResource(R.drawable.icon_buku_copy);
            next_indo.setBackgroundResource(R.drawable.panah_copy);
        }if (AnswerRecordClass.listJawabanBahasaInggris.size() > 0) {
            img_ing.setImageResource(R.drawable.icon_buku_copy);
            next_ing.setBackgroundResource(R.drawable.panah_copy);
        }if (AnswerRecordClass.listJawabanPengetahuanUmum.size() > 0) {
            img_umum.setImageResource(R.drawable.icon_buku_copy);
            next_umum.setBackgroundResource(R.drawable.panah_copy);
        }

        btn_bahasa_inggris.setOnClickListener(this);
        btn_bahasa_indonesia.setOnClickListener(this);
        btn_pengetahuan_umum.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btn_bahasa_inggris):
                Intent bahasa_inggris = new Intent(PilihUjianActivity.this, SoalActivity.class);
                bahasa_inggris.putExtra("sim", 2);
                bahasa_inggris.putExtra("nama", nama);
                startActivity(bahasa_inggris);
                break;

            case (R.id.btn_bahasa_indonesia):
                final Intent bahasa_indonesia = new Intent(PilihUjianActivity.this, SoalActivity.class);
                bahasa_indonesia.putExtra("sim", 1);
                bahasa_indonesia.putExtra("nama", nama);
                startActivity(bahasa_indonesia);
                break;

            case (R.id.btn_pengetahuan_umum):
                final Intent pengetahuan_umum = new Intent(PilihUjianActivity.this, SoalActivity.class);
                pengetahuan_umum.putExtra("sim", 3);
                pengetahuan_umum.putExtra("nama", nama);
                startActivity(pengetahuan_umum);
                break;
        }
    }
}
