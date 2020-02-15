package com.jito.tareaud4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jito.tareaud4.dummy.datos.BaseDatos;
import com.jito.tareaud4.dummy.datos.Usuario;

public class MenuActivity extends AppCompatActivity {

    public static String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.menu);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("usuario");

        // abrir bd
        BaseDatos db = Room.databaseBuilder(getApplicationContext(), BaseDatos.class, "tareaud4").allowMainThreadQueries().build();

        //seleccionamos el usuario de BD
        Usuario usuarioBD = db.Dao().selectUsuario(usuario);

        //escribimos la etiqueta con el nombre del usuario
        TextView tvNombre = findViewById(R.id.textView_nome);
        tvNombre.setText( getString(R.string.Benvido)+ " " + usuarioBD.nombre+" "+usuarioBD.apellidos);


        //pintamos el imageview con la foto (ruta guardada en la BD)
        Bitmap bitmap = BitmapFactory.decodeFile(usuarioBD.foto);
        if (bitmap != null) {
            ImageView imgeview = findViewById(R.id.view_foto);
            imgeview.setImageBitmap(bitmap);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // abrir bd
        BaseDatos db = Room.databaseBuilder(getApplicationContext(),
                BaseDatos.class, "tareaud4").allowMainThreadQueries().build(); //
        //buscar usuario
        Usuario usuarioBD = db.Dao().selectUsuario(usuario);


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
        getMenuInflater().inflate(R.menu.menuuser, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.menu_facer_pedido:

                hacerPedido();
                return true;
            case R.id.menu_ver_pedidos:
                verPedidos();
                return true;
            case R.id.menu_ver_compras:
                verCompras();
                return true;
            case R.id.menu_modificarReistro:
               modificarDatos();
                return true;
            case R.id.menu_alir:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void hacerPedidoBoton(View view) {
        hacerPedido();
    }

    public void verPedidosBoton(View view) {
        verPedidos();
    }

    public void verComprasBoton(View view) {
        verCompras();
    }

    private void hacerPedido() {

        // abrir bd
        BaseDatos db = Room.databaseBuilder(getApplicationContext(),
                BaseDatos.class, "tareaud4").allowMainThreadQueries().build(); //
        Usuario usuarioBD = db.Dao().selectUsuario(usuario);

        Intent intent = new Intent(this, PedidoActivity.class);
        intent.putExtra("usuario", usuarioBD.usuario);
        startActivity(intent);
    }

    private void verPedidos() {
        Intent intent = new Intent(this, VerPedidosActivity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("estado", "");
        intent.putExtra("adminx", false);
        intent.putExtra("titulo", "Pedidos realizados");
        startActivity(intent);
    }

    private void verCompras() {
        Intent intent = new Intent(this, VerPedidosActivity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("estado", "aceptado");
        intent.putExtra("adminx", false);
        intent.putExtra("titulo", "Compras");
        startActivity(intent);
    }


    public void modificarDatosBoton(View view) {

        modificarDatos();
    }


    public void modificarDatos(){

        Intent intent = new Intent(this, ModificarRegistroActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);

    }



    public void salir(View view) {
        finish();
    }


}

