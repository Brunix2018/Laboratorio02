package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RepositorioResto;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RestClient;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.CategoriaRest;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionProductoActivity extends AppCompatActivity {
    private Button btnMenu;
    private Button btnGuardar;
    private Spinner comboCategorias;
   // private Spinner cmbProductosCategoria;
    private EditText nombreProducto;
    private EditText descProducto;
    private EditText precioProducto;
    private ToggleButton opcionNuevoBusqueda;
    private EditText idProductoBuscar;
    private Button btnBuscar;
    private Button btnBorrar;
    private Boolean flagActualizacion;
    private ArrayAdapter<Categoria> comboAdapter;
    //private ArrayAdapter<Categoria> adapterCategoria;
    private Categoria categoria;
    private CategoriaDao catDao;
    private ProductoDao proDao;
    private List<Categoria> listaCat;
    private List<Producto> listaProd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);
        flagActualizacion = false;
        opcionNuevoBusqueda = (ToggleButton)findViewById(R.id.abmProductoAltaNuevo);
        idProductoBuscar = (EditText)findViewById(R.id.abmProductoIdBuscar);
        nombreProducto = (EditText) findViewById(R.id.abmProductoNombre);
        descProducto = (EditText) findViewById(R.id.abmProductoDescripcion);
        precioProducto = (EditText) findViewById(R.id.abmProductoPrecio);
        comboCategorias = (Spinner) findViewById(R.id.abmProductoCategoria);
        btnMenu = (Button) findViewById(R.id.btnAbmProductoVolver);
        btnGuardar = (Button) findViewById(R.id.btnAbmProductoCrear);
        btnBuscar = (Button) findViewById(R.id.btnAbmProductoBuscar);
        btnBorrar= (Button) findViewById(R.id.btnAbmProductoBorrar);
        opcionNuevoBusqueda.setChecked(false);
        btnBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);
        idProductoBuscar.setEnabled(false);
        opcionNuevoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView,
                                        boolean isChecked) {
               flagActualizacion =isChecked;
               btnBuscar.setEnabled(isChecked);
               btnBorrar.setEnabled(isChecked);
               idProductoBuscar.setEnabled(isChecked);
           }
       });

        catDao = RepositorioResto.getInstance(this).getCategoriaDao();
        proDao = RepositorioResto.getInstance(this).getProductoDao();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                listaCat = catDao.getAll();
                comboAdapter = new ArrayAdapter<Categoria>(GestionProductoActivity.this,android.R.layout.simple_spinner_dropdown_item, listaCat);
                comboCategorias.setAdapter(comboAdapter);
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            comboCategorias.setSelection(0);
                            categoria = (Categoria) comboCategorias.getSelectedItem();
                            comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    categoria = (Categoria) parent.getItemAtPosition(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {}
                            });
                        }
                    });
                }

        };
        Thread t = new Thread(r);
        t.start();



        /*
        Runnable r = new Runnable() {
            @Override
            public void run() {
                CategoriaRest catRest = new CategoriaRest();
                Categoria[] cats = new Categoria[0];
                try {
                    cats = catRest.listarTodas().toArray(new Categoria[0]);
                } catch (Exception e) {
                    System.out.println(" ############  ERROR hilo get categoria en producto activity");
                    e.printStackTrace();
                }
                System.out.println(" ############  CATs ###################");
                System.out.println(cats);

                final Categoria[] finalCats = cats;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        comboAdapter = new ArrayAdapter<Categoria>(GestionProductoActivity.this,android.R.layout.simple_spinner_dropdown_item, finalCats);
                        comboCategorias.setAdapter(comboAdapter);
                        comboCategorias.setSelection(0);
                        categoria = (Categoria) comboCategorias.getSelectedItem();
                        comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                categoria = (Categoria) parent.getItemAtPosition(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });
                    }
                });

            }
        };
        Thread hiloCargarCombo = new Thread(r);
        hiloCargarCombo.start();





        /*###########################################*/

/*
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Producto p = new Producto();

                String incompleto="";

                if (descProducto.getText().length()==0) incompleto = "Ingrese Descripción";
                if (nombreProducto.getText().length()==0) incompleto = "Ingrese Nombre";
                if (precioProducto.getText().length()==0) incompleto = "Ingrese Precio";


                if (incompleto ==""){

                    p.setCategoria(categoria);
                    p.setDescripcion(descProducto.getText().toString());
                    p.setNombre(nombreProducto.getText().toString());
                    p.setPrecio(Double.parseDouble(precioProducto.getText().toString()));

                    ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);

                    Call<Producto> altaCall= clienteRest.crearProducto(p);

                    altaCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call,
                                               Response<Producto> resp) {

                            switch (resp.code()) {
                                case 201:
                                    descProducto.setText("");
                                    nombreProducto.setText("");
                                    precioProducto.setText("");
                                    comboCategorias.setSelection(0);
                                    Toast.makeText(GestionProductoActivity.this, "CREATED", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(GestionProductoActivity.this, "Error", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                            Log.e("Error", t.toString());
                        }
                    });

                }else{Toast.makeText(GestionProductoActivity.this,incompleto,Toast.LENGTH_LONG).show();}
            }
        });
*/
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String incompleto="";

                if (descProducto.getText().length()==0) incompleto = "Ingrese Descripción";
                if (nombreProducto.getText().length()==0) incompleto = "Ingrese Nombre";
                if (precioProducto.getText().length()==0) incompleto = "Ingrese Precio";


                if (incompleto ==""){

                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                Producto p = new Producto();
                                p.setCategoria(categoria);
                                p.setDescripcion(descProducto.getText().toString());
                                p.setNombre(nombreProducto.getText().toString());
                                p.setPrecio(Double.parseDouble(precioProducto.getText().toString()));
                                proDao.agregarProd(p);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        descProducto.setText("");
                                        nombreProducto.setText("");
                                        precioProducto.setText("");
                                        comboCategorias.setSelection(0);
                                    }
                                });

                            }
                        };
                        Thread t = new Thread(r);
                        t.start();




                }else{Toast.makeText(GestionProductoActivity.this,incompleto,Toast.LENGTH_LONG).show();}
            }
        });

   /*     btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer id = Integer.parseInt(idProductoBuscar.getText().toString());
                System.out.println(idProductoBuscar.getText().toString());
                System.out.println(id);
                ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);

                Call<Producto> altaCall= clienteRest.buscarProductoPorId(id);

                altaCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call,
                                           Response<Producto> resp) {
                        // procesar la respuesta
                        switch (resp.code()) {
                            case 200:
                                Producto p = new Producto();
                                p = resp.body();
                                descProducto.setText(p.getDescripcion());
                                nombreProducto.setText(p.getNombre());
                                precioProducto.setText(p.getPrecio().toString());
                                comboCategorias.setSelection(comboAdapter.getPosition(p.getCategoria()));
                                Toast.makeText(GestionProductoActivity.this, "OK", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(GestionProductoActivity.this, "Error", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                        Log.e("Error", t.toString());
                    }
                });
            }
        });*/

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (idProductoBuscar.getText().length()==0) {
                    Toast.makeText(GestionProductoActivity.this,"Ingrese un ID de Producto",Toast.LENGTH_LONG).show();}
                else{
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            Integer id = Integer.parseInt(idProductoBuscar.getText().toString());

                            listaProd = proDao.buscarPorIdProd(id);

                            if (listaProd.isEmpty()) {
                                Toast.makeText(GestionProductoActivity.this, "Producto no encontrado", Toast.LENGTH_LONG).show();
                            } else {
                                final Producto p = listaProd.get(0);
                                System.out.println(p.getNombre());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        descProducto.setText(p.getDescripcion());
                                        nombreProducto.setText(p.getNombre());
                                        precioProducto.setText(p.getPrecio().toString());
                                        comboCategorias.setSelection(comboAdapter.getPosition(p.getCategoria()));
                                    }
                                });
                            }

                        }
                    };
                    Thread t = new Thread(r);
                    t.start();
                 }
            }
        });

      /*  btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer id = Integer.parseInt(idProductoBuscar.getText().toString());
                System.out.println(idProductoBuscar.getText().toString());
                System.out.println(id);
                ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);

                Call<Producto> altaCall= clienteRest.borrar(id);

                altaCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call,
                                           Response<Producto> resp) {
                        // procesar la respuesta
                        switch (resp.code()) {
                            case 200:
                                descProducto.setText("");
                                nombreProducto.setText("");
                                precioProducto.setText("");
                                comboCategorias.setSelection(0);
                                Toast.makeText(GestionProductoActivity.this, "OK", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(GestionProductoActivity.this, "Error", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                        Log.e("Error", t.toString());
                    }
                });
            }
        });*/

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (idProductoBuscar.getText().length()==0) {
                    Toast.makeText(GestionProductoActivity.this,"Ingrese un ID de Producto",Toast.LENGTH_LONG).show();}
                else{
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            Integer id = Integer.parseInt(idProductoBuscar.getText().toString());

                            listaProd = proDao.buscarPorIdProd(id);

                            if (listaProd.isEmpty()) {
                                Toast.makeText(GestionProductoActivity.this, "Producto no encontrado", Toast.LENGTH_LONG).show();
                            } else {
                                final Producto p = listaProd.get(0);
                                
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        descProducto.setText(p.getDescripcion());
                                        nombreProducto.setText(p.getNombre());
                                        precioProducto.setText(p.getPrecio().toString());
                                        comboCategorias.setSelection(comboAdapter.getPosition(p.getCategoria()));
                                    }
                                });
                                proDao.borrarProd(p);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        descProducto.setText("");
                                        nombreProducto.setText("");
                                        precioProducto.setText("");
                                        comboCategorias.setSelection(0);
                                    }
                                });
                            }

                        }
                    };
                    Thread t = new Thread(r);
                    t.start();
                }
            }
        });


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(GestionProductoActivity.this, MainActivity.class);
                startActivity(i);


            }
        });


    }
}



