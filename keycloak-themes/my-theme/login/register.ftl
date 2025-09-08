<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация / Банк</title>
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
    <h1>Регистрация</h1>

    <form id="kc-register-form" action="${url.registrationAction}" method="post">
        <#if messagesPerField.exists('firstName')>
            <div class="error">${messagesPerField.get('firstName')}</div>
        </#if>
        <div>
            <label for="firstName">Имя</label>
            <input type="text" id="firstName" name="firstName" value="${(register.formData.firstName!'')}" />
        </div>

        <#if messagesPerField.exists('lastName')>
            <div class="error">${messagesPerField.get('lastName')}</div>
        </#if>
        <div>
            <label for="lastName">Фамилия</label>
            <input type="text" id="lastName" name="lastName" value="${(register.formData.lastName!'')}" />
        </div>

        <#if messagesPerField.exists('email')>
            <div class="error">${messagesPerField.get('email')}</div>
        </#if>
        <div>
            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="${(register.formData.email!'')}" />
        </div>

        <#if messagesPerField.exists('username')>
            <div class="error">${messagesPerField.get('username')}</div>
        </#if>
        <div>
            <label for="username">Логин</label>
            <input type="text" id="username" name="username" value="${(register.formData.username!'')}" />
        </div>

        <#if messagesPerField.exists('password')>
            <div class="error">${messagesPerField.get('password')}</div>
        </#if>
        <div>
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" />
        </div>

        <#if messagesPerField.exists('password-confirm')>
            <div class="error">${messagesPerField.get('password-confirm')}</div>
        </#if>
        <div>
            <label for="password-confirm">Подтверждение пароля</label>
            <input type="password" id="password-confirm" name="password-confirm" />
        </div>

        <#if messagesPerField.exists('attributes.birthDate')>
            <div class="error">${messagesPerField.get('attributes.birthDate')}</div>
        </#if>
        <div>
            <label for="birthDate">Дата рождения</label>
            <input type="date" id="birthDate" name="user.attributes.birthDate" value="${(register.formData['user.attributes.birthDate']!'')}" />
        </div>

        <div>
            <input type="submit" value="Зарегистрироваться" />
        </div>
    </form>
</div>
</body>
</html>
