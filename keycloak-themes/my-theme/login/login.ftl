<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход / Банк</title>
    <style>
        body {
            background: #fffef3;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            width: 100%;
        }

        .content {
            background: #fff5e5;
            padding: 50px;
        }
    </style>
</head>
<body>
<div class="content">
    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
        <h1>Вход</h1>
        <div>
            <label for="username">Логин</label>
            <input type="text" id="username" name="username" autofocus/>
        </div>
        <div>
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password"/>
        </div>
        <div>
            <input type="submit" value="Войти"/>
        </div>
    </form>

    <#if url.registrationUrl??>
        <p style="margin-top: 10px;">
            <a href="${url.registrationUrl}">Регистрация</a>
        </p>
    </#if>
</div>
</body>
</html>

