 $.post(
        commonUrl+"/"+explain_name+"/FillFilmInformation",
        {value:explain_path},
        function (data) {
            /*
            转为json对象
             */
            data=JSON.parse(data);
            Fill(data);


        }
    );








