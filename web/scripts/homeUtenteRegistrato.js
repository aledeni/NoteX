let username="Alessio";

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


function showVistaCreazioneBloccoDiAppunti()
{
    html="<input type='text' id='inputNomeBlocco' placeholder='nome blocco di appunti' required/>"
    html+="<input type='text' id='inputAteneo' placeholder='ateneo'/>"
    html+="<input type='text' id='inputCdl' placeholder='corso di laurea'/>"
    html+="<input type='text' id='inputInsegnamento' placeholder='insegnamento'/>"
    html+="<input type='text' id='inputProf' placeholder='prof.'/>"
    html+="<input type='number' id='inputAnnoAccademico' placeholder='a.a.' min='1900' max='2023'>"
    html+="<input type='button' id='inputCreaBlocco' value='crea'/>"
    
    $(".lightBox > .content").html(html);
    $(".lightBox > .content > #inputCreaBlocco").on("click",nuovoBlocco);
    $(".splitScreenLayout").css("filter","blur(1px)");
    $(".lightBoxLayout").css("display","flex");
    
}

function nuovoBloccoHandler(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let result=req.responseText;
            if(result=="true")
            {
                alert("blocco di appunti creato!");
            }else{
                alert("qualcosa e' andato storto");
            }
            elencoBlocchi();
        }
    }
}

function nuovoBlocco()
{
   let nomeBlocco=$("#inputNomeBlocco").val();
   let etichetta=[]
   let etichette=[];

   etichetta[0]="ATENEO";
   etichetta[1]=$("#inputAteneo").val().trim();
   if(etichetta[1]!=""){
    etichette[0]=etichetta;
   }

   etichetta[0]="CDL";
   etichetta[1]=$("#inputCdl").val().trim();
   if(etichetta[1]!=""){
    etichette[1]=etichetta;
   }

   etichetta[0]="INSEGNAMENTO";
   etichetta[1]=$("#inputInsegnamento").val().trim();
   if(etichetta[1]!=""){
    etichette[2]=etichetta;
   }

   etichetta[0]="PROF";
   etichetta[1]=$("#inputProf").val().trim();
   if(etichetta[1]!=""){
    etichette[3]=etichetta;
   }

   etichetta[0]="ANNO_ACCADEMICO";
   etichetta[1]=$("#inputProf").val().trim();
   if(etichetta[1]!=""){
    etichette[4]=etichetta;
   }
   etichette=JSON.stringify(etichette);

   ajaxRequest("get",
    "./servletUtenteRegistrato",
    "action=creaBlocco&nomeBlocco="+nomeBlocco+"&etichette="+etichette,
    true,
    (req)=>{ nuovoBloccoHandler(req);},
    5000,
    (req)=>{ onAbort(req); });


}

function eliminaBloccoHandler(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let result=req.responseText;
            if(result=="true")
            {
                alert("blocco di appunti eliminato!");
            }else{
                alert("qualcosa e' andato storto");
            }
            elencoBlocchi();
        }
    }
}

function eliminaBlocco(nomeBlocco)
{
    ajaxRequest("get",
    "./servletUtenteRegistrato",
    "action=eliminaBlocco&nomeBlocco="+nomeBlocco,
    true,
    (req)=>{ eliminaBloccoHandler(req);},
    5000,
    (req)=>{ onAbort(req); });
    
}

function insertBlocchi(blocchiDiAppunti)
{
    let html="";
    let bloccoDiAppuntiDiv=""
    let actionsDiv="";
    for(let i=0;i<blocchiDiAppunti.length;i++)
    {
        bloccoDiAppuntiDiv="<div class='item blocco' id='blocco-"+blocchiDiAppunti[i].nome+"' onClick='elencoNote(\""+blocchiDiAppunti[i].nome+"\")'>";
        bloccoDiAppuntiDiv+="<img src='resources/icons/bloccoDiAppunti.svg'/>";
        bloccoDiAppuntiDiv+="<div class='name'>"+blocchiDiAppunti[i].nome+"</div>";
        bloccoDiAppuntiDiv+="</div>";

        actionsDiv="<div class='multipleIconsDiv actions'>"
        actionsDiv+="<img class='icon delete' src='resources/icons/delete.svg' onClick='eliminaBlocco(\""+blocchiDiAppunti[i].nome+"\")'/>"
        actionsDiv+="</div>";

        html+="<div class='gridItemDiv'>"+bloccoDiAppuntiDiv+actionsDiv+"</div>";
    }
    html+="<div class='gridItemDiv' id='nuovoBlocco'><div class='item'><img src='resources/icons/addSquare.svg'/><div class='name'>nuovo blocco</div></div></div>"
    $(".gridDiv").html(html);
    $("#nuovoBlocco").on("click",showVistaCreazioneBloccoDiAppunti);
}

function elencoBlocchiHandler(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let blocchiDiAppunti=JSON.parse(req.responseText);
            insertBlocchi(blocchiDiAppunti);
        }
    }
}

function elencoBlocchi(event)
{
    ajaxRequest("get",
    "./servletUtenteRegistrato",
    "action=elencoBlocchi",
    true,
    (req)=>{ elencoBlocchiHandler(req);},
    5000,
    (req)=>{ onAbort(req); });

}

function showVistaCreazioneNota(nomeBlocco)
{
    html="<input type='text' id='inputNomeNota' placeholder='nome nota' required/>"  
    html+="<input type='text' id='inputArgomento' placeholder='argomento'/>"
    html+="<input type='file' id='inputFile'/>"
    html+="<input type='button' id='inputCreaBlocco' value='crea'/>"
    
    $(".lightBox > .content").html(html);
    $(".lightBox > .content > #inputCreaBlocco").on("click",()=>{nuovaNota(nomeBlocco);});
    $(".splitScreenLayout").css("filter","blur(1px)");
    $(".lightBoxLayout").css("display","flex");
}


function nuovaNotaHandler(req,nomeBlocco,nomeNota)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let result=req.responseText;
            if(result=="true")
            {
                alert("nota creata!");
            }else{
                alert("qualcosa e' andato storto");
            }
            elencoNote(nomeBlocco);
        }
    }
}

function nuovaNota(nomeBlocco)
{
   let nomeNota=$("#inputNomeNota").val();
   let fileInput = document.getElementById("inputFile");
   var file = fileInput.files[0];
   let etichetta=[]
   let etichette=[];

   etichetta[0]="ARGOMENTO";
   etichetta[1]=$("#inputArgomento").val().trim();
   if(etichetta[1]!=""){
    etichette[0]=etichetta;
   }
   etichette=JSON.stringify(etichette);

   let formData=new FormData();
   formData.append("action","creaNota");
   formData.append("nomeBlocco",nomeBlocco);
   formData.append("nomeNota",nomeNota);
   formData.append("etichette",etichette);
   formData.append("file",file);

   ajaxRequest("post",
    "./servletUtenteRegistrato",
    formData,
    true,
    (req)=>{ nuovaNotaHandler(req,nomeBlocco,nomeNota);},
    5000,
    (req)=>{ onAbort(req); });
}

function eliminaNotaHandler(req,nomeBlocco)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let result=req.responseText;
            if(result=="true")
            {
                alert("nota eliminata!");
            }else{
                alert("qualcosa e' andato storto");
            }
            elencoNote(nomeBlocco);
        }
    }
    
}

function eliminaNota(nomeBlocco,nomeNota)
{
    ajaxRequest("get",
    "./servletUtenteRegistrato",
    "action=eliminaNota&nomeBlocco="+nomeBlocco+"&nomeNota="+nomeNota,
    true,
    (req)=>{ eliminaNotaHandler(req,nomeBlocco);},
    5000,
    (req)=>{ onAbort(req); });
}



function insertNote(nomeBlocco,note)
{
    let html="";
    let notaDiv=""
    let actionsDiv="";
    for(let i=0;i<note.length;i++)
    {
        notaDiv="<div class='item nota' id='nota-"+note[i].nome+"'>";
        notaDiv+="<img src='resources/icons/nota.svg'/>";
        notaDiv+="<div class='name'>"+note[i].nome+"</div>";
        notaDiv+="</div>";

        actionsDiv="<div class='multipleIconsDiv actions'>"
        actionsDiv+="<img class='icon delete' src='resources/icons/delete.svg' onClick='eliminaNota(\""+nomeBlocco+"\",\""+note[i].nome+"\")'/>";
        actionsDiv+="<a class='iconDiv download' href='"+note[i].percorsoFile+"' target='_blank' download='"+note[i].nome+"'>";
        actionsDiv+="<img class='icon download' src='resources/icons/download.svg'/>"
        actionsDiv+="</a>";
        actionsDiv+="</div>";

        html+="<div class='gridItemDiv'>"+notaDiv+actionsDiv+"</div>";
    }
    html+="<div class='gridItemDiv' id='nuovaNota'><div class='item'><img src='resources/icons/addSquare.svg'/><div class='name'>nuova nota</div></div></div>";
    $(".gridDiv").html(html);
    $("#nuovaNota").on("click",()=>{showVistaCreazioneNota(nomeBlocco);});   
}


function elencoNoteHandler(req,nomeBlocco)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            let note=JSON.parse(req.responseText);
            insertNote(nomeBlocco,note);
        }
    }
}

function elencoNote(nomeBlocco)
{
    ajaxRequest("get",
    "./servletUtenteRegistrato",
    "action=elencoNote&nomeBlocco="+nomeBlocco,
    true,
    (req)=>{ elencoNoteHandler(req,nomeBlocco);},
    5000,
    (req)=>{ onAbort(req); });
}


function closeLightBox()
{
    $(".lightBoxLayout").css("display","none");
}

function init()
{
    elencoBlocchi();
    $(".lightBox .icon.close").on("click",closeLightBox);
}


