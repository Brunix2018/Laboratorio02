package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;
    private String esPedido;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AltaPedidosActivity.class);
                startActivity(i);

                //esPedido="1";
                //Intent i = new Intent(MainActivity.this, ProductosActivity.class);
                //i.putExtra("NUEVO_PEDIDO",esPedido);
                //startActivityForResult(i,codigoNuevoPedido);


            }
        });


        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);
        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esPedido="0";
                Intent i = new Intent(MainActivity.this, ProductosActivity.class);
                i.putExtra("NUEVO_PEDIDO",esPedido);
                startActivity(i);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HistorialPedidooActiity.class);
                startActivity(i);
            }
        });

    }


}
