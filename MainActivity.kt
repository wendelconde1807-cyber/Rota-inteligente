package br.com.rotainteligente

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.*

class MainActivity : Activity() {
    private lateinit var pay: EditText
    private lateinit var km: EditText
    private lateinit var minutes: EditText
    private lateinit var fuelPrice: EditText
    private lateinit var consumption: EditText
    private lateinit var maintenance: EditText
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(36, 36, 36, 36) }
        root.addView(TextView(this).apply { text = "ROTA INTELIGENTE"; textSize = 25f })
        root.addView(TextView(this).apply { text = "Calculadora de ganhos • Android"; textSize = 14f })
        pay = field("Pagamento (R$)", "31.14", root)
        km = field("Distância total (km)", "17.1", root)
        minutes = field("Tempo estimado (min)", "50", root)
        fuelPrice = field("Gasolina (R$/L)", "6.50", root)
        consumption = field("Consumo (km/L)", "35", root)
        maintenance = field("Manutenção (R$/km)", "0.15", root)
        val calc = Button(this).apply { text = "CALCULAR GANHO"; setOnClickListener { calculate() } }
        root.addView(calc)
        result = TextView(this).apply { textSize = 18f; setPadding(0, 24, 0, 24) }
        root.addView(result)
        root.addView(Button(this).apply {
            text = "Ativar bolha flutuante"
            setOnClickListener {
                if (!Settings.canDrawOverlays(this@MainActivity)) {
                    startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
                } else startService(Intent(this@MainActivity, FloatingBubbleService::class.java))
            }
        })
        root.addView(TextView(this).apply {
            text = "Leitura automática: habilite o serviço Rota Inteligente em Configurações > Acessibilidade. A leitura depende da estrutura visual/textual de cada app e deve ser testada individualmente."
            textSize = 13f
        })
        setContentView(ScrollView(this).apply { addView(root) })
        calculate()
    }

    private fun field(label: String, value: String, root: LinearLayout): EditText {
        root.addView(TextView(this).apply { text = label })
        return EditText(this).also { it.setSingleLine(); it.setText(value); it.inputType = 8194; root.addView(it) }
    }

    private fun num(e: EditText) = e.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0
    private fun br(v: Double) = "R$ " + String.format(java.util.Locale("pt", "BR"), "%.2f", v)

    private fun calculate() {
        val p = num(pay); val d = num(km); val t = num(minutes)
        val fuel = if (num(consumption) > 0) d / num(consumption) * num(fuelPrice) else 0.0
        val upkeep = d * num(maintenance)
        val net = p - fuel - upkeep
        val hourly = if (t > 0) net * 60 / t else 0.0
        val perKm = if (d > 0) net / d else 0.0
        val verdict = when {
            net <= 0 -> "RUIM"
            hourly >= 30 && perKm >= 1.50 -> "BOA"
            hourly >= 20 -> "REGULAR"
            else -> "RUIM"
        }
        result.text = "Lucro estimado: ${br(net)}\nCombustível: ${br(fuel)}\nManutenção: ${br(upkeep)}\nLucro/hora: ${br(hourly)}\nLucro/km: ${br(perKm)}\nClassificação: $verdict\n\nEstimativa: confira se distância e tempo incluem todo o deslocamento."
    }
}
