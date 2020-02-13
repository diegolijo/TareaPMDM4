package com.jito.tareaud4;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.room.Room;

import com.jito.tareaud4.dummy.datos.BaseDatos;
import com.jito.tareaud4.dummy.datos.Usuario;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModificarRegistroActivity extends AppCompatActivity {

    public static String user;
    private File ruta, arquivo;
    private static final int REQUEST_CODE_GRAVACION_OK = 1;
    private String nomeFoto;
    private String rutaFoto;
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_registro);


        Toolbar Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.modificarDatos);


        Bundle extras = getIntent().getExtras();
        user = extras.getString("usuario");

        //Abrimos usuario BD, seleccio0namos la ruta de la foto, pintamos ImageView y rellenamos los campos
        BaseDatos db = Room.databaseBuilder(getApplicationContext(), BaseDatos.class, "tareaud4").allowMainThreadQueries().build(); //
        Usuario usuario = db.Dao().selectUsuario(user);

        Bitmap bitmap = BitmapFactory.decodeFile(usuario.foto);
        ImageView imgeview = findViewById(R.id.imageView);
        imgeview.setImageBitmap(bitmap);

        EditText ETnome = findViewById(R.id.editText_nombre);
        EditText ETapellidos = findViewById(R.id.editText_apellidos);
        EditText ETemail = findViewById(R.id.editText_email);
        EditText Epass1 = findViewById(R.id.editText_contraseña);
        EditText Epass2 = findViewById(R.id.editText_contraseña2);

        ETnome.setText(usuario.nombre);
        ETapellidos.setText(usuario.apellidos);
        ETemail.setText(usuario.email);
        Epass1.setText(usuario.contraseña);
        Epass2.setText(usuario.contraseña);


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.modificar_registro, menu);
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
            case R.id.menu_salir:
                finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private void clickModificarRegistro() {
        BaseDatos db = Room.databaseBuilder(getApplicationContext(), BaseDatos.class, "tareaud4").allowMainThreadQueries().build(); //


        EditText ETnome = findViewById(R.id.editText_nombre);
        EditText ETapellidos = findViewById(R.id.editText_apellidos);
        EditText ETemail = findViewById(R.id.editText_email);
        EditText ETpass = findViewById(R.id.editText_contraseña);
        EditText ETpass2 = findViewById(R.id.editText_contraseña2);
        TextView TVaviso = findViewById(R.id.textView_info);


        String nome = ETnome.getText().toString();
        String apellidos = ETapellidos.getText().toString();
        String email = ETemail.getText().toString();
        String pass = ETpass.getText().toString();
        String pass2 = ETpass2.getText().toString();


        if (nome.equals("") || apellidos.equals("") || email.equals("")) {
            TVaviso.setText(R.string.camposVacios);
        } else if (!email.contains("@") || !email.contains(".")) {
            TVaviso.setText(R.string.emailIncorrecto);
        } else {


            //crompobamos  contraseñas son iguales
            if (!pass.equals(pass2)) {
                TVaviso.setText(R.string.ContraseñaDiferente);
            } else {
                TVaviso.setText("");

                Usuario usuario = new Usuario();
                usuario.usuario = user;
                usuario.nombre = nome;
                usuario.apellidos = apellidos;
                usuario.email = email;
                usuario.contraseña = pass;


                if (rutaFoto == null) {
                    rutaFoto = usuario.foto;
                }

                TVaviso.setText("");
                db.Dao().updateUsuario(usuario.usuario, usuario.nombre, usuario.apellidos, usuario.contraseña, rutaFoto, usuario.email);

                Usuario usuarioNuevo = db.Dao().selectUsuario(usuario.usuario);

                Toast toastregistrado = Toast.makeText(this, String.format("Usuario modificado\n%s\n%s\n%s\n%s", usuarioNuevo.usuario, usuarioNuevo.email, usuarioNuevo.nombre, usuarioNuevo.apellidos), Toast.LENGTH_LONG);
                toastregistrado.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastregistrado.show();

                finish();


            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void clickModificar(View view) {

        clickModificarRegistro();

    }

    public void borrarCampo(View view) {

        TextView campo = findViewById(R.id.editText_nombre);
        campo.setText("");
        campo.requestFocus();

    }

    //abrimos la activity del SO que gestiona camara
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ClicFoto(View view) {

        nomeFoto = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


        //conseguimos la  foto creamos File
        ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        arquivo = new File(ruta, nomeFoto);

        // pedimos permiso CAMARA
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }


    //respuesta del permiso para acceder a la CAMARA
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //indicamos donde se guardara la foto
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivo));

                //solucianar la excepcion   StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                //                          StrictMode.setVmPolicy(builder.build());
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                startActivityForResult(intent, REQUEST_CODE_GRAVACION_OK);


            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }


    //recogemos la imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {


            ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            arquivo = new File(ruta, nomeFoto);
            if (arquivo.exists()) {

                rutaFoto = arquivo.getAbsolutePath();

                Bitmap bitmap = BitmapFactory.decodeFile(rutaFoto);
                ImageView imgeview = findViewById(R.id.imageView);

                imgeview.setImageBitmap(bitmap);

            } else {
                return;
            }


        }
    }


}
