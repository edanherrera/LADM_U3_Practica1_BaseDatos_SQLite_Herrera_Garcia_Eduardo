package mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite_herrera_garcia_eduardo.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite.Arrendamiento
import mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite_herrera_garcia_eduardo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var listaIDs = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatosEnListView()
        binding.insertar.setOnClickListener {
            val arr = Arrendamiento(this.requireContext())

            arr.nombre = binding.nombre.text.toString()
            arr.domicilio = binding.domicilio.text.toString()
            arr.licencia = binding.licencia.text.toString()
            //arr.idAuto = binding.idAuto.text.toString()


            val resultado = arr.insertar()
            if (resultado){
                Toast.makeText(this.requireContext(),"SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                mostrarDatosEnListView()
                binding.nombre.setText("")
                binding.domicilio.setText("")
                binding.licencia.setText("")
                //binding.idAuto.setText("")

            }

        }
        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }


    fun mostrarDatosEnListView(){
        var listaArr = Arrendamiento(this.requireContext()).mostrarTodos()
        var nombreArr = ArrayList<String>()
        listaIDs.clear()
        (0..listaArr.size-1).forEach{
            val al = listaArr.get(it)
            nombreArr.add(al.nombre)
            listaIDs.add(al.idArrenda)

        }
        binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,nombreArr)

        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idArr = listaIDs.get(indice)
            val arr = Arrendamiento(this.requireContext()).mostrarArr(idArr)

            AlertDialog.Builder(this.requireContext())
                .setTitle("ATENCIÓN")
                .setMessage("Qué deseas hacer con Id: ${arr.idArrenda} Nombre: ${arr.nombre}, \nDomicilio: ${arr.domicilio}, " +
                        "\nAuto: ${arr.idAuto}, \nFecha: ${arr.fecha}?")
                .setNegativeButton("Eliminar"){d,i->
                    arr.eliminar()
                    mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar"){d,i->

                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        mostrarDatosEnListView()
        super.onResume()
    }

}