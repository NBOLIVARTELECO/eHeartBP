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
  - Validaciones robustas de email y contraseña.
- **Obtención de presión arterial:**
  - Recupera datos de Firebase Realtime Database.
  - Visualización de la última medición.
  - Manejo optimizado de múltiples llamadas a Firebase.
- **Historial y estadísticas:**
  - Tabla de registros históricos optimizada con RecyclerView.
  - Gráficas de tendencias (MPAndroidChart).
- **Predicción de riesgo:**
  - Consulta a un modelo de IA vía API REST (Volley).
  - Visualización gráfica del riesgo (CircleProgressView).
- **Soporte multilenguaje:**
  - Español e inglés.
- **Arquitectura mejorada:**
  - Patrón Singleton para gestión de datos (DataManager).
  - Validaciones centralizadas (ValidationUtils).
  - Manejo robusto de errores y estados de actividad.

## Estructura del proyecto

```
app/
  src/main/java/com/nestor/eheartbp/
    MainActivity.java              # Pantalla principal
    SignInActivity.java            # Inicio de sesión y registro (mejorado)
    ObtainPressureActivity.java    # Obtención de presión arterial (optimizado)
    ViewMeasurementActivity.java   # Visualización de la medición (mejorado)
    RecordsActivity.java           # Historial de mediciones (RecyclerView)
    StatisticsActivity.java        # Gráficas y estadísticas
    RiskPrediction.java            # Predicción de riesgo cardiovascular
    MenuActions.java               # Acciones del menú (mejorado)
    GoogleAccountsActivity.java    # Actividad de cuentas Google (optimizada)
    DataManager.java               # Gestión de datos (nuevo)
    ValidationUtils.java           # Validaciones (nuevo)
    RecordItem.java                # Modelo de registro (nuevo)
    RecordsAdapter.java            # Adaptador para RecyclerView (nuevo)
    objetos/                      # Clases de apoyo (Firebase, Usuario mejorado)
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
- **Inicio de sesión:** Formulario de email y contraseña con validaciones, registro e inicio de sesión.
- **Obtención de presión:** Botón para obtener la última medición desde Firebase con indicador de carga.
- **Visualización:** Muestra valores de presión y pulso, fecha y hora con manejo robusto de errores.
- **Historial:** Tabla optimizada con RecyclerView para mejor rendimiento.
- **Estadísticas:** Gráfica de tendencias de presión y pulso.
- **Predicción de riesgo:** Barra circular con porcentaje de riesgo y botones para recargar o simular un paciente no saludable.

## Mejoras implementadas

### 1. **Gestión de datos mejorada**
- Reemplazo de variables estáticas globales con DataManager (patrón Singleton)
- Uso de SharedPreferences para persistencia de datos
- Mejor manejo de memoria y concurrencia

### 2. **Validaciones robustas**
- Clase ValidationUtils para validaciones centralizadas
- Validación de email, contraseña, presión arterial y pulso
- Mensajes de error informativos

### 3. **Optimización de Firebase**
- Consolidación de múltiples llamadas a Firebase
- Manejo unificado de callbacks
- Prevención de problemas de concurrencia

### 4. **Mejoras de UI/UX**
- Verificación de estado de actividades antes de actualizar UI
- RecyclerView para mejor rendimiento en listas
- Manejo robusto de errores y estados

### 5. **Convenciones de código**
- Nomenclatura correcta siguiendo convenciones de Java
- Encapsulación apropiada de variables
- Logging mejorado para debugging

### 6. **Manejo de navegación**
- Verificación de estado de actividades antes de navegar
- Eliminación de threads innecesarios
- Manejo apropiado del ciclo de vida

## Internacionalización

La app soporta español e inglés automáticamente según la configuración del dispositivo.

## Licencia

Este proyecto está bajo la Licencia MIT.

## Notas de desarrollo

- **Versión mínima de Android:** API 21 (Android 5.0)
- **Versión objetivo:** API 26 (Android 8.0)
- **Arquitectura:** MVC con mejoras de arquitectura moderna
- **Base de datos:** Firebase Realtime Database
- **Autenticación:** Firebase Auth
- **Networking:** Volley para llamadas HTTP 