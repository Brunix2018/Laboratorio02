package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.AltaPedidosActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.HistorialPedidooActiity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.R;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context ctx;
    private List<Pedido> listaPedidos;


    public PedidoAdapter(Context context,List<Pedido> objects) {
        super(context, 0, objects);
        this.ctx = context;
        this.listaPedidos = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View fila = convertView;
        PedidoHolder holder;

        if(fila== null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            fila = inflater.inflate(R.layout.fila_historial, parent, false);
            holder = new PedidoHolder(fila);
            fila.setTag(holder);
        }else{
            holder = (PedidoHolder) fila.getTag();
        }


        Pedido unPedido = (Pedido) super.getItem(position);
        Log.d("PedidoAdapter","Pedido "+unPedido.toString());


        if(unPedido.getRetirar()==true){
            holder.tipoEntrega.setImageResource(R.drawable.tenedor);
        }else{
            holder.tipoEntrega.setImageResource(R.drawable.camion);
        }

        String email= "Contacto: "+unPedido.getMailContacto();
        String fecha= unPedido.getFecha().toString();
        String items= "Items: "+String.valueOf(unPedido.getDetalle().size());
        String pago= "A pagar: $"+String.valueOf(unPedido.total());


        holder.tvMailPedido.setText(email);
        holder.tvHoraEntrega.setText(fecha);
        holder.tvCantidadItems.setText(items);
        holder.tvPrecio.setText(pago);
        holder.estado.setText(unPedido.getEstado().toString());






/*
        holder.tvMailPedido.setText(unPedido.getMailContacto());
        holder.tvHoraEntrega.setText((CharSequence) unPedido.getFecha().toString());
        holder.tvCantidadItems.setText(String.valueOf(unPedido.getDetalle().size()));
        holder.tvPrecio.setText(this.getCostoTotal(unPedido));
        holder.estado.setText(unPedido.getEstado().toString());
*/



        switch (unPedido.getEstado()){
            case LISTO:
                holder.estado.setTextColor(Color.DKGRAY);
                break;
            case ENTREGADO:
                holder.estado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
            case RECHAZADO:
                holder.estado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                holder.estado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                holder.estado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                holder.estado.setTextColor(Color.BLUE);
                break;
        }

        holder.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //int indice = (int) view.getTag();
                int indice= position;


                Pedido pedidoSeleccionado = listaPedidos.get(indice);
                if( pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO)||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO)||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)){
                    pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);
                    PedidoAdapter.this.notifyDataSetChanged();
                    return;
            }
        }});

        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int indice = (int) view.getTag();
                int indice= position;
                Pedido pedidoSeleccionado = listaPedidos.get(indice);

                Intent i = new Intent(ctx, AltaPedidosActivity.class);

                i.putExtra("idPedidoSeleccionado",pedidoSeleccionado.getId());
                ctx.startActivity(i);

            }
        });



        return fila;
    }



}
