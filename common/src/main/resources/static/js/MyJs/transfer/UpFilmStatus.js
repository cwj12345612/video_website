let transfer_sea=window.location.search;

    let transfer_status=transfer_sea.substring(transfer_sea.indexOf("=")+1);

    if(transfer_status==="true"){
        $("#Up")[0].style.color ="yellow";
         $("#Up").html("  ³É¹¦!!");
         $("#h1")[0].style.display ="none";
    }else {
            $("#Up")[0].style.color ="red";
            $("#Up").html("  Ê§°Ü!!");
            $("#h1")[0].style.display ="";

    }

