package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface ProductoDao {


    @Query("SELECT * FROM Producto")
    List<Producto> getAll();

    @Query("SELECT * FROM Categoria WHERE id = :prodId")
    List<Producto> buscarPorIdCProd(int prodId);

    @Insert
    void agregarProd(Producto producto);

    @Delete
    void borrarProd(Producto producto);

    @Update
    void actualizarProd(Producto producto);

}