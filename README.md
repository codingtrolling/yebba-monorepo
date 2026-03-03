# 🚀 YEBBA Ecosystem

Welcome to the **YEBBA** project—a unified Android ecosystem consisting of four interconnected applications designed to work as a single, seamless experience.

## 🏗️ The Suite
* **🌐 YEBBA Search**: A high-performance web search engine and browser.
* **🛠️ YEBBA Services**: The backbone of the ecosystem. Handles background tasks, authentication, and inter-app communication.
* **📦 YEBBA Store**: A dedicated distribution hub for managing updates across the YEBBA suite.
* **💬 YEBBA Chat**: A real-time messaging platform integrated with the YEBBA identity system.

---

## 📐 Architecture
This project uses a **Monorepo** structure. All apps share a common core and are built simultaneously using **GitHub Actions**.

- **Inter-App Communication**: Handled via AIDL (Android Interface Definition Language) through the `yebba-services` module.
- **CI/CD**: Automated APK generation and signing via GitHub Workflows.

---

## 🛠️ Getting Started

### Prerequisites
- Android Studio Iguana or newer
- JDK 17
- Android SDK 34 (Upside Down Cake)

### Local Build
1. Clone the repository:
   ```bash
   git clone [https://github.com/codingtrolling/yebba-monorepo.git](https://github.com/codingtrolling/yebba-monorepo.git)
