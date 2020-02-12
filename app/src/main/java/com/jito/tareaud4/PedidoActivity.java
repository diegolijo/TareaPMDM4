package com.jito.tareaud4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class PedidoActivity extends AppCompatActivity {


    String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        Toolbar Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.novoPedido);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("usuario");

        seleccionar_categorias();
    }


    public void seleccionar_categorias() {

        final Spinner SP_categoria = findViewById(R.id.SP_categoria);
        final Spinner SP_producto = findViewById(R.id.SP_productos);
        final String[] informatica = getResources().getStringArray(R.array.Informática);
        final String[] electronica = getResources().getStringArray(R.array.Electrónica);
        final String[] mobiles = getResources().getStringArray(R.array.Móbiles);


        SP_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (SP_categoria.getSelectedItemPosition() == 0) {
                    SP_producto.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, informatica));
                } else if (SP_categoria.getSelectedItemPosition() == 1) {
                    SP_producto.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, electronica));
                } else if (SP_categoria.getSelectedItemPosition() == 2) {
                    SP_producto.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mobiles));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    public void siguiente(View view) {
        Spinner SP_categoria = findViewById(R.id.SP_categoria);
        Spinner SP_producto = findViewById(R.id.SP_productos);
        Spinner SP_cantidade = findViewById(R.id.SP_cantidade);

        Intent intent = new Intent(this, Direccion_Activity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("categoria", SP_categoria.getSelectedItem().toString());
        intent.putExtra("producto", SP_producto.getSelectedItem().toString());
        intent.putExtra("cantidade", SP_cantidade.getSelectedItem().toString());
        startActivity(intent);
        finish();
    }


}
