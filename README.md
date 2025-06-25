# 📱 Clínica Salud Total - App Mobile

Este repositorio contiene el desarrollo de la aplicación **mobile** de la clínica *Salud Total*, creada con **Kotlin** en **Android Studio**, y conectada a la **API RESTful** desarrollada en el repositorio principal del sistema web.

> ⚠️ Este proyecto forma parte del Trabajo Práctico Integrador (TPI) de la materia **Programación 3 - Mobile** y se desarrolla en conjunto con el sistema web y la base de datos del mismo dominio.

---

## 🎯 Objetivo General

Desarrollar una **aplicación mobile completa** para la gestión de turnos médicos, utilizando tecnologías modernas y aplicando buenas prácticas de desarrollo mobile vistas en clase.

La app replica y adapta funcionalidades clave del sistema web a una experiencia mobile, aplicando diseño nativo de Android y arquitectura robusta basada en **MVVM**.

---

## 🧩 Tecnologías y herramientas utilizadas

* 🛠️ **Android Studio**
* 💻 **Kotlin**
* 🧱 **MVVM (Model - View - ViewModel)**
* 🧮 **ViewBinding**
* 🌐 **Retrofit** para consumo de API RESTful
* 🧾 **SharedPreferences** para almacenamiento local
* 🔄 **Coroutines** para llamadas asíncronas
* 📦 Librerías y componentes nativos: `RecyclerView`, `TextView`, `ImageView`, `LinearLayout`, entre otros

---

## 🔌 Conexión a la API

La aplicación se conecta a la **API RESTful del sistema Clínica Salud Total**, alojada en un repositorio separado.

* 🔐 Autenticación mediante **usuario/contraseña**
* 📲 Al recibir un **token JWT**, este se guarda temporalmente en el dispositivo usando `SharedPreferences`
* 🔁 Todos los endpoints se consumen utilizando la librería **Retrofit** y un `RestClient` personalizado

---

## 📱 Funcionalidades principales

* 🔓 **Login** con validación de credenciales
* 🏠 **Pantalla Home** con navegación (opcional)
* 📋 **Listado** de elementos (por ejemplo, turnos, pacientes o especialidades) utilizando `RecyclerView`
* 🔍 **Vista de detalle** de un elemento específico
* ⭐ **Marcado como favorito** mediante `SharedPreferences` (opcional)
* ➕ **Alta**, ✏️ **edición** y 🗑️ **eliminación** de elementos
* ✅ Validaciones de formularios y manejo de errores

---

## 📂 Estructura del proyecto

```
📦 CLINICA-ST-MOBILE
 ┣ 📁 data              -> Modelos, servicios Retrofit y repositorios
 ┣ 📁 ui                -> Pantallas y componentes visuales
 ┣ 📁 viewmodel         -> ViewModels conectados a UI
 ┣ 📁 utils             -> Clases auxiliares (validaciones, constantes, helpers)
 ┣ 📄 AndroidManifest.xml
 ┗ 📄 build.gradle
```

---

## 🧪 Requisitos técnicos cumplidos

✔️ Proyecto basado en MVVM
✔️ Conexión con API externa vía Retrofit
✔️ Manejo de autenticación con token
✔️ Navegación entre múltiples pantallas
✔️ Uso de componentes nativos de Android
✔️ Almacenamiento local con SharedPreferences
✔️ CRUD completo sobre al menos una entidad
✔️ Manejo de estados y errores
✔️ Estructura clara con separación de responsabilidades

---

## 💡 Bonus (Buenas prácticas implementadas)

* ✅ Validaciones de entrada en formularios
* ✅ Manejo de errores HTTP y de conexión
* ✅ Diseño responsive y adaptable
* 🚧 Integración continua (pendiente / opcional)

---

## 📸 Capturas de pantalla

> *(Aquí se incluirán capturas reales de la app funcionando)*

---

## 📄 Documentación adicional

* Descripción del escenario elegido: **Clínica médica con sistema de gestión de turnos**
* Relación con el sistema web y API: uso del mismo dominio funcional
* Documento con mapeo de requisitos funcionales y técnicos cumplidos

---

## 🧑‍💻 Autor

* 👨‍💻 **Juan Gabriel Pared** – Desarrollo mobile, conexión a API y coordinación general del proyecto

---

## 📬 Contacto

Para consultas, sugerencias o reportes, comunicarse por el canal oficial del curso o directamente con el desarrollador.

---
