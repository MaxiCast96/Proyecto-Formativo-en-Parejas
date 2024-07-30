package guillermo.luis.proyecto_formativo_perjeas

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClassConexion
import java.sql.PreparedStatement
import java.util.*

class Activity_Paciente : AppCompatActivity() {
    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etEdad: EditText
    private lateinit var etEnfermedad: EditText
    private lateinit var etNumeroHabitacion: EditText
    private lateinit var etNumeroCama: EditText
    private lateinit var etFechaNacimiento: EditText
    private lateinit var spMedicamento: Spinner
    private lateinit var buttonAgregar: Button

    private var pacienteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        etNombres = findViewById(R.id.etNombres)
        etApellidos = findViewById(R.id.etApellidos)
        etEdad = findViewById(R.id.etEdad)
        etEnfermedad = findViewById(R.id.etEnfermedad)
        etNumeroHabitacion = findViewById(R.id.etNumeroHabitacion)
        etNumeroCama = findViewById(R.id.etNumeroCama)
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento)
        spMedicamento = findViewById(R.id.spMedicamento)
        buttonAgregar = findViewById(R.id.button)

        pacienteId = intent.getStringExtra("id_paciente")
        etNombres.setText(intent.getStringExtra("nombres"))
        etApellidos.setText(intent.getStringExtra("apellidos"))
        etEdad.setText(intent.getIntExtra("edad", 0).toString())
        etEnfermedad.setText(intent.getStringExtra("enfermedad"))
        etNumeroHabitacion.setText(intent.getIntExtra("numero_habitacion", 0).toString())
        etNumeroCama.setText(intent.getIntExtra("numero_cama", 0).toString())
        etFechaNacimiento.setText(intent.getStringExtra("fecha_nacimiento"))

        etFechaNacimiento.setOnClickListener {
            showDatePickerDialog()
        }

        if (pacienteId == null) {
            buttonAgregar.text = "Añadir"
            buttonAgregar.setOnClickListener {
                if (validarDatos()) {
                    addPacienteToDatabase()
                }
            }
        } else {
            buttonAgregar.text = "Actualizar"
            buttonAgregar.setOnClickListener {
                if (validarDatos()) {
                    updatePacienteInDatabase()
                }
            }
        }

        loadMedicamentos()

        val btnAtras = findViewById<ImageView>(R.id.img_GoBack)

        btnAtras.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            etFechaNacimiento.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun loadMedicamentos() {
        CoroutineScope(Dispatchers.IO).launch {
            val connection = ClassConexion().cadenaConexion()
            val medicamentos = mutableListOf<Pair<Int, String>>()

            connection?.let {
                try {
                    val statement = it.createStatement()
                    val resultSet = statement.executeQuery("SELECT id_medicamento, nombre_medicamento FROM Medicamentos")

                    while (resultSet.next()) {
                        val idMedicamento = resultSet.getInt("id_medicamento")
                        val nombreMedicamento = resultSet.getString("nombre_medicamento")
                        medicamentos.add(Pair(idMedicamento, nombreMedicamento))
                    }

                    resultSet.close()
                    statement.close()
                    it.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(this@Activity_Paciente, android.R.layout.simple_spinner_item, medicamentos.map { it.second })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spMedicamento.adapter = adapter

                // Seleccionar el medicamento adecuado si hay uno disponible
                val idMedicamento = intent.getIntExtra("id_medicamento", -1)
                val position = medicamentos.indexOfFirst { it.first == idMedicamento }
                if (position >= 0) {
                    spMedicamento.setSelection(position)
                }
            }
        }
    }

    private fun validarDatos(): Boolean {
        val nombres = etNombres.text.toString().trim()
        val apellidos = etApellidos.text.toString().trim()
        val edad = etEdad.text.toString().trim().toIntOrNull()
        val enfermedad = etEnfermedad.text.toString().trim()
        val numeroHabitacion = etNumeroHabitacion.text.toString().trim().toIntOrNull()
        val numeroCama = etNumeroCama.text.toString().trim().toIntOrNull()
        val fechaNacimiento = etFechaNacimiento.text.toString().trim()

        if (nombres.isEmpty() || apellidos.isEmpty() || edad == null || edad <= 0 || enfermedad.isEmpty() ||
            numeroHabitacion == null || numeroHabitacion <= 0 || numeroCama == null || numeroCama <= 0 || fechaNacimiento.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun addPacienteToDatabase() {
        val idPaciente = UUID.randomUUID().toString()
        val nombres = etNombres.text.toString()
        val apellidos = etApellidos.text.toString()
        val edad = etEdad.text.toString().toInt()
        val enfermedad = etEnfermedad.text.toString()
        val numeroHabitacion = etNumeroHabitacion.text.toString().toInt()
        val numeroCama = etNumeroCama.text.toString().toInt()
        val fechaNacimiento = etFechaNacimiento.text.toString()
        val idMedicamento = spMedicamento.selectedItemPosition + 1

        CoroutineScope(Dispatchers.IO).launch {
            val connection = ClassConexion().cadenaConexion()

            connection?.let {
                try {
                    val insertPacienteSQL = """
                    INSERT INTO Pacientes (id_paciente, nombres, apellidos, edad, enfermedad, numero_habitacion, numero_cama, fecha_nacimiento, id_medicamento)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """
                    val preparedStatement: PreparedStatement = it.prepareStatement(insertPacienteSQL)
                    preparedStatement.setString(1, idPaciente)
                    preparedStatement.setString(2, nombres)
                    preparedStatement.setString(3, apellidos)
                    preparedStatement.setInt(4, edad)
                    preparedStatement.setString(5, enfermedad)
                    preparedStatement.setInt(6, numeroHabitacion)
                    preparedStatement.setInt(7, numeroCama)
                    preparedStatement.setString(8, fechaNacimiento)
                    preparedStatement.setInt(9, idMedicamento)

                    preparedStatement.executeUpdate()
                    preparedStatement.close()

                    it.close()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Activity_Paciente, "Paciente añadido exitosamente", Toast.LENGTH_SHORT).show()
                        finish()  // Cierra la actividad y regresa a la anterior
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Activity_Paciente, "Error al añadir el paciente", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updatePacienteInDatabase() {
        val nombres = etNombres.text.toString()
        val apellidos = etApellidos.text.toString()
        val edad = etEdad.text.toString().toInt()
        val enfermedad = etEnfermedad.text.toString()
        val numeroHabitacion = etNumeroHabitacion.text.toString().toInt()
        val numeroCama = etNumeroCama.text.toString().toInt()
        val fechaNacimiento = etFechaNacimiento.text.toString()
        val idMedicamento = spMedicamento.selectedItemPosition + 1

        CoroutineScope(Dispatchers.IO).launch {
            val connection = ClassConexion().cadenaConexion()

            connection?.let {
                try {
                    val updatePacienteSQL = """
                        UPDATE Pacientes 
                        SET nombres = ?, apellidos = ?, edad = ?, enfermedad = ?, numero_habitacion = ?, numero_cama = ?, fecha_nacimiento = ?, id_medicamento = ?
                        WHERE id_paciente = ?
                    """
                    val preparedStatement: PreparedStatement = it.prepareStatement(updatePacienteSQL)
                    preparedStatement.setString(1, nombres)
                    preparedStatement.setString(2, apellidos)
                    preparedStatement.setInt(3, edad)
                    preparedStatement.setString(4, enfermedad)
                    preparedStatement.setInt(5, numeroHabitacion)
                    preparedStatement.setInt(6, numeroCama)
                    preparedStatement.setString(7, fechaNacimiento)
                    preparedStatement.setInt(8, idMedicamento)
                    preparedStatement.setString(9, pacienteId)

                    preparedStatement.executeUpdate()
                    preparedStatement.close()

                    it.close()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Activity_Paciente, "Paciente actualizado exitosamente", Toast.LENGTH_SHORT).show()
                        finish()  // Cierra la actividad y regresa a la anterior
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Activity_Paciente, "Error al actualizar el paciente", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
