package com.hivetech.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.hivetech.myapplication.globales.obtenerFechaEnString;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private final ArrayList<cliente> misClientes;

    public listAdapter(Context context, ArrayList<cliente> clientesAdapter){
        this.misClientes = clientesAdapter;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public listAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = mInflater.inflate(R.layout.cliente_item, parent, false);
        return new listAdapter.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull listAdapter.ViewHolder holder, int position) {
        holder.bindData(misClientes.get(position));

    }

    @Override
    public int getItemCount() {
        return misClientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgMarca;
        TextView txtNombreCliente, txtTelefonoCliente, txtModelo, txtFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMarca = itemView.findViewById(R.id.imgClienteItemMarca);
            txtNombreCliente = itemView.findViewById(R.id.txtClienteItemNombre);
            txtTelefonoCliente = itemView.findViewById(R.id.txtClienteItemTelefono);
            txtModelo = itemView.findViewById(R.id.txtClientItemModelo);
            txtFecha = itemView.findViewById(R.id.txtClienteItemFecha);
        }

        void bindData(final cliente cliente){
            txtNombreCliente.setText(cliente.getNombre());
            txtTelefonoCliente.setText(cliente.getTelefono());
            txtModelo.setText(cliente.getModeloVehiculo());
            txtFecha.setText(obtenerFechaEnString(cliente.getFechaRegistro()));

            switch (cliente.getMarcaVehiculo()){
                case "Audi":
                    imgMarca.setImageResource(R.mipmap.audi);
                    break;
                case "BMW":
                    imgMarca.setImageResource(R.mipmap.bmw);
                    break;
                case "Chevrolet":
                    imgMarca.setImageResource(R.mipmap.chevrolet);
                    break;
                case "Ford":
                    imgMarca.setImageResource(R.mipmap.ford);
                    break;
                case "Honda":
                    imgMarca.setImageResource(R.mipmap.honda);
                    break;
                case "Kia":
                    imgMarca.setImageResource(R.mipmap.kia);
                    break;
                case "Nissan":
                    imgMarca.setImageResource(R.mipmap.nissan);
                    break;
                case "Toyota":
                    imgMarca.setImageResource(R.mipmap.toyota);
                    break;
            }
        }
    }
}
