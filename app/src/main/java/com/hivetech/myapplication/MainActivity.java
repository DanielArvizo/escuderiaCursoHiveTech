package com.hivetech.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    //Vistas:
    Button btnRegistrar, btnEditar, btnEliminar, btnClientes, btnEliminarTodos;
    //Utilidades:
    Context context;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configuracionesIniciales();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Listeners de los botones
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,CrearClienteActivity.class));
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnEliminarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void configuracionesIniciales(){
        context = MainActivity.this;
        btnClientes = findViewById(R.id.btnClientes);
        btnRegistrar = findViewById(R.id.btnRegistrarCliente);
        btnEditar = findViewById(R.id.btnEditarCliente);
        btnEliminar = findViewById(R.id.btnEliminarCliente);
        btnEliminarTodos = findViewById(R.id.btnEliminarTodos);
    }




}