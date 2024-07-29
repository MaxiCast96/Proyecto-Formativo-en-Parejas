package guillermo.luis.proyecto_formativo_perjeas

import Modelo.ListaPacientes
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClassConexion

class MainActivity : AppCompatActivity() {
    private lateinit var pacienteAdapter: PacienteAdapter
    private lateinit var rcvPacientes: RecyclerView
    private val ADD_TICKET_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcvPacientes = findViewById(R.id.rcvPacientes)
        rcvPacientes.layoutManager = LinearLayoutManager(this)

        actualizarRecyclerView()

        val btnAgregar= findViewById<FloatingActionButton>(R.id.btnAddPaciente)
        btnAgregar.setOnClickListener {
            val intent = Intent(this, Activity_Paciente::class.java)
            startActivity(intent)
        }
    }

    private fun actualizarRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val pacientes = obtenerDatos()

            withContext(Dispatchers.Main) {
                pacienteAdapter = PacienteAdapter(pacientes,
                    onPacienteClick = { paciente ->
                        val intent = Intent(this@MainActivity, activity_detalleCliente::class.java).apply {
                            putExtra("paciente", paciente)
                        }
                        startActivity(intent)
                    },
                    onEditarClick = { paciente ->
                        val intent = Intent(this@MainActivity, Activity_Paciente::class.java).apply {
                            putExtra("id_paciente", paciente.id)
                            putExtra("nombres", paciente.nombres)
                            putExtra("apellidos", paciente.apellidos)
                            putExtra("edad", paciente.edad)
                            putExtra("enfermedad", paciente.enfermedad)
                            putExtra("numero_habitacion", paciente.numeroHabitacion)
                            putExtra("numero_cama", paciente.numeroCama)
                            putExtra("fecha_nacimiento", paciente.fechaNacimiento)
                            putExtra("id_medicamento", paciente.idMedicamento)
                        }
                        startActivity(intent)
                    }
                )
                rcvPacientes.adapter = pacienteAdapter
            }
        }
    }

    private suspend fun obtenerDatos(): List<ListaPacientes> {
        val pacientes = mutableListOf<ListaPacientes>()
        val classConexion = ClassConexion()
        val connection = classConexion.cadenaConexion()

        connection?.let {
            try {
                val statement = it.createStatement()
                val resultSet = statement.executeQuery("SELECT * FROM Pacientes")

                while (resultSet.next()) {
                    val paciente = ListaPacientes(
                        id = resultSet.getString("id_paciente"),
                        nombres = resultSet.getString("nombres"),
                        apellidos = resultSet.getString("apellidos"),
                        edad = resultSet.getInt("edad"),
                        enfermedad = resultSet.getString("enfermedad"),
                        numeroHabitacion = resultSet.getInt("numero_habitacion"),
                        numeroCama = resultSet.getInt("numero_cama"),
                        fechaNacimiento = resultSet.getString("fecha_nacimiento"),
                        idMedicamento = resultSet.getInt("id_medicamento")
                    )
                    pacientes.add(paciente)
                }

                resultSet.close()
                statement.close()
                it.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return pacientes
    }
}
