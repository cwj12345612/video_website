
function Fill(data) {


    $("#TypeFilm").attr({href:commonUrl+MovieSection_name+".html?type="+TypeMap.get(data.type)});
    $("#TypeFilm").html(TypeMap.get(data.type));
    $("#explain").html(TypeMap.get(data.type) + "����");
    $("#type").html(TypeMap.get(data.type));
    /*
    ��ȡ����
     */

    /*
    ��� ͼƬ
     */
    data._570x516pict = data._570x516pict.replace(/\\/g, '/');

    $("#img").attr({src:commonUrl+ data._570x516pict});


    /*
    ��� ���߲���
     */
    let $href=commonUrl+play_name+".html?"+data.title+"/";
    if (data.videoPath.length > 1) {
        for (let i = 0; i < data.videoPath.length; i++) {
            let k = i + 1;
            data.videoPath[i] = data.videoPath[i].replace(/\\/g, "/");
            let $a = "<u style='color: #2a58ae'><a style='font-size: 20px' href='"+$href + data.videoPath[i] + "'>��" + k + "��</a></u>";
            $("#play").after($a);
            //ʹ��cookie��ӰƬ���������·���浽�ͻ���
        }
    }else {
        data.videoPath[0] = data.videoPath[0].replace(/\\/g, "/");
    }
    let $href0=$href+data.videoPath[0];

    $("#againPlay").attr({href: $href0});

    /*
    ������ذ�ť
        Ϊ���ذ�ť�󶨵����¼�
     */
    let Ahref = commonUrl+explain_name+"/DownloadFilm/" + data.title + "/" + data.type;
    if(data.videoPath.length>1) {

        for (let i = 0; i < data.videoPath.length; i++) {
            let k = i + 1;
            let videoSize = Ahref + "/" +i;
            let $a = "<u style='color: #2a58ae;'><a style='font-size: 20px' href=" + videoSize + " '>��" + k + "��</a></u>";
            $("#down").after($a);
            //ʹ��cookie��ӰƬ���������·���浽�ͻ���
        }
    }
    let downAll =Ahref+"/-1";
    $("#downAll").attr({href:downAll});

    /*
    ���ӰƬ����
     */
    $("#filmName").html(data.title);
    /*
    ���ʱ��
     */
    $("#length").html(data.length+"����");
    /*
    ��伯��
     */

    $("#episodes").html(data.episodes);
    /*
    �����ӳʱ��
     */
    $("#releaseDate").html(data.releaseDate);
    /*
    ��䵼��
     */
    $("#director").html(data.director);
    /*
    �������
     */
    $("#protagonist").html(data.protagonist);
    /*
    ��������
     */
    $("#synopsis").html(data.synopsis);
    /*
    �����Դ
     */
    $("#source").html(data.filmSource);


}
