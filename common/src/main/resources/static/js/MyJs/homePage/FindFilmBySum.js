/*
       ���˷���post����  ���󷵻�һ��������ӰƬ��Ϣ
         */
/*
��ȡҪ���570x516ͼƬ�ĸ���
��ȡҪ���870x518ͼƬ�ĸ���
 */


$.post(
    commonUrl+"homepage/FindFilmBySum/"+homePage_sum,
    function (data) {
       // data=JSON.parse(data);
         /*
        ����ͼƬ
                 */


        fillBigPict(data);
        /*
        ���СͼƬ
         */

      fillLittlePict(data);
        //
        pictCilk();



    },
    "json"
)

/**
 * �˷�������ͼƬ��src·��
 * @param data
 */
