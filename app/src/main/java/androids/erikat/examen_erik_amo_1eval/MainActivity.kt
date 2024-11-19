package androids.erikat.examen_erik_amo_1eval

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var countDownTimer: CountDownTimer //Timer
    var juego = Juego_De_Palabras() //Objeto de tipo Juego_De_Palabras
    lateinit var modificada:TextView
    lateinit var pista:TextView
    lateinit var respuesta:EditText
    lateinit var puntos:TextView
    lateinit var tiempo:TextView
    lateinit var imagen:ImageView
    lateinit var comprobar:Button
    lateinit var jugar:Button
    var palabraOriginal = "" //String que guarda la palabra a buscar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        modificada = findViewById(R.id.palabraModificadaTView)
        pista = findViewById(R.id.pistaTView)
        respuesta = findViewById(R.id.palabraEText)
        puntos = findViewById(R.id.puntosTField)
        tiempo = findViewById(R.id.tiempoTField)
        imagen = findViewById(R.id.imageView2)
        comprobar = findViewById(R.id.comprobarBtt)
        jugar = findViewById(R.id.playBtt)
        imagen.setImageResource(R.drawable.ic_launcher_foreground) //Pongo una imagen cualquiera

        /*Listener del botón de jugar*/
        jugar.setOnClickListener{
            imagen.setImageResource(R.drawable.ic_launcher_foreground) //Pongo una imagen cualquiera
            respuesta.isEnabled = true
            jugar.isEnabled = false
            comprobar.isEnabled = true
            nextWord() //Carga una palabra
            countDownTimer = object : CountDownTimer(180000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tiempo.text = "Tiempo restante: ${calcularTiempo(millisUntilFinished)}"
                }

                override fun onFinish() {
                    finalizar(juego.puntos >= 50)
                }
            } //Creo el timer
            countDownTimer.start() //Comienza a funcionar
        }

        /*Listener del botón de comprobar*/
        comprobar.setOnClickListener{
            var palabraAProbar:String = respuesta.text.toString().lowercase() //Cojo lo que haya escrito y lo paso a minúsculas
            if (palabraAProbar == palabraOriginal){
                juego.puntos+=10
            } else {
                if (palabraAProbar.length == palabraOriginal.length) {
                    var puntuacionLetras:Int = 0
                    for (indice in palabraOriginal.indices) {
                        if (palabraAProbar[indice] == palabraOriginal[indice]){
                            puntuacionLetras++ //Cada letra bien, medio punto
                        }
                    }
                    puntuacionLetras/=2 //Como no puedo poner 0.5 puntos, pongo 1 punto y divido
                    if (puntuacionLetras>0) {
                        juego.puntos += puntuacionLetras
                    } else {
                        juego.puntos-=1 //Si no acierta ningún caracter, -1 punto
                    }
                } else {
                    juego.puntos-=2 //Si no acierta nada, -2 puntos
                }
            }
            if (juego.puntos <0){ //Si se queda sin puntos:
                countDownTimer.cancel() //Paro el temporizador y reinicio
                countDownTimer.onFinish()
            } else {
                puntos.text = "Puntos restantes: ${juego.puntos}"
                nextWord() //Siguiente palabra
            }
        }

        reiniciarElementos(); //Reinicia las clases al terminar de crear todos los objetos
    }

    /*FUNCIÓN DE CAMBIO DE PALABRA*/
    private fun nextWord() {
        palabraOriginal = juego.obtener_Palabra()
        var numPista = juego.obtener_Pista();
        if (numPista==3){
            randomNum = (1..20).toList().random() //Cojo un número aleatorio y lo guardo en una variable para su futuro uso
        }
        var descolocar:Boolean = (listOf(true, false).random()) //50% de posibilidades de colocar o descolocar
        pista.text = "${juego.listaMensajesPistas[numPista]}${if(numPista==3)" $randomNum veces" else ""} - ${if(descolocar) "Descolocado" else "Colocado"}"
        modificada.text = (when(numPista){ //Switch dependiendo del número de pista que salga
            0-> palabraOriginal.transformar(descolocar, funcion_cambio_caracter)
            1-> palabraOriginal.transformar(descolocar, funcion_cambio_vocal)
            2-> palabraOriginal.transformar(descolocar, funcion_posicion_caracter)
            else -> palabraOriginal.transformar(descolocar, funcion_cambio_posiciones)
        })
    }

    /*FUNCIÓN QUE SE EJECUTA CUANDO FINALIZA EL PROGRAMA*/
    private fun finalizar(ganador: Boolean) {
        Toast.makeText(this, if(ganador) "¡¡¡GANADOR!!!" else "Perdiste, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
        imagen.setImageResource(if (ganador) R.drawable.baseline_star_24 else R.drawable.baseline_mood_bad_24) //Dos fotos de recursos para hacer la prueba
        reiniciarElementos()
    }

    /*FUNCIÓN CON LA QUE SE CALCULA EL TIEMPO EN FORMATO MINUTOS:SEGUNDOS*/
    @SuppressLint("DefaultLocale")
    private fun calcularTiempo(millisUntilFinished: Long): String {
        var minutos:Int
        var segundos:Int

        segundos = millisUntilFinished.toInt()/1000
        minutos = segundos/60
        segundos -= minutos*60

        return "${minutos}:${String.format("%02d", segundos)}" //Devuelve el tiempo en formato m:ss
    }

    /*FUNCION QUE DEJA TODOS LOS ELEMENTOS COMO ESTABAN AL PRINCIPIO*/
    private fun reiniciarElementos() {
        juego.puntos = resources.getInteger(R.integer.EurosInicio)
        modificada.text = ""
        pista.text = ""
        respuesta.text = null
        respuesta.hint = "Introduce palabra"
        respuesta.isEnabled = false //Con esto, me ahorro tener que guardar el KeyListener para poder o no escribir
        comprobar.isEnabled = false
        tiempo.text = "Tiempo restante: 3:00"
        puntos.text = "Puntos restantes: ${resources.getInteger(R.integer.EurosInicio)}"
        jugar.isEnabled = true
    }
}