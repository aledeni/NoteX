function onAbort(req)
{
    if(req.readyState!=4)
    {
        alert("request aborted, somethig went wrong");
    }
    
}

function ajaxRequest(method,url,params,async,onReadyStateChange,timeToAbort,onAbort)
{
    let req=new XMLHttpRequest();
    if(method=="get")
    {
        req.open(method,url+"?"+params,async);
        req.onreadystatechange=()=>{onReadyStateChange(req);}
        req.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
        req.send();
        setTimeout(()=>{onAbort(req);},timeToAbort);
    }else if(method=="post")
    {
        req.open(method,url,async);
        req.onreadystatechange=()=>{onReadyStateChange(req);}
        req.send(params);
        setTimeout(()=>{onAbort(req);},timeToAbort);
    }
}

function stringaPunteggio(punteggio)
{
    let html="";
    for(let i=0;i<5;i++)
    {
        if(i<punteggio){
            html+="<img class='icon star toFill' src='resources/icons/star.svg'>";
        }else{
            html+="<img class='icon star' src='resources/icons/star.svg'>";
        }
    }
    return html;
    
}

function stringaFirstLineUtenteRegistrato(utenteRegistrato)
{
    let html=""; 
    html="<div class='firstLine'>";
    html+="<div class='resultNameAndType'>";
    html+="<div class='iconDiv'><img class='icon' src='resources/icons/utente.svg'/></div>";
    html+="<div class='resultNameDiv'>"+utenteRegistrato.username+"</div>";
    html+="</div>";
    html+="<div class='multipleIconsDiv starsAndActions'>";
    html+="<div class='multipleIconsDiv stars'>";
    html+= stringaPunteggio(utenteRegistrato.punteggioMedio);
    html+="</div>";
    html+="<label class='iconDiv expand'>"
    html+="<input type='checkbox'>";
    html+="<img class='icon expand' src='resources/icons/expand.svg'>";
    html+="</label>";
    html+="</div>";
    html+="</div>";
    return html;
}

function stringaFirstLineBloccoDiAppunti(bloccoDiAppunti)
{
    let html=""; 
    html="<div class='firstLine'>";
    html+="<div class='resultNameAndType'>";
    html+="<div class='iconDiv'><img class='icon' src='resources/icons/bloccoDiAppunti.svg'/></div>";
    html+="<div class='resultNameDiv'>"+bloccoDiAppunti.nome+"</div>";
    html+="</div>";
    html+="<div class='multipleIconsDiv starsAndActions'>";
    html+="<div class='multipleIconsDiv stars'>";
    html+= stringaPunteggio(bloccoDiAppunti.punteggioMedio);
    html+="</div>";
    html+="<label class='iconDiv expand'>"
    html+="<input type='checkbox'>";
    html+="<img class='icon expand' src='resources/icons/expand.svg'>";
    html+="</label>";
    html+="</div>";
    html+="</div>";
    return html;
}

function stringaFirstLineNota(nota)
{
    let html="";
    html="<div class='firstLine'>";
    html+="<div class='resultNameAndType'>";
    html+="<div class='iconDiv'><img class='icon' src='resources/icons/nota.svg'/></div>";
    html+="<div class='resultNameDiv'>"+nota.nome+"</div>";
    html+="</div>"
    html+="<div class='multipleIconsDiv starsAndActions'>";
    html+="<div class='iconDiv review'><img class='icon review' src='resources/icons/review.svg'/></div>";
    html+="<div class='multipleIconsDiv stars'>";
    html+= stringaPunteggio(nota.punteggioMedio);
    html+="</div>";
    html+="<a class='iconDiv download' href='"+nota.percorsoFile+"' target='_blank' download='Web Statico'>";
    html+="<img class='icon download' src='resources/icons/download.svg'/>";
    html+="</a>";
    html+="</div>"; 
    html+="</div>";
    return html;
}


function inserisciUtentiRegistrati(utentiRegistrati)
{
    let searchResult="";
    let html="";
    for(let i=0;i<utentiRegistrati.length;i++)
    {
        searchResult="<div class='searchResult'>";
        searchResult+=stringaFirstLineUtenteRegistrato(utentiRegistrati[i]);
        searchResult+="<div class='hiddenBox'>";
        for(let j=0;j<utentiRegistrati[i].blocchiDiAppunti.length;j++)
        {
            searchResult+="<div class='searchResult'>";
            searchResult+=stringaFirstLineBloccoDiAppunti(utentiRegistrati[i].blocchiDiAppunti[j]);
            searchResult+="<div class='hiddenBox'>";
            for(let h=0;h<utentiRegistrati[i].blocchiDiAppunti[j].note.length;h++)
            {
                searchResult+="<div class='searchResult'>";
                searchResult+=stringaFirstLineNota(utentiRegistrati[i].blocchiDiAppunti[j].note[h]);
                searchResult+="</div>";

            }
            searchResult+="</div>";
            searchResult+="</div>";
        }
        searchResult+="</div>";
        searchResult+="</div>";
        html+=searchResult;
    }
    $(".searchResults").html(html);
}

function inserisciBlocchiDiAppunti(blocchiDiAppunti)
{
    let searchResult="";
    let html="";
    for(let i=0;i<blocchiDiAppunti.length;i++)
    {
        searchResult="<div class='searchResult'>";
        searchResult+=stringaFirstLineBloccoDiAppunti(blocchiDiAppunti[i])
        searchResult+="<div class='hiddenBox'>";
        for(let j=0;j<blocchiDiAppunti[i].note.length;j++)
        {
            searchResult+="<div class='searchResult'>";
            searchResult+=stringaFirstLineNota(blocchiDiAppunti[i].note[j]);
            searchResult+="</div>";

        }
        searchResult+="</div>";
        searchResult+="</div>";
        html+=searchResult;
    }
    $(".searchResults").html(html);
}

function inserisciNote(note)
{
    let searchResult="";
    let html="";
    for(let h=0;h<note.length;h++)
    {
        searchResult+="<div class='searchResult'>";
        searchResult+=stringaFirstLineNota(note[h]);
        searchResult+="</div>";
        html+=searchResult;
    }
    $(".searchResults").html(html);
}

function cercaHandler(req, tipoRicerca)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let result=JSON.parse(req.responseText);
            if(tipoRicerca=="utentiRegistrati"){
                inserisciUtentiRegistrati(result);
            }else if(tipoRicerca=="blocchiDiAppunti")
            {
                inserisciBlocchiDiAppunti(result);
            }else if(tipoRicerca=="note")
            {
                inserisciNote(result);
            }
        }
    }
}

function cerca()
{
    let tipoRicerca=$("input[name='searchType']:checked").val();
    let stringaRicerca=$("input[name='searchBar'").val();

    ajaxRequest("get",
    "./servletUtente",
    "tipoRicerca="+tipoRicerca+"&stringaRicerca="+stringaRicerca,
    true,
    (req)=>{ cercaHandler(req, tipoRicerca);},
    5000,
    (req)=>{ onAbort(req); });


}

function init()
{
    $(".searchBar").on("focus",function(){
        $(".rightHalf").css("width","100%","justify-content","flex-start");
        $(".searchBarDiv .iconDiv.close").css("display","flex");
    });
    $(".searchBarDiv .iconDiv.close").on("click",function(){
        $(".searchBar").prop("disabled",true);
        $(".searchBar").val("");
        $(".searchBar").prop("disabled",false);
        $(".rightHalf").css("width","50%","justify-content","center");
        $(".searchResultDiv").css("flex","0");
        $(".searchBarDiv .iconDiv.close").css("display","none");
    });
    $(".searchBar").on("keypress",function(event)
    {
        if(event.key=="Enter")
        {
            cerca();
            $(".searchResultDiv").css("flex","1");
        }
    });
    $(".icon.review").on("click",function(){
        $(".splitScreenLayout").css("filter","blur(1px)");
        $(".lightBoxLayout").css("display","flex");
    });

    $(".lightBox .icon.close").on("click",function(){
        $(".splitScreenLayout").css("filter","blur(0px)");
        $(".lightBoxLayout").css("display","none");
    });

}