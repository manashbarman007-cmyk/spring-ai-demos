# 🛒 Shopping Cart MCP Server

This project implements a **Spring Boot MCP (Model Context Protocol) server** for managing a shopping cart system.  
It is designed to run as a **stdio-based MCP server** (non-web application) with MySQL as the database backend, clean logging, and environment-based configuration.

---

## 🚀 Features
- **Shopping Cart Backend**:
  - Add and remove cart items
  - Persist cart state in MySQL
  - Ready for integration with MCP clients
- **Database Integration**:
  - MySQL with Hibernate ORM
  - Auto schema updates (`ddl-auto: update`)
- **MCP Server**:
  - Registered as **stdio type**
  - Lightweight, non-web application
- **Configuration**:
  - Environment-based (`.env` or `.properties`)
  - Example file provided: `.env.example`
- **Logging**:
  - File-based logging only (`./logs/mcp-server.log`)
  - Console logging disabled for clean stdio communication

---

## ⚙️ Environment Setup

Sensitive configuration values are managed via environment variables.  
Use the provided **`.env.example`** as a template.

### Steps:
1. Copy `.env.example` to `.env`:
   ```bash
   cp .env.example .env

---

## 👨‍💻 Author
**Manash Barman**  
Backend Developer | Java, Spring Boot, Microservices  
[LinkedIn](https://www.linkedin.com/in/manash-barman-15b1833a1/) | [GitHub](https://github.com/manashbarman007-cmyk)

---
