# ⚡ Bukkey – License Keys Management for Bukkit Plugins  

![Security](https://img.shields.io/badge/Security-Licensing-blue)  
![Bukkit](https://img.shields.io/badge/API-Bukkit%201.16.5-green)  
![Lang](https://img.shields.io/badge/Languages-RU%20%7C%20EN-orange)  

<img width="1430" height="862" alt="изображение" src="https://github.com/user-attachments/assets/499b7c73-51c0-48e4-9de8-4bd6d182dccc" />


---

## 🇷🇺 Русская версия

### 🔒 Что такое Bukkey?
Bukkey — это удобный сервис для управления лицензионными ключами для ваших Bukkit-плагинов.  
Он взаимодействует с сервером лицензий и защищает ваш плагин от несанкционированного использования.  

📌 **Важно:**  
Bukkey не является полной защитой, а служит дополнением. Для максимальной безопасности рекомендуется использовать **обфускатор кода** вместе с Bukkey.  

---

### ⚡ Установка
1. Добавьте репозиторий и зависимость в плагин.

```yml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>                            
```
```yml
<dependency>
    <groupId>com.github.alexec0de</groupId>
    <artifactId>Bukkey</artifactId>
    <version>1.0.0</version>
</dependency>                                                      
```

2. В методе `onEnable()` вашего плагина вызовите наш основной класс:  

```java
@Override
public void onEnable() {
    // ...
    saveDefaultConfig();

    String licenseKey = getConfig().getString("LICENSE_KEY");

    Bukkey bukkey = new Bukkey(licenseKey, "PLUGIN_ID", this.getLogger());

    if (!bukkey.check()) {
        // проверка не пройдена, можете к примеру выключить плагин
        Bukkit.getPluginManager().disablePlugin(this);
    }

    // ...
}
```
Где:

1. ```licenseKey``` - это ваш лицензионный ключ из конфига
2. ```YOUR_PLUGIN_ID``` - ID вашего плагина в панели управления на сайте

☄️ Минимальная версия **Java** для запуска: **8**

## 🇬🇧 English version

### 🔒 What is Bukkey?
Bukkey is a convenient service for managing license keys for your Bukkit plugins.
It communicates with your licensing server and prevents unauthorized usage of your plugin. 

📌 **Note:**  
Bukkey itself is not a full protection system. For maximum security, it is recommended to combine Bukkey + **Java obfuscator** you'd prefer.

---

### ⚡ Installation
1. Add a repo and a dependency in your plugin.

```yml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>                            
```
```yml
<dependency>
    <groupId>com.github.alexec0de</groupId>
    <artifactId>Bukkey</artifactId>
    <version>1.0.0</version>
</dependency>                                                      
```

2. In `onEnable()` of your plugin call our main class:  

```java
@Override
public void onEnable() {
    // ...
    saveDefaultConfig();

    String licenseKey = getConfig().getString("LICENSE_KEY");

    Bukkey bukkey = new Bukkey(licenseKey, "PLUGIN_ID", this.getLogger());

    if (!bukkey.check()) {
        // check has failed, you can disable the plugin, for example:
        Bukkit.getPluginManager().disablePlugin(this);
    }

    // ...
}
```
Where:

1. ```licenseKey``` - Your license key from a config.yml
2. ```YOUR_PLUGIN_ID``` - Your plugin's ID in the control panel on the website

☄️ Minimal version of **Java** for launch: **8**
