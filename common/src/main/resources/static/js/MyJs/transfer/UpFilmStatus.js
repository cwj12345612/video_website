let transfer_sea=window.location.search;

    let transfer_status=transfer_sea.substring(transfer_sea.indexOf("=")+1);

    if(transfer_status==="true"){
        $("#Up")[0].style.color ="yellow";
         $("#Up").html("  �ɹ�!!");
         $("#h1")[0].style.display ="none";
    }else {
            $("#Up")[0].style.color ="red";
            $("#Up").html("  ʧ��!!");
            $("#h1")[0].style.display ="";

    }

