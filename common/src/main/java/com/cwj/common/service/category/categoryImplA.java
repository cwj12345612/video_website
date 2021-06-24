package com.cwj.common.service.category;


import com.cwj.common.domain.Film;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class categoryImplA implements categoryInterface{
    @Override
    public List GetFilmListByTypeAndLength(Map<String, List<Film>> filmMap, String type, int length) {
       List<Object> JsonList=new ArrayList<>(length);
        List<Film> filmList = filmMap.get(type);
        if(filmList==null){
            return new ArrayList();
        }
        if(filmList.size()==0){
            return new ArrayList();
        }
        if(filmList.size()>=length){
            for(int i=0;i<length;i++){
                JsonList.add(filmList.get(i));
            }
        }else if(filmList.size()<length&&filmList.size()>0){
            for (Film film : filmList) {
                JsonList.add(film);
            }
            Film film = filmList.get(filmList.size() - 1);
            for(int i=filmList.size();i<length;i++){
                JsonList.add(film);
            }
        }
        Map<String,Object> OtherMap=new HashMap<>();
        OtherMap.put("FilmByTypeAllSize",filmList.size());
        JsonList.add(OtherMap);
        return JsonList;
    }

    @Override
    public List GetFilmListByPageSizeAndType(Map<String, List<Film>> filmMap, String type, int length, int pageSize) {
        List<Film> JsonList=null;
        List<Film> filmList = filmMap.get(type);
        int max=pageSize*length-1;  //最大索引
        int min=(pageSize-1)*length; //最小索引
        if(filmList.size()<max){
            JsonList=new ArrayList<>();
            //如果 本页不能完全填充
            List<Film> films = filmList.subList(min, filmList.size());
            JsonList.addAll(films);

            for(int i= filmList.size();i<=max;i++){
                JsonList.add(films.get(films.size()-1));
            }
        }else {
            JsonList = filmList.subList(min, max+1);
        }
        return JsonList;
    }
}
