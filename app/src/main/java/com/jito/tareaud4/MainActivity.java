package com.jito.tareaud4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.jito.tareaud4.dummy.datos.BaseDatos;
import com.jito.tareaud4.dummy.datos.Usuario;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getSupportActionBar().setTitle(R.string.login);


        Toolbar Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.login);


    }


    //toolbar back button click event
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return super.onSupportNavigateUp();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_login:
                clickLoginMenu(item);
                return true;
            case R.id.action_registro:
                clickRexistroMenu(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    public void clickLoginBoton(View view) {
        login();
    }

    public void clickLoginMenu(MenuItem item) {
        login();
    }


    public void login() {

        //capturar objetos
        TextView TVetiqueta = findViewById(R.id.textView_info);
        EditText ETuser = findViewById(R.id.editText_usuario);
        EditText ETpass = findViewById(R.id.editText_Pass);
        String contraseña = ETpass.getText().toString();
        String usuario = ETuser.getText().toString();

        TVetiqueta.setText("");

        if (contraseña.equals("") || usuario.equals("")) {
            TVetiqueta.setText(R.string.camposVacios);
        } else {
            //abrir base de datos
            BaseDatos db = Room.databaseBuilder(getApplicationContext(),
                    BaseDatos.class, "tareaud4").allowMainThreadQueries().build(); //
            //select  usuario
            Usuario usuarioBd = db.Dao().selectUsuario(usuario);

            //  existe usuario  && combrobar contraseña
            if (usuarioBd == null) {
                TVetiqueta.setText(R.string.usuarioNoRegistrado);
            } else {
                if (!usuarioBd.contraseña.equals(contraseña)) {
                    TVetiqueta.setText(R.string.contraseñaIncorrecta);
                } else {
                    if (usuarioBd.admin) {

                        TVetiqueta.setText("");
                        Intent intent = new Intent(this, MenuActivityAdmin.class);
                        intent.putExtra("usuario", usuarioBd.usuario);
                        startActivity(intent);

                    } else {

                        TVetiqueta.setText("");
                        Intent intent = new Intent(this, MenuActivity.class);
                        intent.putExtra("usuario", usuarioBd.usuario);
                        startActivity(intent);

                    }

                }
            }
        }

    }


    public void clickRexistroBoton(View view) {

        registro();
    }

    public void clickRexistroMenu(MenuItem item) {

        registro();
    }


    private void registro() {

        TextView TVetiqueta = findViewById(R.id.textView_info);
        TVetiqueta.setText("");
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }


}