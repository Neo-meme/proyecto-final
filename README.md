[README.md](https://github.com/user-attachments/files/28720190/README.md)
# 🟡 Strawus

> Videojuego inspirado en Pac-Man desarrollado en Java utilizando arquitectura MVC (Modelo - Vista - Controlador).

---

## 📖 Descripción

Strawus es un videojuego arcade basado en la mecánica clásica de Pac-Man, donde el jugador debe recorrer un laberinto recolectando pellets mientras evita a los fantasmas.

El proyecto incorpora características adicionales como:

- 🎮 Selección de personajes con habilidades únicas.
- 🏆 Sistema de puntuación.
- 📊 Clasificaciones persistentes.
- 🗺️ Dos niveles jugables.
- 🍒 Power-ups especiales.
- ❤️ Sistema de vidas.
- ⏸️ Pausa de juego.
- 👻 Fantasmas vulnerables.
- 🚇 Túneles laterales.

---

## 🎯 Objetivos del Proyecto

- Aplicar el patrón arquitectónico MVC.
- Implementar Programación Orientada a Objetos (POO).
- Gestionar eventos mediante Java Swing.
- Implementar persistencia de datos.
- Desarrollar mecánicas de videojuegos utilizando algoritmos de colisión y estructuras de datos.

---

# 🏗️ Arquitectura del Proyecto

```text
📦 Proyecto Strawus
├── 📁 resources
│   ├── 📁 cosas
│   │   ├── 🖼️ icono.png
│   │   ├── 🖼️ instrucciones.png
│   │   ├── 📄 README.md
│   │   └── 🖼️ titulo.png
│   ├── 📁 nivel 1
│   │   ├── 🖼️ CLASICO.png
│   │   ├── 🖼️ tanque.png
│   │   └── 🖼️ veloz.png
│   ├── 📁 nivel 2
│   │   ├── 🖼️ CLASICO.png
│   │   ├── 🖼️ tanque.png
│   │   └── 🖼️ veloz.png
│   └── 📄 IMAGENES_REQUERIDAS.txt
│
├── 📁 src
│   ├── 📁 CONTROLADOR
│   │   ├── 📁 controller
│   │   │   ├── ☕ ControladorJuego.java
│   │   │   └── ☕ ControladorMenu.java
│   │   └── 📁 input
│   │       └── ☕ KeyHandler.java
│   │
│   ├── 📁 Games
│   │   ├── ☕ GamePanel.java
│   │   └── ☕ Main.java
│   │
│   ├── 📁 MODELO
│   │   ├── 📁 collision
│   │   │   └── ☕ CollisionChecker.java
│   │   ├── 📁 entity
│   │   │   ├── ☕ Entity.java
│   │   │   ├── ☕ Fantasma.java
│   │   │   ├── ☕ Pacman.java
│   │   │   └── ☕ TipoPacman.java
│   │   ├── 📁 object
│   │   │   ├── ☕ GestorClasificaciones.java
│   │   │   └── ☕ Pellet.java
│   │   └── 📁 tile
│   │       ├── ☕ Tile.java
│   │       └── ☕ TileManager.java
│   │
│   └── 📁 VISTA
│       ├── ☕ MenuPrincipal.java
│       ├── ☕ PanelPrincipal.java
│       ├── ☕ ResourceManager.java
│       ├── ☕ VistaClasificaciones.java
│       ├── ☕ VistaInstrucciones.java
│       ├── ☕ VistaJuego.java
│       └── ☕ VistaSeleccionPersonaje.java
│
├── 📄 clasificaciones.txt
└── 📄 README.md
```

---

## 🧠 Patrón MVC

### Modelo

Gestiona toda la lógica del videojuego:

- Pacman
- Fantasma
- Pellet
- Tile
- CollisionChecker
- GestorClasificaciones

### Vista

Contiene todas las interfaces gráficas:

- MenuPrincipal
- PanelPrincipal
- VistaJuego
- VistaClasificaciones
- VistaInstrucciones
- VistaSeleccionPersonaje

### Controlador

Gestiona la interacción entre usuario y sistema:

- ControladorMenu
- ControladorJuego
- KeyHandler

---

# 🎮 Personajes Disponibles

## 🟡 Pacman Clásico

| Característica | Valor |
|---------------|--------|
| Vidas | 3 |
| Velocidad | 2 |
| Color | Amarillo |

Equilibrio entre velocidad y resistencia.

---

## 🟢 Pacman Tanque

| Característica | Valor |
|---------------|--------|
| Vidas | 5 |
| Velocidad | 1 |
| Color | Verde |

Mayor resistencia a cambio de menor velocidad.

---

## 🔵 Pacman Veloz

| Característica | Valor |
|---------------|--------|
| Vidas | 1 |
| Velocidad | 4 |
| Color | Cian |

Alta velocidad con poca capacidad de supervivencia.

---

# 👻 Fantasmas

Los fantasmas representan los enemigos principales del juego.

### Características

- Movimiento aleatorio.
- Detección de colisiones con paredes.
- Cambio automático de dirección.
- Uso de túneles laterales.
- Estado vulnerable mediante Power Pellets.

---

# 🍒 Objetos del Juego

## ⚪ Pellet Normal

- Incrementa la puntuación.
- Debe ser consumido para completar el nivel.

---

## 🟠 Power Pellet

- Cambia el estado de los fantasmas a vulnerable.
- Permite eliminarlos temporalmente.

---

## 🍒 Cereza Especial

Activa una habilidad especial llamada:

### ⏳ Cronokinesis

Reduce temporalmente la velocidad de los enemigos.

---

# 🗺️ Niveles

## Nivel 1

Laberinto clásico diseñado para introducir las mecánicas básicas del juego.

---

## Nivel 2

Laberinto avanzado con rutas alternativas y mayor dificultad.

---

# 🎹 Controles

| Tecla | Acción |
|--------|---------|
| W | Mover arriba |
| A | Mover izquierda |
| S | Mover abajo |
| D | Mover derecha |
| ↑ | Mover arriba |
| ← | Mover izquierda |
| ↓ | Mover abajo |
| → | Mover derecha |
| P | Pausar juego |
| ESC | Pausar juego |

---

# 🧱 Sistema de Colisiones

El sistema utiliza hitboxes rectangulares para detectar:

- Colisiones con paredes.
- Colisiones entre Pacman y fantasmas.
- Movimiento válido dentro del mapa.
- Uso de túneles laterales.

---

# 🏆 Sistema de Clasificaciones

Los resultados se almacenan en:

```text
clasificaciones.txt
```

Se registra:

- Puntaje obtenido.
- Tiempo de partida.

Solo se conservan los:

```text
Top 10 mejores resultados
```

---

# 💾 Persistencia de Datos

Formato de almacenamiento:

```text
PUNTOS;TIEMPO
```

Ejemplo:

```text
1500;125
1200;140
950;180
```

---

# ⚙️ Tecnologías Utilizadas

- Java 17+
- Java Swing
- Java AWT
- Programación Orientada a Objetos (POO)
- Arquitectura MVC

---

# 🚀 Instalación y Ejecución

## Clonar el repositorio

```bash
git clone https://github.com/usuario/strawus.git
```

## Abrir el proyecto

Puede utilizar cualquiera de los siguientes IDEs:

- IntelliJ IDEA
- Eclipse
- NetBeans

## Ejecutar la aplicación

Ejecutar la clase:

```java
src.Games.Main
```

---

# 📋 Requisitos

- JDK 17 o superior
- IDE compatible con Java
- Windows, Linux o macOS

---

# 📂 Estructura General

```text
CONTROLADOR
│
├── ControladorMenu
├── ControladorJuego
└── KeyHandler

MODELO
│
├── Entity
├── Pacman
├── Fantasma
├── Pellet
├── Tile
├── CollisionChecker
└── GestorClasificaciones

VISTA
│
├── MenuPrincipal
├── PanelPrincipal
├── VistaJuego
├── VistaClasificaciones
├── VistaInstrucciones
└── VistaSeleccionPersonaje

Games
│
├── GamePanel
└── Main
```

---

# ✅ Funcionalidades Implementadas

- Arquitectura MVC
- Menú principal interactivo
- Selección de personaje
- Tres tipos de Pacman
- Sistema de vidas
- Sistema de puntuación
- Clasificaciones persistentes
- Dos niveles jugables
- Colisiones con paredes
- Fantasmas enemigos
- Fantasmas vulnerables
- Power Pellets
- Cereza especial
- Cronokinesis
- Túneles laterales
- Pausa del juego
- Persistencia mediante archivos

---

# 👥 Integrantes

- Juan David Merchan Gonzalez 
- Alan David Rodriguez Cruz 

---

# 📄 Licencia

Proyecto desarrollado con fines académicos.

---

## 🌟 Estado del Proyecto

```diff
+ Proyecto Finalizado
+ Arquitectura MVC Implementada
+ Persistencia de Datos Implementada
+ Sistema de Juego Funcional
```

---

**Strawus © 2025** 🟡👻🍒
