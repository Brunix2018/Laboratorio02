package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CategoriaRest{
    // realiza el POST de una categoría al servidor REST
    public void crearCategoria(Categoria c) throws Exception {
//Variables de conexión y stream de escritura y lectura
        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
        InputStream in =null;
//Crear el objeto json que representa una categoria
        JSONObject categoriaJson = new JSONObject();
        categoriaJson.put("nombre",c.getNombre());
//Abrir una conexión al servidor para enviar el POST
        URL url = new URL("http://192.168.1.7:4000/categorias");
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
//Obtener el outputStream para escribir el JSON
        printout = new DataOutputStream(urlConnection.getOutputStream());
        String str = categoriaJson.toString();
        byte[] jsonData=str.getBytes("UTF-8");
        printout.write(jsonData);
        printout.flush();
//Leer la respuesta
        in = new BufferedInputStream(urlConnection.getInputStream());
        InputStreamReader isw = new InputStreamReader(in);
        StringBuilder sb = new StringBuilder();
        int data = isw.read();
//Analizar el codigo de lar respuesta
        if( urlConnection.getResponseCode() ==200 ||
                urlConnection.getResponseCode()==201){
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
//analizar los datos recibidos
            Log.d("LAB_04",sb.toString());
        }else{
// lanzar excepcion o mostrar mensaje de error
// que no se pudo ejecutar la operacion
            System.out.println("No se pudo realizar la operación");
        }
// caputurar todas las excepciones y en el bloque finally
// cerrar todos los streams y HTTPUrlCOnnection
        if(printout!=null)
            try { printout.close();
        } catch ( IOException e1){
            e1.printStackTrace();
            }
        if(in!=null)
            try { in.close();
            } catch ( IOException e2){
                e2.printStackTrace();
            }
        finally {
                   if (urlConnection != null) urlConnection.disconnect();
        }
    }


}