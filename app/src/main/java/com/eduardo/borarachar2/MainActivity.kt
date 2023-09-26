package com.eduardo.borarachar2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import java.math.RoundingMode
import java.text.DecimalFormat
import android.speech.tts.TextToSpeech
import android.widget.Button
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                tts!!.language = Locale.forLanguageTag("pt-BR")
            }
        }

        findViewById<EditText>(R.id.dinheiro).addTextChangedListener {
            onChange()
        }
        findViewById<EditText>(R.id.pessoas).addTextChangedListener {
            onChange()
        }

        findViewById<Button>(R.id.tts).setOnClickListener {
            var resultadoText = findViewById<TextView>(R.id.resultado).text.toString()
            tts!!.speak(resultadoText, TextToSpeech.QUEUE_FLUSH, null,"")
        }

        findViewById<Button>(R.id.share).setOnClickListener {
            var resultadoText = findViewById<TextView>(R.id.resultado).text.toString()
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, resultadoText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    fun onChange(){
        val pessoas = findViewById<EditText>(R.id.pessoas).text.toString().toIntOrNull()
        val dinheiro = findViewById<EditText>(R.id.dinheiro).text.toString().toDoubleOrNull()
        if (pessoas == null || dinheiro == null || pessoas == 0)  return;

        var resultado = dinheiro / pessoas
        var resultadoText = findViewById<TextView>(R.id.resultado)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN

        resultadoText.setText("R$ ${df.format(resultado)}")
    }
}