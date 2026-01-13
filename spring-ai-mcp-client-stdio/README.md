# 🤖 MCP Client (Spring Boot)

This project implements a **Spring Boot MCP (Model Context Protocol) client** that connects to the **Shopping Cart MCP Server** via **stdio**.  
It integrates with **OpenAI** for chat-based interactions and uses environment-based configuration for flexibility.

---

## 🚀 Features
- **OpenAI Integration**:
  - Configurable API key via environment variables
  - Customizable chat options (model, temperature, max tokens)
- **MCP Client**:
  - Connects to the Shopping Cart MCP Server using stdio
  - You can add item and get item details from the cart
  - Server configurations loaded from `mcp-servers.json`
  - Configure the `command` and `args` in the `mcp-servers.json` according to your paths
```
{
    "mcpServers": {
      "stdio-mcp-server" : {
         "command" : "path/to/the/java.exe",
         "args" : ["-jar", "path/to/the/jar/file"]
        }
    }
}
```
    
- **Configuration**:
  - Environment-based (`.env` or `.properties`)
- **Logging**:
  - Debug-level logging for AI chat client advisor

---

## ⚙️ Environment Setup

Sensitive configuration values are managed via environment variables.  
Use the provided **`.env.example`** as a template.

### Example `.env` file:
```properties
OPEN_AI_API_KEY=your_openai_api_key_here
AI_CHAT_MODEL=gpt-4.1
```

---

## 👨‍💻 Author
**Manash Barman**  
Backend Developer | Java, Spring Boot, Microservices  
[LinkedIn](https://www.linkedin.com/in/manash-barman-15b1833a1/) | [GitHub](https://github.com/manashbarman007-cmyk)

---
