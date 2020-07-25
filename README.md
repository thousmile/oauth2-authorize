# OAuth 2.0 授权服务

## 1.导入数据库，最好是 MySQL 8.0 

## 2.修改 [ oauth2-server ]  application-dev.yml 中的  数据库连接地址，和 redis 连接地址

## 3.在浏览器中打开如下地址

```
http://localhost:8098/authorize/code?responseType=code&clientId=484446609732808704&redirectUri=https%3a%2f%2fwww.xaaef.com%2fgetCode&scope=base_user&state=K2KpBc6cF00JkRZPd
```

## 4.输入用户名和密码， 默认都是： admin

## 5.复制浏览器地址栏中的 code 参数

![](./images/1.png)

## 6.通过 code 换 access_token 

```
POST：  http://localhost:8098/authorize/code

body：
{
    "grant_type":"authorization_code",
    "client_id":"484446609732808704",
    "client_secret":"r6BUhkaBpJKktXY33H9UNxaLJZIwE2",
    "code":"上一步，中获取的 code "
}

response: 
{
    "status": 200,
    "message": "操作成功",
    "data": {
        "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIjUwNH0.gyQXwPIHlTt0akARTP-Ig4GrmvpV4RYl6Aziqh1k0iM",
        "token_type": "Bearer ",
        "expires_in": 1800,
        "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0ODQzNTQiOjE1OTU2NzA3MDQsImV4cCI6MTU5NTY3MjUwNH0.gyQXwPIHiqh1k0iM"
    }
}
```



## 7.通过上一步获取的 access_token 参数，获取用户信息

```

POST：  http://localhost:8098/authorize/userinfo
headers：  Authorization: "Bearer 上一步的获取的access_token值"

response: 
{
    "status": 200,
    "message": "操作成功",
    "data": {
        "userId": "484352883471691776",
        "username": "admin",
        "mobile": "15071523322",
        "nickname": "管理员",
        "avatar": "https://image.xaaef.com/09083333a95f44c38e0b2ef829718a18.jpg",
        "gender": 1,
        "address": "广东省深圳市光明区星皇大厦",
        "status": 1
    }
}
```















