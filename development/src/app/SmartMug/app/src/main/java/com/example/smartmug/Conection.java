package com.example.smartmug;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Conection extends AppCompatActivity {
    private TextView output;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conection);
        output = (TextView) findViewById(R.id.textView);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());

        ejecutaCliente();
    }

    private void ejecutaCliente() {
        String ip="127.0.0.1";
        int puerto=8080;
        log(" socket " + ip + " " + puerto);
        try {
            Socket sk = new Socket(ip,puerto);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(sk.getInputStream()));
            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(sk.getOutputStream()),true);
            log("enviando...");
            salida.println("Hola Mundo");
            log("recibiendo ... " + entrada.readLine());
            sk.close();
        } catch (Exception e) {
            log("error: " + e.toString());
        }
    }

    private void log(String string) {
        output.append(string + "\n");
    }
}
