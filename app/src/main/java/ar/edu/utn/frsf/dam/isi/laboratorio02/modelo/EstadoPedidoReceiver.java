package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static final String EVENTO_ACEPTADO="ar.edu.utn.dam.isi.laboratorio02.ESTADO_ACEPTADO";
    public static final String EVENTO_CANCELADO="ar.edu.utn.dam.isi.laboratorio02.ESTADO_CANCELADO";
    public static final String EVENTO_PREPARACION="ar.edu.utn.dam.isi.laboratorio02.ESTADO_EN_PREPARACION";
    public static final String EVENTO_LISTO="ar.edu.utn.dam.isi.laboratorio02.ESTADO_LISTO";



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        PedidoRepository Pedidos= new PedidoRepository();
        int idPedido=  intent.getExtras().getInt("idPedido");
        Pedido p = Pedidos.buscarPorId(idPedido);

        Toast.makeText(context,"El pedido para "+p.getMailContacto()+" a cambiado de estado a "+p.getEstado().toString(),Toast.LENGTH_LONG).show();


    }
}
