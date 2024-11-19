package androids.erikat.examen_erik_amo_1eval

/*FUNCION DE ORDEN SUPERIOR QUE TRANSFORMA LA PALABRA*/
fun String.transformar(descolocada:Boolean, funcion:(Char, Int)->Char):String{
    var resultado:String = "" //String vacío
    var palabra:String
    if(descolocada){
        palabra = ""
        for (char in this.toList().shuffled()){ //Paso a lista, mezclo los índices con List.shuffled() y meto de caracter en caracter en el string
            palabra += char
        }
    } else {
        palabra = this
    }
    println(palabra)
    for (index in palabra.indices){
        resultado += funcion(palabra[index], index) //Le paso el caracter y el indice del caracter
    }
    return resultado
}


var abecedario_ESP:String = "abcdefghijklmnñopqrstuvwxyz" //String de abecedario que se utilizará más tarde
var vocales:String = "aeiou" //String con vocales que se utilizará más tarde

/*LAMBDA QUE EXPRESA EL CAMBIO DE CARACTER A _ DE FORMA ALEATORIA*/
var funcion_cambio_caracter:(Char, Int)->Char = { caracter, posicion ->
    if((1..100).toList().random()<=50){ //Cuando el número que se genere aleatoriamente entre 1 y 100 sea menor a 50 (50% de posibilidades), se pone '_' en vez del caracter
        '_'
    } else {
        caracter
    }
}

/*FUNCION QUE CAMBIA LA VOCAL A LA SIGUIENTE*/
var funcion_cambio_vocal:(Char, Int) ->Char = { caracter, posicion ->
    if (caracter in vocales){
        vocales[(vocales.indexOf(caracter)+1)%vocales.length]
        /*
        a -> 0 + 1 % 5 = 1 = e
        e -> 1 + 1 % 5 = 2 = i
        i -> 2 + 1 % 5 = 3 = o
        o -> 3 + 1 % 5 = 4 = u
        u -> 4 + 1 % 5 = 0 = a
         */
    } else {
        caracter
    }
}


/*FUNCION LAMBDA QUE CAMBIA LOS CARACTERES EN POSICIONES PARES POR SU VALOR POSICIONAL EN EL ALFABETO*/
var funcion_posicion_caracter:(Char, Int) -> Char = {caracter, posicion->
    if (posicion%2==0){
        abecedario_ESP.indexOf(caracter).toChar()
    } else {
        caracter
    }
}
//Cabe recalcar que esta función ha dado muchisimos problemas y no he conseguido implementarla bien. No guarda en ASCII el número, por lo que devuelve un caracter vacío


/*FUNCION LAMBDA QUE CAMBIA LA POSICIÓN ALFABÉTICA DE TODAS LAS LETRAS*/
var randomNum:Int = 0 //Esta variable se editará cuando salga la función de suma de posiciones. De este modo, no se hace el random cada iteración
var funcion_cambio_posiciones:(Char, Int)->Char= {caracter, posicion ->
    var numIndice = (abecedario_ESP.indexOf(caracter) + randomNum) % abecedario_ESP.length
    abecedario_ESP[numIndice]
}