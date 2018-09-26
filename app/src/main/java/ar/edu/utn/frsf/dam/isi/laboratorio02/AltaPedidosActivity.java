package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
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
        btnPedidoAddProducto = findViewById(R.id.btnPedidoAddProducto);
        btnPedidoQuitarProducto = findViewById(R.id.btnPedidoQuitarProducto);
        lblTotalPedido = findViewById(R.id.lblTotalPedido);


        btnPedidoHacerPedido = findViewById(R.id.btnPedidoHacerPedido);
        btnPedidoVolver = findViewById(R.id.btnPedidoVolver);

        unPedido = new Pedido();
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

                        repositorioPedido.guardarPedido(unPedido);
                        unPedido=new Pedido();
                        Log.d("APP_LAB02","Pedido "+unPedido.toString());

                        Intent i = new Intent(AltaPedidosActivity.this, HistorialPedidooActiity.class);
                        startActivity(i);
                    }



            }
        });






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
               PedidoDetalle unPedidoDetalle = new PedidoDetalle(cantProducto,unProducto);


               unPedidoDetalle.setPedido(unPedido);

                Log.d("Cantidad pedidos=","cant="+unPedido.getDetalle().size());

               adapterPedidos.notifyDataSetChanged();

               double costoTotal=0.0;

               for (int i = 0; i < unPedido.getDetalle().size(); i++){
                   PedidoDetalle pedDet = unPedido.getDetalle().get(i);
                   costoTotal= pedDet.getProducto().getPrecio()*cantProducto+costoTotal;
                   Log.d("costoTotal=","costoTotal="+costoTotal);
                }

                lblTotalPedido.setText("Total del Pedido: $"+String.valueOf(costoTotal));

            }
        }
    }


}
