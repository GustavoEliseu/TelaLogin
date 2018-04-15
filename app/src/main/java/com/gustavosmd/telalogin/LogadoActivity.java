package com.gustavosmd.telalogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LogadoActivity extends AppCompatActivity {
    TextView nomeUsuarioTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logado);
        nomeUsuarioTxt=(TextView) findViewById(R.id.nomeUsuarioTxt);

        Intent meuIntent= this.getIntent();
        User usuario = (User)meuIntent.getParcelableExtra("meuUser");
        nomeUsuarioTxt.setText(usuario.getNome());
    }
}
