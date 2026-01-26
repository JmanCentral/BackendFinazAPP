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
├── AuthController          # Autenticación y registro
├── UserController          # Gestión de usuarios
├── IncomeController        # Ingresos
├── ExpenseController       # Gastos
├── CategoryController      # Categorías
├── SavingGoalController    # Metas de ahorro (alcancías)
├── ReminderController      # Recordatorios
├── AlertController         # Alertas
├── AdviceController        # Consejos financieros
└── ReportController        # Reportes y análisis
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
├── AuthService             # Lógica de autenticación
├── UserService             # Operaciones de usuarios
├── IncomeService           # Cálculos de ingresos
├── ExpenseService          # Análisis de gastos
├── SavingGoalService       # Gestión de metas
├── ReminderService         # Control de recordatorios
├── AlertService            # Sistema de alertas
├── AdviceService           # Generación de consejos
└── ReportService           # Generación de reportes
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
├── UserRepository
├── IncomeRepository
├── ExpenseRepository
├── CategoryRepository
├── SavingGoalRepository
├── DepositRepository
├── ReminderRepository
├── AlertRepository
├── AdviceRepository
└── RoleRepository
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
├── User                    # Usuario del sistema
├── Income                  # Ingresos
├── Expense                 # Gastos
├── Category                # Categorías de gasto
├── SavingGoal              # Metas de ahorro
├── Deposit                 # Depósitos a alcancías
├── Reminder                # Recordatorios
├── Alert                   # Alertas
├── Advice                  # Consejos financieros
└── Role                    # Roles de usuario
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
├── UserDTO
├── IncomeDTO
├── ExpenseDTO
├── SavingGoalDTO
├── ReminderDTO
├── AlertDTO
├── AdviceDTO
├── AuthRequestDTO
├── AuthResponseDTO
└── ReportDTO
```

#### 7️⃣ Capa de Excepciones y Manejo de Errores

Centralización de errores con respuestas estructuradas.

```
com.finazapp.exceptions
├── ResourceNotFoundException
├── BadRequestException
├── UnauthorizedException
├── ConflictException
├── ApiExceptionHandler        # @ControllerAdvice
└── ErrorResponse              # Estructura de respuesta
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
public class User {
    private Long id;
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;
}

// DTO (API Pública)
@Data
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
}
```

#### 🎯 Patrón Repositorio

Abstracción de acceso a datos:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByCreatedAtAfter(LocalDateTime date);
}
```

#### 🎯 Inyección de Dependencias

```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
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

```

┌──────────────────────────────────────────────────────────────────────────────┐
│                         MODELO ENTIDAD-RELACIÓN                              │
└──────────────────────────────────────────────────────────────────────────────┘

                              ┌─────────────┐
                              │   ROLES     │
                              ├─────────────┤
                              │ id (PK)     │
                              │ name        │
                              └──────┬──────┘
                                     │
                      (1:N) ┌────────┴────────┐
                           │                  │
                    ┌──────▼─────────┐   ┌──────────────┐
                    │ USERS (1)      │   │  CATEGORIES  │
                    ├────────────────┤   ├──────────────┤
                    │ id (PK)        │   │ id (PK)      │
                    │ email (UNIQUE) │   │ name         │
                    │ password_hash  │   │ description  │
                    │ full_name      │   │ icon         │
                    │ created_at     │   └──────┬───────┘
                    │ role_id (FK)   │          │
                    └──────┬─────────┘          │
                           │                    │
          ┌────────────────┼────────────────┐  │
          │                │                │  │
    (1:N) │           (1:N) │            (1:N) │
          │                │                │  │
    ┌─────▼────────┐ ┌──────▼────┐  ┌─────▼────────────┐
    │   INCOMES    │ │  EXPENSES │  │ SAVING_GOALS     │
    ├──────────────┤ ├───────────┤  ├──────────────────┤
    │ id (PK)      │ │ id (PK)   │  │ id (PK)          │
    │ user_id (FK) │ │user_id(FK)│  │ user_id (FK)     │
    │ amount       │ │ amount    │  │ goal_name        │
    │ description  │ │ description  │ target_amount    │
    │ date         │ │ date      │  │ current_amount   │
    │ is_recurring │ │ category_id  │ due_date         │
    │ created_at   │ │ created_at   │ created_at       │
    └──────────────┘ └───────────┤  └──────┬───────────┘
                                │          │
                         (1:N)  │          │ (1:N)
                                │          │
                    ┌───────────▼──┐  ┌───▼──────────────┐
                    │  REMINDERS   │  │    DEPOSITS      │
                    ├──────────────┤  ├──────────────────┤
                    │ id (PK)      │  │ id (PK)          │
                    │ user_id (FK) │  │ saving_goal_id(FK)
                    │ description  │  │ amount           │
                    │ due_date     │  │ deposit_date     │
                    │ is_paid      │  │ created_at       │
                    │ is_overdue   │  └──────────────────┘
                    │ created_at   │
                    └──────────────┘

                    ┌──────────────┐  ┌──────────────┐
                    │    ALERTS    │  │   ADVICE     │
                    ├──────────────┤  ├──────────────┤
                    │ id (PK)      │  │ id (PK)      │
                    │ user_id (FK) │  │ user_id (FK) │
                    │ category(FK) │  │ title        │
                    │ limit_amount │  │ description  │
                    │ alert_type   │  │ type         │
                    │ is_active    │  │ liked        │
                    │ created_at   │  │ created_at   │
                    └──────────────┘  └──────────────┘
```

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

### Mejoras de Seguridad Recomendadas

#### ✅ Ya Implementadas
- ✓ Autenticación JWT
- ✓ Hashing de contraseñas (BCrypt)
- ✓ Validación de entrada
- ✓ Manejo centralizado de excepciones

#### 🔄 En Implementación
- ⏳ HTTPS (SSL/TLS) - Certificados Let's Encrypt
- ⏳ Rotación de claves JWT
- ⏳ Rate limiting
- ⏳ EncryptedSharedPreferences para tokens

#### 📋 Recomendadas a Futuro
- 🎯 OAuth2 / Google Sign-In
- 🎯 Autenticación multifactor (2FA)
- 🎯 Auditoría de accesos
- 🎯 Cumplimiento GDPR/CCPA

---

## ⚙️ Configuración

### Variables de Entorno

```bash
# .env (En producción, usar AWS Secrets Manager)
DB_URL=jdbc:postgresql://localhost:5432/finazapp
DB_USERNAME=finazapp_user
DB_PASSWORD=tu_contraseña_segura

JWT_SECRET=tu_clave_secreta_super_segura_minimo_32_caracteres
JWT_EXPIRATION=86400000

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=tu_email@gmail.com
MAIL_PASSWORD=tu_app_password

RESET_PASSWORD_URL=http://localhost:3000/reset-password?token=

GOOGLE_AI_API_KEY=tu_api_key_gemini
```

### Perfiles de Spring

#### Perfil Development (application-dev.properties)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finazapp_dev
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
logging.level.com.finazapp=DEBUG
server.port=8080
```

#### Perfil Production (application-prod.properties)

```properties
spring.datasource.url=jdbc:postgresql://prod-db.rds.amazonaws.com:5432/finazapp
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.com.finazapp=INFO
server.port=8080
server.ssl.enabled=true
server.ssl.key-store=keystore.p12
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
```

### Ejecutar con Perfil Específico

```bash
# Development
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Production
java -jar target/finazapp-backend.jar --spring.profiles.active=prod
```

---

## 🔧 Mantenimiento

### Mantenimiento Preventivo

#### Frontend & Backend

```bash
# Verificar vulnerabilidades en dependencias
mvn dependency-check:check

# Actualizar dependencias de Maven
mvn clean install -U

# Ejecutar análisis SonarQube (si está configurado)
mvn sonar:sonar
```

#### Base de Datos

```sql
-- Hacer backup automático
pg_dump -U finazapp_user finazapp > backup_$(date +%Y%m%d_%H%M%S).sql

-- Analizar y optimizar tablas
ANALYZE;
REINDEX;

-- Ver estadísticas
SELECT 
    tablename, 
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
FROM pg_tables 
WHERE schemaname = 'public'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;
```

### Mantenimiento Correctivo

#### Problemas Comunes y Soluciones

##### ❌ Error: Database connection refused

```bash
# Verificar que PostgreSQL está corriendo
sudo systemctl status postgresql

# Reiniciar PostgreSQL
sudo systemctl restart postgresql

# Verificar conexión
psql -U finazapp_user -d finazapp -c "SELECT 1"
```

##### ❌ Error: JWT Token expired

```java
// El token expiró. Usuario debe hacer login nuevamente
POST /api/auth/login

// Opción: Implementar refresh token (futuro)
POST /api/auth/refresh-token
```

##### ❌ Error: Constraint violation - Duplicate email

```
Solución: El email ya existe. Usar email diferente en registro.
```

##### ❌ Error: OutOfMemoryException

```bash
# Aumentar memoria JVM
java -Xmx2g -Xms1g -jar target/finazapp-backend.jar
```

### Bitácora de Cambios

Ver archivo [CHANGELOG.md](CHANGELOG.md) para historial de versiones y cambios.

---

## 🧪 Testing

### Pruebas Unitarias

```bash
# Ejecutar todas las pruebas
mvn test

# Pruebas de un módulo específico
mvn test -Dtest=UserServiceTest

# Pruebas sin mostrar output
mvn test -q
```

### Pruebas de Integración

```bash
# Ejecutar pruebas de integración
mvn verify

# Con cobertura de código
mvn clean verify jacoco:report
```

### Ejemplo de Prueba Unitaria

```java
@SpringBootTest
@DisplayName("UserService Tests")
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("Debe registrar usuario correctamente")
    void testRegisterUser() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
            "test@gmail.com", 
            "Password123", 
            "Test User"
        );
        
        // Act
        UserDTO result = userService.register(request);
        
        // Assert
        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals("Test User", result.getFullName());
    }
}
```

---

## 🚀 Despliegue en AWS

### EC2 + RDS

```bash
# 1. Conectarse a instancia EC2
ssh -i key.pem ubuntu@your-instance-ip

# 2. Instalar Java
sudo apt update
sudo apt install openjdk-17-jdk

# 3. Descargar aplicación
wget https://your-repo/finazapp-backend.jar

# 4. Crear servicio systemd
sudo nano /etc/systemd/system/finazapp.service
```

**archivo finazapp.service:**
```ini
[Unit]
Description=FinazApp Backend
After=network.target

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu
ExecStart=/usr/bin/java -jar finazapp-backend.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# 5. Iniciar servicio
sudo systemctl daemon-reload
sudo systemctl start finazapp
sudo systemctl enable finazapp

# 6. Verificar estado
sudo systemctl status finazapp
```

### Docker (Alternativa)

```dockerfile
# Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/finazapp-backend.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Construir imagen
docker build -t finazapp-backend:latest .

# Ejecutar contenedor
docker run -d \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/finazapp \
  -e SPRING_DATASOURCE_USERNAME=finazapp_user \
  -e SPRING_DATASOURCE_PASSWORD=password \
  -p 8080:8080 \
  --name finazapp-backend \
  finazapp-backend:latest
```

---

## 📚 Recursos Útiles

### Documentación Oficial

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [JWT.io](https://jwt.io)

### Librerías Principales

| Librería | Versión | Propósito |
|----------|---------|----------|
| spring-boot-starter-web | 3.x | APIs REST |
| spring-boot-starter-data-jpa | 3.x | Persistencia ORM |
| spring-boot-starter-security | 3.x | Autenticación |
| spring-boot-starter-mail | 3.x | Envío de emails |
| jjwt | 0.11+ | Tokens JWT |
| postgresql | 42.x | Driver BD |
| lombok | 1.18+ | Reducir boilerplate |
| mapstruct | 1.5+ | Mapeo de DTOs |
| springdoc-openapi | 2.x | Documentación Swagger |

---

## 📖 Guía Rápida de Comandos Maven

```bash
# Compilar proyecto
mvn clean compile

# Ejecutar pruebas
mvn test

# Compilar y empaquetar
mvn clean package

# Instalar en repositorio local
mvn clean install

# Ejecutar aplicación
mvn spring-boot:run

# Generar documentación
mvn javadoc:javadoc

# Ver árbol de dependencias
mvn dependency:tree
```

---

## 🤝 Contribuciones

1. Hacer fork del repositorio
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Hacer commit de cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

### Estándares de Código

- Usar Java conventions
- Máximo 120 caracteres por línea
- Javadoc para métodos públicos
- Nombres descriptivos en inglés
- Pruebas unitarias obligatorias

---

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT. Ver archivo `LICENSE` para más detalles.

---

## 👥 Autores

- **Alexander Forero Consuegra** - Backend Architecture
- **Manuel Humberto Pinzón Hernández** - Database Design & Security
- **Julián Camilo Mancipe Triviño** - API Development & Testing

**Docente Supervisor:** Ingeniero Oscar David Murcia Portilla

**Institución:** Facultad de Ingeniería y Ciencias Básicas, Universidad Central

**Código del Proyecto:** 43390865 - Práctica de Ingeniería V

---

## 📞 Soporte

Para reportar bugs o sugerencias:
- Crear un [Issue](https://github.com/JmanCentral/BackendFinazAPP/issues)
- Email: soporte@finazapp.com
- Documentación: Ver [Wiki](https://github.com/JmanCentral/BackendFinazAPP/wiki)

---

## 🗺️ Roadmap

### Versión 1.0 (Actual)
- ✅ Gestión de usuarios
- ✅ Control de ingresos y gastos
- ✅ Metas de ahorro
- ✅ Recordatorios y alertas
- ✅ API REST con JWT

### Versión 1.1 (Próxima)
- 🔄 HTTPS / SSL-TLS
- 🔄 Refresh tokens
- 🔄 Rate limiting
- 🔄 Mejora de reportes

### Versión 2.0 (Futuro)
- 📋 Autenticación OAuth2 / Google Sign-In
- 📋 Autenticación multifactor (2FA)
- 📋 Web app (React/Vue)
- 📋 Integración con APIs bancarias
- 📋 Notificaciones push mejoradas
- 📋 Machine Learning para predicciones

---

**Última actualización:** 21 de Mayo de 2025

---