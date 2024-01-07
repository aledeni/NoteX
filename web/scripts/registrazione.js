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
        req.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
        req.send(params);
        setTimeout(()=>{onAbort(req);},timeToAbort);
    }
}

function registraHandler(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let result=req.responseText;
            let html="";
            if(result)
            {
                html="<p>registrazione andata a buon fine, clicca <a href='./login.html'>qui</a> per autenticarti </p>"
                
            }else{
                html="<p>qualcosa e' andato storto</p>";
            }
            $("#resultDiv").html(html);
        }
    }
}

function registra()
{
    let username=$("input[name='username']").val();
    let password=$("input[name='password']").val();
    let passwordCopy=$("input[name='passwordCopy']").val();

    if(password==passwordCopy)
    {
        ajaxRequest("post",
         "./servletRegistrazione",
         "username="+username+"&password="+password,
         true,
         (req)=>{ registraHandler(req);},
         5000,
         (req)=>{ onAbort(req); });
    }

}

function init()
{
    $("input[type='submit'").on("click",registra);
}