package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class ProductosActivity extends AppCompatActivity {


    private ProductoRepository listaProductos;
    private PedidoRepository listaPedidos;

    private Spinner cmbProductosCategoria;
    private ListView lstProductos;
    private EditText edtProdCantidad;
    private Button btnPrrAddPedido;
    private ArrayAdapter<Categoria> adapterCategoria;
    private ArrayAdapter<Producto> adapterProductos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        listaProductos = new ProductoRepository();
        listaPedidos = new PedidoRepository();
        cmbProductosCategoria = (Spinner) findViewById(R.id.cmbProductosCategoria);
        lstProductos = (ListView) findViewById(R.id.lstProducos);
        edtProdCantidad = (EditText) findViewById(R.id.edtProdCantidad);

        btnPrrAddPedido = (Button) findViewById(R.id.btnProdAddPedido);

        edtProdCantidad.setEnabled(false);
        btnPrrAddPedido.setEnabled(false);


        adapterCategoria = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listaProductos.getCategorias());
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbProductosCategoria.setAdapter(adapterCategoria);

        adapterProductos = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, listaProductos.buscarPorCategoria(adapterCategoria.getItem(0)));
        lstProductos.setAdapter(adapterProductos);

        String esPedido = getIntent().getStringExtra("NUEVO_PEDIDO");



        edtProdCantidad.setText(esPedido.toString());


        //Oyentes


        cmbProductosCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapterProductos.clear();
                ;
                adapterProductos.addAll(listaProductos.buscarPorCategoria(adapterCategoria.getItem(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
