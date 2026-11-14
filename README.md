# SDist: laboratorios de Sistemas Distribuidos

Ejercicios cliente-servidor en Java: sockets TCP y UDP, hilos, un servidor de colas de mensajes con un protocolo propio, Java RMI y RabbitMQ.

Asignatura **Sistemas Distribuidos**, 2.º curso, 2.º cuatrimestre, curso 2022-2023. Grado en Ingeniería Informática, Universidad de Valladolid.

## Qué hace

| Carpeta | Contenido |
| --- | --- |
| `minileccion-1`, `minileccion-1.1` | Hilos en Java: `TimePrinter`, y productor-consumidor sobre una cola compartida (`hilosjava`). |
| `minileccion-1.2` | Sockets: `InetAddress`, un servidor TCP y servidores de eco (sencillo, en mayúsculas y multihilo). |
| `minileccion-2` | `MultiMap` / `MapQueue`: colas de mensajes indexadas por clave. |
| `minileccion-3` | Java RMI: servicio `Hello` con cliente, servidor y lanzador. |
| `laboratorio-1` | Servidor TCP multihilo de registro de usuarios ("NETFLIX"): recibe `usuario-contraseña` y los guarda en un `ConcurrentHashMap`. Incluye las tres rondas de correcciones de la entrega. |
| `laboratorio-2` | Servidor TCP de colas de mensajes con un *pool* de hilos y un protocolo de mensajes serializados (`HELLO`, `PUSH`, `PULL_WAIT`, `PULL_NOWAIT`...). Incluye variantes con modificaciones (la sesión debe empezar por `HELLO`; un protocolo de listas de reproducción `AUTH`/`ADD2PL`/`WAIT4PL`/`NEWCHAP`/`OVERLOAD`) y sus correcciones. |
| `seminario-1` | Comunicación UDP: eco, eco con sesión y servidor de colas de mensajes sobre UDP (versiones 1 y 2, la segunda con sesiones y `PULL_PROMISE`). |
| `seminario-2` | Mensajería con RabbitMQ: *hello world*, colas de trabajo (`NewTask`/`Worker`), *publish/subscribe* (`EmitLog`/`ReceiveLogs`) y *routing* (`EmitLogDirect`/`ReceiveLogsDirect`). |

Parte del código es material base de la asignatura (prof. César Llamas Bello), que se amplió o modificó en las prácticas. Cada carpeta conserva sus `README` originales. El enunciado de la lección 2 está en [`laboratorio-2/README.pdf`](laboratorio-2/README.pdf).

## Tecnologías

- Java (sockets `java.net`, `java.util.concurrent`, serialización de objetos, RMI)
- RabbitMQ Java client 5.7.1 con SLF4J (los JAR están incluidos en `seminario-2/`)
- Bash para los scripts de lanzamiento

## Lo que aprendí / Aspectos técnicos destacados

- **Un hilo por cliente**: el servidor del laboratorio 2 acepta conexiones en un hilo propio y entrega cada `Socket` a un `Sirviente` (`Runnable`) que ejecuta un `ExecutorService` de 5 hilos.
- **Protocolo de aplicación tipado**: las primitivas son un `enum` (`Primitiva`). Los mensajes (`MensajeProtocolo`) son `Serializable` y viajan por `ObjectInputStream`/`ObjectOutputStream`, y los malformados lanzan `MalMensajeProtocoloException`.
- **Estado compartido seguro**: los accesos al `MultiMap` compartido se protegen con `synchronized` y se usa `ConcurrentHashMap` y `AtomicInteger` para los contadores de instancias.
- **Del mismo problema con TCP, UDP y colas**: el servicio de colas con clave se implementa sobre TCP (laboratorio 2) y sobre UDP con sesiones (seminario 1), y se compara con un *broker* real (RabbitMQ).

## Estructura del repositorio

```text
SDist/
├── minileccion-1/ minileccion-1.1/ minileccion-1.2/ minileccion-2/ minileccion-3/
├── laboratorio-1/
│   ├── EchoSencillo/{maquinacliente,maquinaservidor}/
│   └── correcciones/{primera,segunda,tercera}Correccion/
├── laboratorio-2/
│   ├── programa1/                  # Versión base del servidor de colas
│   ├── modificacion/ modificacion1/ modificacionLaboratorio/
│   ├── correciones/
│   └── README.TXT  README.pdf
├── seminario-1/                    # UDP: EjemploEcho, EjemploEchoSession, UDPMultimapa, UDPMultimapaV2
└── seminario-2/                    # RabbitMQ + JAR de dependencias
```

Junto a los fuentes hay `.class` ya compilados en las carpetas de cada paquete.

## Requisitos

- JDK 8 o superior.
- Para `seminario-2`: un servidor RabbitMQ en `localhost`.

## Cómo compilar y ejecutar

Los ejercicios con `README.md` propio en `seminario-1/` explican paso a paso cómo compilarlos y lanzarlos. Por ejemplo, el servidor de colas del laboratorio 2 (puerto 2000):

```sh
cd laboratorio-2/programa1
javac -d . *.java
java protocol.servidor.Servidor          # terminal 1
java protocol.cliente.Cliente localhost  # terminal 2
```

Y para RabbitMQ:

```sh
cd seminario-2
javac -cp amqp-client-5.7.1.jar Send.java Recv.java
java -cp .:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar Recv   # terminal 1
java -cp .:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar Send   # terminal 2
```

Con OpenJDK 17 compilan `laboratorio-2/programa1` y, en `seminario-2`, `Send`, `Recv`, `NewTask`, `EmitLog`, `ReceiveLogs` y `ReceiveLogsDirect`. `Worker.java` y `EmitLogDirect.java` no compilan tal cual: a una función auxiliar le faltan los operadores de comparación (`if (length 0)`). No se ha probado la ejecución.

`seminario-2/rabbitmq-java-client` es un submódulo de git sin `.gitmodules`, así que al clonar queda vacío. Los JAR necesarios ya están en la carpeta.

## Autoría

Javier García González (JavivuG).

---

Parte de [UVa-Projects](https://github.com/JavivuG/UVa-Projects).
