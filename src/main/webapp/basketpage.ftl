<#include 'base.ftl'>
<link rel="stylesheet" type="text/css" href="/styles/basketpage.css">
<table>
    <thead>
    <tr>
        <td class="table-name">Наименование товара</td>
        <td class="table-count">Количество</td>
        <td class="table-price">Цена за шт.</td>
    </tr>
    </thead>
    <tbody>
    <#if username?has_content>
    <#if products?has_content>
    <#list products as product>
        <tr>
            <td class="table-name">${product.getName()}</td>
            <td class="table-count">1</td>
            <form method="post" action="/deleteFromBasket?id=${product.getId()}">
                <td class="table-price">${product.getPrice()} рублей <button class="delete">Удалить</button></td>
            </form>
        </tr>
    </#list>
    </tbody>
</table>
<form action="/basketpage" method="post">
    <div style="text-align: center;"><button class="buy_btn">Совершить покупку</button></div>
</form>
<#else>
    <div style="text-align: center;"><h2>Добавьте товар в корзину!</h2></div>
</#if>
<#else>
    <div style="text-align: center">
        <a class='login' href="/login">Для покупки нужно авторизоваться!</a>
    </div>
</#if>