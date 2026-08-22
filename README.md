CyberGaming es una aplicación móvil desarrollada para Android. Está diseñada como un catálogo y gestor de videojuegos, permite al usuario explorar títulos destacados mediante una API en tiempo real, añadirlos a una biblioteca personal persistente y personalizar su perfil de usuario mediante preferencias locales y captura fotográfica directa con la cámara del dispositivo.

Arquitectura Elegida (MVVM + Repository)

El proyecto implementa la arquitectura recomendada por Google (MVVM - Model-View-ViewModel junto con el patrón Repository), garantizando una separación estricta de responsabilidades:

Capa UI (Jetpack Compose)

Pantallas reactivas (Pantalla, PantallaCartelera, PantallaAjuste, Detalle) que observan los estados expuestos por los ViewModels y reaccionan de forma fluida.

Capa ViewModel

Contiene la lógica de negocio y mantiene el estado de la interfaz de forma independiente a los cambios de configuración.

Capa Repository

Actúa como una única fuente de verdad, decidiendo si los datos provienen de la fuente local (Room / DataStore) o de la fuente remota (Retrofit).

Fuentes de Datos (Data)

Room para almacenamiento estructurado local.

DataStore para preferencias de usuario simples.

Retrofit para el consumo de la API REST externa.

API Utilizada

Proveedor: FreeToGame API / Endpoints públicos de videojuegos.

Características: API REST pública y gratuita que provee un listado actualizado de videojuegos, consumida mediante peticiones HTTP asíncronas con Retrofit y serialización de datos con Gson.

Diagrama Simple de la Arquitectura

<img width="903" height="1024" alt="image" src="https://github.com/user-attachments/assets/8a0e330d-5dc8-4a2a-92fe-11e2528e4fef" />

Funcionalidad de Hardware y Permisos

Cámara del Dispositivo: Implementada mediante ActivityResultContracts.TakePicturePreview() para tomar fotos de perfil.

Permisos en Tiempo de Ejecución: Solicita formalmente el permiso android.permission.CAMERA al usuario y maneja el caso de rechazo mostrando un aviso visual de advertencia.
