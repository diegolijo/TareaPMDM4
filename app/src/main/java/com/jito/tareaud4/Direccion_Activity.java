package com.jito.tareaud4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jito.tareaud4.dummy.datos.BaseDatos;
import com.jito.tareaud4.dummy.datos.Pedido;

import static android.app.PendingIntent.getActivity;

public class Direccion_Activity extends AppCompatActivity {

    String usuario;
    String categoria;
    String producto;
    int cantidade;
    TextView TVetiqueta;
    EditText ETrua;
    EditText ETcidade;
    EditText ETcp;
    String rua;
    String cidade;
    String cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_);
        try {
            getSupportActionBar().setTitle(R.string.direccion);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toolbar Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.direccion);


        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("usuario");
        categoria = extras.getString("categoria");
        producto = extras.getString("producto");
        cantidade = Integer.parseInt(extras.getString("cantidade"));

        TVetiqueta = findViewById(R.id.textView_info2);
    }






    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    public void finalizar(View view) {


        TVetiqueta.setText("");
        ETrua = findViewById(R.id.editText_direccion);
        ETcidade = findViewById(R.id.editText_cidade);
        ETcp = findViewById(R.id.editText_cp);
        rua = ETrua.getText().toString();
        cidade = ETcidade.getText().toString();
        cp = ETcp.getText().toString();

        if (rua.equals("") || cidade.equals("") || cp.equals("")) {
            TVetiqueta.setText(R.string.camposVacios);
        } else {


            //dialogo -> actualizar el campo estado en BD
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Aceptar ou rexeitar o pedido");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    guardarDatos();

                }
            });

            builder.setNegativeButton("Rexeitar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            AlertDialog dialogo = builder.create();
            dialogo.show();


        }
    }


    public void guardarDatos() {
        BaseDatos db = Room.databaseBuilder(getApplicationContext(),
                BaseDatos.class, "tareaud4").allowMainThreadQueries().build();

        Pedido pedido = new Pedido();
        pedido.categoria = categoria;
        pedido.cidade = cidade;
        pedido.cp = Integer.parseInt(cp);
        pedido.cantidade = cantidade;
        pedido.usuario = usuario;
        pedido.producto = producto;
        pedido.rua = rua;

        db.Dao().insertPedido(pedido);


        Toast toastregistrado = Toast.makeText(this, R.string.pedidoconfirmado, Toast.LENGTH_LONG);
        toastregistrado.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toastregistrado.show();


        finish();

    }
}
