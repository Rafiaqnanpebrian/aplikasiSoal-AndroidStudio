package id.techarea.ujiantnipolri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText nama;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        nama = (EditText) findViewById(R.id.name);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (nama.getText().toString().equals("") || nama.getText().toString().isEmpty() ){
            Toast.makeText(Login.this, "Nama Harus Diisi", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent j = new Intent(Login.this, Login.class);
            j.putExtra("nama", String.valueOf(nama.getText()));
            startActivity(j);
            finish();
        }
    }
}
