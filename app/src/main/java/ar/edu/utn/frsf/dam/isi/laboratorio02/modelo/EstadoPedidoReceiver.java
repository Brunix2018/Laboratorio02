package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;




import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.AltaPedidosActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.HistorialPedidooActiity;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static final String EVENTO_ACEPTADO="ar.edu.utn.dam.isi.laboratorio02.ESTADO_ACEPTADO";
    public static final String EVENTO_CANCELADO="ar.edu.utn.dam.isi.laboratorio02.ESTADO_CANCELADO";
    public static final String EVENTO_PREPARACION="ar.edu.utn.dam.isi.laboratorio02.ESTADO_EN_PREPARACION";
    public static final String EVENTO_LISTO="ar.edu.utn.dam.isi.laboratorio02.ESTADO_LISTO";
    NotificationCompat.Builder notificarEstadoPedido;
    Pedido p;
    PedidoRepository Pedidos= new PedidoRepository();


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        int idPedido=  intent.getExtras().getInt("idPedido");
        p = Pedidos.buscarPorId(idPedido);

        if (intent.getAction() == EstadoPedidoReceiver.EVENTO_ACEPTADO) {

            //Toast.makeText(context,"El pedido para "+p.getMailContacto()+" a cambiado de estado a "+p.getEstado().toString(),Toast.LENGTH_LONG).show();


            String detalle = "El costo total será de $" + p.total() + "\n" + "Previsto en envio para " + p.getFecha();

            // Create an explicit intent for an Activity in your app
            Intent destino = new Intent(context, AltaPedidosActivity.class);
            destino.putExtra("idPedidoSeleccionado",p.getId());
            destino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

            notificarEstadoPedido = new NotificationCompat.Builder(context, "CANAL01")
                    .setSmallIcon(R.drawable.resto)
                    .setContentTitle("Tu Pedido fue aceptado")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(detalle))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(context);
            notificationManager.notify(7, notificarEstadoPedido.build());


        }else {

                if (intent.getAction() == EstadoPedidoReceiver.EVENTO_PREPARACION) {

                //Toast.makeText(context,"El pedido para "+p.getMailContacto()+" a cambiado de estado a "+p.getEstado().toString(),Toast.LENGTH_LONG).show();


                String detalle = "El costo total será de $" + p.total() + "\n" + "Previsto en envio para " + p.getFecha();

                // Create an explicit intent for an Activity in your app
                Intent destino = new Intent(context, HistorialPedidooActiity.class);
                destino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, 0);

                notificarEstadoPedido = new NotificationCompat.Builder(context, "CANAL01")
                        .setSmallIcon(R.drawable.resto)
                        .setContentTitle("Tu Pedido esta en preparación")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(detalle))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(context);
                notificationManager.notify(7, notificarEstadoPedido.build());

                } else{
                    if (intent.getAction() == EstadoPedidoReceiver.EVENTO_LISTO) {

                        //Toast.makeText(context,"El pedido para "+p.getMailContacto()+" a cambiado de estado a "+p.getEstado().toString(),Toast.LENGTH_LONG).show();


                        String detalle = "El costo total será de $" + p.total() + "\n" + "Previsto en envio para " + p.getFecha();

                        // Create an explicit intent for an Activity in your app
                        Intent destino = new Intent(context, HistorialPedidooActiity.class);
                        destino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, 0);

                        notificarEstadoPedido = new NotificationCompat.Builder(context, "CANAL01")
                                .setSmallIcon(R.drawable.resto)
                                .setContentTitle("Tu Pedido esta Listo")
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(detalle))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(context);
                        notificationManager.notify(7, notificarEstadoPedido.build());
                    }
                }


        }
    }
}
