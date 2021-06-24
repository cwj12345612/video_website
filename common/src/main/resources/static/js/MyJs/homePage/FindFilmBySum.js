/*
       向后端发出post请求  请求返回一定数量的影片信息
         */
/*
获取要填充570x516图片的个数
获取要填充870x518图片的个数
 */


$.post(
    commonUrl+"homepage/FindFilmBySum/"+homePage_sum,
    function (data) {
       // data=JSON.parse(data);
         /*
        填充大图片
                 */


        fillBigPict(data);
        /*
        填充小图片
         */

      fillLittlePict(data);
        //
        pictCilk();



    },
    "json"
)

/**
 * 此方法填充大图片的src路径
 * @param data
 */
