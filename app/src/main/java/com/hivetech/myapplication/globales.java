package com.hivetech.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class globales {

    //Variables

    public static String DEBUG_TAG = "Depuracion";

    public static ArrayList<cliente> clientes;
    public static cliente clienteAEditar = null;

    public static String[] marcas = {
            "Audi",
            "BMW",
            "Chevrolet",
            "Ford",
            "Honda",
            "Kia",
            "Nissan",
            "Toyota"
    };



    //Metodos:

    //muestra un toast
    public static void mostrarToast(Context context, String mensaje){
        Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
    }

    //Regresa la hora del dispositivo en formato Date
    public static Date obtenerFechaYHoraActual(){
        return Calendar.getInstance().getTime();
    }

    /**
     * Convierte una fecha de tipo Date en String
     * @param date : Recibe una fecha en formato date
     * @return: regresa la fecha en formato string
     */
    @SuppressLint("SimpleDateFormat")
    public static String obtenerFechaEnString(Date date){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        return formatoFecha.format(date);
    }

    /**
     * Convierte una fecha de tipo String en formato Date
     * @param fechaString: String a convertir
     * @return: regresa la fecha convertida en tipo date, en caso de que no la pueda convertir
     * regresa un valor nulo
     */
    @SuppressLint("SimpleDateFormat")
    public static Date obtenerFechaDesdeString(String fechaString){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        try {
            return formatoFecha.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
