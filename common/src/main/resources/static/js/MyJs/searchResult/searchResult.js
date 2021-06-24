
/*
向后端发送送post请求  得到film的list集合
 */


$.post(
    commonUrl+searchResult_name+"/FindFilmListByName",
    {videoName:searchResult_videoName},
    function (data) {
              data=JSON.parse(data);

                filmListByType(data);
                page(data);
                CilkFilmName(data);
    }
)