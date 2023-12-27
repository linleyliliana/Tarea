package com.example.apirestful;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.apirestful.WebServices.Asynchtask;
import com.example.apirestful.WebServices.WebService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickLogin(View view){
        //Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        TextInputLayout txtusuario = findViewById(R.id.txtUser);
        TextInputLayout txtclave = findViewById(R.id.txtPass);
        WebService ws= new WebService(
                "https://revistas.uteq.edu.ec/ws/login.php?usr="
                        + txtusuario.getEditText().getText().toString() + "&pass=" +
                        txtclave.getEditText().getText().toString(),
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");
}
    @Override
    public void processFinish(String result) throws JSONException {
        Intent intent = new Intent(this, MainActivity2.class);
        TextView txtRespuesta = findViewById(R.id.txtResp);
        if (result.equals("Login Correcto!")) {
            startActivity(intent);
        }
        else{
            txtRespuesta.setText(result);
        }
    }
}
