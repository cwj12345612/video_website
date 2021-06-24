package com.cwj.common.service.searchResult;

import com.cwj.common.domain.Film;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class searchResultImpl implements searchResultInterface{
    @Override
    public List<Film> FindFilmListByName(String Name, Map<String, List<Film>> filmMap) {

        List<Film> filmList=new ArrayList<>();
        for (String type : filmMap.keySet()) {
            List<Film> films = filmMap.get(type);
            for (Film film : films) {

                String title = film.getTitle();

                if(Name.contains(title)||title.contains(Name)){

                    filmList.add(film);
                }
            }
        }
        return filmList;
    }
}
