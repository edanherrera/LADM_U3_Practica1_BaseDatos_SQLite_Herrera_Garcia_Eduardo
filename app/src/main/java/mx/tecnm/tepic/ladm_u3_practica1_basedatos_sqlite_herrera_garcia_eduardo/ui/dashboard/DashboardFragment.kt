package mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite_herrera_garcia_eduardo.ui.dashboard

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
import mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite.Automovil
import mx.tecnm.tepic.ladm_u3_practica1_basedatos_sqlite_herrera_garcia_eduardo.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var listaIDs = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatosEnListView()
        binding.insertar.setOnClickListener {
            val auto = Automovil(this.requireContext())
            auto.modelo = binding.modelo.text.toString()
            auto.marca = binding.marca.text.toString()
            auto.kilometrage = Integer.parseInt(binding.kilometrage.text.toString())
            val resultado = auto.insertar()
            if (resultado){
                Toast.makeText(this.requireContext(),"SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                mostrarDatosEnListView()
                binding.modelo.setText("")
                binding.marca.setText("")
                binding.kilometrage.setText("")
            }

        }
        //val textView: TextView = binding.textDashboard
        /*dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    fun mostrarDatosEnListView(){
        var listaAuto = Automovil(this.requireContext()).mostrarTodos()
        var nombreAuto = ArrayList<String>()
        listaIDs.clear()
        (0..listaAuto.size-1).forEach{
            val al = listaAuto.get(it)
            nombreAuto.add(al.modelo)
            listaIDs.add(al.idAuto)

        }
        binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,nombreAuto)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idArr = listaIDs.get(indice)
            val auto = Automovil(this.requireContext()).mostrarAuto(idArr.toString())

            AlertDialog.Builder(this.requireContext())
                .setTitle("ATENCIÓN")
                .setMessage("Qué deseas hacer con Id: ${auto.idAuto}, \nModelo: ${auto.modelo}, \nMarca: ${auto.marca}, " +
                        "\nKilometrage: ${auto.kilometrage}?")
                .setNegativeButton("Eliminar"){d,i->
                    auto.eliminar()
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