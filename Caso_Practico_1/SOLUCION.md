# SOLUCION — Caso Practico 1

## Endpoints

- **GET /eventos** — Busca y muestra los eventos de la base de datos, y soporta filtro por `?categoria=...` para filtrar por solo un valor de categoia.
- **GET /eventos/{id}** — Busca y muestra la fecha, lugar, categoria, precio, etc de un solo evento.
- **GET /eventos/nuevo** — Permite crear un evento por un formulario.
- **POST /eventos** — Funciona como capa de validacion para determinar si sae debe mostrar al usuario errores en datos
- **GET /eventos/{id}/editar** — Funciona igual que un formulario para un evento nuevo pero con datos ya cargados de un evento selecionado.
- **POST /eventos/{id}** — Permite cambiar los datos de un evento.
- **POST /eventos/{id}/eliminar** — Borra el evneto de la db y nos redijie a la nueva vista.

## Validaciones en Evento

Se implementa lasen base a que datos son requeridos para un evento:

- **@NotBlank** en nombre, lugar y categoria — son campos minimos para identificar un evento
- **@NotNull + @Future** en fecha — para asegurar no crearlo en el pasado
- **@Min(value = 1)** en cupoMaximo — permite como minimo 1 persona, sino no es evento
- **@PositiveOrZero** en precio — permite un evento gratuito, pero no que tenga precio negativo
- **@Size** en nombre, descripcion, lugar, categoria y organizador — establece limite para no sobrecargar la base de datos con valores innecesariamente largos

## Modal de confirmacion para eliminar

En vez de `confirm()` del navegador, use un modal de Bootstrap. Cada boton "Eliminar" en las cards del listado tiene estos atributos:

- `data-bs-toggle="modal"` — abre un modal.
- `data-bs-target="#modalEliminar"` — indica cual modal.

Tambien guarda `th:data-id` y `th:data-nombre` en el vento directamente

De esta forma si  se abre se activa el evento `show.bs.modal` y con JavaScript leer el `data-id` del boton y actualizxar accion a `/eventos/{id}/eliminar`. Y el nombre  es para que el usuario sepa que evento va a borrar.

## Decisiones tecnicas

- Use **LocalDate** para la fecha del evento porque solo necesito el dia, la hora no es necesaria en este caso
- Agregue los metodos **isCompleto()**, **isFuturo()** y **isGratis()** en la entidad Evento para ajustar las vistas
- El formulario de crear y editar es el mismo (`form.html`). Cambia segun si viene el `id`
- Agregue un `<select>` de categorias , filtra los eventos al seleccionar una option.