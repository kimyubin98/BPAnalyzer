# BPAnalyzer

<div align="center">
  <img src="https://img.shields.io/badge/Python-3.x-3776AB?style=for-the-badge&logo=python&logoColor=white" alt="Python" />
  <img src="https://img.shields.io/badge/GUI-Tkinter-00AEEF?style=for-the-badge&logo=python&logoColor=white" alt="Tkinter" />
  <img src="https://img.shields.io/badge/Android-Application-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Status-En%20desarrollo-orange?style=for-the-badge" alt="Status" />
</div>

<p align="center">
  <strong>Registro inteligente de presión arterial para seguimiento personal y clínico.</strong>
</p>

BPAnalyzer es una aplicación orientada a facilitar el registro, la organización y la revisión de mediciones de presión arterial. Su objetivo es ofrecer una herramienta clara y funcional para mantener un historial ordenado de datos como la presión sistólica, la diastólica, el peso, la edad y el día de medición.

## ✨ Descripción general

Este repositorio reúne dos versiones del proyecto:

- una aplicación de escritorio desarrollada con Python y Tkinter
- una versión móvil para Android ubicada en la carpeta [android-app](android-app)

La idea principal es simplificar el proceso de captura de datos de salud, brindando una interfaz amigable y una estructura que permite consultar información de manera rápida y ordenada.

## 🎯 Objetivos

- Registrar mediciones de presión arterial de forma sencilla.
- Mantener un historial organizado por día y por persona.
- Facilitar la consulta de datos en una sola vista.
- Proporcionar una base útil para extensiones futuras, como análisis, gráficos o exportación de datos.
- Diseñar una solución accesible tanto para uso personal como para contextos profesionales.

## 🧩 Funcionalidades principales

- Registro de mediciones diarias
- Captura de datos personales básicos
- Visualización de presión sistólica/diastólica
- Organización por días de la semana
- Interfaz intuitiva para usuarios no técnicos
- Compatibilidad con una versión Android del mismo sistema

## 📁 Estructura del repositorio

```text
BPAnalyzer/
├── Presion arterial.py        # Aplicación principal de escritorio
├── README.md                  # Documentación del proyecto
├── android-app/               # Proyecto móvil para Android
│   ├── app/
│   ├── gradle/
│   ├── build.gradle
│   └── settings.gradle
└── ...
```

## 🛠️ Tecnologías utilizadas

- Python
- Tkinter para la interfaz gráfica
- Android Studio
- Gradle
- Java/Kotlin para la versión Android

## 🚀 Cómo usarlo

### Aplicación de escritorio

1. Clona este repositorio.
2. Abre la carpeta del proyecto en tu entorno local.
3. Ejecuta el archivo `Presion arterial.py`.
4. Completa los campos del formulario y guarda las mediciones.

### Versión Android

1. Abre la carpeta [android-app](android-app) en Android Studio.
2. Sincroniza el proyecto con Gradle.
3. Ejecuta la app en un emulador o dispositivo físico.

## 📈 Estado del proyecto

BPAnalyzer se encuentra en desarrollo activo como una herramienta práctica para el registro y seguimiento de la presión arterial, con una base funcional para uso local y una base sólida para futuras mejoras y ampliaciones.

## 📜 Licencia

Este proyecto se distribuye bajo la licencia definida en el repositorio, si aplica según la estructura del desarrollo actual.

---

<p align="center">
  <em>Diseñado para facilitar el control de salud con una experiencia clara, ordenada y profesional.</em>
</p>
