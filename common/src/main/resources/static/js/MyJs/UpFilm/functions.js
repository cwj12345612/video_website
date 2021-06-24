
getFilmAll();
pictLength();
reset();
function reset() {
    $(":input").val("");
}

function clear() {

    /*
    ����ϴ��ϴ��������ʾ��Ϣ
     */
    let $div = $("div[name='newError']");
    $div.remove();

}

function valueNull() {
    /*
    �жϱ������Ƿ������   true��ʾ�����
     */
    let pd = true;
    let $label = $("label[style='color: red;']");
    for (let i = 0; i < $label.length; i++) {
        let val = $label.eq(i).next().eq(0).val();

        if (val === "") {
            pd = false;
            let sr = "<div  name='newError'><span style='color: green;background-color: yellow' >����Ϊ������</span></div>";
            $label.eq(i).next().eq(0).after(sr);
        }
    }

    return pd;
}

function Auto() {
    /*
    ���Ǳ������  ����û�û����д  ����Ĭ��ֵ
     */

    let $label = $("label").not($("label[style='color: red;']")); //�Ǳ�����
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
    �ж�ͼƬ��ʽ�Ƿ���ȷ

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
        let sr = "<div   name='newError'><span style='color: green;background-color: yellow' >��֧�ִ˸�ʽ��ͼƬ �������ϴ�</span></div>";
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
            array[i] = "<div style='color: green'  name='newError'>�� <spen style='color: red'> " + (i + 1) + " </spen> ��ӰƬ �ĸ�ʽ����ȷ</div>";
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
    �ж����ݿ���û�д�ӰƬ  û���򷵻�true
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
        let sr = "<div  name='newError'><span style='color: green;background-color: yellow' >�Ѿ����ڴ�ӰƬ</span></div>";
        $("#filmName").after(sr);
        pd = false;
    }

    return pd;

}

function select() {

    let pd = true;
    let $select = $("#select").val();
    if ($select === "") {
        let sr = "<div  name='newError'><span style='color: green;background-color: yellow' >��ѡ��ӰƬ����</span></div>";
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
                    // alert("ͼƬ�Ŀ��Ϊ"+this.width+",����Ϊ"+this.height);
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
        let sr = "<div   name='newError'><span style='color: green;background-color: yellow' >ͼƬ�ߴ粻���Ϲ淶</span></div>";
        $("#pict").after(sr);
        pd=false;
    }

    return pd;
}

