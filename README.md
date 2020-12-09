# FancyFruitAdmin

<br/>
<br/>
<br/>

<p align="center">
  <img src="https://coraa776fff39610ff48570b1b890.herokuapp.com/assets/logo.svg" />
</p>

<br/>
<br/>
<br/>



Bienvenido al repositorio oficial de la app de administración de Tropical Fancy Fruit.

## ¿Qué no es?
Una app de entretenimiento o shopping per se.

## ¿Qué es?
Fancy Fruit Admin es un proyecto desarrollado en Android Nativo para la empresa familiar de productores de fruta tropical de Jardinerías Verdemar. En concreto, Tropical Fancy Fruit constituye el ala de venta online de fruta tropical, actualmente sólo a nivel nacional.

Está respaldada por una API creada con NestJS, documentada con Swagger UI y alojada en un servidor Heroku. 
Actualmente, las funciones cubiertas por la app son:

- Identificación mediante API de Google Firebase UI
- Crear etiquetas identificativas para productos.
- Ver, añadir, editar y eliminar productos. También filtrarlos por sus respectivas etiquetas.
- Servicio de recolección de datos del terminal, que se envían a administración.

### Descarga APK: 

https://www.dropbox.com/s/wgtbfpcmctyajwf/app-debug.apk?dl=0

## Guia breve de uso:

1. Primero, hemos de instalar la apk en nuestro terminal. La mínima versión de SDK requerido es la API 24 (ANDROID 7 NOUGAT).
2. A continuación, una vez la abramos aparecerá la pantalla de login. Los campos email y password están desactivados por el momento, porque aún no tienen la funcionalidad completa. En su lugar, usaremos el botón de Sign In de Google. Elegiremos nuestra cuenta de Google que **AVISO** *_debe estar registrada en administración_*.

<img style="margin: auto; display: inline-block;"  height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/login.png" />

**NOTA**: Si su cuenta de Google no está registrada en administración, avance al punto `"Por algún motivo no puedo utilizarla"`, que se encuentra al final del documento. Si intentamos acceder con una cuenta que no es válida nos saldrá el siguiente mensaje:

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/login_error_email.png" />
    
>"No se encontró ninguna cuenta registrada con este email. Contacta con soporte para obtener tu cuenta"

4. Una vez hemos introducido nuestra cuenta nos aparecerá este mensaje:

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/login_aviso.png" />

Procederemos a aceptar el diálogo y nos aparecerá el siguiente diálogo, en el que introduciremos nuestra contraseña proporcionada por administración:

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/login_pass_admin.png" />

Si la contraseña es correcta, avanzaremos a la siguiente pantalla. El panel principal de administración. Si por el contrario la contraseña es incorrecta nos aparecerá el siguiente mensaje:

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/login_error_pass.png" />

>"La contraseña introducida no se corresponde con tus credenciales de registro"

5. Panel principal de administración. 

Aquí nos damos cuenta de varias cosas. Tenemos dos botones nuevos en la barra superior. Uno abre el menú lateral de cajón, y el otro nos mostrará un menú con una opción para cerrar sesión. Además, si nos fijamos en las notificaciones veremos que tenemos una nueva notificación. Esta notificación aparece cuando el servicio de recogida de datos está activo.

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/panel.png" />

6. Primero, añadiremos una nueva etiqueta. Las etiquetas se usan para clasificar productos. La etiqueta no es más que un nombre identificativo que se puede compartir entre productos. Para ello pulsaremos el botón de la etiqueta en el panel principal. Nos llevará a la lista de etiquetas.

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/tags.png" />

7. Añadiremos una etiqueta pulsando el botón flotante de abajo a la derecha con el símbolo de adición. Al hacer esto, nos llevará a la pantalla de añadir etiqueta. Una vez aquí introduciremos un nombre y pulsaremos guardar.

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/tag_añadir.png" />

Si la operación se ha completado con éxito aparecerá el siguiente mensaje: <br></br>
<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/tag_ok.png" />

Por el contrario, aparecerá un mensaje genérico indicando que no se ha podido completar con éxito.

Finalmente, una vez volvamos a la lista de etiquetas, nuestra nueva etiqueta debe aparecer. Podemos eliminar nuestras etiquetas si requerimos de ello, pero no podemos editar. Al ser un campo simplemente se decidió no incluir la funcionalidad en producción. Como alternativa sugerimos que se elimine la etiqueta y se cree una nueva.

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/tag_nuevo.png" />

Para eliminar, pulsamos sobre el item durante unos segundos hasta que salga el menú con las opciones.

<div style="display: flex;">
  <img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/tag_eliminar.png" />
  <img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/tag_eliminar_aviso.png" />
  <img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/tag_eliminar_ok.png" />
</div>

**NOTA**: Si intentamos cancelar el formulario de añadir nueva etiqueta nos saldrá un mensaje como este:

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/formulario_aviso.png" />

8. Creación de productos.

Para crear un producto nos dirigiremos a la pantalla de listado de productos abriendo el menú cajón. El resultado tiene que ser algo parecido a esto:

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/productos.png" />

Como con las etiquetas, para crear un nuevo producto pulsaremos el botón de adición de la parte inferior de la pantalla. Nos llevará a esta pantalla, en la que introduciremos los datos requeridos con el formato que se indica en el respectivo placeholder.
  
**Formatos**:
- Nombre: Cualquiera
- Slug: ejemplo-slug
- Referencia: 1234567N (XXXXXXXY)
- Cantidad: n > 0

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_a%C3%B1adir.png" />

También tenemos la posibilidad de asignar las etiquetas creadas anteriormente pulsando el botón "Añadir etiquetas".

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_tags.png" />

Si los datos son correctos, al guardar aparecerá el siguiente mensaje:

<img style="margin: auto" height=520 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_ok.png" />

Cuando volvamos hacia atrás, nuestro producto debería estar en la lista.

Ahora podremos editar nuestro producto o eliminarlo como explicamos anteriormente para las etiquetas.

9. Para editar o eliminar, pulsamos sobre el item durante unos segundos hasta que salga el menú con las opciones.

**Editar**: 

<div style="display: flex;">
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_opciones.png" />
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_editar_aviso.png" />
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_editar.png" />
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_editar_ok.png" />
</div>

<br></br>
**Eliminar**: 

<div style="display: flex;">
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_opciones.png" />
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_eliminar_aviso.png" />
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/producto_eliminar_ok.png" />
</div>

10. También podremos filtrar nuestros productos gracias al botón de filtrado de la barra superior y las etiquetas. Simplemente pulsaremos el botón y aparecerá el siguiente diálogo:

<img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/productos_filtrar.png" />

Seleccionamos las etiquetas que requiramos y pulsaremos seleccionar. El servidor nos devolverá sólamente productos que contengan las etiquetas mencionadas.

## Diagrama de base de datos: 

<img style="margin: auto" width=640 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/db_diagram.png" />

## Se ha perdido la conexión a internet:

En caso de que el dispositivo pierda la conexión a internet, la aplicación cerrará sesión y llevará al usuario a la pantalla de login nuevamente:

<div style="display: flex;">
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/conexion_off_3.png" />
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/conexion_off_2.png" />
  <img style="margin: auto" height=480 src="https://github.com/almAngel/FancyFruitAdmin/blob/master/screenshots/conexion_off.png" />
</div>

## Tengo conexión, pero aún así la app ha crasheado al intentar crear una etiqueta o un producto:

Puede ser que por la funcionalidad de suspensión del servidor Heroku la app no responda a la hora de crear el item, pero una vez hagamos el intento el servidor despertará y la próxima vez que iniciemos sesión y lo intentemos podremos crearlo nuevamente.

## Por algún motivo no puedo utilizarla:

Si por algún motivo la app no funciona en su terminal o necesita que le habilitemos una cuenta contáctenos a _jardineriasverdemar@gmail.com_ con la siguiente información:

- Nombre y primer apellido
- Nombre de usuario
- Email creado con extensión Gmail

# VIDEO:
[![VIDEO](http://i3.ytimg.com/vi/X3MldwQH2Ig/hqdefault.jpg)](https://www.youtube.com/watch?v=X3MldwQH2Ig)

Ángel López Molina, 2ºDAM I.E.S Campanillas
