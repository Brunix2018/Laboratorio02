package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.firebase.iid.*;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ar.edu.utn.frsf.dam.isi.laboratorio02.AltaPedidosActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.Estado;

public class RestoMessagingService extends FirebaseMessagingService {



    private Integer id;

    Pedido unPedido;
    Estado unEstado;
    PedidoRepository repositorioPedido;



    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Map<String, String> remoteData = remoteMessage.getData();

        if (remoteMessage.getData().size() > 0) {


            System.out.print("######################################################ESTADO");
            System.out.print(remoteMessage.getData());

            id = Integer.parseInt(remoteMessage.getData().get("ID_PEDIDO"));
            unPedido = repositorioPedido.buscarPorId(id);

            switch (remoteMessage.getData().get("NUEVO_ESTADO")) {
                case "REALIZADO":

                    break;
                case "ACEPTADO":

                    break;
                case "RECHAZADO":

                    break;
                case "EN_PREPARACION":

                    break;
                case "LISTO":
                unEstado = Estado.LISTO;
                unPedido.setEstado(unEstado);
                    Intent br = new Intent(this,EstadoPedidoReceiver.class);
                    br.putExtra("idPedido", id);
                    br.setAction(EstadoPedidoReceiver.EVENTO_LISTO);
                    sendBroadcast(br);
                    break;
                case "ENTREGADO":

                    break;
                case "CANCELADO":

                    break;

                default:
                    System.out.println("Estado Invalido");

            }





        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        repositorioPedido = new PedidoRepository();
    }
}
