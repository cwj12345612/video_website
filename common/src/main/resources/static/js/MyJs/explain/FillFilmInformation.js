 $.post(
        commonUrl+"/"+explain_name+"/FillFilmInformation",
        {value:explain_path},
        function (data) {
            /*
            תΪjson����
             */
            data=JSON.parse(data);
            Fill(data);


        }
    );








