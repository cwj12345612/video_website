
function Fill(data) {


    $("#TypeFilm").attr({href:commonUrl+MovieSection_name+".html?type="+TypeMap.get(data.type)});
    $("#TypeFilm").html(TypeMap.get(data.type));
    $("#explain").html(TypeMap.get(data.type) + "详情");
    $("#type").html(TypeMap.get(data.type));
    /*
    获取其他
     */

    /*
    填充 图片
     */
    data._570x516pict = data._570x516pict.replace(/\\/g, '/');

    $("#img").attr({src:commonUrl+ data._570x516pict});


    /*
    填充 在线播放
     */
    let $href=commonUrl+play_name+".html?"+data.title+"/";
    if (data.videoPath.length > 1) {
        for (let i = 0; i < data.videoPath.length; i++) {
            let k = i + 1;
            data.videoPath[i] = data.videoPath[i].replace(/\\/g, "/");
            let $a = "<u style='color: #2a58ae'><a style='font-size: 20px' href='"+$href + data.videoPath[i] + "'>第" + k + "集</a></u>";
            $("#play").after($a);
            //使用cookie把影片服务器相对路径存到客户端
        }
    }else {
        data.videoPath[0] = data.videoPath[0].replace(/\\/g, "/");
    }
    let $href0=$href+data.videoPath[0];

    $("#againPlay").attr({href: $href0});

    /*
    填充下载按钮
        为下载按钮绑定单击事件
     */
    let Ahref = commonUrl+explain_name+"/DownloadFilm/" + data.title + "/" + data.type;
    if(data.videoPath.length>1) {

        for (let i = 0; i < data.videoPath.length; i++) {
            let k = i + 1;
            let videoSize = Ahref + "/" +i;
            let $a = "<u style='color: #2a58ae;'><a style='font-size: 20px' href=" + videoSize + " '>第" + k + "集</a></u>";
            $("#down").after($a);
            //使用cookie把影片服务器相对路径存到客户端
        }
    }
    let downAll =Ahref+"/-1";
    $("#downAll").attr({href:downAll});

    /*
    填充影片名称
     */
    $("#filmName").html(data.title);
    /*
    填充时长
     */
    $("#length").html(data.length+"分钟");
    /*
    填充集数
     */

    $("#episodes").html(data.episodes);
    /*
    填充上映时间
     */
    $("#releaseDate").html(data.releaseDate);
    /*
    填充导演
     */
    $("#director").html(data.director);
    /*
    填充主演
     */
    $("#protagonist").html(data.protagonist);
    /*
    填充剧情简介
     */
    $("#synopsis").html(data.synopsis);
    /*
    填充来源
     */
    $("#source").html(data.filmSource);


}
