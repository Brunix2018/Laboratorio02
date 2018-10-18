package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.app.IntentService;

import android.content.Intent;

import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.AltaPedidosActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;

public class PrepararPedidoService extends IntentService {
    public PrepararPedidoService() {
        super("PrepararPedidoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                PedidoRepository repositorioPedido = new PedidoRepository();


                // buscar pedidos  aceptados y pasarlos a preparacion
                List<Pedido> lista = repositorioPedido.getLista();
                for(Pedido p:lista){
                    if(p.getEstado().equals(Pedido.Estado.ACEPTADO))
                        p.setEstado(Pedido.Estado.EN_PREPARACION);
                    Intent br = new Intent();
                    br.putExtra("idPedido", p.getId());
                    br.setAction(EstadoPedidoReceiver.EVENTO_PREPARACION);
                    sendBroadcast(br);
                    System.out.println("se proceso el pedido: "+p.getMailContacto());
                }

            }
        };
        Thread unHilo = new Thread(r);
        unHilo.start();








    }
}
