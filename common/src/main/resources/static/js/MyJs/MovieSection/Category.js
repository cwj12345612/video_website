
/*
向服务器发出请求 请求FilmLength个数的影片
 */

    $.post(
        commonUrl+"Category/fillOneByType",
        {type:MovieSection_typeEnglish,length:MovieSection_FilmLength},
        function (data) {
            /*
                填充
                 */
            data=JSON.parse(data);
            filmListByType(data);
            /*
            页数
             */
            page(data[MovieSection_FilmLength].FilmByTypeAllSize);


        }

    );

