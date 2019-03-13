package id.techarea.ujiantnipolri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class PilihUjianActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout btn_bahasa_inggris, btn_bahasa_indonesia, btn_pengetahuan_umum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pilih_ujian);

        btn_bahasa_inggris = (LinearLayout) findViewById(R.id.btn_bahasa_inggris);
        btn_bahasa_indonesia = (LinearLayout) findViewById(R.id.btn_bahasa_indonesia);
        btn_pengetahuan_umum = (LinearLayout) findViewById(R.id.btn_pengetahuan_umum);

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
                startActivity(bahasa_inggris);
                break;

            case (R.id.btn_bahasa_indonesia):
                final Intent bahasa_indonesia = new Intent(PilihUjianActivity.this, SoalActivity.class);
                bahasa_indonesia.putExtra("sim", 1);
                startActivity(bahasa_indonesia);
                break;

            case (R.id.btn_pengetahuan_umum):
                final Intent pengetahuan_umum = new Intent(PilihUjianActivity.this, SoalActivity.class);
                pengetahuan_umum.putExtra("sim", 3);
                startActivity(pengetahuan_umum);
                break;
        }
    }
}
