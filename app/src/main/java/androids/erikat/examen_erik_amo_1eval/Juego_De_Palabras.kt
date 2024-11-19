package androids.erikat.examen_erik_amo_1eval

class Juego_De_Palabras {
    val listaPalabras:MutableList<String> = mutableListOf(
        "perro",
        "gato",
        "esperpento",
        "sota",
        "mercadero",
        "estudiante",
        "maravilla",
        "superdotado",
        "programa",
        "pesado"
    )
    val listaMensajesPistas:List<String> = listOf(
        "Faltan caracteres",
        "Cambio de vocal",
        "Posición de caracter",
        "Posiciones sumadas" //Suma a todas las letras de la palabra x posiciones, será definido por un random entre n números
    )
    var puntos:Int = 2
        get() = field
        set(value){
            field = value
        }

    fun obtener_Palabra():String{
        return listaPalabras.random(); //Devuelve una palabra aleatoria
    }
    fun obtener_Pista():Int{
        var pista:Int = listaMensajesPistas.indices.random() //Devuelve un valor aleatorio entre 0 y list.length-1
        return pista
    }
}