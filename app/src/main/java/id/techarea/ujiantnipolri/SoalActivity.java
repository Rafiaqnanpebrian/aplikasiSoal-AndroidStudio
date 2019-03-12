package id.techarea.ujiantnipolri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SoalActivity extends AppCompatActivity {

    Button btn_finish_soal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_soal);

        btn_finish_soal = (Button) findViewById(R.id.btn_finish_soal);
        btn_finish_soal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent finish_soal = new Intent(SoalActivity.this, HasilActivity.class);
                finish_soal.putExtras(getIntent().getExtras());
                startActivity(finish_soal);
                finish();
            }
        });
    }
}
