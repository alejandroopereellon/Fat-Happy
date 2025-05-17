<!-- Banner/logo principal -->
<!-- Sustituye docs/images/banner.png por la ruta real -->
<p align="center">
  <img src="docs/images/banner.png" alt="Fat-Happy banner" width="600">
</p>

<h1 align="center">Fat-Happy 🍔</h1>
<p align="center">
  Plataforma integral de gestión de restaurantes con stock en tiempo real, servidor de sockets multihilo y persistencia Hibernate&nbsp;6.
</p>

<p align="center">
  <!-- Badges básicos -->
  <a href="https://github.com/alejandroopereellon/Fat-Happy/actions">
    <img src="https://github.com/alejandroopereellon/Fat-Happy/actions/workflows/maven.yml/badge.svg" alt="Build status"/>
  </a>
  <img src="https://img.shields.io/badge/JDK-17%2B-blue" alt="JDK 17+"/>
  <img src="https://img.shields.io/badge/Hibernate-6.6.11.Final-green" alt="Hibernate 6.6.11"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="MIT license"/>
</p>

---

## 🧐 ¿Qué es Fat-Happy?
Fat-Happy es un conjunto de **tres micro-proyectos Maven 100 % Java**:

| Módulo | Descripción breve |
|--------|-------------------|
| **FatHappyRestaurant** | Capa de negocio y persistencia (Hibernate 6 + MariaDB). |
| **SocketServidorFatHappy** | Servidor TCP multihilo que emite actualizaciones de stock y ventas. |
| **SocketClienteFatHappy** | Cliente ligero que consume el canal y actualiza la interfaz local en tiempo real. |

> **Objetivo**: ofrecer a restaurantes pequeños y medianos una base sólida para centralizar inventario, pedidos y ventas sin depender de costosas soluciones SaaS.

---

## ✨ Funcionalidades clave

* CRUD completo de productos y categorías.  
* Sincronización de stock **en tiempo real** entre varios puntos de venta mediante sockets.  
* Registro exhaustivo con **Log4j 2 v2.24.3** (última versión estable) <!-- cita para Log4j -->  
* Actualizaciones masivas de inventario con SQL nativo (`createNativeMutationQuery`) evitando excepciones `UnknownEntityException`.  
* Arquitectura modular: cada componente puede desplegarse por separado o juntos en un único host.

---

## 🏗️ Arquitectura

<!-- Inserta aquí un diagrama PNG/SVG -->
<p align="center">
  <img src="docs/images/arquitectura.svg" alt="Diagrama de arquitectura" width="700">
</p>

1. La app **Cliente** envía peticiones locales y escucha eventos del **Servidor**.  
2. El **Servidor de sockets** difunde los cambios a todos los clientes conectados.  
3. El **Módulo Restaurant** persiste la información en la base de datos MariaDB mediante Hibernate.

---

## 🚀 Primeros pasos

### Prerrequisitos
* **JDK 17** o superior.  
* **Maven 3.9+** con `JAVA_HOME` configurado.  
* Base de datos **MariaDB 10 +**.

### Instalación rápida

```bash
git clone https://github.com/alejandroopereellon/Fat-Happy.git
cd Fat-Happy
mvn clean package
