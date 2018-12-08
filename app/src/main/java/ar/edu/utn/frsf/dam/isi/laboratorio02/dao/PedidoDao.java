package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoConDetalles;

@Dao
public interface PedidoDao {
    @Query("SELECT * FROM Pedido")
    List<Pedido> getAll();

    @Insert
    long insert(Pedido pedido);

    @Delete
    void delete(Pedido Pedido);

    @Query("SELECT * FROM Pedido WHERE id = :pedId")
    List<Pedido> buscarPorIdPed(int pedId);

    @Query("SELECT * FROM Pedido WHERE id = :id")
    List<PedidoConDetalles> buscarPorIdConPedDetalles(int id);
}
