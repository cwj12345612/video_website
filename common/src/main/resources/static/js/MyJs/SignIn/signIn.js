
$.post(
    commonUrl+"UserUrl",
    function (data) {
        $("#signForm").attr({action:data+"user/login"});
    }
)