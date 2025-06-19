# Mrs. Clean Laundry System

**👨‍💻 Developer:** Eric Russel M. Lopez

A desktop Laundry Management & Monitoring System built with Java Swing and PHP/MySQL.

## 🚀 Setup & Installation

Follow these steps to get the system running:

1. **Download the Source Code**

   ```bash
   git clone https://github.com/EricRusselLopez/mrscleansystem.git
   ```
2. **Install XAMPP**

   * Download from [https://www.apachefriends.org](https://www.apachefriends.org)
   * Install and start **Apache** & **MySQL** via XAMPP Control Panel
3. **Deploy PHP Backend**

   * Copy the `whole mrscleansystem/` folder into `htdocs`:

     ```
     C:\xampp\htdocs\mrscleansystem\
     ```
4. **Import the Database**

   * Open [http://localhost/phpmyadmin](http://localhost/phpmyadmin)
   * Create a database named **mrscleanlaundryease**
   * Import the schema file:

     ```
     server/sql/mrscleanlaundryease.sql
     ```
5. **Configure PHP Connection**

   * Edit `server/php/conn.php`:

     ```php
     $host     = 'localhost';
     $user     = 'root';
     $password = '';
     $database = 'mrscleanlaundryease';
     ```
6. **Set Up Email (PHPMailer)**

   * In `server/php/email_config.php`, update SMTP credentials:

     ```php
     username = 'your_email@gmail.com';
     password = 'your_app_password';
     ```
7. **Run the PHP Server**

   * Ensure Apache & MySQL are running.
8. **Run the Java Client**

   * Navigate to the parent `mrscleansystem` folder:

     ```bash
     cd mrscleansystem
     java -jar mrscleansystem.jar
     ```
9. **Initial Login**

   * **Employee**: Use test credentials or register to request access.
   * **Owner**: Login with email.

## 🌐 Server URL Configuration

The Java application connects to the backend PHP server using a dynamic IP-based URL. This logic is defined in:

```
src/server/java/ServerURL.java
```

#### 🔧 How it works:

```java
public ServerURL() {
    try {
        // OPTION 1: Manual IP (for clients on the same network)
        // Replace '192.168.1.3' with the IP of the main machine
        // this.baseURL = "http://192.168.1.3/mrscleansystem/src/server/php/";

        // OPTION 2: Automatic detection (for main/local server only)
        InetAddress localIp = InetAddress.getLocalHost();
        String ip = localIp.getHostAddress();
        this.baseURL = "http://" + ip + "/mrscleansystem/src/server/php/";
    } catch (Exception e) {
        this.baseURL = "http://localhost/mrscleansystem/src/server/php/";
    }
}
```

#### 📌 Notes:

* If the machine is on a local network (LAN), the IP will allow others to connect using the same app. Just decide where the main server will run, and turn off Windows Defender Firewall (or allow PHP/Apache through) to enable access from other devices.
* You can change this path if you are deploying online (e.g. to a web host or cloud VM).
* The fallback ensures functionality even if the IP cannot be resolved.

#### 📁 Expected PHP Path:

Make sure your PHP backend files (e.g., `login.php`, `get_branches.php`) are located in:

```
src/server/php/
```

## 📂 Folder Structure

```
mrscleansystem/
├── .vscode/              # VSCode workspace settings
├── src/
│   ├── assets/           # Images, logos, visuals
│   ├── icons/            # App icons and UI graphics
│   ├── lib/              # External Java libraries (e.g., JSON, Layout)
│   ├── main/             # Java source files (App.java, Login.java, etc.)
│   ├── server/           # PHP backend + SQL
│   └── utils/            # Custom Java utilities (e.g., renderers)
├── LICENSE               # License
├── mrscleansystem.jar     # Compiled Java application
├── README.md             # Documentation
```

## 👍 Next Steps

* Open `mrscleansystem.jar` and start managing orders, inventory, and reports.
* For issues, check XAMPP logs and PHP error logs.
* Consult the **Troubleshooting** section in project wiki.
