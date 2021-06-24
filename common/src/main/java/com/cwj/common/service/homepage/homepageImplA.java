package com.cwj.common.service.homepage;


import com.cwj.common.Utils.Utils;
import com.cwj.common.domain.Film;
import org.springframework.stereotype.Service;

import java.util.*;


@Service  //把service注册进ioc容器
public class homepageImplA  implements homepageInterface{
    /**
     *  在film集合中获取sizeByType个影片  返回一个list集合
     * @param films-List<Film>
     * @param sizeByType-int
     * @return list
     */
    private List<Film> GetFilmListBySizeByType(List<Film> films, int sizeByType){


        if(sizeByType==films.size()){
                /*
                如果所需的影片的数量  等于 服务器中的影片数
                    则把 所有影片都发送给前端
                 */
            return  films;
        }else if(sizeByType>films.size()&&films.size()!=0){
            /*
              如果所需的影片的数量  大于 服务器中的影片数
              则大于部分用最后一个影片充当
             */
            List<Film> filmList=new ArrayList<>();
            filmList.addAll(films);
            for(int i=films.size();i<sizeByType;i++){
                filmList.add(films.get(films.size()-1));
            }
            return filmList;
        } else {
                /*
                如果所需的影片的数量  小于 服务器中的影片数
                则随机获取服务器中一定数量的影片
                 */
            int[] random = Utils.GetRandom(films.size() - 1, 0, sizeByType);
            List<Film> filmList=new ArrayList<>();
            for (int i : random) {
                filmList.add(films.get(i));
            }
            return filmList;
        }

    }

    /**
     * 从ServletContext中获取全部影片 并查找sum数量的影片返回给控制器  以map集合形式返回
     * @param sum-int
     * @param FilmMap-Map<String, List<Film>>
     * @return map
     */
    @Override
    public Map<String,List<Film>> FilmMapBySum(int sum, Map<String, List<Film>> FilmMap) {
        Map<String,List<Film>> FilmMapBySum=new HashMap<>();
        if(FilmMap.size()==0){

            return FilmMapBySum;
        }
        Set<String> TypeSet = FilmMap.keySet();
        int sizeByType=sum/TypeSet.size();   //平均要获取每类影片的数量
       //由于本人对Set集合不熟悉  所以把Set集合转成数组
        Object[] Types = TypeSet.toArray();
        for (Object type : Types) {
            List<Film> films = FilmMap.get(type);
            List<Film> filmList = this.GetFilmListBySizeByType(films, sizeByType);
             FilmMapBySum.put(type.toString(),filmList);
            }



        if(sum%TypeSet.size()!=0){
            //  如果  结果不为0  则说明还未获取到所需影片个数的影片
            int num=sizeByType+sum%TypeSet.size();
            List<Film> films = FilmMap.get(Types[Types.length - 1]);
            List<Film> filmList = this.GetFilmListBySizeByType(films, num);
            FilmMapBySum.put(Types[Types.length-1].toString(),filmList);
        }
        return FilmMapBySum;
    }

   //**************************************************************************************************************
@Override
public Map FillListByArray(String[] Types, int[] nums,Map<String,List<Film>> filmMap) {
        Map<String,List<Film>> JsonMap=new HashMap<>();
    for(int i=0;i<Types.length;i++){
        List<Film> filmList = filmMap.get(Types[i]);
        if(filmList==null){
            continue;
        }
        if(filmList.size()==0){
            continue;
        }
        List<Film> films = this.GetFilmListBySizeByType(filmList, nums[i]);
        JsonMap.put(Types[i],films);
    }


    return JsonMap;
}

}