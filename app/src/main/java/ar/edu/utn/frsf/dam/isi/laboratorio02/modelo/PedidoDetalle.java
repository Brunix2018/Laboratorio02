package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Ignore;


@Entity
public class PedidoDetalle {


    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "cantidad")
    private Integer cantidad;
    @Embedded(prefix = "pro_")
    private Producto producto;
    @Embedded(prefix = "ped_")
    private Pedido pedido;

   public PedidoDetalle(Integer cantidad, Producto producto) {

        this.cantidad = cantidad;
        this.producto = producto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        pedido.agregarDetalle(this);
    }

    @Override
    public String toString() {
        return producto.getNombre() + "( $"+producto.getPrecio()+")"+ cantidad;
    }
}
