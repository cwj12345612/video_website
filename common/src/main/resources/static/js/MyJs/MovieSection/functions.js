function filmListByType(data) {

    for(let i=0; i<MovieSection_FilmLength; i++){
        /*
        Ìî³äÍ¼Æ¬
         */
        MovieSection_$classMovie.eq(i).find("img").attr({src:commonUrl+data[i]._270x244pict});
        /*
        Ìî³äÓ°Æ¬Ãû³Æ
         */
        MovieSection_$classMovie.eq(i).find("a").html(data[i].title);

    }
}

function page(filmByTypeAllSize) {
    /*
    µã»÷·ÖÇøÄ¬ÈÏÏÔÊ¾
     */
    let size = filmByTypeAllSize / MovieSection_FilmLength;
    size=Math.ceil(size);

    $("#PageAll").html(size);
    $("#FilmAll").html(filmByTypeAllSize);
    for(let i=1; i<size; i++){
        let s = "<a class='page-number current' name='ListPage'>" + (i + 1) + "</a>";
        $("#LastPage").before(s);
    }

    $("a[name='ListPage']").first().attr({class:"page-number current"});
    $("a[name='ListPage']").not($("a[name='ListPage']").first()).attr({class: "page-number"});
    ClickPageSize();
    CilkFilmName();
}

function CilkFilmName() {
    let url = commonUrl+explain_name+".html?=";

    let $a = MovieSection_$classMovie.find("a");

    for(let i=0; i<$a.length; i++){

        let $img = $a.eq(i).parent().parent().find("img");

        let $src = $img.attr("src");

        $src=$src.replace(/\\/g,'/');

        if(commonUrl!=""||commonUrl!=undefined){
            $src=  $src.substring(commonUrl.length);
        }

        $a.eq(i).attr({href: url+$src});

    }

}



function ClickPageSize() {
    $("a[name='ListPage']").click(function () {
        $(this).attr({class:"page-number current"});
        $("a[name='ListPage']").not($(this)).attr({class: "page-number"});
        let $pageSize = $(this).html();
        $.post(
            commonUrl+"Category/FillListByTypePageSize",
            {type:MovieSection_typeEnglish,size:$pageSize,length:MovieSection_FilmLength},
            function (data) {

                data=JSON.parse(data);
                filmListByType(data);
                CilkFilmName();
            }
        )
    });
}
