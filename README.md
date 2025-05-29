# Examen Final

## Instrucciones

1. Para iniciar, debe crear un *Fork* del repositorio:

![fork button](images/fork.png)

2. Vaya la pestaña de **actions** de su repositorio. Si ve un botón verde, haga clic en él para poder ejecutar las pruebas en el futuro.

![actions tab](images/actions.png)

3. Clone el nuevo repositorio en su computadora y ábralo en IntelliJ.

4. Construya/compile la aplicación en IntelliJ.

5. Ejecute las pruebas unitarias.

6. No se preocupe si todas o la mayoría de las pruebas fallan. Al terminar el examen, todas las pruebas deberían funcionar.
___

## Introducción

- Todos los ejercicios deben compilar para poder ser probados. Si por lo menos uno de los ejercicios no compila, la nota sera de **cero** puntos.
- Si el código original de un ejercicio no se modifica o la intención de su resolución no es clara, la nota del ejercicicio será de **cero puntos**, aún si hay pruebas que sí pasen para dicho ejercicio.
- NO agregue nuevos métodos `main()`, de lo contrario ninguna prueba podrá ejecutarse.
- NO cambie la firma de los métodos existentes (no agrege más parámetros ni cambie el nombre), estos son utilizados para probar su código.
- NO haga cambios en las pruebas, esto resulta en un **cero inmediato**.
- Puede agregar nuevas clases y/o archivos, como sea necesario.
- En la pestaña de **Actions** podrá ver como las pruebas se ejecutan con su código implementado (si hace `git push` de un nuevo commit).
___

## Ejercicio 1

Un **trie** o árbol de prefijos es una estructura de datos utilizada para almacenar y recuperar eficientemente llaves en un set de datos de strings. Existen varias aplicaciones para esta estructra de datos, como por ejemplo: autocompletar o autocorrector.

Implemente la clase **Trie**:

- `Trie()` Instancia el objeto de **Trie**.
- `void insert(String word)` Inserta el string `word` en el árbol de prefijos.
- `int countWordsEqualTo(String word)` Retorna la cantidad de instancias de la palabra dentro del árbol de prefijos.
- `int countWordsStartingWith(String prefix)` Retorna la cantidad de strings, dentro del árbol de prefijos, que inician con el prefijo `prefix`.
- `void erase(String word)` Elimina el string `word` del árbol de prefijos.

### Ejemplo 1.1

```java
Trie trie = new Trie();
trie.insert("apple");               // Inserta "apple".
trie.insert("apple");               // Inserts otra instancia de "apple".
trie.countWordsEqualTo("apple");    // Existen dos instancias de "apple", retorne 2.
trie.countWordsStartingWith("app"); // "app" es un prefijo de "apple", entonces retorne 2.
trie.erase("apple");                // Elimina una instancia de "apple".
trie.countWordsEqualTo("apple");    // Ahora solo existe una instancia de "apple", así que retorne 1.
trie.countWordsStartingWith("app"); // Retorne 1
trie.erase("apple");                // Elimina la última instancia de "apple". Ahora el árbol de prefijos está vacío.
trie.countWordsStartingWith("app"); // Retorne 0
```
___

## Ejercicio 2

Cree un API capaz de administrar un árbol de prefijos.

Para ello, implemente los siguientes endpoints utilizando Spring Boot:

- **POST /trie/{word}** agrega una nueva palabra al árbol de prefijos.
  - Debe especificar la palabra en la ruta de la solicitud.
  - La palabra debe ser en letras minúsculas, pertenecer al alfabeto y no estar vacía.
  - Si se intenta agregar una palabra existente, se incrementa la cantidad de ocurrencias para dicha palabra.
  - Si la palabra fue agregada correctamente, el código HTTP de la respuesta será: `CREATED - 201`.
  - Si alguna palabra no puede ser agregada, debe retornarse un código HTTP `BAD REQUEST - 400`
- **GET /trie/{word}/count** obtiene la cantidad de ocurrencias de la palabra dentro del árbol de prefijos.
- **GET /trie/{prefix}/prefix** obtiene la cantidad de palabras dentro del árbol que inician con el prefijo especificado.
- **DELETE /trie/{word}** elimina una ocurrencia de la palabra dentro del árbol de prefijos.
  - Si la solicitud es exitosa, la respuesta HTTP debe retornar `NO CONTENT - 204`.

### POST /trie/{word}

#### Ejemplo de petición correcta

```http request
POST /trie/apple
```

### GET /trie/{word}/count

#### Ejemplo de petición correcta

```http request
GET /trie/apple/count
```

#### Ejemplo de respuesta exitosa
```json
{
    "word": "apple",
    "wordsEqualTo": 2
}
```

### GET /trie/{word}/prefix

#### Ejemplo de petición correcta

```http request
GET /trie/apple/prefix
```

#### Ejemplo de respuesta exitosa
```json
{
    "word": "ap",
    "wordsStartingWith": 2
}
```

### DELETE /trie/{word}

#### Ejemplo de petición correcta

```http request
DELETE /trie/apple
```