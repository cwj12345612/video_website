
/*
���˷�����post����  �õ�film��list����
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