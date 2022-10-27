<#include 'base.ftl'>

<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/styles/basketpage.css">
<br>
<div style="text-align: center;"><h1>Поздравляем с покупкой!</h1></div>
<form method="post" action="/buypage">
    <table>
        <thead>
        <tr>
            <td class="table-name">Наименование товара</td>
            <td class="table-price">Оценка</td>
        </tr>
        </thead>
        <tbody>
        <#list products as product>
            <tr>
                <td class="table-name">${product.getName()}</td>
                <td class="table-price">
                    <select name="evaluation${product.getId()}" id="evaluation${product.getId()}">
                        <option disabled>Выберите героя</option>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                    <label for="evaluation${product.getId()}">Баллов</label></td>
            </tr>
        </#list>
        </tbody>

    </table>
    <div style="text-align: center;"><button id="marks" type="submit" class="btn">Поставить оценку</button></div>
</form>

