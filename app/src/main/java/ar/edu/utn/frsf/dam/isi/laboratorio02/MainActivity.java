package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;
    private Intent pantallaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pantallaProductos=  new Intent(MainActivity.this, ProductosActivity.class);

        btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent i = new Intent(MainActivity.this, ProductosActivity.class);
                // startActivity(i);
                pantallaProductos.putExtra("NUEVO_PEDIDO",1);
                startActivity(pantallaProductos);
            }
        });


        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);
        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Intent i = new Intent(MainActivity.this, ProductosActivity.class);
               // startActivity(i);
                pantallaProductos.putExtra("NUEVO_PEDIDO",0);
                startActivity(pantallaProductos);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                startActivity(i);
            }
        });

    }
}
