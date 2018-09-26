package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context ctx;
    private List<Pedido> datos;


    public PedidoAdapter(Context context,List<Pedido> objects) {
        super(context, 0, objects);
        this.ctx = context;
        this.datos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        View fila = convertView;

        if(fila== null) {
            fila = inflater.inflate(R.layout.fila_historial, parent, false);
        }
        PedidoHolder holder = (PedidoHolder) fila.getTag();
        if(holder==null){
            holder = new PedidoHolder(fila);
            fila.setTag(holder);
        }

        Pedido unPedido = (Pedido) super.getItem(position);


        if(unPedido.getRetirar()){
            holder.tipoEntrega.setImageResource(R.drawable.tenedor);
        }else{
            holder.tipoEntrega.setImageResource(R.drawable.camion);
        }

        String email= "Contacto: "+unPedido.getMailContacto();
        String fecha= "Fecha de entrega: "+unPedido.getFecha().toString();
        String items= "Items: "+unPedido.getDetalle().size();
        String pago= "A pagar: "+this.getCostoTotal(unPedido);

        holder.tvMailPedido.setText(email);
        holder.tvHoraEntrega.setText(fecha);
        holder.tvCantidadItems.setText(items);
        holder.tvPrecio.setText(pago);
        holder.estado.setText(unPedido.getEstado().toString());

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
                int indice = (int) view.getTag();
                Pedido pedidoSeleccionado = datos.get(indice);
                if( pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO)||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO)||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)){
                    pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);
                    PedidoAdapter.this.notifyDataSetChanged();
                    return;
            }
        }});

        return fila;
    }


    private String getCostoTotal ( Pedido unPedido){
        double costoTotal=0.0;

        for (int i = 0; i < unPedido.getDetalle().size(); i++){

            PedidoDetalle pedDet = unPedido.getDetalle().get(i);
            double precio = pedDet.getProducto().getPrecio();
            int cantidad= pedDet.getCantidad();
            costoTotal= precio*cantidad+costoTotal;

        }

        return String.valueOf(costoTotal);





    }
}
