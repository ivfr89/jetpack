# Kotlin Android Clean Architecture + MVVM + Coroutines + Jetpack (ViewModel, Room, Paging Library, Navigation) + Koin

Consumo de servicios API Rest en JSON a través de JSONPlaceholder (https://jsonplaceholder.typicode.com/) usando una arquitectura Clean
y utilizando Jetpack como biblioteca para la carga de tres tipos de entidades; usuarios, posts y fotos. 

Se integra un tamaño de página de carga de 10 entidades para que pueda verse la aplicación de los principios de scroll infinito junto con Room.

Se ha dejado en un segundo plano el diseño, únicamente está centrado en la parte del código, mostrando títulos y descripciones en
la sección de Post, un pequeño resumen del usuario en su sección, y títulos y fotos asociadas.


## Estructura del proyecto


Es una arquitectura clean, siguiendo un patrón MVVM

Gradle se estructura:

-A nivel de proyecto: Aquí se definen las bibliotecas y cada una de las versiones
-A nivel de módulo: Se utilizan alias para implementar cada una de estas versiones

Se separa el concepto de versión / implementación


El código se divide en varios packagename, donde las raices de los mismos son:

core : Aspectos relacionados con extensiones y clases abstractas propias de arquitectura

data:  Endpoints, manejadores de conectividad, y todo lo relacionado a los modelos de servidor y respuestas.
También se incluye aquí la base de datos y sus modelos correspondientes

di: Definición de las dependencias, a través del inyector koin

domain: Lógica del dominio, aquí además de usar un patrón repository, añado los casos de uso asociados a la carga de entidades
mediante PhotoPaging, PostPaging y UserPaging.

presentation: Pantallas, ViewModels y adapters, junto con sus posibles estados

utils: Fichero Constants

Los estados asociados a cada una de las pantallas se representan mediante un NombreActividadScreenState, y representa el estado de la pantalla en ese momento. 

En el proyecto se usa jetpack para los ViewModels, y se utilizan corutinas de kotlin a partir de la versión 1.3 para la programación multihilo. Es igualmente aplicable al uso de programación reactiva.
Aquí los casos de uso se ejecutan en un contexto que permite llamadas asíncronas, tanto en el consumo de API como en cualquier operación definida dentro del viewmodel. Estos casos de uso realizan la parte funcional y el consumo de APIs.

Este consumo de APIs se hace a través del patrón repository AccountRepository. Lleva un manejador de conexiones para verificar que existe conexión y se envían los datos asociados y las llamadas correspondientes definidas en ApiService



Las respuestas llevan un Either como devolución en las llamadas: Ejemplo


```
fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure,R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> {

                    Either.Left(Failure.ServerErrorCode(response.code()))
                }
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerException(exception))
        }
    }
```

Either es un monad, unión disjuntiva, devuelve siempre un valor, o bien la clase izquierda (en cuyo caso será un fallo) o bien la derecha (entonces será el dato que buscas). 
Esto es así ya que de esta forma se controla cada uno de los fallos posibles a la hora de devolver la petición; Failure.NetworkConnection es un ejemplo, pero la estructura permite un modelo abierto de códigos de error de petición o extender de la clase abstracta CustomFailure para decidir qué casos de uso tienen que errores.

Los BoundaryCallback actúan como casos de uso en la parte de carga inicial del proyecto, los cuales implementan la siguiente interface:

```
interface BoundaryCoroutine<in Scope> where Scope : CoroutineScope
{
    fun execute(scope: Scope, dataIncome: ()->Unit)
    {
        scope.launch {

            val deferred = async { dataIncome.invoke() }

            withContext(Dispatchers.Main)
            {
                deferred.await()
            }
        }
    }
}
```

De esta forma los dos métodos, onZeroItemsLoaded y onItemAtEndLoaded de los boundary callbacks puede usar este método a través de corutinas

Los EntityDao devuelven un DataSource.Factory que permite de forma automática paginar correctamente los adaptadores

