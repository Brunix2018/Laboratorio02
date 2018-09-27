package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoAdapter;

public class HistorialPedidooActiity extends AppCompatActivity {

    ListView lstHistoalPedidos;
    Button btnHistorialNuevo;
    Button btnHistorialMmenu;
    PedidoAdapter unAdapter;
    PedidoRepository unRepoPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidoo_actiity);

        lstHistoalPedidos = findViewById(R.id.lstHiorialPedidos);
        btnHistorialNuevo = findViewById(R.id.btnHistorialNuevo);
        btnHistorialMmenu = findViewById(R.id.btnHistorialMenu);
        unRepoPedido = new PedidoRepository();
        unAdapter= new PedidoAdapter(HistorialPedidooActiity.this,unRepoPedido.getLista());



        btnHistorialNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistorialPedidooActiity.this, AltaPedidosActivity.class);
                startActivity(i);
            }
        });


        btnHistorialMmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistorialPedidooActiity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }










}
