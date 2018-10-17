package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.EstadoPedidoReceiver;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class AltaPedidosActivity extends AppCompatActivity {


    private EditText edtPedidoCorreo;
    private RadioGroup optedidoModoEntrega;
    private RadioButton optPedidoRetira;
    private RadioButton optPedidoEnviar;
    private EditText edtPedidoDireccion;
    private EditText edtPedidoHoraEntrega;
    private ListView lstPedidoItems;
    private Button btnPedidoAddProducto;
    private Button btnPedidoQuitarProducto;
    private TextView lblTotalPedido;
    private Button btnPedidoHacerPedido;
    private Button btnPedidoVolver;
    static final int codigoNuevoPedido = 1;

    private String esPedido;
    private ArrayAdapter<PedidoDetalle> adapterPedidos;

    Pedido unPedido;
    PedidoRepository repositorioPedido;
    ProductoRepository repositorioProducto;
    PedidoDetalle unPedidoDetalle;
    int detalleSelect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_pedidos);
        edtPedidoCorreo = findViewById(R.id.edtPedidoCorreo);
        optedidoModoEntrega = findViewById(R.id.optPedidoModoEntrega);
        optPedidoRetira = findViewById(R.id.optPedidoRetirar);
        optPedidoEnviar = findViewById(R.id.optPedidoEnviar);
        optPedidoEnviar.setChecked(true);
        edtPedidoDireccion = findViewById(R.id.edtPedidoDireccion);
        edtPedidoHoraEntrega = findViewById(R.id.edtPedidoHoraEntrega);
        lstPedidoItems = findViewById(R.id.lstPedidoItems);
        lstPedidoItems.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        btnPedidoAddProducto = findViewById(R.id.btnPedidoAddProducto);
        btnPedidoQuitarProducto = findViewById(R.id.btnPedidoQuitarProducto);
        lblTotalPedido = findViewById(R.id.lblTotalPedido);


        btnPedidoHacerPedido = findViewById(R.id.btnPedidoHacerPedido);
        btnPedidoVolver = findViewById(R.id.btnPedidoVolver);

        /**************************************************
        Agarramos un pedido que viene por parametros*/

        Intent i1= getIntent();
        int idPedido = 0;
        if(i1.getExtras()!=null){
            idPedido = i1.getExtras().getInt("idPedidoSeleccionado");
        }
        if(idPedido>0){
            unPedido = repositorioPedido.buscarPorId(idPedido);
            edtPedidoCorreo.setText(unPedido.getMailContacto());
            edtPedidoDireccion.setText(unPedido.getDireccionEnvio());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            edtPedidoHoraEntrega.setText(sdf.format(unPedido.getFecha()));
            optPedidoEnviar.setChecked(!unPedido.getRetirar());
            optPedidoRetira.setChecked(unPedido.getRetirar());
        }else {
            unPedido = new Pedido();
        }


        /**************************************************
         Agarramos un pedido que viene por parametros*/

        //unPedido = new Pedido();


        repositorioPedido = new PedidoRepository();
        repositorioProducto = new ProductoRepository();


        adapterPedidos = new ArrayAdapter<PedidoDetalle>(this,
                android.R.layout.simple_list_item_single_choice, unPedido.getDetalle());
        lstPedidoItems.setAdapter(adapterPedidos);


        //oyentes
        optedidoModoEntrega.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (optPedidoRetira.isChecked()) edtPedidoDireccion.setEnabled(false);
                else edtPedidoDireccion.setEnabled(true);
            }
        });



        btnPedidoAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esPedido="1";
                Intent i = new Intent(AltaPedidosActivity.this, ProductosActivity.class);
                i.putExtra("NUEVO_PEDIDO",esPedido);
                startActivityForResult(i,codigoNuevoPedido);


            }
        });


        /*
        inicializacion de la clase broadcast receiver
        */
        final BroadcastReceiver estadoReceiver = new EstadoPedidoReceiver();




        btnPedidoHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] horaIngresada = edtPedidoHoraEntrega.getText().toString().split(":");
                GregorianCalendar hora = new GregorianCalendar();
                int valorHora = Integer.valueOf(horaIngresada[0]);
                int valorMinuto = Integer.valueOf(horaIngresada[1]);
                if(valorHora<0 || valorHora>23){
                    Toast.makeText(AltaPedidosActivity.this,
                            "La hora ingresada ("+valorHora+" es incorrecta",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(valorMinuto <0 || valorMinuto >59){
                    Toast.makeText(AltaPedidosActivity.this,
                            "Los minutos ("+valorMinuto+" son incorrectos",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                hora.set(Calendar.HOUR_OF_DAY,valorHora);
                hora.set(Calendar.MINUTE,valorMinuto);
                hora.set(Calendar.SECOND,Integer.valueOf(0));
                unPedido.setFecha(hora.getTime());

                String incompleto="";

                if (edtPedidoCorreo.getText().length()==0) incompleto = "Ingrese Correo";

                if (optPedidoEnviar.isChecked() && edtPedidoDireccion.getText().length()==0) incompleto = "Ingrese Dirección";


                if (incompleto !=""){
                    Toast.makeText(AltaPedidosActivity.this,incompleto,
                            Toast.LENGTH_LONG).show();
                    }else{
                        unPedido.setMailContacto(edtPedidoCorreo.getText().toString());
                        unPedido.setDireccionEnvio(edtPedidoDireccion.getText().toString());
                        unPedido.setEstado(Pedido.Estado.REALIZADO);

                        if (optPedidoEnviar.isChecked()){
                            unPedido.setRetirar(false);
                        }else unPedido.setRetirar(true);


                        repositorioPedido.guardarPedido(unPedido);
                    Log.d("AltaPedidoActivity","Pedido "+unPedido.toString());
                       // unPedido=new Pedido();
                        Log.d("APP_LAB02","Pedido "+unPedido.toString());


                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.currentThread().sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Intent br = new Intent();
                            br.putExtra("idPedido", unPedido.getId());
                            br.setAction(((EstadoPedidoReceiver) estadoReceiver).EVENTO_ACEPTADO);
                            sendBroadcast(br);

                        // buscar pedidos no aceptados y aceptarlos automáticamente
                            List<Pedido> lista = repositorioPedido.getLista();
                            for(Pedido p:lista){
                                if(p.getEstado().equals(Pedido.Estado.REALIZADO))
                                    p.setEstado(Pedido.Estado.ACEPTADO);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AltaPedidosActivity.this,"Informacion de pedidos actualizada!",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    };
                    Thread unHilo = new Thread(r);
                    unHilo.start();




                        Intent i = new Intent(AltaPedidosActivity.this, HistorialPedidooActiity.class);
                        startActivity(i);
                    }



            }
        });

        btnPedidoVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AltaPedidosActivity.this, MainActivity.class);
                startActivity(i);


            }
        });

        btnPedidoQuitarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unPedido.quitarDetalle(unPedidoDetalle);
                adapterPedidos.notifyDataSetChanged();
                lblTotalPedido.setText("Total del Pedido: $"+String.valueOf(unPedido.total()));


            }
        });

        lstPedidoItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                unPedidoDetalle = (PedidoDetalle) adapterView.getItemAtPosition(position);
                detalleSelect= position;
                lstPedidoItems.setSelection(position);
                                                }
                                            }
        );



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         if( resultCode== Activity.RESULT_OK){
            if(requestCode==codigoNuevoPedido){
                int cantProducto = data.getExtras().getInt("cantidad");
                int idProducto=  data.getExtras().getInt("idProducto");


                Log.d("idProducto=","idProducto="+idProducto);
                Log.d("cantProducto=","cantProducto="+cantProducto);

               Producto unProducto = repositorioProducto.buscarPorId(idProducto);
               PedidoDetalle pedDta = new PedidoDetalle(cantProducto,unProducto);


                pedDta.setPedido(unPedido);

                Log.d("Cantidad pedidos=","cant="+unPedido.getDetalle().size());

               adapterPedidos.notifyDataSetChanged();

                lblTotalPedido.setText("Total del Pedido: $"+String.valueOf(unPedido.total()));

            }
        }
    }


}
