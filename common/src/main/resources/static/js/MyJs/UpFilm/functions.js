
getFilmAll();
pictLength();
reset();
function reset() {
    $(":input").val("");
}

function clear() {

    /*
    清除上次上传错误的提示信息
     */
    let $div = $("div[name='newError']");
    $div.remove();

}

function valueNull() {
    /*
    判断必填项是否已填充   true表示已填充
     */
    let pd = true;
    let $label = $("label[style='color: red;']");
    for (let i = 0; i < $label.length; i++) {
        let val = $label.eq(i).next().eq(0).val();

        if (val === "") {
            pd = false;
            let sr = "<div  name='newError'><span style='color: green;background-color: yellow' >此项为必填项</span></div>";
            $label.eq(i).next().eq(0).after(sr);
        }
    }

    return pd;
}

function Auto() {
    /*
    不是必填项的  如果用户没有填写  则用默认值
     */

    let $label = $("label").not($("label[style='color: red;']")); //非必填项
    for (let i = 0; i < $label.length; i++) {

        let val = $label.eq(i).next().eq(0).val();
        // alert($label.eq(i).next().eq(0).attr("placeholder"));
        if (val === "") {
            $label.eq(i).next().eq(0).val($label.eq(i).next().eq(0).attr("placeholder"));
        }
    }

}

function pictA() {
    /*
    判断图片格式是否正确

     */

    let val = $("#pict").val();
    if (val === "" || val == null) {
        return false;
    }
    let PictTypeArray = "jpgJpEgpng";

    let pictElement = $("#pict")[0];
    let PictType = pictElement.value;

    PictTypeArray = PictTypeArray.toLowerCase();
    PictType = PictType.toLowerCase();
    let lastIndexOf = PictType.lastIndexOf(".");
    PictType = PictType.substring(lastIndexOf + 1);
    let indexOf = PictTypeArray.indexOf(PictType);

    if (indexOf === -1) {
        let sr = "<div   name='newError'><span style='color: green;background-color: yellow' >不支持此格式的图片 请重新上传</span></div>";
        $("#pict").after(sr);
        return false;
    }

    return true;
}

function filmType() {
    let i;
    let pd = true;
    let array = new Array();
    let val = $("#film").val();
    if (val === "" || val == null) {
        return false;
    }
    let Filmtype = "mp4swfoggwebm";
    Filmtype = Filmtype.toLowerCase();
    let films = document.getElementById("film");
    let files = films.files;
    for (i = 0; i < files.length; i++) {
        let name = files[i].name;
        let lastIndexOf = name.lastIndexOf(".");
        name = name.substring(lastIndexOf + 1).toLowerCase();
        let indexOf = Filmtype.indexOf(name);
        if (indexOf === -1) {
            array[i] = "<div style='color: green'  name='newError'>第 <spen style='color: red'> " + (i + 1) + " </spen> 个影片 的格式不正确</div>";
        }
    }
    if (array.length === 0) {
        pd = true

    } else {
        for (i = 0; i < array.length; i++) {
            $("#film").after(array[i]);
        }
        pd = false;

    }
    return pd;
}

function filmExists() {

    /*
    判断数据库有没有此影片  没有则返回true
     */
    let pd ;
    if(UpFilm_FilmAll.length===0){

        return true;
    }

    let $filmName = $("#filmName").val();
    if($filmName===""){

        return true;
    }
    let indexOf = UpFilm_FilmAll.indexOf($filmName);

    if (indexOf === -1) {
        pd = true;
    } else {
        let sr = "<div  name='newError'><span style='color: green;background-color: yellow' >已经存在此影片</span></div>";
        $("#filmName").after(sr);
        pd = false;
    }

    return pd;

}

function select() {

    let pd = true;
    let $select = $("#select").val();
    if ($select === "") {
        let sr = "<div  name='newError'><span style='color: green;background-color: yellow' >请选择影片类型</span></div>";
        $("#select").after(sr);
        pd = false;
    }
    return pd;
}

function getFilmAll() {
    $.post(
       commonUrl+UpFilm_name+ "/GetFilmTitleAll",
        function (data) {
          let data_string= JSON.stringify(data);

            if(data_string==="[]"){

                UpFilm_FilmAll=[];
            }else {

               for(let i=0;i<data.length;i++){
                   UpFilm_FilmAll[i]=data[i];
               }

            }
        },
        "json"
    )
}

function pictLength() {
    let w=870;
    let h=518;
    $(document).mousemove(function () {

        let val = $("#pict").val();
        if (val === "") {

        } else {

            let MyTest = document.getElementById("pict").files[0];
            let reader = new FileReader();
            reader.readAsDataURL(MyTest);
            reader.onload = function (theFile) {
                let image = new Image();
                image.src = theFile.target.result;
                image.onload = function () {
                    // alert("图片的宽度为"+this.width+",长度为"+this.height);
                    let width = image.width;
                    let height = image.height;
                    if(w<=width&&h<=height){
                        $("#pict").attr({alt: ""});
                    }else {
                        $("#pict").attr({alt: "LengthError"});
                    }
                };
            };

        }
    });
}
function PictB() {

    let pd;
    let $alt = $("#pict").attr("alt");
    if($alt===""){
        pd=true;
    }else {
        let sr = "<div   name='newError'><span style='color: green;background-color: yellow' >图片尺寸不符合规范</span></div>";
        $("#pict").after(sr);
        pd=false;
    }

    return pd;
}

