package com.hivetech.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.hivetech.myapplication.globales.clienteAEditar;
import static com.hivetech.myapplication.globales.clientes;
import static com.hivetech.myapplication.globales.marcas;
import static com.hivetech.myapplication.globales.mostrarToast;
import static com.hivetech.myapplication.globales.obtenerFechaEnString;
import static com.hivetech.myapplication.globales.obtenerFechaYHoraActual;

public class CrearClienteActivity extends AppCompatActivity {

    //Vistas:
    EditText edtNombre, edtTelefono, edtEdad, edtModelo;
    Spinner spinMarca;
    Button btnGuardar, btnLlamar, btnMensaje;

    //Utilidades
    Context context;

    //Variables
    cliente clienteCreado = new cliente();
    AlertDialog alertDialog;

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
                                    clienteCreado.setNombre(nombreEscrito);
                                    clienteCreado.setTelefono(telefonoEscrito);
                                    clienteCreado.setEdad(Integer.parseInt(edadString));
                                    clienteCreado.setModeloVehiculo(modeloEscrito);
                                    clienteCreado.setMarcaVehiculo(marcaSeleccionada);
                                    clienteCreado.setFechaRegistro(obtenerFechaYHoraActual());
                                    guardarClienteEnBaseDeDatos();
                                    mostrarToast(context,"Cliente registrado correctamente!");
                                    clienteAEditar = null;
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
                String telefonoEscrito = edtTelefono.getText().toString().trim();
                if (telefonoEscrito.isEmpty()){
                    mostrarToast(context,"Ingrese el telefono para continuar");
                }else{
                    enviarMensajeDialogo(telefonoEscrito);
                }
            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefonoEscrito = edtTelefono.getText().toString().trim();
                if (telefonoEscrito.isEmpty()){
                    mostrarToast(context,"Ingrese el telefono para continuar");
                }else{
                    llamarCliente(telefonoEscrito);
                }
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

        if (clienteAEditar != null){
            edtNombre.setText(clienteAEditar.getNombre());
            edtTelefono.setText(clienteAEditar.getTelefono());
            edtEdad.setText(String.valueOf(clienteAEditar.getEdad()));
            for (int i=0; i<marcas.length; i++){
                String marca = marcas[i];
                if (marca.equals(clienteAEditar.getMarcaVehiculo())){
                    int finalI = i;
                    spinMarca.post(new Runnable() {
                        @Override
                        public void run() {
                            spinMarca.setSelection(finalI);
                        }
                    });
                }
            }
            edtModelo.setText(clienteAEditar.getModeloVehiculo());
        }
    }

    private void guardarClienteEnBaseDeDatos(){
        clientDataBase clientDB = new clientDataBase(context);
        SQLiteDatabase database = clientDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(clientContract.clientColumns.NOMBRE,clienteCreado.getNombre());
        contentValues.put(clientContract.clientColumns.TELEFONO,clienteCreado.getTelefono());
        contentValues.put(clientContract.clientColumns.EDAD,clienteCreado.getEdad());
        contentValues.put(clientContract.clientColumns.MARCA_VEHICULO,clienteCreado.getMarcaVehiculo());
        contentValues.put(clientContract.clientColumns.MODELO_VEHICULO,clienteCreado.getModeloVehiculo());
        contentValues.put(clientContract.clientColumns.FECHA_REGISTRO,obtenerFechaEnString(clienteCreado.getFechaRegistro()));
        if (clienteAEditar == null) {
            database.insert(clientContract.clientColumns.TABLE_NAME, null, contentValues);
        }else{
            clienteCreado.setId(clienteAEditar.getId());
            database.update(clientContract.clientColumns.TABLE_NAME,contentValues,clientContract.clientColumns._ID + "=?", new String[]{String.valueOf(clienteCreado.getId())});
        }
    }

    private void llamarCliente(String telefono){
        Uri call = Uri.parse("tel:" + telefono);
        Intent phoneIntent = new Intent(Intent.ACTION_CALL, call);
        context.startActivity(phoneIntent);
    }

    private void enviarMensajeDialogo(String telefono){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup parent = findViewById(R.id.parent);
        View view = getLayoutInflater().inflate(R.layout.enviar_mensaje_layout,parent,false);
        EditText edtMensaje = view.findViewById(R.id.edtMensajeEnviar);
        Button btnEnviar = view.findViewById(R.id.btnEnviarMensajeDialog);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = edtMensaje.getText().toString();
                if (mensaje.isEmpty()){
                    mostrarToast(context,"Ingrese el mensaje a enviar");
                }else{
                    try{
                        SmsManager manager = SmsManager.getDefault();
                        manager.sendTextMessage(telefono,null,mensaje,null,null);
                        mostrarToast(context,"mensaje enviado exitosamente!");
                        alertDialog.cancel();
                    }catch (Exception e){
                        mostrarToast(context,"Error al enviar mensaje: " + e);
                    }
                }

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();


    }


}