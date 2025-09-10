# security-keycloak-jwt

Заготовка для spring security-keycloak-jwt с загрузкой данных пользователей из keycloak.

Пример реализации сайта с простой регистрацией и авторизацией пользователя. Если открыть страницу /managers, указанную
в коде как
"только для авторизованных пользователей", то появится форма авторизации/регистрации keycloak. Если же
авторизоваться, то появится само содержимое страницы. При этом на стороне пользователя не нужно использовать
специальные скрипты, чтобы хранить и обновлять JWT токен.

Предполагаем, что keycloak будет запущен на порту 8080, а микросервис на порту 8081.

## Это не Stateless

Реализация не является Stateless, чтобы всё работало без скриптов на стороне браузера.

Если захочется сделать полностью Stateless режим, то клиентская (браузерная) часть должна:

- Сохранять JWT токен
- Добавлять его в заголовок Authorization для каждого запроса
- Обрабатывать истечение токена
- Получать новый токен при необходимости

## Контейнер keycloak

Открываем docker-compose файл. Запускаем контейнер с keycloak, переходим на ip:8080, доступы admin/admin указаны в .env
файле

## Настройка keycloak

Заходим в keycloak по адресу localhost:8080. В боковом меню создаём или переключаемся на свой realm.

Заходим во вкладку "Clients" из бокового меню. Там жмём "Create client". Вводим "Client Id" (символьный код,
например "my-realm), выбираем тип "OpenID Connect". Переключаемся на "Capability config" и ставим только одну галку
"Standard flow" и "Service accounts roles".
Остальные
пункты деактивированы, чтобы получилось так:

```
Client authentication - Off
Authorization - Off
Authentication flow - Off
Standard flow - On
Direct access grants - Off
Implicit flow - Off
Service accounts roles - On
Standard Token Exchange - Off
OAuth 2.0 Device Authorization Grant - Off
```

Переходим на закладку "Login settings" и заполняем поля

```
Root URL - http://localhost:8081
Valid redirect URIs - http://localhost:8081/*
Web origins - http://localhost:8081
```

Сохраняем клиента. И сразу переходим в его настройки. Жмём на "Credentials" и копируем к себе "Client Secret". Его
вместе с "Client Id" надо поставить в application.yaml приложения спринга.

Жмём закладку "Service Account Roles" и дальше "Assign
role"
-> "Client Roles". В появившемся списке выбираем

```
manage-users
query-users
view-users
view-realm
```

И жмём "Assign".

В боковом меню keycloak находим "Client scopes", переходим на страницу и списке находим "roles". Переходим в его
настройки, закладка "Mapper", там пункт "realm roles". На открывшейся странице пункт "Token Claim Name" меняем с
"realm_access.roles" на "roles", чтобы легче доставать в приложении данные. Там же включаем опции:

```
Add to access token - On
Add to userinfo - On
```

Чтобы получилось:

```
Add to ID token - Off
Add to access token - On
Add to lightweight access token - Off
Add to userinfo - On
Add to token introspection - On
```

Создаём роли пользователей. Для этого переходим в закладку "Realm roles" и жмём "Create role", создаём роли с именами
"ROLE_USER" и "ROLE_ADMIN".

Добавляем пользователя для тестов и роли пользователей так: заходим во вкладку "Users" левого меню, жмём "Add user".
Вводим обязательные поля и жмём "Create". Переходим в настройки созданного пользователя. Вкладка "Credentials" и
задём пароль, делаем его постоянным. В закладке "Role mapping" жмём "Assign role" и добавляем "Realm roles",
выбираем "ROLE_USER" и "ROLE_ADMIN".

Чтобы пользователи при регистрации автоматически получали роль "ROLE_USER", необходимо перейти в "Realm settings",
вкладка "User registration", вкладка "Default roles" и добавить "Assign
role" -> "Realm Roles".

## Настройка spring

В application.yaml необходимо внести id клиента, secret клиента и название realm, которое указывалось в keycloak.

В файле SecurityConfig.java находятся правила фильтрации адресов.