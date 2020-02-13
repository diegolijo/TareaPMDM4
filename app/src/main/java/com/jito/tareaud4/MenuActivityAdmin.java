package com.jito.tareaud4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.jito.tareaud4.dummy.datos.BaseDatos;
import com.jito.tareaud4.dummy.datos.Usuario;

public class MenuActivityAdmin extends AppCompatActivity {
    public static String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        Toolbar Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.menu);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("usuario");

        // abrir bd
        BaseDatos db = Room.databaseBuilder(getApplicationContext(),
                BaseDatos.class, "tareaud4").allowMainThreadQueries().build(); //
        //buscar usuario
        Usuario usuarioBD = db.Dao().selectUsuario(usuario);


        TextView TV_nombre = findViewById(R.id.TextView_nombre);
        TV_nombre.setText(R.string.Benvido + usuarioBD.nombre + " " + usuarioBD.apellidos);

        //pintamos el imageview con la foto (ruta guardada en la BD)
        Bitmap bitmap = BitmapFactory.decodeFile(usuarioBD.foto);
        if (bitmap != null) {
            ImageView imgeview = findViewById(R.id.view_foto);
            imgeview.setImageBitmap(bitmap);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuadmin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.menu_modificarReistro:
                clickModificarRegistro();
                return true;
            case R.id.menu_pedido_tamite:
                clickverPedidoMenu();
                return true;
            case R.id.menu_ver_aceptados:
                clickveracAptadossMenu();
                return true;
            case R.id.menu_ver_rechazados:
                clickverRechazadosMenu();
                return true;
            case R.id.menu_salir:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    public void verPedidosTramite(View view) {
        clickverPedidoMenu();
    }


    public void verPedidosAceptados(View view) {

        clickveracAptadossMenu();
    }

    public void verPedidosRechazados(View view) {
        clickverRechazadosMenu();
    }

    public void ModificarDatos(View view) {

        clickModificarRegistro();
    }


    public void salir(View view) {
        finish();
    }


    private void clickverRechazadosMenu() {
        Intent intent = new Intent(this, VerPedidosActivity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("estado", "rechazado");
        intent.putExtra("admin", true);
        intent.putExtra("titulo", "Pedidos rexeitados");
        startActivity(intent);
    }


    private void clickveracAptadossMenu() {
        Intent intent = new Intent(this, VerPedidosActivity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("estado", "aceptado");
        intent.putExtra("admin", true);
        intent.putExtra("titulo", "Pedidos aceptados");
        startActivity(intent);
    }


    private void clickverPedidoMenu() {
        Intent intent = new Intent(this, VerPedidosActivity.class);
        intent.putExtra("admin", true);
        intent.putExtra("usuario", usuario);
        intent.putExtra("estado", "");
        intent.putExtra("titulo", "Pedidos en tramite");
        startActivity(intent);
    }


    private void clickModificarRegistro() {

        Intent intent = new Intent(this, ModificarRegistroActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);

    }



}
