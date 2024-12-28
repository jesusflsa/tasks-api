# API de Tareas

Esta es una API para la gestión de tareas que permite a los usuarios registrarse, iniciar sesión y realizar operaciones CRUD (crear, leer, actualizar, eliminar) sobre sus tareas personales. Este proyecto está diseñado para practicar conceptos de desarrollo de APIs y prepararse para situaciones reales de trabajo.

---

## Características Principales

- **Autenticación JWT**: Registro e inicio de sesión seguro para los usuarios.
- **Gestor de Tareas**: Crear, leer, actualizar y eliminar tareas.
- **Filtros y Ordenamiento** (opcional): Filtrar tareas por estado o fecha de vencimiento y ordenarlas.
- **Notificaciones** (opcional): Listar tareas próximas a vencer.
- **Documentación con Swagger**.

---

## Tecnologías Utilizadas

- **Backend**: Java 21, Spring Boot 3.4.1.
- **Base de Datos**: MySQL.
- **Autenticación**: JSON Web Tokens (JWT).
- **Pruebas**: JUnit.
- **Despliegue**: Render.

---

## Instalación y Configuración

### Requisitos Previos

- JDK 21.
- Maven 3.3.2
- MySQL Server.

### Pasos para Instalar

1. Clona este repositorio:
   ```bash
   git https://github.com/jesusflsa/tasks-api.git
   cd tasks-api
   ```

2. Instala las dependencias:
   ```bash
   mvn install
   ```

3. Configura las variables de entorno:
   ```env
   PORT=3000
   DB_DOMAIN=your_database_domain
   DB_NAME=database_name;
   DB_USER=mysql_user;
   DB_PASSWORD=mysql_password;
   JWT_PRIVATE_KEY=tu_secreto_jwt
   ```

4. Inicia el servidor:
   ```bash
   mvn spring-boot:run
   ```

5. Accede a la API en `http://localhost:3000`.

---

## Rutas Principales

### Usuarios

- **POST /auth/register**: Registrar un nuevo usuario.
    - Request Body:
      ```json
      {
        "name": "Jesús Flores",
        "email": "jesus@example.com",
        "password": "password123"
      }
      ```

- **POST /auth/login**: Iniciar sesión y obtener un token JWT.
    - Request Body:
      ```json
      {
        "email": "jesus@example.com",
        "password": "password123"
      }
      ```

### Tareas

- **GET /tasks**: Obtener todas las tareas del usuario autenticado.
- **POST /tasks**: Crear una nueva tarea.
    - Request Body:
      ```json
      {
        "title": "Terminar proyecto",
        "description": "Completar la API de tareas",
        "expiration_date": "2024-12-31",
        "status": "pending"
      }
      ```
- **PUT /tasks/:id**: Actualizar una tarea existente.
- **DELETE /tasks/:id**: Eliminar una tarea.

---

## Despliegue

1. Asegúrate de que tus variables de entorno estén configuradas correctamente en el servicio de despliegue.
2. Sube el código a un repositorio (por ejemplo, GitHub).
3. Configura el servicio (Render, Railway, Heroku, etc.) para desplegar desde tu repositorio.

---

## Mejoras Futuras

- Implementación de OAuth para autenticación externa.
- Integración con servicios de notificación como Twilio o SendGrid.
- Dashboard para visualizar estadísticas de tareas.

---

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más información.
