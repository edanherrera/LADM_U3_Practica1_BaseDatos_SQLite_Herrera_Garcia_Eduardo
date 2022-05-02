package mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Automovil(este: Context) {
    private val este = este
    var idAuto = 0
    var modelo = ""
    var marca = ""
    var kilometrage = 0
    private var err = ""

    fun insertar() : Boolean{
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()
            datos.put("MODELO",modelo)
            datos.put("MARCA",marca)
            datos.put("KILOMETRAGE",kilometrage)


            val respuesta = tabla.insert("AUTOMOVIL",null,datos)
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
    fun mostrarTodos() : ArrayList<Automovil>{
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        var arreglo = ArrayList<Automovil>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM AUTOMOVIL"

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if (cursor.moveToFirst()){
                do {
                    val automovil = Automovil(este)
                    automovil.idAuto = cursor.getInt(0)
                    automovil.modelo = cursor.getString(1)
                    automovil.marca = cursor.getString(2)
                    automovil.kilometrage = cursor.getInt(3)
                    arreglo.add(automovil)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarAuto(idAuto:String):Automovil{
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        var resultado =""
        err=""
        val automovil = Automovil(este)
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE IDAUTO=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(idAuto))
            if (cursor.moveToFirst()){

                automovil.idAuto = cursor.getInt(0)
                automovil.modelo = cursor.getString(1)
                automovil.marca = cursor.getString(2)
                automovil.kilometrage = cursor.getInt(3)
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return automovil
    }
    fun actualizar(): Boolean{
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val datosAcualizados = ContentValues()

            datosAcualizados.put("IDAUTO",idAuto)
            datosAcualizados.put("MODELO",modelo)
            datosAcualizados.put("MARCA",marca)
            datosAcualizados.put("KILOMETRAGE",kilometrage)

            val respuesta = tabla.update("AUTOMOVIL",datosAcualizados,"IDAUTO=?", arrayOf(idAuto.toString()))

            if(respuesta==0)return false


        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun eliminar(idAutoEliminar : String): Boolean {
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("AUTOMOVIL","IDAUTO=?", arrayOf(idAutoEliminar))

            if (resultado == 0) return false

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
            return true
        }
    }
    fun eliminar() : Boolean {
        val baseDatos = BaseDatos(este,"bdaAutomovil",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("AUTOMOVIL","IDAUTO=?", arrayOf(idAuto.toString()))

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