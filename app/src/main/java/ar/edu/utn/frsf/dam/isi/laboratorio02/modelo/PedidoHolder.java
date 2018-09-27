package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;

public class PedidoHolder {
    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView estado;
    public ImageView tipoEntrega;
    public Button btnCancelar;
    public Button btnDetalle;


    public PedidoHolder(View base) {
    this.tvMailPedido = (TextView) base.findViewById(R.id.tvmailPedido);
    this.tvHoraEntrega = (TextView) base.findViewById(R.id.tvHoraEntrega);
    this.tvCantidadItems = (TextView) base.findViewById(R.id.tvCantidadItems);
    this.tvPrecio = (TextView) base.findViewById(R.id.tvPrecio);
    this.estado = (TextView) base.findViewById(R.id.estado);
    this.btnCancelar = (Button) base.findViewById(R.id.btnCancelar);
    this.btnDetalle = (Button) base.findViewById(R.id.btnDetalle);

    }
}
