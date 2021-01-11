package com.hivetech.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.hivetech.myapplication.globales.clienteAEditar;
import static com.hivetech.myapplication.globales.clientes;
import static com.hivetech.myapplication.globales.mostrarToast;
import static com.hivetech.myapplication.globales.obtenerFechaDesdeString;
import static com.hivetech.myapplication.globales.obtenerFechaEnString;

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
        leerClientesDesdeBaseDeDatos();
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
                leerClientesDesdeBaseDeDatos();
                if (clientes.isEmpty()){
                    mostrarToast(context,"No hay ningun cliente registrado!");
                }else{
                    mostrarEliminarDialogo(false);
                }
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerClientesDesdeBaseDeDatos();
                if (clientes.isEmpty()){
                    mostrarToast(context,"No hay ningun cliente registrado!");
                }else{
                    mostrarEliminarDialogo(true);
                }
            }
        });
        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerClientesDesdeBaseDeDatos();
                if (clientes.isEmpty()){
                    mostrarToast(context,"No hay ningun cliente registrado!");
                }else{
                    startActivity(new Intent(context,ClientesActivity.class));
                }
            }
        });
        btnEliminarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerClientesDesdeBaseDeDatos();
                if (clientes.isEmpty()){
                    mostrarToast(context,"No hay ningun cliente registrado!");
                }else{
                    context.deleteDatabase("clientes.db");
                    mostrarToast(context,"Clientes eliminados");
                }

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

    private void leerClientesDesdeBaseDeDatos(){
        clientDataBase clientDataBase = new clientDataBase(context);
        SQLiteDatabase database = clientDataBase.getWritableDatabase();
        clientes = new ArrayList<>();
        Cursor cursor = database.query(clientContract.clientColumns.TABLE_NAME,null,null,
                null,null,null,null);
        while (cursor.moveToNext()){
            cliente clienteDB = new cliente();
            clienteDB.setNombre(cursor.getString(clientContract.clientColumns.NOMBRE_INDEX));
            clienteDB.setTelefono(cursor.getString(clientContract.clientColumns.TELEFONO_INDEX));
            clienteDB.setEdad(cursor.getInt(clientContract.clientColumns.EDAD_INDEX));
            clienteDB.setMarcaVehiculo(cursor.getString(clientContract.clientColumns.MARCA_VEHICULO_INDEX));
            clienteDB.setModeloVehiculo(cursor.getString(clientContract.clientColumns.MODELO_VEHICULO_INDEX));
            clienteDB.setFechaRegistro(obtenerFechaDesdeString(cursor.getString(clientContract.clientColumns.FECHA_REGISTRO_INDEX)));
            clienteDB.setId(cursor.getInt(0));
            clientes.add(clienteDB);
        }
        cursor.close();
    }

    private void mostrarEliminarDialogo(boolean eliminar){
        //crea un arreglo de string temporal
        ArrayList<String> clientesTemporalString = new ArrayList<>();
        for (cliente c: clientes){
            clientesTemporalString.add(c.getNombre());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup parent = findViewById(R.id.parent);
        View view = getLayoutInflater().inflate(R.layout.eliminar_cliente_dialog,parent,false);
        TextView txtTitulo = view.findViewById(R.id.txtDialogoTitulo);
        String titulo;
        if (eliminar){
            titulo = "Seleccione el cliente a eliminar:";
        }else{
            titulo = "Seleccione el cliente a editar:";
        }
        txtTitulo.setText(titulo);
        ListView listClientes = view.findViewById(R.id.listEliminarCliente);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,clientesTemporalString);
        listClientes.setAdapter(adapter);

        listClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreClienteSeleccionado = clientesTemporalString.get(position);
                if (eliminar) {
                    eliminarClienteDeBaseDeDatos(nombreClienteSeleccionado);
                }else{
                    for (cliente c: clientes){
                        if (c.getNombre().equals(nombreClienteSeleccionado)){
                            clienteAEditar = c;
                            break;
                        }
                    }
                    startActivity(new Intent(context,CrearClienteActivity.class));
                }
                alertDialog.cancel();
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();


    }

    private void eliminarClienteDeBaseDeDatos(String nombreClienteAEliminar){
        clientDataBase clientDataBase = new clientDataBase(context);
        SQLiteDatabase database = clientDataBase.getWritableDatabase();
        database.delete(clientContract.clientColumns.TABLE_NAME,clientContract.clientColumns.NOMBRE + "=?",new String[]{nombreClienteAEliminar});
    }


}