🏨 Ultimate Hotel System
Sistema de gestión integral para hoteles, diseñado para optimizar el control de reservas, estadías, gestión de pasajeros y facturación. Desarrollado con una arquitectura robusta y escalable.

🚀 Descripción del Proyecto
El Ultimate Hotel System es una solución backend integral construida sobre el ecosistema de Spring Boot. El sistema gestiona el ciclo de vida completo de un cliente, desde su reserva y check-in, hasta su estadía y cancelación, garantizando una experiencia fluida y segura.

🛠️ Tecnologías Utilizadas
Backend: Java 17+, Spring Boot 3

Seguridad: Spring Security con JWT (JSON Web Token)

Base de Datos: MySQL

ORM: Spring Data JPA / Hibernate

Documentación API: OpenAPI / Swagger

Gestión de Calidad: Lombok, Maven

🔑 Características Principales
Autenticación Segura: Sistema de acceso basado en tokens JWT para proteger las rutas de la API.

Gestión de Reservas: Creación, modificación y seguimiento de reservas con estados en tiempo real.

Control de Estadías: Registro detallado de pasajeros, asignación de habitaciones y gestión de check-in/check-out.

Estructura del Proyecto

src/main/java/ar/edu/utn/frmdp/ultimate_hotel_software/
├── controller/         # Endpoints de la API
├── dto/                # Data Transfer Objects para comunicación
├── models/             # Entidades de base de datos (@Entity)
├── repository/         # Interfaces de acceso a datos (Spring Data)
├── security/           # Configuración JWT y filtros de seguridad
└── service/            # Lógica de negocio (Servicios e Implementaciones)

Clonar el repositorio:
git clone https://github.com/Falconleos/ultimate-hotel-software.git

2.  **Configurar base de datos:** Crea una base de datos en MySQL y ajusta los parámetros en tu archivo `application.yml`:
    ```YAML
    spring:
  datasource:
    url: jdbc:mysql://localhost:3306/nombre_tu_db?createDatabaseIfNotExist=true&serverTimezone=UTC
    username: tu_usuario
    password: tu_contraseña
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update # Útil en desarrollo para actualizar el esquema automáticamente
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect

    Ejecutar el proyecto:
    mvn spring-boot:run

    
Gestión de Cancelaciones: Lógica de negocio para manejar cancelaciones y liberar disponibilidad de inventario.

Arquitectura Escalable: Implementación de patrones Service-Repository y Controller con DTOs para separación de responsabilidades.
