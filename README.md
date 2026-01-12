# PasswordApp Manager

Aplicación android offline-first para la gestión de contraseñas de manera 
local sin depender de servicios externos.
Las contraseñas se almacenan cifradas y solo se descifran al mostrarse.

## Estado

**MVP Funcional**

- [x] Crear, editar, eliminar y ver contraseñas y sus notas asociadas
- [x] Room como almacenamiento cifrado
- [x] Arquitectura por capas con UseCases y DI manual
- [ ] Biometría pendiente
- [ ] Migración a Hilt pendiente

## Requisitos de ejecución

- Android 9 (API 28 o superior)
- 50MB libres de almacenamiento local

## Objetivos

- Crear una aplicación standalone para la gestión de contraseñas en android usando kotlin
- Aprender a usar Room
- Aprender a usar la inversión de dependencias nativas 
  - Migrar a hilt antes de terminar el proyecto
- Entender y practicar el uso de ViewModel
- Aplicar un sistema de criptografía
- Almacenar datos cifrados y descifrarlos solo para mostrarlos
- Implementar biometría para ver los datos cifrados
- Crear test automatizados al menos de las capas de dominio y aplicación

## Tecnologías

- Material 3
- Jetpack compose
- ViewModel
- Room
- Jacoco

## Arquitectura

Presentation (compose+viewmodel)  
↓  
UseCases  
↓  
Repository  
↓  
Room (DAO + Entities)  

DI: AppGraph + ViewModelFactories

## Seguridad

- Cifrado AES-256-GCM local antes de guardar en DB para el texto de la contraseña y las notas
- Las contraseñas se descifran exclusivamente cuando el usuario quiere verla o modificarla
- Las notas se descifran al entrar en la contraseña
- No hay sincronizaciones ni consultas externas
