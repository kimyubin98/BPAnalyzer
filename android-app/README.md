# BPAnalyzer para Android

Aplicación móvil nativa para registrar mediciones de presión arterial.

## Funciones

- Selección del día de la semana.
- Registro de nombre, peso, edad y presión sistólica/diastólica.
- Validación de campos y valores positivos.
- Listado de mediciones guardadas.
- Persistencia local mediante `SharedPreferences`.

## Abrir y ejecutar

1. Abre la carpeta `android-app` desde Android Studio.
2. Espera a que Gradle sincronice el proyecto.
3. Conecta un teléfono Android con depuración USB o inicia un emulador.
4. Pulsa **Run** para instalar y ejecutar `BPAnalyzer`.

El proyecto usa `compileSdk 35` y requiere Android 6.0 (API 23) o superior. En este equipo se detectó el SDK Android, pero no Gradle instalado globalmente; Android Studio puede descargar y usar la versión compatible automáticamente.
