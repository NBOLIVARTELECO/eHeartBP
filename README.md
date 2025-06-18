# eHeart BP

Aplicación Android para el monitoreo y predicción de riesgo cardiovascular a partir de mediciones de presión arterial.

## Descripción

eHeart BP permite a los usuarios:
- Iniciar sesión o registrarse con correo y contraseña (Firebase Auth).
- Obtener y visualizar mediciones de presión arterial almacenadas en Firebase.
- Consultar el historial de mediciones y ver estadísticas gráficas.
- Obtener una predicción de riesgo cardiovascular usando un modelo de IA remoto.
- Navegar por un menú para acceder a las distintas funcionalidades.

## Características principales

- **Inicio de sesión y registro:**
  - Autenticación con Firebase Auth.
  - Sugerencia de correos previamente usados.
- **Obtención de presión arterial:**
  - Recupera datos de Firebase Realtime Database.
  - Visualización de la última medición.
- **Historial y estadísticas:**
  - Tabla de registros históricos.
  - Gráficas de tendencias (MPAndroidChart).
- **Predicción de riesgo:**
  - Consulta a un modelo de IA vía API REST (Volley).
  - Visualización gráfica del riesgo (CircleProgressView).
- **Soporte multilenguaje:**
  - Español e inglés.

## Estructura del proyecto

```
app/
  src/main/java/com/nestor/eheartbp/
    MainActivity.java              # Pantalla principal
    SignInActivity.java            # Inicio de sesión y registro
    ObtainPressureActivity.java    # Obtención de presión arterial
    ViewMeasurementActivity.java   # Visualización de la medición
    RecordsActivity.java           # Historial de mediciones
    StatisticsActivity.java        # Gráficas y estadísticas
    RiskPrediction.java            # Predicción de riesgo cardiovascular
    MenuActions.java               # Acciones del menú
    objetos/                      # Clases de apoyo (Firebase, Usuario)
  res/layout/                     # Layouts XML de cada pantalla
  res/values/strings.xml          # Strings en inglés
  res/values-es/strings.xml       # Strings en español
  res/menu/e_heart_menu.xml       # Menú principal
```

## Dependencias principales

- [Firebase Auth & Database](https://firebase.google.com/)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- [CircleProgressView](https://github.com/eralpyucel/CircleProgressView)
- [Volley](https://developer.android.com/training/volley)

## Instalación y compilación

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Configura tu archivo `google-services.json` para Firebase.
4. Compila y ejecuta en un dispositivo/emulador Android (minSdk 21, targetSdk 26).

## Permisos requeridos

- INTERNET
- ACCESS_NETWORK_STATE

## Experiencia de usuario

- **Pantalla principal:** Botón para empezar e imagen ilustrativa.
- **Inicio de sesión:** Formulario de email y contraseña, registro e inicio de sesión.
- **Obtención de presión:** Botón para obtener la última medición desde Firebase.
- **Visualización:** Muestra valores de presión y pulso, fecha y hora.
- **Historial:** Tabla con registros previos.
- **Estadísticas:** Gráfica de tendencias de presión y pulso.
- **Predicción de riesgo:** Barra circular con porcentaje de riesgo y botones para recargar o simular un paciente no saludable.

## Internacionalización

La app soporta español e inglés automáticamente según la configuración del dispositivo.

## Licencia

Este proyecto está bajo la Licencia MIT. 