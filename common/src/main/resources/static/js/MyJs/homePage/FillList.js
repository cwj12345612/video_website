/*
发送一个get请求
获取后台发送回来的json
填充页面下方的文字和链接
请求数据的格式
格式
影片类型 : [类型1,类型2,类型3]
各类型要填充的个数: [数1,数2,数3]

....
*/
/*
查询所有类型
 */



for(let i=0;i<homePage_$ul.length;i++){
    homePage_typeArray[i]=homePage_$ul[i].id;
}
/*
查询每个类型要填充的数量  查询有几个li标签
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
