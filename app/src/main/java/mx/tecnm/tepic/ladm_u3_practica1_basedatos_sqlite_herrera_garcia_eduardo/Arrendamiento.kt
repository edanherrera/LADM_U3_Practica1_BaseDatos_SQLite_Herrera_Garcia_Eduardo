package mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.fragment.app.Fragment

class Arrendamiento (este: Context) {
    private val este = este
    var idArrenda = ""
    var nombre = ""
    var domicilio = ""
    var licencia = ""
    var idAuto = ""
    var fecha = ""
    private var err = ""

    fun insertar() : Boolean{
        val baseDatos = BaseDatos(este,"aAutomovil",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()
            datos.put("NOMBRE",nombre)
            datos.put("DOMICILIO",domicilio)
            datos.put("LICENCIACOND",licencia)
            datos.put("IDAUTO",idAuto)
            datos.put("FECHA",fecha)


            val respuesta = tabla.insert("ARRENDAMIENTO",null,datos)
            if (respuesta == -1L){
                return false
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
    fun mostrarTodos() : ArrayList<Arrendamiento>{
        val baseDatos = BaseDatos(este,"aAutomovil",null,1)
        err = ""
        var arreglo = ArrayList<Arrendamiento>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ARRENDAMIENTO"

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if (cursor.moveToFirst()){
                do {
                    val arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getString(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licencia = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getString(2)
                    arrendamiento.fecha = cursor.getString(3)
                    arreglo.add(arrendamiento)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarArr(idArrenda:String):Arrendamiento{
        val baseDatos = BaseDatos(este,"aAutomovil",null,1)
        var resultado =""
        err=""
        val arrendamiento = Arrendamiento(este)
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDARRENDA=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(idArrenda))
            if (cursor.moveToFirst()){

                    arrendamiento.idArrenda = cursor.getString(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licencia = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getString(2)
                    arrendamiento.fecha = cursor.getString(3)
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arrendamiento
    }
    fun actualizar(): Boolean{
        val baseDatos = BaseDatos(este,"aAutomovil",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val datosAcualizados = ContentValues()

            datosAcualizados.put("IDARRENDA",idArrenda)
            datosAcualizados.put("NOMBRE",nombre)
            datosAcualizados.put("DOMICILIO",domicilio)
            datosAcualizados.put("LICENCIACOND",licencia)
            datosAcualizados.put("IDAUTO",idAuto)
            datosAcualizados.put("FECHA",fecha)

            val respuesta = tabla.update("ARRENDAMIENTO",datosAcualizados,"IDARRENDA=?", arrayOf(idArrenda))

            if(respuesta==0)return false


        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
    /*fun existe(modelo:String,marca:String):Boolean{
        val baseDatos = BaseDatos(este,"aAutomovil",null,1)
        var resultado =""
        err=""
        val automovil = Automovil(este)
        val arrendamiento = Arrendamiento(este)

        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE MODELO=modelo AND MARCA=marca"
            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(idArrenda))
            if (cursor.moveToFirst()){

                arrendamiento.idArrenda = cursor.getString(0)
                arrendamiento.nombre = cursor.getString(1)
                arrendamiento.domicilio = cursor.getString(2)
                arrendamiento.licencia = cursor.getString(3)
                arrendamiento.idAuto = cursor.getString(2)
                arrendamiento.fecha = cursor.getString(3)
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }*/

    fun eliminar(idArrendaEliminar : String): Boolean {
        val baseDatos = BaseDatos(este,"aAutomovil",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("ARRENDAMIENTO","IDARRENDA=?", arrayOf(idArrendaEliminar))

            if (resultado == 0) return false

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
    fun eliminar() : Boolean {
        val baseDatos = BaseDatos(este,"aAutomovil",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("ARRENDAMIENTO","IDARRENDA=?", arrayOf(idArrenda))

            if (resultado == 0) return false

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
}