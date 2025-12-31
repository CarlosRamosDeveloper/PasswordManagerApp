# TODO's

## Legend

- [1] Max priority
- [9] Min priority

## Bugs / Fixes

- [7] Improve Edit mode UI
- [2] Edit Mode: El botón de generar contraseñas no actualiza el textfield

## Architecture / debt tech

- [1] Mejorar la inyección de dependencias. Pasar todos los UC allí.
- [2] Integrar AppEntity
- [2] Integrar AccountEntity

## Features

- [3] Implementar biometría (deshabilitarla en desarrollo)
- [4] Dropdown con apps ya existentes en su campo correspondiente
- [4] Al pinchar en el campo de cuenta de usuario, debería de desplegarse un dropdown con las cuentas creadas
- [5] Autocompletado de urls -> www.<app>.com
- [6] Permitir la busqueda por app
- [6] Permtir la búsqueda por cuenta de usuario

## Parking Lot

- [8] Mostrar el logo de la app si coincide con la url
- [9] Migrar a Hilt

## Actual

- [x] Revisión de AppGraph -> Comentarios para claridad
- [x] Revisión de usecases ->
  - Ahora múltiples UC piden otros UC como parametro
  - Inyectado en AppGraph
- [x] Revisión de VM ->
  - [x] CreatePassword - Limpio
  - [x] MainScreen - Limpio
  - [x] PasswordList - Limpio
  - [x] Settings - Limpio
  - [ ] PasswordDetail
    - 400 líneas de código -> Separar en responsabilidades
    - [x] Responsabilidad: Mostrar dialogos
    - [ ] Responsabilidad: Editar contraseña
    - [ ] Responsabilidad: Mostrar datos
