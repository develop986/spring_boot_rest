# Spring Boot、RESTサンプルアプリ

> Spring Boot で作成した、簡易RESTシステムです
> - ログイン認証
> - CRUD
> - パスワード暗号化保存

| パッケージ名 | バージョン |
| ------------- | ------------- |
| connect-flash  | ^0.1.1  |
| cookie-parser  | ^1.4.6  |
| ejs  | ^3.1.8  |
| express  | ^4.18.1  |
| express-ejs-layouts  | ^2.5.1  |
| express-session  | ^1.17.3  |
| express-validator  | ^6.14.0  |
| http-status-codes  | ^2.2.0  |
| method-override  | ^3.0.0  |
| mongoose  | ^6.3.3  |
| passport  | ^0.5.2  |
| passport-local-mongoose  | ^7.0.0  |

## 事前準備

- JavaとGragleをインストールしておくこと

http://localhost:3000/users/new

## プロジェクト作成方法

[spring initializr](https://start.spring.io/)

- Gragle
- Java
- Spring Boot 2.7.0
- Metadata
  - Group: com.mysv986
  - Artifact: spring_boot_rest
  - Name: spring_boot_rest
  - Description: Spring Boot、REST、Sample
  - Package name: com.mysv986.spring_boot_rest
- Jar
- Java 17

## 実行方法

```
$ ./gradlew bootRun
```

http://localhost:8080/

## ビルド

```
$ gradlew build
$ java -jar build/libs/helloworld-0.0.1-SNAPSHOT.jar
```

## 参考文献

- Angular
  - [入門Node.jsプログラミング](https://www.shoeisha.co.jp/book/detail/9784798158624)

## サーバー構築（CentOS Stream release 8）

> 動作環境
> - [WebARENA Indigo](https://web.arena.ne.jp/indigo/)
>   - 事前に以下で、Webサーバー環境を作っておく
>     - `01_Indigo.md`
>     - `02_Apache.md`
>     - `80_NodeJs.md`

> https://github.com/develop986/centos_server

### 動作環境構築

```
# git clone https://github.com/develop986/node_express_mongo
# git pull
# cd node_express_mongo
# npm install
```

## サービス作成

```
  580  vi /etc/systemd/system/nodeexpressmongo.service
  581  cat /etc/systemd/system/nodeexpressmongo.service
  582  systemctl enable nodeexpressmongo.service
  583  systemctl start nodeexpressmongo.service
  584  systemctl stop nodeexpressmongo.service
  586  systemctl restart nodeexpressmongo.service

[root@centos ~]# cat /etc/systemd/system/nodeexpressmongo.service
[Unit]
Description=nodeexpressmongo server
After=syslog.target network.target

[Service]
Type=simple
ExecStart=/usr/bin/node /root/node_express_mongo/main.js
WorkingDirectory=/root/node_express_mongo
KillMode=process
Restart=always
User=root
Group=root

[Install]
WantedBy=multi-user.target
```

### VirtualHost 設定

```
# vi /etc/httpd/conf/httpd.conf

以下のファイルを作成する

<VirtualHost *:80>
    ServerAdmin room@mysv986.com
    DocumentRoot /var/www/html/
    ServerName nodeexpressmongo.mysv986.com
    ServerAlias nodeexpressmongo.mysv986.com
RewriteEngine on
RewriteCond %{SERVER_NAME} =nodeexpressmongo.mysv986.com
RewriteRule ^ https://%{SERVER_NAME}%{REQUEST_URI} [END,NE,R=permanent]
</VirtualHost>
```

```
# systemctl restart httpd
# certbot --apache -d nodeexpressmongo.mysv986.com
# systemctl restart httpd
```

```
# vi /etc/httpd/conf/httpd-le-ssl.conf

Include以下に、SSLProxy設定を追記する。

<VirtualHost *:443>
    ServerAdmin room@mysv986.com
    DocumentRoot /var/www/html/
    ServerName nodeexpressmongo.mysv986.com
    ServerAlias nodeexpressmongo.mysv986.com

SSLCertificateFile /etc/letsencrypt/live/nodeexpressmongo.mysv986.com/fullchain.pem
SSLCertificateKeyFile /etc/letsencrypt/live/nodeexpressmongo.mysv986.com/privkey.pem
Include /etc/letsencrypt/options-ssl-apache.conf

    SSLEngine on
    SSLProxyVerify none
    SSLProxyCheckPeerCN off
    SSLProxyCheckPeerName off
    SSLProxyCheckPeerExpire off
    SSLProxyEngine on
    ProxyRequests off
    ProxyPass / http://nodeexpressmongo.mysv986.com:3000/
    ProxyPassReverse / http://nodeexpressmongo.mysv986.com:3000/

</VirtualHost>
```

```
# systemctl restart httpd
```

https://nodeexpressmongo.mysv986.com