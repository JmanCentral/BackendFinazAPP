# FinazApp Backend

> Una solución integral de gestión de finanzas personales basada en una arquitectura cliente-servidor moderna. Backend monolítico desarrollado en Spring Boot con autenticación JWT y base de datos PostgreSQL.

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

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

<img width="1003" height="663" alt="image" src="https://github.com/user-attachments/assets/cabd1156-fb26-4d94-a3d1-c9ca92e48d78" />

---

### Diagrama de Despliegue

<img width="1013" height="581" alt="image" src="https://github.com/user-attachments/assets/cec692ab-85c0-4ceb-abda-aef8c2f4fafc" />

---


## 📊 Modelo de Datos

### Diagrama Entidad-Relación (ERD)

<img width="793" height="566" alt="image" src="https://github.com/user-attachments/assets/12ee87c8-1d1a-4a17-930e-ea30d2ae3ded" />


### Descripción de Entidades

#### 👤 USUARIO 

```sql
CREATE TABLE users (
    id_usuario BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
);
```

| Campo      | Tipo         | Descripción                     |
| ---------- | ------------ | ------------------------------- |
| id_usuario | BIGSERIAL    | Identificador único del usuario |
| username   | VARCHAR(255) | Nombre de usuario único         |
| nombre     | VARCHAR(255) | Nombre del usuario              |
| apellido   | VARCHAR(255) | Apellido del usuario            |
| email      | VARCHAR(255) | Correo electrónico              |
| password   | VARCHAR(255) | Contraseña hasheada             |


**Validaciones:**
- Email único y válido
- Contraseña mínimo 8 caracteres
- Nombre requerido

---

#### 💰 ROLES

```sql
CREATE TABLE roles (
    id_rol BIGSERIAL PRIMARY KEY,
    nombre_rol VARCHAR(100) NOT NULL
);

```

| Campo      | Tipo         | Descripción           |
| ---------- | ------------ | --------------------- |
| id_rol     | BIGSERIAL    | Identificador del rol |
| nombre_rol | VARCHAR(100) | Nombre del rol        |

---

#### 💸 INGRESO

```sql
CREATE TABLE ingreso (
    id_ingreso BIGSERIAL PRIMARY KEY,
    nombre_ingreso VARCHAR(255),
    valor DECIMAL(10,2) NOT NULL,
    fecha DATE NOT NULL,
    tipo_ingreso VARCHAR(100),
    id_usuario BIGINT REFERENCES usuario(id_usuario)
);

```

| Campo          | Tipo          | Descripción               |
| -------------- | ------------- | ------------------------- |
| id_ingreso     | BIGSERIAL     | Identificador del ingreso |
| nombre_ingreso | VARCHAR(255)  | Nombre o descripción      |
| valor          | DECIMAL(10,2) | Monto del ingreso         |
| fecha          | DATE          | Fecha del ingreso         |
| tipo_ingreso   | VARCHAR(100)  | Tipo de ingreso           |
| id_usuario     | BIGINT        | Usuario propietario       |


**Validaciones:**
- Cantidad positiva
- tipo ingreso (casual o mensual)
- Descripción

---

#### 🏷️ GASTO

```sql
CREATE TABLE gasto (
    id_gasto BIGSERIAL PRIMARY KEY,
    nombre_gasto VARCHAR(255),
    categoria VARCHAR(100),
    fecha_gasto DATE NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    id_usuario BIGINT REFERENCES usuario(id_usuario)
);
```

| Campo        | Tipo          | Descripción             |
| ------------ | ------------- | ----------------------- |
| id_gasto     | BIGSERIAL     | Identificador del gasto |
| nombre_gasto | VARCHAR(255)  | Descripción             |
| categoria    | VARCHAR(100)  | Categoría del gasto     |
| fecha_gasto  | DATE          | Fecha del gasto         |
| valor        | DECIMAL(10,2) | Monto                   |
| id_usuario   | BIGINT        | Usuario asociado        |


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

#### 🎯 ALCANCIA

```sql
CREATE TABLE alcancia (
    id_alcancia BIGSERIAL PRIMARY KEY,
    nombre_alcancia VARCHAR(255) NOT NULL,
    meta DECIMAL(10,2) NOT NULL,
    saldo_actual DECIMAL(10,2) DEFAULT 0,
    fecha_creacion DATE NOT NULL
);

```

| Campo           | Tipo          | Descripción       |
| --------------- | ------------- | ----------------- |
| id_alcancia     | BIGSERIAL     | Identificador     |
| nombre_alcancia | VARCHAR(255)  | Nombre de la meta |
| meta            | DECIMAL(10,2) | Monto objetivo    |
| saldo_actual    | DECIMAL(10,2) | Ahorro actual     |
| fecha_creacion  | DATE          | Fecha de creación |
|

---

#### 💳 DEPOSITO

```sql
CREATE TABLE deposito (
    id_deposito BIGSERIAL PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    fecha_deposito DATE NOT NULL,
    nombre VARCHAR(255),
    id_alcancia BIGINT REFERENCES alcancia(id_alcancia),
    id_usuario BIGINT REFERENCES usuario(id_usuario)
);
```

| Campo          | Tipo          | Descripción      |
| -------------- | ------------- | ---------------- |
| id_deposito    | BIGSERIAL     | Identificador    |
| monto          | DECIMAL(10,2) | Monto depositado |
| fecha_deposito | DATE          | Fecha            |
| nombre         | VARCHAR(255)  | Descripción      |
| id_alcancia    | BIGINT        | Alcancía destino |
| id_usuario     | BIGINT        | Usuario          |

---

#### 🔔 RECORDATORIO

```sql
CREATE TABLE recordatorio (
    id_recordatorio BIGSERIAL PRIMARY KEY,
    nombre_recordatorio VARCHAR(255),
    estado VARCHAR(50),
    fecha_recordatorio DATE NOT NULL,
    tiempo_recordatorio TIME,
    valor DECIMAL(10,2),
    id_usuario BIGINT REFERENCES usuario(id_usuario)
);

```

| Campo               | Tipo          | Descripción    |
| ------------------- | ------------- | -------------- |
| id_recordatorio     | BIGSERIAL     | Identificador  |
| nombre_recordatorio | VARCHAR(255)  | Descripción    |
| estado              | VARCHAR(50)   | Estado         |
| fecha_recordatorio  | DATE          | Fecha          |
| tiempo_recordatorio | TIME          | Hora           |
| valor               | DECIMAL(10,2) | Valor asociado |
| id_usuario          | BIGINT        | Usuario        |


---

#### ⚠️ ALERTA

```sql
CREATE TABLE alerta (
    id_alerta BIGSERIAL PRIMARY KEY,
    nombre_alerta VARCHAR(255),
    descripcion VARCHAR(500),
    fecha_alerta DATE NOT NULL,
    id_usuario BIGINT REFERENCES usuario(id_usuario)
);

```

| Campo         | Tipo         | Descripción   |
| ------------- | ------------ | ------------- |
| id_alerta     | BIGSERIAL    | Identificador |
| nombre_alerta | VARCHAR(255) | Nombre        |
| descripcion   | VARCHAR(500) | Detalle       |
| fecha_alerta  | DATE         | Fecha         |
| id_usuario    | BIGINT       | Usuario       |


---

#### 💡 CONSEJOS

```sql
CREATE TABLE consejos (
    id_consejo BIGSERIAL PRIMARY KEY,
    nombre_consejo VARCHAR(255) NOT NULL
);
```

| Campo          | Tipo         | Descripción        |
| -------------- | ------------ | ------------------ |
| id_consejo     | BIGSERIAL    | Identificador      |
| nombre_consejo | VARCHAR(255) | Consejo financiero |


**Tipos de Consejos:**
- 💰 Ahorro: Recomendaciones para ahorrar
- 💸 Gasto: Consejos para reducir gastos
- 📈 Inversión: Información sobre inversiones
- 📚 Educación: Conceptos financieros

---

#### 🔐 CALIFICACIONES

```sql
CREATE TABLE calificaciones (
    id_calificacion BIGSERIAL PRIMARY KEY,
    me_gusta BOOLEAN,
    no_me_gusta BOOLEAN,
    id_consejo BIGINT REFERENCES consejos(id_consejo),
    id_usuario BIGINT REFERENCES usuario(id_usuario)
);
```

| Campo           | Tipo      | Descripción   |
| --------------- | --------- | ------------- |
| id_calificacion | BIGSERIAL | Identificador |
| me_gusta        | BOOLEAN   | Like          |
| no_me_gusta     | BOOLEAN   | Dislike       |
| id_consejo      | BIGINT    | Consejo       |
| id_usuario      | BIGINT    | Usuario       |

---

### Relaciones Entre Tablas

#### 📍 Relaciones Principales

```
USUARIO (1) ── (N) INGRESO
USUARIO (1) ── (N) GASTO
USUARIO (1) ── (N) DEPOSITO
USUARIO (1) ── (N) RECORDATORIO
USUARIO (1) ── (N) ALERTA
USUARIO (1) ── (N) CALIFICACIONES
USUARIO (N) ── (M) ROLES (vía USER_ROLES)

ALCANCIA (1) ── (N) DEPOSITO
CONSEJOS (1) ── (N) CALIFICACIONES

```
---

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
