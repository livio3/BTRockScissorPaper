package com.example.bboss.btrockscissorpaper;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by BBOSS on 24/05/2018.
 */

public class ServerGetConnection extends AsyncTask<Void,Void,BluetoothSocket> {
    BluetoothServerSocket bluetoothServerSocket;


    @Override
    protected void onPreExecute() {
        BluetoothAdapter bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();   //singleton..=>ok
        bluetoothServerSocket= null;
        BluetoothSocket serverSocket = null; //blocking call... MUST BE IN ANTOHER TH!


        try {
            bluetoothServerSocket = bluetoothAdapter.
                        listenUsingInsecureRfcommWithServiceRecord(this.getClass().getName(), MainActivity.uuid);
        } catch (IOException e1) {
            e1.printStackTrace();
            BTHandler.setupAllert("ERROR IN SERVER SOCKET INIT");
        }


    }

    @Override
    protected BluetoothSocket doInBackground(Void... voids) {

        BluetoothSocket serverSocket=null;

        //SDP protocol where run bt? has localClassName and uuid specified
        try {
            serverSocket = bluetoothServerSocket.accept();
        }
        catch (IOException e) {
            BTHandler.setupAllert("ERROR IN CREATE COMUNICATION CHANNEL (SERVER)");
            e.printStackTrace();
        }
        try {
            bluetoothServerSocket.close();
            //getted connection==>close way to get more ... //TODO CHANGE FOR >=3 PLAYER!

        } catch (IOException e) {
            e.printStackTrace();

        }
       /* //debug try write "hello fuck bt word"
        try {
            serverSocket.getOutputStream().write("hello fuck bt word".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            BTHandler.setupAllert("ERROR IN WRITE INTO SOCKET");

        }*/
    return serverSocket;
    }
}