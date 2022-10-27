<#include 'base.ftl'>
<style>
    .card {
        background-color: #484545;
    }
    .card-columns {
        column-count: 3;
    }
</style>
<div class="card-columns m-md-auto w-75">
    <#list products as product>
        <div class="card">
            <img class="card-img-top m-auto" src="${product.getImage()}" alt="Альбом 1">
            <div class="card-body">
                <h5 class="card-title">${product.getName()}</h5>
                <h6 class="card-title" style="color: #a7a2a2">${product.getComposer()}</h6>
                <p class="card-text">${product.getDescription()}</p>
                <h5 class="card-title">${product.getPrice()} руб</h5>
                <a href="/productpage?id=${product.getId()}" class="btn btn-light">Купить</a>
                <iframe src="${product.getLinkAppleMusic()}" height="450px" frameborder="0" sandbox="allow-forms allow-popups allow-same-origin allow-scripts allow-top-navigation-by-user-activation" allow="autoplay *; encrypted-media *;" style="width: 100%; max-width: 660px; overflow: hidden; border-top-left-radius: 10px; border-top-right-radius: 10px; border-bottom-right-radius: 10px; border-bottom-left-radius: 10px; background-color: transparent; margin-top: 30px"></iframe>
            </div>
        </div>
    </#list>
</div>