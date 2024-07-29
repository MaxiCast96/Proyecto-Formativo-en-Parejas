package guillermo.luis.proyecto_formativo_perjeas

import Modelo.ListaPacientes
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PacienteAdapter(
    private val pacientes: List<ListaPacientes>,
    private val onPacienteClick: (ListaPacientes) -> Unit,
    private val onEditarClick: (ListaPacientes) -> Unit
) : RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return PacienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        val paciente = pacientes[position]
        holder.bind(paciente, onPacienteClick, onEditarClick)
    }

    override fun getItemCount(): Int = pacientes.size

    class PacienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tv_nombre)
        private val btnEditar: View = itemView.findViewById(R.id.btn_editar)

        fun bind(paciente: ListaPacientes, onPacienteClick: (ListaPacientes) -> Unit, onEditarClick: (ListaPacientes) -> Unit) {
            tvNombre.text = "${paciente.nombres} ${paciente.apellidos}"
            tvNombre.setOnClickListener { onPacienteClick(paciente) }
            btnEditar.setOnClickListener { onEditarClick(paciente) }
        }
    }
}
