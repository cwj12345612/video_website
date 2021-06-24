
/*
 * 如果客户端没有存储commonUrl的Cookie
 *则向后端请求commonUrl并存在客户端中
 */

    if(Cookies.get('commonUrl')===undefined) {
        $.post(
            "CommonUrl",
            function (data) {

                commonUrl=data;
                Cookies.set('commonUrl', data, {expires: 1, path: '/'});
            }
        );
    }
    if(Cookies.get("UserUrl")===undefined){
        $.post(
            "UserUrl",
            function (data) {
                UserUrl=data;
                Cookies.set('UserUrl',data);
            }
        )
    }
if(Cookies.get('commonUrl')===undefined){
    commonUrl="http://172.28.92.139/";
    Cookies.set('commonUrl',commonUrl);
}

if(Cookies.get('UserUrl')===undefined){
    UserUrl="http://172.28.92.139:8080/user/";
    Cookies.set('UserUrl',UserUrl);
}


$.get(
    "Share_the_HTML/header.html",
    function (data) {
        $("#header").html(data);
        AddHeaderA();
        formLocation();
        fillOther();
        AdduserName();
    }

);

$.get(
    "Share_the_HTML/footer.html",
    function (data) {
        $("#footer").html(data);
        AddFootA();

    }
);


