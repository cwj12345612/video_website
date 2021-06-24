/*
 * 填充影片列表   searchResult.js
 * @param data
 */

function filmListByType(data) {
        let FilmLength = data.length;
    for( let i=0;i<FilmLength;i++){
        /*
        填充图片
         */
        searchResult_$classMovie.eq(i).find("img").attr({src:commonUrl+data[i]._270x244pict});
        /*
        填充影片名称
         */
        searchResult_$classMovie.eq(i).find("a").html(data[i].title);
    }
/*
隐藏标签
 */
    if((searchResult_filmSize-FilmLength)>0){
        for(let i=FilmLength;i<searchResult_filmSize;i++){
            searchResult_$classMovie.eq(i).attr({hidden:"hidden"});
        }
    }
}

function page(data) {

     let filmByTypeAllSize = data.length;

    /*
    点击分区默认显示
     */

    let size = filmByTypeAllSize / searchResult_filmSize;
    size=Math.ceil(size);

    $("#PageAll").html(size);
    $("#FilmAll").html(filmByTypeAllSize);
    for(let i=1;i<size;i++){
        let s="<a class='page-number current' name='ListPage'>"+(i+1)+"</a>";
        $("#LastPage").before(s);
    }

    $("a[name='ListPage']").first().attr({class:"page-number current"});
    $("a[name='ListPage']").not($("a[name='ListPage']").first()).attr({class: "page-number"});
}

function CilkFilmName() {
    let url = commonUrl+explain_name+".html?=";

    let $a = searchResult_$classMovie.find("a");

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
