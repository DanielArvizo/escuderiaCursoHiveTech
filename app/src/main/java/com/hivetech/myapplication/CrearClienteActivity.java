package com.hivetech.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import static com.hivetech.myapplication.globales.marcas;
import static com.hivetech.myapplication.globales.mostrarToast;

public class CrearClienteActivity extends AppCompatActivity {

    //Vistas:
    EditText edtNombre, edtTelefono, edtEdad, edtModelo;
    Spinner spinMarca;
    Button btnGuardar, btnLlamar, btnMensaje;

    //Utilidades
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cliente);
        configuracionInicial();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Adaptador para el spinner:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,marcas);
        spinMarca.setAdapter(adapter);

        //Listener del boton guardar cliente:
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extraccion de datos:
                String nombreEscrito = edtNombre.getText().toString().trim();
                String edadString = edtEdad.getText().toString().trim();
                String telefonoEscrito = edtTelefono.getText().toString().trim();
                String modeloEscrito = edtModelo.getText().toString().trim();
                String marcaSeleccionada = spinMarca.getSelectedItem().toString();
                //Comprobacion de datosÑ
                if (!nombreEscrito.isEmpty()){ //nombre vacio
                    if (!telefonoEscrito.isEmpty()){ //telefono vacio
                        if (telefonoEscrito.length() == 10) { //telefono de 10 digitos
                            if (!edadString.isEmpty()){ //edad vacia
                                if (!modeloEscrito.isEmpty()){ //modelo vacio
                                    mostrarToast(context,"Cliente registrado correctamente!");
                                    startActivity(new Intent(context,MainActivity.class));
                                }else{
                                    mostrarToast(context,"Favor de seleccionar el modelo del vehiculo");
                                }
                            }else{
                                mostrarToast(context,"Favor de ingresar la edad para continuar!");
                            }
                        }else{
                            mostrarToast(context,"El teléfono debe ser de 10 dígitos");
                        }
                    }else{
                        mostrarToast(context,"Favor de ingresar el teléfono para continuar!");
                    }
                }else{
                    mostrarToast(context,"Favor de ingresar el nombre para continuar!");
                }

            }
        });

        btnMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void configuracionInicial(){
        edtEdad = findViewById(R.id.edtEdad);
        edtModelo = findViewById(R.id.edtModeloVehiculo);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtNombre = findViewById(R.id.edtNombreCliente);
        spinMarca = findViewById(R.id.spinMarca);
        btnGuardar = findViewById(R.id.btnGuardarCliente);
        context = CrearClienteActivity.this;
        btnMensaje = findViewById(R.id.btnEnviarMensaje);
        btnLlamar = findViewById(R.id.btnLlamarCliente);

    }




}