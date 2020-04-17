EJEMPLO MINIMO 1

La EETAC está interesada en construir un juego que presente el mapa del
campus PMT donde se pueden ver los edificios, el lago, el parking, la huerta,
etc. Sobre este mapa, un usuario podrá realizar, además de los movimientos
básicos, diferentes acciones relacionadas con la vida virtual del campus y en
concreto dentro del espacio de restauración. Por este motivo, propone a sus
alumnos de DSA la construcción de una prueba piloto con las siguientes
operaciones:

- Listado de productos ordenado (ascendentemente) por precio

- Realizar un pedido (que puede estar formado por diferentes productos y en
diferentes cantidades) por parte de un usuario identificado

- Servir un pedido. Los servicios se realizan en orden de llegadas

- Listado de pedidos de un usuario que ya hayan sido realizados

- Listado de productos ordenado (descendentemente) por número de ventas

SE PIDE:
PARTE I: 4 puntos

1.- Especificación del componente que implementará las operaciones descritas
anteriormente: (ProductManager.java , interfaz Java)

2.- Implementación de una Fachada (patrón de diseño) que implemente el
interfaz definido previamente (ProductManagerImpl.java, clase Java).

2.1 Elección de las estructuras de datos

2.3 La fachada deberá implementarse como un Singleton.

2.4 Todos los métodos deberán tener una TRAZA (a nivel de INFO) de
LOG4J que muestre el valor de los parámetros al inicio de los métodos y
al final. También debe contemplarse trazas de otros niveles (ERROR o
FATAL)

3.- Implementación de un test (JUNIT) sobre el componente desarrollado con
los siguientes puntos:

- método setUp que inicializa la estructura de datos

- método tearDown que libera los recursos

- método de test para realizar un pedido

- método de test para servir un pedido

PARTE II: 4,5 puntos
1.- Implementar un servicio REST que permita realizar las operaciones
especificadas en la primera parte del ejercicio

NOTA: El servicio debe utilizar el componente construido en el punto anterior
(ProductManager)
