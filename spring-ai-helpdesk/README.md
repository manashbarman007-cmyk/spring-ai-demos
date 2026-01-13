# 🛠️ AI Helpdesk Bot with Spring AI & Tool Calling

This module describes an **AI-powered Helpdesk Bot** built using **Spring Boot + Spring AI**, capable of **creating, updating, retrieving, and deleting helpdesk tickets** via **LLM tool calling**.

The bot acts as a **natural-language interface** to a traditional ticketing system, allowing users to resolve issues conversationally while maintaining full database-backed persistence and auditability.

---

## 🎯 Purpose

The AI Helpdesk Bot is designed to:

* Accept **free-form user queries** ("Create a ticket for login issue")
* Decide **which backend action (tool)** to invoke
* Execute CRUD operations on tickets
* Maintain **conversation memory** for multi-turn interactions
* Return **structured, reliable responses** to users

This is a practical example of **agentic AI using Spring AI**.

---

## 🤖 Core Capabilities

### 🧰 Tool Calling (Function Calling)

The LLM can dynamically call backend tools to:

| Operation     | Description                             |
| ------------- | --------------------------------------- |
| Create Ticket | Open a new helpdesk ticket              |
| Update Ticket | Modify status, priority, or description |
| Get Ticket    | Fetch ticket details by emaid id   |
| Delete Ticket | Remove a ticket |

The model does **not directly manipulate the database**—it calls **well-defined tools** exposed by Spring.

---

## 🏗️ High-Level Architecture

```
User
  ↓
ChatController
  ↓
Spring AI ChatClient
  ↓
LLM (OpenAI)
  ↓ decides tool
ToolCallback / Function
  ↓
Service Layer
  ↓
JPA Repository
  ↓
MySQL Database
```

---

## 🗄️ Database Configuration

The helpdesk system uses **MySQL** with **Spring Data JPA**.

### Key Characteristics

* Automatic schema updates (`ddl-auto: update`)
* SQL logging enabled for debugging
* MySQL 8 dialect

The database stores:

* Tickets
* Chat memory (conversation history)

---

## 🧠 Chat Memory

The bot uses **JDBC-backed chat memory**, enabling:

* Context-aware conversations
* Follow-up questions ("What is the status now?")
* Ticket references across multiple turns

Schema initialization is handled automatically at startup.

---

## 🧩 Ticket Domain Model (Conceptual)

```text
Ticket
 ├─ id
 ├─ title
 ├─ description
 ├─ status (OPEN, IN_PROGRESS, RESOLVED)
 ├─ priority (LOW, MEDIUM, HIGH)
 ├─ createdAt
 └─ updatedAt
```

---

## 🔧 Tool Definitions (Conceptual)

Each tool is exposed to the LLM with a **clear contract**.

### Create Ticket Tool

* Input: title, description, priority
* Output: ticketId, status

### Update Ticket Tool

* Input: ticketId, fields to update
* Output: updated ticket summary

### Get Ticket Tool

* Input: ticketId or filters
* Output: ticket details

### Delete Ticket Tool

* Input: ticketId
* Output: confirmation

These tools are implemented as **Spring beans** and registered with the `ChatClient`.

---

## 🔄 Example Conversation Flow

**User:**

> I cannot access my account

**AI Reasoning:**

* Intent: Create ticket
* Missing info: priority

**AI:**

> I’ve created a ticket for your account access issue. Would you like to mark it as HIGH priority?

---

**User:**

> Yes, make it high priority

**AI Action:**

* Calls `updateTicket` tool

**AI Response:**

> ✅ Ticket #42 updated to HIGH priority and is currently OPEN.

---

## ⚙️ Configuration Summary

### OpenAI

* Model controlled via `AI_CHAT_MODEL`
* Temperature tuned for balanced reasoning
* Token limit enforced

### Logging

Debug logging enabled for:

```
org.springframework.ai.chat.client.advisor
```

Useful for:

* Inspecting prompts
* Tool selection tracing
* Agent reasoning visibility

---

## 📁 Environment Variables

Create a `.env` file:

```properties
# OpenAI
OPEN_AI_API_KEY=your_openai_api_key
AI_CHAT_MODEL=gpt-4.1

# Database
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
```

---

## 🛡️ Safety & Guardrails

* LLM cannot execute arbitrary SQL
* Only predefined tools are callable
* Input validation enforced at service layer
* Destructive actions (DELETE) can be role-restricted

---

## 🧪 Testing Strategy

* Unit test ticket services
* Mock tool callbacks for LLM tests
* Use SQL logs to verify persistence
* Validate multi-turn memory behavior

---

## 🚀 Future Enhancements

* SLA-based ticket escalation
* Role-based access control
* Email / Slack notifications
* Analytics dashboard
* RAG over historical tickets

---

## 📌 Why This Matters

This project demonstrates:

* **Real-world agentic AI** (not just chat)
* Clean separation of AI reasoning and business logic
* Safe, auditable AI-to-database interaction
* Enterprise-ready Spring AI architecture

---

## 👨‍💻 Author
**Manash Barman**  
Backend Developer | Java, Spring Boot, Microservices  
[LinkedIn](https://www.linkedin.com/in/manash-barman-15b1833a1/) | [GitHub](https://github.com/manashbarman007-cmyk)

---

✨ *A practical blueprint for building AI-driven enterprise helpdesk systems using Spring AI.*
