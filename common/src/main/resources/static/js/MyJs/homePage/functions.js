
/*
 *  FillList.js  专用
 * @param data
 */
function fillList(data) {

    for(let i=0; i<homePage_typeArray.length; i++){
        let FilmByType = data[homePage_typeArray[i]];
        if(FilmByType===undefined){
            continue;
        }
        let $a = $(homePage_$ul[i]).find("a");
        for(let k=0; k<$a.length; k++){
            let Film = FilmByType[k];
            let title = Film.title;
            let _570x516pict = Film._570x516pict;
            let releaseDate = Film.releaseDate;

            $a.eq(k).html(title);

            let pictPath =(commonUrl+ _570x516pict).replace(/\\/g, '/');


            $a.eq(k).parent().attr({title:pictPath});

            // alert($a.eq(k).parent().attr("title"));

            let $div = $a.eq(k).parent().parent().find("div");

            $div.html(releaseDate);

        }

    }
}

/*
 * FindFilmBySum.js 专用
 * @param data
 */
function fillBigPict(data){

    let _870x518pict;
    let Film;
    let typeArray = [];
    /*
    获取影片类型
     */
    let i = 0;
    for(let type in data){
        typeArray[i]=type;
        i++;
    }

    /*
    填充大图片
     */

    let FilmBigPict = [];
    let number = homePage_bigSize /typeArray.length;   //需要的平均每个类型的图片
    number=Math.floor(number);   //向下取整
    ;
    let temp = 0;
    for(i=0;i<typeArray.length;i++){
        let FilmArray = data[typeArray[i]];
        for(let k=0; k<number; k++){
            Film = FilmArray[k];
            _870x518pict = Film._870x518pict;
            FilmBigPict[temp]=_870x518pict;
            temp++;
        }
    }

    if(FilmBigPict.length<homePage_bigSize){

        /*
         如果数组中的大图片还不够
         则继续往数组中添加图片
         */
        let deficiency = homePage_bigSize - FilmBigPict.length;
        temp=FilmBigPict.length;
        for(i=0;i<deficiency;i++){
            let FilmType = typeArray[i];
            Film = data[FilmType][0];

            _870x518pict = Film._870x518pict;
            FilmBigPict[temp]=_870x518pict;
            temp++;
        }
    }


    //
    for(i=0;i<FilmBigPict.length;i++){
        let s = FilmBigPict[i].replace(/\\/g, '/');
        homePage_$big.eq(i).attr({src:commonUrl+s});

    }
}


/*
 * 填充小图片   FindFilmBySum.js
 * @param data
 */
function fillLittlePict(data) {

    let k;
    let LittlePict = [];
    let i = 0;
    //取出json中的全部小图片
    for(let type in data){
        let FilmArrayByType = data[type];

        for(k = 0; k<FilmArrayByType.length; k++){

            let Film = FilmArrayByType[k];

            LittlePict[i]=(commonUrl+Film._570x516pict).replace(/\\/g, '/');

            i++;
        }
    }
    /*
    填充$a
     */
    for (i=0;i<homePage_$a.length;i++){
        homePage_$a.eq(i).attr({src:LittlePict[i]});
    }
    /*
    填充$b
     */
    for(k = 0; k<homePage_$b.length; k++) {

        homePage_$b.eq(k).attr({src: LittlePict[i]});
        i++;
    }
}


/*
 * FindFilmBySum.js
 */
function pictCilk() {

    let $img = $("#ma").find("a").find("img");

    for(let i=0;i<$img.length;i++){

        let $src = $img.eq(i).attr("src");

        if(commonUrl!=""||commonUrl!=undefined){
          $src=  $src.substring(commonUrl.length);
        }
        let Aurl=explain_name+".html"+"?="+$src;
        $img.eq(i).parent().attr({href:commonUrl+Aurl});
    }

}

/*
 * FillList.js
 *
 */
function ListCilk() {

    let $a = $("div.col-md-4").find("ul.movie-schedule").find("li").find("h2.entry-title").find("a");

    for(let i=0;i<$a.length;i++){

        let $src = $a.eq(i).parent().attr("title");
        if($src===undefined){
          continue;
        }
        if(commonUrl!=""||commonUrl!=undefined){
            $src=  $src.substring(commonUrl.length);
        }
        let Aurl=explain_name+".html?="+$src;

        $a.eq(i).attr({href:commonUrl+Aurl});

    }
}
