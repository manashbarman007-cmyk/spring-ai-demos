# 🌱 Spring AI Application with Chat Memory, RAG and Tool Calling

This project demonstrates a **Spring Boot application powered by Spring AI**, integrating:

* 💬 **LLM-based chat using OpenAI**
* 🧠 **Persistent chat memory (JDBC-backed)**
* 📦 **Vector storage using MariaDB** for embeddings
* 🌦️ **External Weather API integration**
* 🔐 **Environment-based configuration** using `.env`

The setup is designed for **production-ready AI applications** with observability, persistence, and extensibility in mind.

---

## 🚀 Key Features

### 🤖 AI Chat Integration

* Uses **Spring AI ChatClient** with OpenAI
* Configurable model, temperature, and token limits
* Debug-level logging enabled for prompt and advisor tracing

### 🧠 Chat Memory (JDBC)

* Conversation history is **persisted in a relational database**
* Automatically initializes required tables
* Enables multi-turn, contextual conversations across sessions

### 📦 Vector Store (MariaDB)

* Stores embeddings in **MariaDB**
* Supports **COSINE similarity search**
* Schema auto-initialization enabled
* Ideal for **RAG (Retrieval-Augmented Generation)** use cases

### 🌦️ Weather API Integration

* External weather API configured via environment variable
* Can be used by tools, agents, or function-calling workflows

### 🔐 Secure Configuration

* Sensitive values managed via environment variables
* Supports `.env` or `.properties` files

---

## ⚙️ Configuration Overview

### Spring AI Configuration

* **Chat Memory**: JDBC-backed repository with automatic schema creation
* **Vector Store**: MariaDB-based embedding store
* **OpenAI**: Chat model configuration with tuning options

### Database Configuration

* MariaDB used for:

  * Chat memory persistence
  * Vector embeddings storage

### Logging

* Debug logging enabled for:

  * `org.springframework.ai.chat.client.advisor`

This helps with:

* Prompt inspection
* Tool invocation debugging
* Advisor execution tracing

---

## 📁 Environment Variables

Create a `.env` file in the project root (or use environment variables directly).

### Example `.env`

```properties
# OpenAI
OPEN_AI_API_KEY=your_openai_api_key
AI_CHAT_MODEL=gpt-4.1

# Database
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DIMENSIONS=1536

# Weather API
WEATHER_API_KEY=your_weather_api_key
```

---

## 🗄️ Database Setup

Ensure MariaDB is running and accessible:

* **Host**: `localhost`
* **Port**: `3307`
* **Database**: `test_DB`

The application will automatically:

* Create chat memory tables
* Create vector embedding tables (`embeddings_table`)

---

## 🧩 Common Use Cases

* Conversational AI with long-term memory
* RAG-based question answering
* Tool-augmented agents (e.g., weather lookup, current date and time lookup)
* Enterprise AI applications with auditability

---

## 🛠️ Tech Stack

* Java 17+
* Spring Boot
* Spring AI
* OpenAI API
* MariaDB
* JDBC

---

## 📌 Notes & Best Practices

* Keep `.env` out of version control
* Tune `DIMENSIONS` to match your embedding model
* Use DEBUG logging only in development
* MariaDB vector store works best with indexed embedding columns

---

## 👨‍💻 Author
**Manash Barman**  
Backend Developer | Java, Spring Boot, Microservices  
[LinkedIn](https://www.linkedin.com/in/manash-barman-15b1833a1/) | [GitHub](https://github.com/manashbarman007-cmyk)

---
