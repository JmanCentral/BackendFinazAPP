# FinazApp Backend

> Una solución integral de gestión de finanzas personales basada en una arquitectura cliente-servidor moderna. Backend monolítico desarrollado en Spring Boot con autenticación JWT y base de datos PostgreSQL.

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

---

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Características Principales](#características-principales)
- [Requisitos del Sistema](#requisitos-del-sistema)
- [Instalación](#instalación)
- [Arquitectura del Sistema](#arquitectura-del-sistema)
    - [Estructura de Capas](#estructura-de-capas)
    - [Patrones de Diseño](#patrones-de-diseño)
    - [Diagrama de Arquitectura](#diagrama-de-arquitectura)
- [Modelo de Datos](#modelo-de-datos)
    - [Diagrama Entidad-Relación](#diagrama-entidad-relación)
    - [Descripción de Entidades](#descripción-de-entidades)
    - [Relaciones Entre Tablas](#relaciones-entre-tablas)
- [API REST](#api-rest)
    - [Autenticación](#autenticación)
    - [Endpoints Principales](#endpoints-principales)
    - [Documentación Swagger](#documentación-swagger)
- [Seguridad](#seguridad)
- [Configuración](#configuración)
- [Mantenimiento](#mantenimiento)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

---

## 📖 Descripción General

**FinazApp Backend** es una aplicación monolítica desarrollada con Spring Boot que proporciona todos los servicios backend para la gestión integral de finanzas personales. Implementa una arquitectura por capas robusta con autenticación basada en JWT, validación de datos, y manejo centralizado de errores.

La aplicación está optimizada para servir tanto a dispositivos móviles (cliente Android en Kotlin) como a potenciales clientes web, garantizando escalabilidad, seguridad y rendimiento en la administración de datos financieros personales.

### Propósito Principal

Proporcionar una solución práctica, accesible y confiable para que los usuarios gestionen sus finanzas personales de manera integral, promoviendo educación financiera y mejorando los hábitos de gasto mediante herramientas de análisis y recomendaciones.

---

## ✨ Características Principales

### Gestión de Usuarios
- ✅ Registro seguro de nuevos usuarios
- ✅ Autenticación con validación de credenciales
- ✅ Recuperación de contraseña por correo electrónico
- ✅ Roles y permisos basados en JWT

### Control Financiero
- ✅ **Gestión de Ingresos**: Registro, modificación y eliminación de ingresos mensuales y eventuales
- ✅ **Gestión de Gastos**: Clasificación por categorías (servicios, transporte, alimentación, etc.)
- ✅ **Gestión de Metas de Ahorro**: Alcancías digitales con seguimiento de depósitos
- ✅ **Recordatorios de Pagos**: Alertas con estado pagado/vencido

### Análisis y Reportes
- ✅ Visualización gráfica de distribución de gastos
- ✅ Reportes semanales, mensuales y anuales
- ✅ Análisis de patrones de consumo
- ✅ Ordenamiento y filtrado por rangos de fecha

### Inteligencia Financiera
- ✅ Recomendaciones personalizadas basadas en comportamiento
- ✅ Alertas de sobrepaso de presupuesto por categoría
- ✅ Consejos financieros contextualizados
- ✅ Sistema de calificación de consejos (me gusta / no me gusta)

---

## 🔧 Requisitos del Sistema

### Requisitos Mínimos para Desarrollo

| Componente | Versión | Descripción |
|-----------|---------|-------------|
| **Java JDK** | 17+ | Runtime y compilación |
| **Spring Boot** | 3.x | Framework principal |
| **Maven** | 3.8+ | Gestor de dependencias |
| **PostgreSQL** | 14+ | Base de datos relacional |
| **Git** | Última | Control de versiones |

### Requisitos Recomendados

| Herramienta | Versión |
|-----------|---------|
| **IntelliJ IDEA** | 2023.1+ |
| **Eclipse** | 2023-03+ |
| **Postman** | Última |
| **pgAdmin 4** | Última |

### Recursos Recomendados

```
RAM: 4 GB mínimo (8 GB recomendado)
Almacenamiento: 2 GB disponibles
CPU: Dual-Core 2.0 GHz mínimo
```

---

## 🚀 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/JmanCentral/BackendFinazAPP.git
cd BackendFinazAPP
```

### 2. Configurar PostgreSQL

#### Crear la Base de Datos

```sql
-- Conectarse como superusuario
psql -U postgres

-- Crear la base de datos
CREATE DATABASE finazapp;

-- Crear el rol (usuario) si es necesario
CREATE USER finazapp_user WITH PASSWORD 'tu_contraseña_segura';
ALTER ROLE finazapp_user SET client_encoding TO 'utf8';
ALTER ROLE finazapp_user SET default_transaction_isolation TO 'read committed';
ALTER ROLE finazapp_user SET default_transaction_deferrable TO on;
ALTER ROLE finazapp_user CREATEDB;

-- Otorgar permisos
GRANT ALL PRIVILEGES ON DATABASE finazapp TO finazapp_user;
```

### 3. Configurar Credenciales (application.properties)

Navega a `src/main/resources/application.properties` y configura:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/finazapp
spring.datasource.username=finazapp_user
spring.datasource.password=tu_contraseña_segura
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# JWT Secret Key (IMPORTANTE: Cambiar en producción)
jwt.secret=tu_clave_secreta_super_segura_minimo_32_caracteres
jwt.expiration=86400000

# Mail Configuration (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# Logging
logging.level.root=INFO
logging.level.com.finazapp=DEBUG
```

### 4. Compilar el Proyecto

```bash
# Descargar dependencias e compilar
mvn clean install

# Compilar sin ejecutar pruebas
mvn clean install -DskipTests
```

### 5. Ejecutar la Aplicación

#### Opción A: Con Maven
```bash
mvn spring-boot:run
```

#### Opción B: Con archivo JAR compilado
```bash
java -jar target/finazapp-backend.jar
```

### 6. Insertar Rol por Defecto

Una vez que la aplicación esté en ejecución, ejecuta en PostgreSQL:

```sql
-- Conectarse a la base de datos
psql -U finazapp_user -d finazapp

-- Insertar el rol USER
INSERT INTO roles(id, name) VALUES (1, 'USER');
```

### 7. Verificar Instalación

```bash
# Verificar que la aplicación está ejecutándose
curl http://localhost:8080/api/health

# Acceder a Swagger UI
# http://localhost:8080/api/swagger-ui.html
```

---

## 🏗️ Arquitectura del Sistema

### Descripción General de la Arquitectura

FinazApp Backend utiliza una **arquitectura cliente-servidor** donde:

- **Cliente**: Aplicación móvil Android desarrollada en Kotlin
- **Servidor**: Aplicación monolítica Spring Boot en Java
- **Base de Datos**: PostgreSQL como almacenamiento persistente

La comunicación entre cliente y servidor se realiza mediante **API REST** con formato **JSON**, asegurando una experiencia fluida y responsiva.

```
┌─────────────────────────────────────────────────────────────────┐
│                    CLIENTE ANDROID (KOTLIN)                     │
│  (Presenta datos, captura interacciones, consume API REST)      │
└────────────────────────────┬────────────────────────────────────┘
                             │
                    HTTP/JSON │ JWT Token
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                 SERVIDOR SPRING BOOT (JAVA)                     │
│              ┌─────────────────────────────────────┐             │
│              │    Capa de Controladores (REST)     │             │
│              │  (@RestController, @RequestMapping) │             │
│              └────────────────┬────────────────────┘             │
│                               │                                   │
│              ┌────────────────▼────────────────┐                 │
│              │  Capa de Servicios (Lógica)     │                 │
│              │  (@Service, @Transactional)     │                 │
│              └────────────────┬────────────────┘                 │
│                               │                                   │
│              ┌────────────────▼────────────────┐                 │
│              │ Capa de Repositorios (DAO)      │                 │
│              │ (extends JpaRepository)         │                 │
│              └────────────────┬────────────────┘                 │
│                               │                                   │
│         ┌─────────────────────┼─────────────────────┐             │
│         │                     │                     │             │
│    ┌────▼─────┐         ┌────▼─────┐         ┌────▼──────┐      │
│    │ DTOs &   │         │ Entities │         │ Config &  │      │
│    │ Mappers  │         │ (@Entity)│         │ Security  │      │
│    └──────────┘         └──────────┘         └───────────┘      │
│                                                                    │
└───────────────────────────────────┬──────────────────────────────┘
                                    │
                                    │ JDBC
                                    ▼
                      ┌──────────────────────────┐
                      │   PostgreSQL Database    │
                      │  (Tablas Relacionales)  │
                      └──────────────────────────┘
```

### Estructura de Capas

#### 1️⃣ Capa de Presentación (Controllers/Resources)

Expone los endpoints HTTP y maneja las solicitudes del cliente.

```
com.finazapp.controllers
├── ControladorLogin          # Autenticación y registro
├── ControladorUsuario        # Gestión de usuarios
├── ControladorIngreso        # Ingresos
├── ControladorGasto          # Gastos
├── ControladorDeposito       # dineró para metas futuras
├── ControladorAlcancia       # Metas de ahorro (alcancías)
├── ControladorRecordatorio   # Recordatorios
├── ControladorAlerta         # Alertas
├── ControladorConsejo        # Consejos financieros
├── ControladorTips           # Consejos personalizados por usuario
├── ControladorCalificación   # Calificar consejos predeterminados

```

**Responsabilidades:**
- Recibir solicitudes HTTP
- Validar parámetros de entrada
- Invocar servicios
- Retornar respuestas JSON estructuradas

#### 2️⃣ Capa de Servicios (Business Logic)

Contiene la lógica de negocio y orquestra operaciones entre datos.

```
com.finazapp.services
├── ServicioAutenticacion      # Gestiona la autenticación de usuarios, incluyendo el registro, login, y manejo de sesiones.
├── ServicioUsuario           # Encargado de la gestión de los datos del usuario (actualización de perfil, eliminación de cuenta, etc.).
├── ServicioIngreso           # Controla la gestión de ingresos de los usuarios (registro de ingresos, clasificación, análisis de flujo de efectivo, etc.).
├── ServicioGasto             # Se ocupa de la administración de los gastos, permitiendo registrar, categorizar y analizar los gastos.
├── ServicioDeposito          # Gestiona los depósitos realizados por el usuario para sus metas de ahorro futuras, permitiendo transferencias y gestión de saldo.
├── ServicioAlcancia          # Encargado de las metas de ahorro del usuario, gestionando el seguimiento, progreso, y cumplimiento de las metas establecidas.
├── ServicioRecordatorio      # Administra los recordatorios que los usuarios configuran (fecha de vencimiento de pagos, plazos de ahorro, etc.).
├── ServicioAlerta            # Se encarga de enviar alertas al usuario en caso de que haya gastos excesivos, metas de ahorro no alcanzadas, o cualquier situación financiera crítica.
├── ServicioConsejo           # Proporciona consejos financieros generales, basados en tendencias o situaciones comunes del mercado o comportamiento general.
├── ServicioTips              # Ofrece consejos personalizados basados en el comportamiento, historial y preferencias del usuario. Usa inteligencia artificial o reglas predefinidas.
├── ServicioCalificacion      # Permite a los usuarios calificar y valorar los consejos financieros predeterminados, ayudando a mejorar la calidad y relevancia de los mismos.

```

**Responsabilidades:**
- Implementar reglas de negocio
- Validaciones de dominio
- Transacciones de base de datos
- Orquestación de operaciones

#### 3️⃣ Capa de Persistencia (Repositories)

Acceso a datos mediante Spring Data JPA.

```
com.finazapp.repositories
├── RepositorioAutenticacion     # Maneja el acceso y almacenamiento de los datos de autenticación (usuarios registrados, credenciales, sesiones activas).
├── RepositorioUsuario          # Administra la persistencia de la información relacionada con el usuario (datos personales, configuración de la cuenta, preferencias).
├── RepositorioIngreso          # Almacena los registros de ingresos del usuario (salarios, pagos extra, etc.) y realiza consultas sobre el flujo de dinero.
├── RepositorioGasto            # Gestiona los registros de gastos (categorías, montos, fechas) y permite consultar los historiales de gasto de los usuarios.
├── RepositorioDeposito         # Maneja las transacciones de dinero hacia las metas de ahorro del usuario, como depósitos y consultas de saldo.
├── RepositorioAlcancia         # Administra la información sobre las metas de ahorro o alcancías (nombre de la meta, monto objetivo, fecha límite).
├── RepositorioRecordatorio     # Almacena los recordatorios configurados por el usuario (fecha, tipo de recordatorio, estado de cumplimiento).
├── RepositorioAlerta           # Gestiona las alertas de la aplicación (alertas de gastos, alertas de fechas de vencimiento, alertas por metas no alcanzadas).
├── RepositorioConsejo          # Almacena y gestiona los consejos financieros generales proporcionados a los usuarios (tipo de consejo, fecha de creación, autor).
├── RepositorioTips             # Almacena los consejos personalizados dados a los usuarios, basados en su historial de gastos, metas y preferencias.
├── RepositorioCalificacion     # Gestiona las calificaciones y valoraciones de los consejos recibidos por los usuarios (feedback sobre la utilidad de los consejos).

```

**Responsabilidades:**
- Operaciones CRUD
- Consultas personalizadas (Query Methods)
- Manejo de relaciones
- Transacciones de base de datos

#### 4️⃣ Capa de Datos (Entities/Models)

Representación de las tablas de la base de datos.

```
com.finazapp.entities
├── Usuario            # Usuario del sistema
├── Ingreso            # Ingresos
├── Gasto              # Gastos
├── Categoria          # Categorías de gasto
├── Alcancia           # Metas de ahorro
├── Deposito           # Depósitos a la alcancía
├── Recordatorio       # Recordatorios
├── Alerta             # Alertas
├── Consejo            # Consejos financieros
└── Rol                # Roles de usuario

```

#### 5️⃣ Capa de Configuración (Config)

Configuraciones globales de seguridad, CORS, JWT, etc.

```
com.finazapp.config
├── SecurityConfig          # Configuración Spring Security
├── JwtConfig              # Configuración JWT
├── CorsConfig             # CORS para cliente móvil
├── DatabaseConfig         # Configuración de BD
├── MailConfig             # Configuración SMTP
└── AppConfig              # Configuración general
```

#### 6️⃣ Capa de DTOs (Data Transfer Objects)

Objetos para transferencia segura de datos.

```
com.finazapp.dtos
├── UsuarioDTO              # Datos del usuario
├── IngresoDTO              # Ingresos
├── GastoDTO                # Gastos
├── CategoriaDTO            # Categorías de gasto
├── AlcanciaDTO             # Metas de ahorro
├── DepositoDTO             # Depósitos
├── RecordatorioDTO         # Recordatorios
├── AlertaDTO               # Alertas
├── ConsejoDTO              # Consejos financieros
├── CalificacionDTO         # Calificación de consejos
├── ProyeccionDTO           # Proyección de gastos
├── TipsDTO                 # Tips financieros
├── LoginRequestDTO         # Login
├── EmailRequestDTO         # Envío de correos
└── RespuestaCorreoDTO      # Respuesta de correo

```

#### 7️⃣ Capa de Excepciones y Manejo de Errores

Centralización de errores con respuestas estructuradas.

```
com.finazapp.excepciones.usuario
├── CorreoInvalidoException          # Email con formato inválido
├── CredencialesIncorrectasException # Usuario o contraseña incorrectos
├── EmailYaRegistradoException       # Email ya registrado
├── RolNoEncontradoException         # Rol no existe
├── UsernameInvalidoException        # Username inválido
├── UsuarioNoEncontradoException     # Usuario no existe
└── UsuarioYaRegistradoException     # Usuario ya registrado

```

### Patrones de Diseño

#### 🎯 Patrón MVC (Model-View-Controller)

```
Controllers (Capa de Entrada)
    ↓
Services (Lógica de Negocio)
    ↓
Repositories (Acceso a Datos)
    ↓
Entities/Models (Representación de Datos)
```

#### 🎯 Patrón DTO (Data Transfer Object)

Separa la representación interna de la API pública:

```java
// Entidad (Interna)
@Entity
public class Usuario {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @Column(name = "USERNAME" , unique = true)
    private String username;
    @Column(nullable = false, name = "EMAIL",unique = true)
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|hotmail\\.com|[a-zA-Z0-9.-]+\\.edu\\.co)$",
            message = "El correo debe ser de dominio @gmail.com, @hotmail.com o terminar en .edu.co"
    )
    private String email;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "CONTRASENA")
    private String contrasena;
}

// DTO (API Pública)
@Data
public class UsuarioDTO {
    private Long id_usuario;
    private String username;
    private String nombre;
    private String email;
    private String apellido;
    private String contrasena;
    private Set<String> roles;
}
```

#### 🎯 Patrón Repositorio

Abstracción de acceso a datos:

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByUsername(String nombreUsuario);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAll();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
```

#### 🎯 Inyección de Dependencias

```java
@Service
@RequerestAllconstructors
public class UserService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    
    // Métodos...
}
```

#### 🎯 Manejo Centralizado de Excepciones

```java
@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
        ResourceNotFoundException ex) {
        // Manejo del error...
    }
}
```

### Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────────────────────┐
│                     ARQUITECTURA POR CAPAS                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ CAPA DE PRESENTACIÓN (Controllers/REST Endpoints)       │   │
│  │ @RestController, @RequestMapping, @GetMapping, etc.     │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                         │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ CAPA DE SERVICIOS (Business Logic)                      │   │
│  │ @Service, @Transactional, Validaciones de Dominio      │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                         │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ CAPA DE DATOS (Repositories/DAOs)                       │   │
│  │ extends JpaRepository, Query Methods                     │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                         │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ CAPA DE ENTIDADES (Models/Entities)                     │   │
│  │ @Entity, @Table, Relaciones (@OneToMany, @ManyToOne)   │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                         │
│                         ▼                                         │
│            SPRING DATA JPA (ORM - Hibernate)               │
│                         │                                         │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │        JDBC Driver (PostgreSQL Driver)                  │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                         │
│                         ▼                                         │
│                   PostgreSQL Database                       │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘

CAPAS TRANSVERSALES:
├── Security Config: Spring Security, JWT, CORS
├── Exception Handling: @ControllerAdvice, Custom Exceptions
├── DTOs & Mappers: ModelMapper, Conversión de datos
└── Logging & Monitoring: SLF4J, Aspectos de auditoría
```

---

## 📊 Modelo de Datos

### Diagrama Entidad-Relación (ERD)

<img width="793" height="566" alt="image" src="https://github.com/user-attachments/assets/12ee87c8-1d1a-4a17-930e-ea30d2ae3ded" />


### Descripción de Entidades

#### 👤 USERS (Usuarios)

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role_id BIGINT REFERENCES roles(id)
);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único del usuario |
| `email` | VARCHAR(255) | Email único para autenticación |
| `password_hash` | VARCHAR(255) | Contraseña hasheada con BCrypt |
| `full_name` | VARCHAR(255) | Nombre completo del usuario |
| `created_at` | TIMESTAMP | Fecha de creación de cuenta |
| `updated_at` | TIMESTAMP | Última actualización |
| `role_id` | BIGINT | Relación con tabla ROLES |

**Validaciones:**
- Email único y válido
- Contraseña mínimo 8 caracteres
- Nombre requerido

---

#### 💰 INCOMES (Ingresos)

```sql
CREATE TABLE incomes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(500),
    income_date DATE NOT NULL,
    is_recurring BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_incomes_user_id ON incomes(user_id);
CREATE INDEX idx_incomes_date ON incomes(income_date);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `user_id` | BIGINT | Referencia a usuario (FK) |
| `amount` | DECIMAL(10,2) | Monto del ingreso |
| `description` | VARCHAR(500) | Descripción del ingreso |
| `income_date` | DATE | Fecha del ingreso |
| `is_recurring` | BOOLEAN | Si se repite mensualmente |
| `created_at` | TIMESTAMP | Fecha de registro |

**Tipos de Ingreso:**
- 💼 Sueldo (recurrente)
- 🎁 Bonificación (único)
- 📈 Inversiones (único/recurrente)
- 🏦 Otros ingresos (flexible)

---

#### 💸 EXPENSES (Gastos)

```sql
CREATE TABLE expenses (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(500),
    expense_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_expenses_user_id ON expenses(user_id);
CREATE INDEX idx_expenses_category_id ON expenses(category_id);
CREATE INDEX idx_expenses_date ON expenses(expense_date);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `user_id` | BIGINT | Referencia a usuario (FK) |
| `category_id` | BIGINT | Referencia a categoría (FK) |
| `amount` | DECIMAL(10,2) | Monto del gasto |
| `description` | VARCHAR(500) | Descripción del gasto |
| `expense_date` | DATE | Fecha del gasto |

**Validaciones:**
- Cantidad positiva
- Categoría válida
- Descripción opcional

---

#### 🏷️ CATEGORIES (Categorías)

```sql
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    icon VARCHAR(50),
    color VARCHAR(7),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `name` | VARCHAR(100) | Nombre único de categoría |
| `description` | VARCHAR(500) | Descripción de la categoría |
| `icon` | VARCHAR(50) | Ícono representativo |
| `color` | VARCHAR(7) | Código color (ej: #FF5733) |

**Categorías Predefinidas:**
- 🏠 Servicios (agua, luz, internet, etc.)
- 🚗 Transporte (gasolina, uber, taxi, etc.)
- 🍔 Alimentación (comida, supermercado, etc.)
- 🛍️ Compras (ropa, electrónica, etc.)
- 🏥 Salud (medicinas, doctor, etc.)
- 📚 Educación (cursos, libros, etc.)
- 🎮 Entretenimiento (cine, videojuegos, etc.)
- ❓ Otros

---

#### 🎯 SAVING_GOALS (Metas de Ahorro / Alcancías Digitales)

```sql
CREATE TABLE saving_goals (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    goal_name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    target_amount DECIMAL(10, 2) NOT NULL,
    current_amount DECIMAL(10, 2) DEFAULT 0,
    due_date DATE,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_saving_goals_user_id ON saving_goals(user_id);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `user_id` | BIGINT | Referencia a usuario (FK) |
| `goal_name` | VARCHAR(255) | Nombre de la meta |
| `description` | VARCHAR(500) | Descripción |
| `target_amount` | DECIMAL(10,2) | Cantidad objetivo |
| `current_amount` | DECIMAL(10,2) | Cantidad ahorrada actual |
| `due_date` | DATE | Fecha límite |
| `status` | VARCHAR(50) | ACTIVE, COMPLETED, CANCELED |

**Estados:**
- `ACTIVE`: Meta en progreso
- `COMPLETED`: Meta alcanzada
- `CANCELED`: Meta cancelada

---

#### 💳 DEPOSITS (Depósitos a Alcancías)

```sql
CREATE TABLE deposits (
    id BIGSERIAL PRIMARY KEY,
    saving_goal_id BIGINT NOT NULL REFERENCES saving_goals(id) ON DELETE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    deposit_date DATE NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_deposits_saving_goal_id ON deposits(saving_goal_id);
CREATE INDEX idx_deposits_date ON deposits(deposit_date);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `saving_goal_id` | BIGINT | Referencia a meta (FK) |
| `amount` | DECIMAL(10,2) | Monto depositado |
| `deposit_date` | DATE | Fecha del depósito |
| `description` | VARCHAR(500) | Descripción |

**Nota:** Los depósitos actualizan automáticamente `current_amount` en `SAVING_GOALS`.

---

#### 🔔 REMINDERS (Recordatorios de Pagos)

```sql
CREATE TABLE reminders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    description VARCHAR(500) NOT NULL,
    due_date DATE NOT NULL,
    amount DECIMAL(10, 2),
    is_paid BOOLEAN DEFAULT FALSE,
    is_overdue BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reminders_user_id ON reminders(user_id);
CREATE INDEX idx_reminders_due_date ON reminders(due_date);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `user_id` | BIGINT | Referencia a usuario (FK) |
| `description` | VARCHAR(500) | Descripción del pago |
| `due_date` | DATE | Fecha de vencimiento |
| `amount` | DECIMAL(10,2) | Monto a pagar |
| `is_paid` | BOOLEAN | Si ya fue pagado |
| `is_overdue` | BOOLEAN | Si está vencido |

---

#### ⚠️ ALERTS (Alertas de Presupuesto)

```sql
CREATE TABLE alerts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    limit_amount DECIMAL(10, 2) NOT NULL,
    alert_type VARCHAR(50) DEFAULT 'CATEGORY_LIMIT',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_alerts_user_id ON alerts(user_id);
CREATE INDEX idx_alerts_category_id ON alerts(category_id);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `user_id` | BIGINT | Referencia a usuario (FK) |
| `category_id` | BIGINT | Referencia a categoría (FK) |
| `limit_amount` | DECIMAL(10,2) | Límite de presupuesto |
| `alert_type` | VARCHAR(50) | Tipo de alerta |
| `is_active` | BOOLEAN | Si la alerta está activa |

**Tipos de Alertas:**
- `CATEGORY_LIMIT`: Alerta al superar límite por categoría
- `GENERAL_LIMIT`: Alerta para gasto general
- `GOAL_ALERT`: Alerta para metas de ahorro

---

#### 💡 ADVICE (Consejos Financieros)

```sql
CREATE TABLE advice (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    advice_type VARCHAR(100),
    context_based BOOLEAN DEFAULT FALSE,
    liked BOOLEAN DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_advice_user_id ON advice(user_id);
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `user_id` | BIGINT | Referencia a usuario (FK) |
| `title` | VARCHAR(255) | Título del consejo |
| `description` | TEXT | Descripción detallada |
| `advice_type` | VARCHAR(100) | Tipo de consejo (ahorro, gasto, inversión) |
| `context_based` | BOOLEAN | Si es personalizado para el usuario |
| `liked` | BOOLEAN | Me gusta (NULL = sin calificar) |

**Tipos de Consejos:**
- 💰 Ahorro: Recomendaciones para ahorrar
- 💸 Gasto: Consejos para reducir gastos
- 📈 Inversión: Información sobre inversiones
- 📚 Educación: Conceptos financieros

---

#### 🔐 ROLES (Roles de Usuario)

```sql
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Datos por defecto
INSERT INTO roles(id, name) VALUES (1, 'USER');
```

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único |
| `name` | VARCHAR(100) | Nombre único del rol |

**Roles Actuales:**
- `USER`: Usuario estándar con acceso a funcionalidades básicas

---

### Relaciones Entre Tablas

#### 📍 Relaciones Principales

```
USERS (1) ──────────── (N) INCOMES
├─ Un usuario tiene muchos ingresos
└─ Eliminación en cascada: DELETE USER → DELETE INCOMES

USERS (1) ──────────── (N) EXPENSES
├─ Un usuario tiene muchos gastos
└─ Eliminación en cascada: DELETE USER → DELETE EXPENSES

USERS (1) ──────────── (N) SAVING_GOALS
├─ Un usuario tiene múltiples metas de ahorro
└─ Eliminación en cascada: DELETE USER → DELETE SAVING_GOALS

USERS (1) ──────────── (N) REMINDERS
├─ Un usuario tiene múltiples recordatorios
└─ Eliminación en cascada: DELETE USER → DELETE REMINDERS

USERS (1) ──────────── (N) ALERTS
├─ Un usuario tiene múltiples alertas
└─ Eliminación en cascada: DELETE USER → DELETE ALERTS

USERS (1) ──────────── (N) ADVICE
├─ Un usuario recibe múltiples consejos
└─ Eliminación en cascada: DELETE USER → DELETE ADVICE

USERS (N) ────────── (1) ROLES
├─ Un usuario tiene un rol
└─ Un rol puede tener múltiples usuarios

CATEGORIES (1) ────── (N) EXPENSES
├─ Una categoría tiene muchos gastos
└─ Sin eliminación en cascada: PROTECT

CATEGORIES (1) ────── (N) ALERTS
├─ Una categoría tiene múltiples alertas
└─ Sin eliminación en cascada: PROTECT

SAVING_GOALS (1) ──── (N) DEPOSITS
├─ Una meta puede tener múltiples depósitos
├─ Actualiza current_amount automáticamente
└─ Eliminación en cascada: DELETE GOAL → DELETE DEPOSITS
```

#### 🔄 Operaciones Entre Entidades

```
INGRESO REGISTRADO
├─ INSERT en INCOMES
└─ UPDATE en SAVING_GOALS.current_amount (si hay depósito automático)

GASTO REGISTRADO
├─ INSERT en EXPENSES
├─ CHECK si supera ALERTS.limit_amount
│  └─ TRIGGER: Generar alerta
└─ UPDATE en IA para ADVICE personalizado

META DE AHORRO CREADA
├─ INSERT en SAVING_GOALS
└─ TRIGGER: Enviar notificación inicial

DEPÓSITO A META
├─ INSERT en DEPOSITS
├─ UPDATE en SAVING_GOALS.current_amount
└─ CHECK si alcanzó target_amount
   └─ UPDATE SAVING_GOALS.status = 'COMPLETED'

RECORDATORIO VENCIDO
├─ UPDATE REMINDERS.is_overdue = TRUE
├─ TRIGGER: Notificación al usuario
└─ CHECK si es recurrente: Crear nuevo

USUARIO ELIMINADO
├─ DELETE FROM ADVICE WHERE user_id = ?
├─ DELETE FROM ALERTS WHERE user_id = ?
├─ DELETE FROM REMINDERS WHERE user_id = ?
├─ DELETE FROM DEPOSITS WHERE saving_goal_id IN (...)
├─ DELETE FROM SAVING_GOALS WHERE user_id = ?
├─ DELETE FROM EXPENSES WHERE user_id = ?
├─ DELETE FROM INCOMES WHERE user_id = ?
└─ DELETE FROM USERS WHERE id = ?
```

---

## 🔌 API REST

### Autenticación

#### Registro de Usuario

```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "usuario@gmail.com",
  "password": "MiContraseña123!",
  "fullName": "Juan Pérez"
}
```

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "email": "usuario@gmail.com",
  "fullName": "Juan Pérez",
  "createdAt": "2025-02-20T10:30:00Z",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "usuario@gmail.com",
  "password": "MiContraseña123!"
}
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "email": "usuario@gmail.com",
  "fullName": "Juan Pérez",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 86400
}
```

#### Recuperar Contraseña

```http
POST /api/auth/forgot-password
Content-Type: application/json

{
  "email": "usuario@gmail.com"
}
```

---

### Endpoints Principales

#### 📊 Usuarios

```http
# Obtener perfil actual
GET /api/users/profile
Authorization: Bearer {JWT_TOKEN}

# Actualizar perfil
PUT /api/users/{id}
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "fullName": "Nuevo Nombre",
  "email": "nuevo@gmail.com"
}

# Eliminar usuario
DELETE /api/users/{id}
Authorization: Bearer {JWT_TOKEN}
```

#### 💰 Ingresos

```http
# Listar ingresos del usuario
GET /api/incomes
Authorization: Bearer {JWT_TOKEN}

# Crear nuevo ingreso
POST /api/incomes
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "amount": 2000.00,
  "description": "Sueldo mensual",
  "incomeDate": "2025-02-20",
  "isRecurring": true
}

# Obtener ingreso específico
GET /api/incomes/{id}
Authorization: Bearer {JWT_TOKEN}

# Actualizar ingreso
PUT /api/incomes/{id}
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "amount": 2100.00,
  "description": "Sueldo actualizado"
}

# Eliminar ingreso
DELETE /api/incomes/{id}
Authorization: Bearer {JWT_TOKEN}

# Filtrar ingresos por rango de fechas
GET /api/incomes?startDate=2025-01-01&endDate=2025-02-28
Authorization: Bearer {JWT_TOKEN}
```

#### 💸 Gastos

```http
# Listar gastos del usuario
GET /api/expenses
Authorization: Bearer {JWT_TOKEN}

# Crear nuevo gasto
POST /api/expenses
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "amount": 50.00,
  "description": "Almuerzo",
  "categoryId": 3,
  "expenseDate": "2025-02-20"
}

# Obtener gasto específico
GET /api/expenses/{id}
Authorization: Bearer {JWT_TOKEN}

# Actualizar gasto
PUT /api/expenses/{id}
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "amount": 55.00,
  "categoryId": 3
}

# Eliminar gasto
DELETE /api/expenses/{id}
Authorization: Bearer {JWT_TOKEN}

# Gastos por categoría
GET /api/expenses/category/{categoryId}
Authorization: Bearer {JWT_TOKEN}

# Gastos en rango de fechas
GET /api/expenses?startDate=2025-01-01&endDate=2025-02-28
Authorization: Bearer {JWT_TOKEN}
```

#### 🎯 Metas de Ahorro

```http
# Listar metas del usuario
GET /api/saving-goals
Authorization: Bearer {JWT_TOKEN}

# Crear nueva meta
POST /api/saving-goals
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "goalName": "Viaje a Cartagena",
  "targetAmount": 5000.00,
  "dueDate": "2025-12-31",
  "description": "Ahorro para vacaciones"
}

# Obtener meta específica
GET /api/saving-goals/{id}
Authorization: Bearer {JWT_TOKEN}

# Actualizar meta
PUT /api/saving-goals/{id}
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "goalName": "Viaje mejorado",
  "targetAmount": 6000.00
}

# Eliminar meta
DELETE /api/saving-goals/{id}
Authorization: Bearer {JWT_TOKEN}

# Obtener progreso de meta
GET /api/saving-goals/{id}/progress
Authorization: Bearer {JWT_TOKEN}
```

#### 💳 Depósitos

```http
# Listar depósitos de una meta
GET /api/deposits/goal/{savingGoalId}
Authorization: Bearer {JWT_TOKEN}

# Crear depósito
POST /api/deposits
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "savingGoalId": 1,
  "amount": 500.00,
  "depositDate": "2025-02-20",
  "description": "Primer depósito"
}

# Eliminar depósito
DELETE /api/deposits/{id}
Authorization: Bearer {JWT_TOKEN}
```

#### 🔔 Recordatorios

```http
# Listar recordatorios
GET /api/reminders
Authorization: Bearer {JWT_TOKEN}

# Crear recordatorio
POST /api/reminders
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "description": "Pagar servicios",
  "dueDate": "2025-03-05",
  "amount": 150.00
}

# Marcar como pagado
PUT /api/reminders/{id}/mark-paid
Authorization: Bearer {JWT_TOKEN}

# Actualizar recordatorio
PUT /api/reminders/{id}
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "description": "Pagar servicios y otros",
  "dueDate": "2025-03-10"
}

# Eliminar recordatorio
DELETE /api/reminders/{id}
Authorization: Bearer {JWT_TOKEN}
```

#### ⚠️ Alertas

```http
# Listar alertas
GET /api/alerts
Authorization: Bearer {JWT_TOKEN}

# Crear alerta
POST /api/alerts
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "categoryId": 2,
  "limitAmount": 500.00,
  "alertType": "CATEGORY_LIMIT"
}

# Actualizar alerta
PUT /api/alerts/{id}
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "limitAmount": 600.00,
  "isActive": true
}

# Eliminar alerta
DELETE /api/alerts/{id}
Authorization: Bearer {JWT_TOKEN}
```

#### 💡 Consejos

```http
# Listar consejos
GET /api/advice
Authorization: Bearer {JWT_TOKEN}

# Calificar consejo (Me gusta / No me gusta)
PUT /api/advice/{id}/like
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
{
  "liked": true
}

# Obtener consejo específico
GET /api/advice/{id}
Authorization: Bearer {JWT_TOKEN}
```

#### 📈 Reportes

```http
# Reporte general del mes
GET /api/reports/monthly?year=2025&month=2
Authorization: Bearer {JWT_TOKEN}

# Reporte anual
GET /api/reports/yearly?year=2025
Authorization: Bearer {JWT_TOKEN}

# Reporte por categoría
GET /api/reports/category?categoryId=1&startDate=2025-01-01&endDate=2025-02-28
Authorization: Bearer {JWT_TOKEN}

# Resumen financiero
GET /api/reports/summary
Authorization: Bearer {JWT_TOKEN}
```

### Documentación Swagger

Una vez que el backend está corriendo, accede a:

```
http://localhost:8080/api/swagger-ui.html
```

En Swagger podrás:
- ✅ Ver todos los endpoints documentados
- ✅ Hacer pruebas directas
- ✅ Ver esquemas de solicitud y respuesta
- ✅ Verificar códigos de respuesta HTTP

---

## 🔐 Seguridad

### JWT (JSON Web Tokens)

#### Flujo de Autenticación

```
┌─────────────┐                                      ┌──────────────┐
│   CLIENTE   │                                      │   SERVIDOR   │
└──────┬──────┘                                      └──────┬───────┘
       │                                                    │
       │─────────── POST /auth/login ────────────────────>│
       │ (email, password)                                 │
       │                                                    │
       │                        ┌──────────────────────────│
       │                        │ 1. Validar credenciales
       │                        │ 2. Hasear password
       │                        │ 3. Generar JWT
       │                        │ 4. Firmar con secret
       │                        └──────────────────────────│
       │                                                    │
       │<─────────── JWT Token (3600 segundos) ──────────│
       │ {                                                  │
       │   "sub": "usuario@gmail.com",                     │
       │   "iat": 1677686400,                              │
       │   "exp": 1677690000,                              │
       │   "role": "USER"                                  │
       │ }                                                  │
       │                                                    │
       │─────── GET /incomes + Bearer Token ────────────>│
       │                                                    │
       │                        ┌──────────────────────────│
       │                        │ 1. Extraer token
       │                        │ 2. Verificar firma
       │                        │ 3. Validar expiración
       │                        │ 4. Extraer user
       │                        │ 5. Verificar permisos
       │                        └──────────────────────────│
       │                                                    │
       │<────────────── JSON de ingresos ───────────────│
       │                                                    │
```

#### Configuración JWT

```properties
# application.properties
jwt.secret=tu_clave_secreta_super_segura_minimo_32_caracteres_aleatorios_2024
jwt.expiration=86400000  # 24 horas en milisegundos
```

#### Estructura del Token

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiJ1c3VhcmlvQGdtYWlsLmNvbSIsImlhdCI6MTY3NzY4NjQwMCwiZXhwIjoxNjc3NjkwMDAwLCJyb2xlIjoiVVNFUiJ9.
NjU0MjJmNDZkN2M2MWU1MzQ2YjQ1ZjU3YzQ5YjcyNmQ1ZTZmNjE2MjczMjc2YTdhNDg2ZjU3YzJlNjY2ZjY2YQ==

[Header].[Payload].[Signature]
```

**Header:**
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**Payload:**
```json
{
  "sub": "usuario@gmail.com",
  "iat": 1677686400,
  "exp": 1677690000,
  "role": "USER"
}
```

### Encriptación de Contraseñas

```java
// BCrypt con 10 rondas de salting
$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QYRG
```

**Proceso:**
1. Usuario registra contraseña en texto plano
2. Sistema genera salt aleatorio
3. BCrypt aplica 2^10 rondas de hashing
4. Se almacena: `$2a$10$salt$hash`
5. En login, se compara sin desencriptar

### Spring Security

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/swagger-ui/**", "/api/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
```

### CORS (Cross-Origin Resource Sharing)

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(false)
            .maxAge(3600);
    }
}
```
**Última actualización:** 21 de Mayo de 2025

---
