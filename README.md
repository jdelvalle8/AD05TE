# AD05TE - Gimnasio: rutinas y ejercicios

Aplicación web local para gestionar rutinas de gimnasio y sus ejercicios.
Tema libre de la tarea evaluativa Acceso a Datos 5.


## Entorno fijado
Java 21, Maven, Spring Boot 4.1.2, Oracle XE 21c.
Thymeleaf, Spring Data JPA y el driver ojdbc17 se resuelven con el BOM de Boot.
Bootstrap 5.3.3 se carga desde CDN: necesita Internet en el navegador para el estilo
completo. Sin CDN, el CRUD sigue siendo HTML navegable.


## Preparación
1. Como administrador de Oracle conectado a XEPDB1, adapta la contraseña de
   `preparar_usuario_oracle.sql` y ejecútalo una vez, si el usuario AD05_APP todavía
   no existe. No en CDB$ROOT.
2. Define DB_PASSWORD en las variables de entorno de la configuración de ejecución
   del IDE. El resto de ajustes (URL, usuario y puerto) están en application.properties.
3. Importa como proyecto Maven y espera a que se descarguen las dependencias.
4. Ejecuta GimnasioApplication o `mvn spring-boot:run` y abre http://127.0.0.1:8083/.
5. Los datos se añaden desde los formularios y se conservan al reiniciar.


PowerShell (solo contraseña de práctica):
```powershell
$env:DB_PASSWORD="tu_contrasena_de_practica"
mvn spring-boot:run
```
En Eclipse: Run Configurations > Java Application > GimnasioApplication > Environment.
Selecciona JDK 21. El `mvn` del terminal es una instalación de Maven independiente;
la integración Maven del IDE no garantiza que exista ese comando en el PATH.


## Decisiones de la aplicación
- Un ejercicio siempre pertenece a una rutina.
- Eliminar una rutina elimina sus ejercicios.
- Eliminar un ejercicio no elimina su rutina.
- Cambiar un ejercicio de rutina conserva su id y sus datos.
- `ddl-auto=update`, identificadores generados con IDENTITY.
- `open-in-view=false`: las consultas cargan la rutina necesaria antes de la vista.
- GET muestra la confirmación; POST ejecuta el borrado.
- El servidor comprueba que la rutina elegida existe y avisa si no hay ninguna creada.


## Comprobación manual
Consulta rutinas y ejercicios; crea una rutina; crea un ejercicio; edita sus datos;
cámbialo de rutina; cancela una confirmación de borrado; borra un ejercicio y comprueba
que la rutina sigue; borra una rutina de prueba y comprueba si borra los ejercicios;
reinicia la aplicación y comprueba que los cambios confirmados se mantienen.


## Alcance de seguridad
Solo aprendizaje en el equipo local: server.address=127.0.0.1.
No incorpora login, autorización ni protección CSRF de Spring Security. POST no
sustituye esa protección. No desplegar públicamente esta aplicación sin adaptarla.
No guardar contraseñas reales en Git.


## Estado de verificación de esta entrega
Se ha realizado la comprobación manual descrita arriba.
