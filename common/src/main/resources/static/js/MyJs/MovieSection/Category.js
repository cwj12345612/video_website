
/*
��������������� ����FilmLength������ӰƬ
 */

    $.post(
        commonUrl+"Category/fillOneByType",
        {type:MovieSection_typeEnglish,length:MovieSection_FilmLength},
        function (data) {
            /*
                ���
                 */
            data=JSON.parse(data);
            filmListByType(data);
            /*
            ҳ��
             */
            page(data[MovieSection_FilmLength].FilmByTypeAllSize);


        }

    );

