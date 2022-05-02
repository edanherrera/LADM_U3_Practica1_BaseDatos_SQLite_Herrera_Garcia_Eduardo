package mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.time.Instant
import java.time.ZoneId

class Arrendamiento (este: Context) {
    private val este = este
    var idArrenda = 0
    var nombre = ""
    var domicilio = ""
    var licencia = ""
    var idAuto = 0
    var fecha = ""
    var marca = ""
    var modelo = ""
    private var err = ""

    fun insertar(marca: String, modelo: String) : Boolean{
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()
            var SQL_SELECT = "SELECT IDAUTO FROM AUTOMOVIL WHERE MODELO = ? AND MARCA = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(modelo, marca))

            if(cursor.moveToFirst()){
                idAuto = cursor.getInt(0)
            }else{
                Toast.makeText(este,"Dato no encontrado", Toast.LENGTH_LONG)
                    .show()
                return false
            }
            datos.put("NOMBRE",nombre)
            datos.put("DOMICILIO",domicilio)
            datos.put("LICENCIACOND",licencia)
            datos.put("IDAUTO",idAuto)
            var fechaA= Instant.now()
            val fec = fechaA.atZone(ZoneId.of("America/Mazatlan")).toString()
            val split = fec.split("T")
            fecha = split[0]
            datos.put("FECHA",fecha)

            val respuesta = tabla.insert("ARRENDAMIENTO","IDARRENDA",datos)

            if (respuesta == -1L){
                Toast.makeText(este,"No se pudo insertar", Toast.LENGTH_LONG)
                    .show()
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
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        var arreglo = ArrayList<Arrendamiento>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ARRENDAMIENTO"

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if (cursor.moveToFirst()){
                do {
                    val arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getInt(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licencia = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getInt(4)
                    var automovil = Automovil(este).mostrarAuto(cursor.getInt(4).toString())
                    arrendamiento.marca = automovil.marca
                    arrendamiento.modelo = automovil.modelo
                    arrendamiento.fecha = cursor.getString(5)


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
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        var resultado =""
        err=""
        val arrendamiento = Arrendamiento(este)
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDARRENDA=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(idArrenda))
            if (cursor.moveToFirst()){

                arrendamiento.idArrenda = cursor.getInt(0)
                arrendamiento.nombre = cursor.getString(1)
                arrendamiento.domicilio = cursor.getString(2)
                arrendamiento.licencia = cursor.getString(3)
                arrendamiento.idAuto = cursor.getInt(4)
                var automovil = Automovil(este).mostrarAuto(cursor.getInt(4).toString())
                arrendamiento.marca = automovil.marca
                arrendamiento.modelo = automovil.modelo
                arrendamiento.fecha = cursor.getString(5)
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arrendamiento
    }
    fun actualizar(): Boolean{
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
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

            val respuesta = tabla.update("ARRENDAMIENTO",datosAcualizados,"IDARRENDA=?", arrayOf(idArrenda.toString()))

            if(respuesta==0)return false


        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }


    fun eliminar(idArrendaEliminar : String): Boolean {
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
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
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("ARRENDAMIENTO","IDARRENDA=?", arrayOf(idArrenda.toString()))

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