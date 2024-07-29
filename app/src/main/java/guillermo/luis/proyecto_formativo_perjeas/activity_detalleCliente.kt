package guillermo.luis.proyecto_formativo_perjeas

import Modelo.ListaPacientes
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_detalleCliente : AppCompatActivity() {
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_cliente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ArrowBack = findViewById<ImageView>(R.id.ArrowBack)
        ArrowBack.setOnClickListener {
            onBackPressed()
        }
        val paciente = intent.getParcelableExtra<ListaPacientes>("paciente")

        if (paciente != null) {
            findViewById<TextView>(R.id.textView2).text = paciente.nombres
            findViewById<TextView>(R.id.textView3).text = paciente.apellidos
            findViewById<TextView>(R.id.textView4).text = paciente.edad.toString()
            findViewById<TextView>(R.id.textView5).text = paciente.enfermedad
            findViewById<TextView>(R.id.textView6).text = paciente.numeroHabitacion.toString()
            findViewById<TextView>(R.id.textView7).text = paciente.numeroCama.toString()
            findViewById<TextView>(R.id.textView8).text = paciente.fechaNacimiento
            findViewById<TextView>(R.id.textView9).text = paciente.idMedicamento.toString()
        }
    }
}
