package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RepositorioResto;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.CategoriaRest;

public class CategoriaActivity extends AppCompatActivity {
    private EditText textoCat;
    private Button btnCrear;
    private Button btnMenu;
    private CategoriaDao catDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        textoCat = (EditText) findViewById(R.id.txtNombreCategoria);
        btnCrear = (Button) findViewById(R.id.btnCrearCategoria);

        catDao = RepositorioResto.getInstance(this).getCategoriaDao();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textoCat.getText().toString().length()>0) {

                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            Categoria categoria = new Categoria();
                            categoria.setNombre(textoCat.getText().toString());
                            catDao.agregarCat(categoria);
                            textoCat.setText("");
                        }
                    };
                    Thread t = new Thread(r);
                    t.start();

                }else{
                    Toast.makeText(CategoriaActivity.this,"Introduzca un Nombre de Categoria",Toast.LENGTH_LONG).show();
                }





/*
                final CategoriaRest categoriaRest = new CategoriaRest();
                final Categoria categoria = new Categoria();

                categoria.setNombre(textoCat.getText().toString());
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Boolean creadoOK = false;
                        try {
                            categoriaRest.crearCategoria(categoria);
                            creadoOK = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("################## ERROR 23 ########################");
                        }

                        final Boolean finalCreadoOK = creadoOK;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalCreadoOK){
                                    Toast.makeText(CategoriaActivity.this,"La Categoria fue Creada",Toast.LENGTH_LONG).show();
                                    textoCat.setText("");
                                }else{
                                    Toast.makeText(CategoriaActivity.this,"La Categoria no se pudo crear",Toast.LENGTH_LONG).show();
                                     }

                            }
                        });
                    }
                };
                Thread unHilo = new Thread(r);
                unHilo.start();

*/
            }
        });
        btnMenu= (Button) findViewById(R.id.btnCategoriaVolver);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoriaActivity.this,
                        MainActivity.class);
                startActivity(i);
            }
        });
    }
}