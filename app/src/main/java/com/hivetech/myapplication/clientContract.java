package com.hivetech.myapplication;

import android.provider.BaseColumns;

public class clientContract {
    
    public static class clientColumns implements BaseColumns{
        
        public static final String TABLE_NAME = "CLIENTES_TABLE";
        
        public static final String NOMBRE = "NOMBRE";
        public static final String TELEFONO = "TELEFONO";
        public static final String EDAD = "EDAD";
        public static final String MARCA_VEHICULO = "MARCA_VEHICULO";
        public static final String MODELO_VEHICULO = "MODELO_VEHICULO";
        public static final String FECHA_REGISTRO = "FECHA_REGISTRO";

        public static final int NOMBRE_INDEX = 1;
        public static final int TELEFONO_INDEX  = 2;
        public static final int EDAD_INDEX  = 3;
        public static final int MARCA_VEHICULO_INDEX  = 4;
        public static final int MODELO_VEHICULO_INDEX  = 5;
        public static final int FECHA_REGISTRO_INDEX  = 6;
    }
}
