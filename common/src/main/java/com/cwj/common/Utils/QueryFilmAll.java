package com.cwj.common.Utils;


import com.cwj.common.Dao.MysqlDao;
import com.cwj.common.domain.Film;
import com.cwj.common.domain.MysqlFilm;
import io.netty.handler.codec.EncoderException;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类查询服务器中所有影片
 */
@SuppressWarnings("all")
@Component(value = "queryFilmAll")
public class QueryFilmAll {

    @Autowired
    private MysqlDao mysqlDao;

    public MysqlDao getMysqlDao() {
        return mysqlDao;
    }


    public Map<String, List<Film>> GetFIlmAll(String ServerPath) throws IllegalAccessException, EncoderException, it.sauronsoftware.jave.EncoderException {
       Map<String,List<Film>> FilmMap=new HashMap<>();
        /*
        查询数据库的所有影片信息
         */
        List<MysqlFilm> filmList = mysqlDao.FindAll();
        if(filmList.size()==0||filmList==null){
            return FilmMap;
        }
        //查询有多少种影片类型
        List<String> FilmType = mysqlDao.FindType();

        //为每个类型创建一个list集合 用来存储film
        for (String type : FilmType) {
            FilmMap.put(type, new ArrayList<>());
        }
            //获取每部影片的film对象

        for (MysqlFilm mysqlFilm : filmList) {
            Film  film= this.fillFilm(mysqlFilm,ServerPath);
           //把影片添加到
            List<Film> films = FilmMap.get(film.getType());
            films.add(film);
        }
        return FilmMap;
    }

    /**
     * 根据从数据表提取的一条数据封装成的对象  查询对应的Film对象
     * @param mysqlFilm
     * @param ServerPath
     * @return
     */
    public Film fillFilm(MysqlFilm mysqlFilm,String ServerPath) throws IllegalAccessException, EncoderException, it.sauronsoftware.jave.EncoderException {
        Film film=new Film();
        //通过BeanUtils把mysqlFilm对象的属性的值 填充到FIlm对象中
        BeanUtils.copyProperties(mysqlFilm,film);

        //填充Film对象的其他属性
        this.FileToFilm(film,ServerPath);

        return film;
    }

    /**
     * 从文件中读取相关信息 填充到film对象中
     * @param film
     * @param ServerPath
     * @throws EncoderException
     */
   private void  FileToFilm(Film film,String ServerPath) throws EncoderException, it.sauronsoftware.jave.EncoderException {
        //获取影片名称
        String title = film.getTitle();

        //填充图片路径

        film.set_570x516pict(this.GetPictPathByTitle(ServerPath,"_570x516",title));
        film.set_270x244pict(this.GetPictPathByTitle(ServerPath,"_270x244",title));
        film.set_870x518pict(this.GetPictPathByTitle(ServerPath,"_870x518",title));

        //填充影片其他信息
        this.fillFilmOther(film,ServerPath);

    }

    /**
     * 获取特定尺寸的图片的服务器相对路径
     * @param ServerPath
     * @param Size
     * @param title
     * @return
     */
    private String GetPictPathByTitle(String ServerPath,String Size,String title){
        String PictPath=null;
        String PictSizePath=ServerPath+"pict\\"+Size;
        File Picts=new File(PictSizePath);
        File[] PictArray = Picts.listFiles();
        if(PictArray==null||PictArray.length==0){
            //如果找不到图片
        }else {
            for (File file : PictArray) {
                String fileName = file.getName();
                if (fileName.contains(title)) {
                    String absolutePath = file.getAbsolutePath();
                    PictPath = absolutePath.substring(ServerPath.length());
                    break;
                }
            }
        }
        return PictPath;
    }

    /**
     * 填充影片其他信息
     * @param film
     * @param ServerPath
     */
    private void fillFilmOther(Film film,String ServerPath) throws EncoderException, it.sauronsoftware.jave.EncoderException {
        //获取影片名称和影片类型
        String title = film.getTitle();
        String filmType = film.getType();
        //获取影片的服务器绝对路径
        String FilmAbsolutePath=ServerPath+"\\film\\"+filmType+"\\"+title;

        //获取影片的所有集
        List<File> Films = Utils.GetFileAllByPath(FilmAbsolutePath);
            if(Films.size()==0||Films==null){

            }else {
                //获取影片集数
                film.setEpisodes(Films.size());

                //获取第一个影片的时长
                long length = new Encoder().getInfo(Films.get(0)).getDuration();
                long min = length / 1000 / 60;
                film.setLength((int) min);

                //获取第一个影片的大小
                long Size = Films.get(0).length() / 1024 / 1024;
                film.setSize((int) Size);

                //获取影片所有集的服务器相对路径
                List<String> FilmPathList = new ArrayList<String>();
                for (File file : Films) {
                    String absolutePath = file.getAbsolutePath();
                    String FilmPath = absolutePath.substring(ServerPath.length());
                    FilmPathList.add(FilmPath);
                }
                film.setVideoPath(FilmPathList);
            }
    }
}
