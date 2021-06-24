
/*
添加分区下面的横条
 */
function Add() {

    let MovieTitle = $("#MovieTitle").html();
    let $a = $("#ul").find("a");
    for (let i=0; i<$a.length; i++){
        let html = $a.eq(i).html();
        if(html==MovieTitle){
            $a.eq(i).parent().attr({class: "menu-item current-menu-item"});
            break;
        }
    }
}
function AddHeaderA() {
    let s = commonUrl+homepage_name+".html";
     $("#ul").find("a").first().attr({href:s});

    let $a = $("#ul").find("a").not($("#ul").find("a").first());

    for(let  i=0;i<$a.length;i++){
        let $href=commonUrl+MovieSection_name+".html?type="+$a.eq(i).html();
        $a.eq(i).attr({href:$href});
    }
}
function AddFootA() {
    /*
    给共享叫的a标签的href属性添加commonUrl
     */
    let $a = $("#footUlA").find("a");
    for(let  i=0;i<$a.length;i++){
        let $href=commonUrl+MovieSection_name+".html?type="+$a.eq(i).html();
        $a.eq(i).attr({href:$href});
    }

}

function formLocation() {
    /*
    填充表单要发送的action
     */
        $("#form").attr({action:commonUrl+searchResult_name+".html"});
}
function fillOther() {
        $("#signIn").attr({href:commonUrl+signIn_name+".html"});
        $("#signUp").attr({href:commonUrl+signUp_name+".html"});
}
function AdduserName() {

    if(Cookies.get("userName")!=undefined){

        $("#userName").html(Cookies.get("userName"));
    }else {

    }

}