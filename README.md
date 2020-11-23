# canteen

# Backend Run (Local)  
```gradle bootRun```
# Frontend Run (Local)  
1. **Install Packages**  
```npm install```
2. **Frontend run**  
```npm run serve```


# Backend Run (Production)  
```gradle build```
# Frontend Run (Production)  
1. **Install Packages**  
```npm install```
2. **Frontend run**  
```npm run build```

Адресс  | Запрос
----------------|----------------------
http://localhost:25016/api/register   | Регистрация POST запросом
http://localhost:25016/api/authenticate  | POST запрос, получаешь токен авторизации
http://localhost:25016/api/user   | GET запрос с Authorization в HEADERS с токеном, получаешь данные об авторизованном пользователе
