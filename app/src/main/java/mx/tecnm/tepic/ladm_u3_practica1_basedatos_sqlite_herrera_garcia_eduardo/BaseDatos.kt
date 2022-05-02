package mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.fragment.app.Fragment

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE AUTOMOVIL(IDAUTO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "MODELO VARCHAR(50),MARCA VARCHAR(50),KILOMETRAGE INTEGER)")
        db.execSQL("CREATE TABLE ARRENDAMIENTO(IDARRENDA INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "NOMBRE VARCHAR(50),DOMICILIO VARCHAR(50),LICENCIACOND VARCHAR(50),IDAUTO INTEGER,FECHA DATE, " +
                "FOREIGN KEY(IDAUTO) REFERENCES AUTOMOVIL(IDAUTO))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}