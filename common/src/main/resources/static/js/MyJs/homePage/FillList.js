/*
����һ��get����
��ȡ��̨���ͻ�����json
���ҳ���·������ֺ�����
�������ݵĸ�ʽ
��ʽ
ӰƬ���� : [����1,����2,����3]
������Ҫ���ĸ���: [��1,��2,��3]

....
*/
/*
��ѯ��������
 */



for(let i=0;i<homePage_$ul.length;i++){
    homePage_typeArray[i]=homePage_$ul[i].id;
}
/*
��ѯÿ������Ҫ��������  ��ѯ�м���li��ǩ
 */

for(let i=0;i<homePage_$ul.length;i++){

    homePage_sizeArray[i]=$(homePage_$ul[i]).find("li").length
}

$.post(
    commonUrl+homepage_name+"/FillList",
    {"video":homePage_typeArray,"size":homePage_sizeArray},
    function (data) {
       fillList(data);
         ListCilk();
    },
    "json"
);
