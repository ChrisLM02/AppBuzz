package ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appbuzz.R
import com.example.appbuzz.ui.theme.AppBuzzTheme
import data.Pregunta
import java.util.Random

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBuzzTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen() {

    /*TODO =
        *  -> Cambiar forma para añadir las preguntas*/

    val listaPreguntas: ArrayList<Pregunta> = ArrayList()

    val p1: Pregunta = Pregunta(
        "El Club Deportivo Tenerife fue fundado en 1912", true, R.drawable.cdt_escudo
    )
    val p2: Pregunta = Pregunta(
        "El Girona eliminó al club tinerfeño en los playoffs de la temporada 22/23",
        true,
        R.drawable.girona_escudo
    )
    val p3: Pregunta = Pregunta(
        "La UD Las Palmas ha disputado menos partidos en 1ª división que el CD Tenerife",
        false,
        R.drawable.udlp_escudo
    )
    val p4: Pregunta =
        Pregunta(
            "El máximo goleador de la historia del CD Tenerife es Suso Santana",
            false,
            R.drawable.suso_imagen
        )
    val p5: Pregunta = Pregunta(
        "Aun así el CD Tenerife sigue siendo superior a la UD Las Palmas (no acepto discusion)",
        true,
        R.drawable.cdt_udlp_escudos
    )

    listaPreguntas.add(p1)
    listaPreguntas.add(p2)
    listaPreguntas.add(p3)
    listaPreguntas.add(p4)
    listaPreguntas.add(p5)

    var preguntaActual by remember {
        mutableStateOf(0)
    }

    var respuestaUsuario: Boolean? by remember {
        mutableStateOf(null)
    }

    var imagen: Int? by remember {
        mutableStateOf(null)
    }

    val pregunta = if (preguntaActual < listaPreguntas.size) {
        listaPreguntas[preguntaActual]
    } else if (preguntaActual > listaPreguntas.size) {
        listaPreguntas[0]
    } else {
        null
    }

    var textoPregunta by remember {
        mutableStateOf("Pregunta Default")
    }

    var textoResultado by remember {
        mutableStateOf("")
    }

    var colorBotonVerdadero by remember { mutableStateOf(Color.White) }
    var colorBotonFalso by remember { mutableStateOf(Color.White) }

    if (pregunta != null) {
        val resultado = pregunta.resultado
        imagen = pregunta.imagen
        textoPregunta = pregunta.pregunta

        if (resultado) {
            textoResultado.equals("Respuesta Correcta")
        } else {
            textoResultado.equals("Respuesta Incorrecta")
        }

        Column(
            verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            /*TODO =
            *  -> Seleccionar pregunta segun clase "Pregunta"
            *  [!] El texto actual es temporal, solo para ordenar los elementos
            * */

            Text(
                text = textoPregunta,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )

            /*TODO =
            *  -> Seleccionar imagen segun clase "Pregunta"
            *  -> Cambiar en conjunto a la pregunta
            *  [!] La imagen actual es temporal, solo para ordenar los elementos
            * */

            pregunta.imagen?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = "",
                    Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                        .size(250.dp)
                )
            }

            if (respuestaUsuario != null) {
                textoResultado = if (respuestaUsuario == resultado) {
                    "Respuesta correcta"
                } else {
                    "Respuesta incorrecta"
                }
            } else {
                textoResultado = ""
            }

            /*TODO =
            *  -> Seleccionar el texto segun la respuesta del usuario y la respuesta real
            *  -> El texto aparecera invisible hasta que se seleccione una de las dos respuestas
            *  -> Cambiar color del texto segun resultado
            *  [!] El texto actual es temporal, solo para ordenar los elementos
            * */

            Text(
                text = textoResultado,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        respuestaUsuario = true
                        colorBotonVerdadero =
                            if (respuestaUsuario == pregunta.resultado) Color.Green else Color.Red
                        colorBotonFalso = Color.Black
                        /*TODO -> Comprobar si es verdadero
                                              -> Cambiar color segun resultado */
                    }
                ) {
                    Text(text = "True", color = colorBotonVerdadero, fontSize = 16.sp)
                }

                Button(
                    onClick = {
                        respuestaUsuario = false
                        colorBotonFalso =
                            if (respuestaUsuario == pregunta.resultado) Color.Green else Color.Red
                        colorBotonVerdadero = Color.Black
                        /*TODO -> Comprobar si es falso
                                              -> Cambiar color segun resultado */
                    }
                ) {
                    Text(text = "False", color = colorBotonFalso, fontSize = 16.sp)
                }
            }


            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        preguntaActual =
                            (preguntaActual - 1 + listaPreguntas.size) % listaPreguntas.size
                        respuestaUsuario = null
                        /*TODO -> Volver a la pregunta anterior*/
                        if (preguntaActual == listaPreguntas.size - 1) {
                            textoPregunta = listaPreguntas.last().pregunta
                            imagen = listaPreguntas.last().imagen
                        }
                        colorBotonVerdadero = Color.Unspecified
                        colorBotonFalso = Color.Unspecified
                    }

                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Icono Flecha Atras")
                    Text(text = "ANTERIOR")
                }

                Button(
                    onClick = {
                        seleccionarPreguntaAlAzar(listaPreguntas.size) { nuevoIndice ->
                            preguntaActual = nuevoIndice
                            colorBotonVerdadero = Color.Black
                            colorBotonFalso = Color.Black
                        }
                    }
                ) {
                    Icon(Icons.Default.CompareArrows, contentDescription = "Icono Flechas Random")
                    Text(text = "RANDOM")
                }

                Button(
                    onClick = {
                        preguntaActual = (preguntaActual + 1) % listaPreguntas.size
                        respuestaUsuario = null
                        /*TODO -> Pasar a la siguiente pregunta*/
                        if (preguntaActual == 0) {
                            textoPregunta = listaPreguntas.first().pregunta
                            imagen = listaPreguntas.first().imagen
                        }
                        colorBotonVerdadero = Color.Unspecified
                        colorBotonFalso = Color.Unspecified
                    }

                ) {
                    Text(text = "SIGUIENTE")
                    Icon(Icons.Default.ArrowForward, contentDescription = "Icono Flecha Siguiente")
                }
            }
        }
    }
}

fun seleccionarPreguntaAlAzar(totalPreguntas: Int, onPreguntaSeleccionada: (Int) -> Unit) {
    val random = Random()
    val nuevoIndice = random.nextInt(totalPreguntas)
    onPreguntaSeleccionada(nuevoIndice)
}