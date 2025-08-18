# 📖 Bukkey – License Key Managment for Bukkit Plugins  

![Security](https://img.shields.io/badge/Security-Licensing-blue)  
![Bukkit](https://img.shields.io/badge/API-Bukkit%201.16.5-green)  
![Lang](https://img.shields.io/badge/Languages-RU%20%7C%20EN-orange)  

---

## 🇷🇺 Русская версия

### 🔒 Что такое Bukkey?
Bukkey — это удобный класс для проверки лицензионных ключей Bukkit-плагинов.  
Он взаимодействует с сервером лицензий и защищает ваш плагин от несанкционированного использования.  

📌 **Важно:**  
Bukkey не является полной защитой, а служит дополнением. Для максимальной безопасности рекомендуется использовать **обфускатор кода** вместе с Bukkey.  

---

### ⚡ Установка
1. Скачайте **актуальный релиз** из раздела [Releases](../../releases).  
2. Поместите файл `Bukkey.java` в папку вашего Bukkit-плагина.  
3. В методе `onEnable()` вашего плагина вызовите:  

```java
@Override
public void onEnable() {
    String licenseKey = getConfig().getString("LICENSE_KEY"); 
    boolean enableCheck = true; // включить/выключить проверку

    new Bukkey(this, licenseKey, "YOUR_PLUGIN_ID", enableCheck);
}
```
Где:

1. ```licenseKey``` - это ваш лицензионный ключ из конфига
2. ```enableCheck``` - включить/выключить проверку ключа
3. ```YOUR_PLUGIN_ID``` - ID вашего плагина в панели управления на сайте

☄️ Минимальная версия **Java** для запуска: **8**

## 🇬🇧 English version

### 🔒 What is Bukkey?
Bukkey is a lightweight class for validating license keys in Bukkit plugins.
It communicates with your licensing server and prevents unauthorized usage of your plugin. 

📌 **Note:**  
Bukkey itself is not a full protection system. For maximum security, it is recommended to combine Bukkey + **Java obfuscator** you'd prefer.

---

### ⚡ Installation
1. Download **the latest** relase from [Releases](../../releases).  
2. Put `Bukkey.java` inside your Bukkit plugin's folder.  
3. In plugin's method `onEnable()` call:  

```java
@Override
public void onEnable() {
    String licenseKey = getConfig().getString("LICENSE_KEY"); 
    boolean enableCheck = true; // enable/disable license validation

    new Bukkey(this, licenseKey, "YOUR_PLUGIN_ID", enableCheck);
}
```
Where:

1. ```licenseKey``` - your license key from the config.yml
2. ```enableCheck``` - enable/disable license validation
3. ```YOUR_PLUGIN_ID``` - your plugin ID, available in the plugin management panel on the website.

☄️ Minimal version of **Java** for launch: **8**
