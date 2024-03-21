# LABORATORIO 7 - APLICACIÓN DISTTRIBUIDA SEGURA EN TODOS SUS FRENTES

En este laboratorio, se llevará a cabo el desarrollo de una aplicación web robusta, priorizando la seguridad en cada etapa del proceso. La meta fundamental consiste en asegurar la autenticación, autorización e integridad de los usuarios que interactúen con la aplicación. Los resultados esperados comprenden la entrega del código fuente alojado en GitHub, un informe detallado disponible en el archivo README, y un video demostrativo alojado en AWS. Todo ello se logrará mediante la implementación de protocolos de seguridad sólidos, como HTTPS, y la incorporación de medidas para garantizar la confiabilidad y seguridad del acceso a servicios remotos. Este enfoque integral permitirá no solo evidenciar la seguridad de nuestro prototipo, sino también destacar su funcionalidad y eficacia en la protección de datos sensibles y la salvaguardia de la experiencia del usuario.
# Iniciando 
A continuación se indican una serie de instruciones para bajar y ejecutar el proyecto de manera exitosa:

Es **importante**❗tener instalado: 
- [MAVEN](https://maven.apache.org) : Manejo de las dependecias. 
- [GIT](https://git-scm.com) : Control de versiones.
- [JAVA](https://www.java.com/es/) : Lenguaje de programación (JDK 17).
- [AWS ACADEMI](https://awsacademy.instructure.com/): Para el uso de una máquina virtual.
# Arquitectura 📄 
La aplicación consta de tres clases: HelloWorld, SecureURLReader y UserDB.

La clase HelloWorld es el principal punto de entrada a la aplicación. Configura el marco web Spark y define las rutas para la aplicación. La ruta /login es un punto final POST que recibe las credenciales del usuario (nombre de usuario y contraseña) y las pasa a la clase SecureURLReader para su autenticación. El método seguro se llama al comienzo del método principal, que configura el cifrado SSL/TLS para la aplicación. Esto se hace utilizando un almacén de claves y un almacén de confianza PKCS12, y configurando el contexto SSL predeterminado para la aplicación.

La clase SecureURLReader contiene métodos de utilidad para realizar solicitudes HTTPS y leer el contenido de la respuesta. También incluye un método para crear un contexto SSL seguro para las solicitudes HTTPS cargando un almacén de confianza y configurando TrustManagerFactory. Esto se utiliza para garantizar que el certificado del servidor sea confiable.

La clase UserDB contiene la lógica principal para el proceso de autenticación. Utiliza un HashMap llamado datos para almacenar las credenciales del usuario (nombre de usuario y contraseña). El método checkPassword se utiliza para autenticar al usuario comparando las credenciales proporcionadas con las almacenadas en el HashMap de datos. Utiliza un mecanismo de almacenamiento seguro de contraseñas aplicando una función hash SHA-256 a la contraseña antes de almacenarla. El método getSecurePassword se utiliza para calcular el hash de la contraseña proporcionada y compararlo con el hash almacenado en el HashMap de datos.

En resumen, la clase HelloWorld es responsable de manejar las solicitudes HTTP y delegar el proceso de autenticación a las clases SecureURLReader y UserDB. La clase SecureURLReader es responsable de realizar solicitudes HTTP seguras y la clase UserDB es responsable de administrar las credenciales del usuario y validar la autenticación del usuario.

 # Diseño de clases 📝
La clase UserDB se encarga de gestionar una base de datos de usuarios en memoria. Esta clase tiene un método main donde se crea un mapa para almacenar los usuarios y sus contraseñas cifradas. Además, se define un método getSecurePassword para cifrar las contraseñas utilizando el algoritmo SHA-256 y la codificación Base64. También se define un método checkPassword para verificar si una contraseña coincide con la contraseña cifrada almacenada en el mapa de usuarios.

La clase SecureURLReader se encarga de leer el contenido de una URL de forma segura. Esta clase tiene un método secureReadUrl que crea un almacén de confianza (truststore) y lo utiliza para establecer una conexión segura con el servidor web. Después de establecer la conexión, se lee el contenido de la URL y se devuelve como una cadena de texto.

Por último, la clase HelloWorld es la encargada de inicializar la aplicación web. En el método main de esta clase, se define una ruta de acceso a un archivo de almacén de claves (keystore) y su contraseña correspondiente. Después, se define una ruta para servir archivos estáticos y una ruta para procesar solicitudes de inicio de sesión. Cuando se recibe una solicitud de inicio de sesión, se llama al método secureReadUrl de la clase SecureURLReader para leer el contenido de la URL de forma segura.

# Instalación ⬇️ y Ejecución🪄

* Los siguiente comando le permitira clonar el repositorio de manera local:
  ~~~
  https://github.com/SantiagoMelo0104/AREP-LAB7.git
  ~~~
* Entrar al directorio del proyecto con el siguiente comando:
  tenga abirto dos lineas de comando (cmd) pues neceistamos ejecutar las dos clases que van estar ejecutandose al mismo tiempo‼️
   ~~~
   cd AREP-LAB7
   ~~~
* Compilar cada proyecto:
  
  ~~~
  java -cp "target/classes;target/dependency/*" org.arep.UserDB
  ~~~
  ~~~
  java -cp "target/classes;target/dependency/*" org.arep.HelloWorld
  ~~~
ahora en su navegador escriba copie y pegue el siguinte comando 
~~~
https://localhost:5000/index.html
~~~
Inicialmente nos saldra esta ventana, siguiendo los pasos
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB7/assets/123812833/9fc41570-5843-4889-a0f2-70a56ec931b7)
vera esta pantalla:
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB7/assets/123812833/186fee41-f758-429d-925f-b387c75c278c)

para probar que si funciona correctamente pruebe con:
~~~
usuario: santiago
contraseña: 12345
~~~
# pruebas
## Local
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB7/assets/123812833/973cfad3-a982-4032-8305-1a4a4699a9ee)
Cuando se logue correctamente
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB7/assets/123812833/4be82e2a-095a-4e85-96f5-0dc0daa99a2b)
Si la contraseña esta mal pero si exoste el usuario
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB7/assets/123812833/c4439228-79e1-453d-9059-945c99d8c914)
Si  no existe el usuario
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB7/assets/123812833/6c042f44-606d-4fa7-9979-ca5aee9e3300)




## AWS
Abrir en el navegador y colocar el path lo siguiente: 
~~~
https://ec2-34-228-68-212.compute-1.amazonaws.com:5000/index.html
~~~
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB7/assets/123812833/116dabbe-7f7e-4f74-8904-396029dd5277)

# prueba de despliegue
  [VIDEO AWS](https://youtu.be/9-EW-EMRNCM) 






# Autor 
Santiago Naranjo Melo [SantiagoMelo0104](https://github.com/SantiagoMelo0104)
