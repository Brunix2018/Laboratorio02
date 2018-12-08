package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

public class RepositorioResto {


    // variable de clase privada que almacena una instancia unica de esta entidad
    private static RepositorioResto INSTANCIA_UNICA=null;

    // metodo static publico que retorna la unica instancia de esta clase
    // si no existe, cosa que ocurre la primera vez que se invoca
    // la crea, y si existe retorna la instancia existente.
    public static RepositorioResto getInstance(Context ctx){
        if(INSTANCIA_UNICA==null) INSTANCIA_UNICA = new RepositorioResto(ctx);
        return INSTANCIA_UNICA;
    }

    private BaseDatosRom db;
    private CategoriaDao categoriaDao;
    private ProductoDao productoDao;


    // constructor privado para poder implementar SINGLETON
    // al ser privado solo puede ser invocado dentro de esta clase
    // el único lugar donde se invoca es en la linea 16 de esta clase
    // y se invocará UNA Y SOLO UNA VEZ, cuando _INSTANCIA_UNICA sea null
    // luego ya no se invoca nunca más. Nos aseguramos de que haya una
    // sola instancia en toda la aplicacion
    private RepositorioResto(Context ctx){
        db = Room.databaseBuilder(ctx,
                BaseDatosRom.class, "DB-Resto")
                .fallbackToDestructiveMigration()
                .build();
        categoriaDao = db.categoriaDao();
        productoDao = db.productoDao();


    }

    public void borrarTodo(){
        this.db.clearAllTables();
    }

    public CategoriaDao getCategoriaDao() {
        return categoriaDao;
    }

    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public ProductoDao getProductoDao() {
        return productoDao;
    }

    public void setProductoDao(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

}
