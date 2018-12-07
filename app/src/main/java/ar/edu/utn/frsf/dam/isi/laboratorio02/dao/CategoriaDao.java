package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

@Dao
public interface CategoriaDao {

        @Query("SELECT * FROM Categoria")
        List<Categoria> getAll();

        @Query("SELECT * FROM Categoria WHERE id = :catId")
        List<Categoria> buscarPorIdCat(int catId);

        @Insert
        void agregarCat(Categoria categoria);

        @Delete
        void borrarCat(Categoria categoria);

        @Update
        void actualizarCat(Categoria categoria);

}
