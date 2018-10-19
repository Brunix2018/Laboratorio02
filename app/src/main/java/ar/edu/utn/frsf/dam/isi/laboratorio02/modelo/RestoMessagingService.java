package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.firebase.iid.*;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class RestoMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
       
    }

    @Override
    public void onCreate() {
        super.onCreate()
    }


}
