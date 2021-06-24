package com.cwj.common.service.UpFilm;


import com.cwj.common.Dao.MysqlDao;
import com.cwj.common.Utils.QueryFilmAll;
import com.cwj.common.Utils.Utils;
import com.cwj.common.domain.Film;
import com.cwj.common.domain.MysqlFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("all")
@Service
public class UpFilmImplA implements UpFilm{



    @Autowired
    private MysqlDao mysqlDao;
    @Autowired
    private QueryFilmAll queryFilmAll;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    /*
     * 存储图片
     * @param request
     * @param list
     * @param filmMap
     * @throws IOException
     */
    private void storagePict(MultipartFile pictFile,MysqlFilm mysqlFilm) throws IOException {

                    //获取要存储图片的服务器绝对路径
                    String UserUpPictPath = servletContext.getRealPath("pict/UpPict");

                    //更改图片的名字为 影片名称 +

        String pictType = pictFile.getOriginalFilename().substring(Objects.requireNonNull(pictFile.getOriginalFilename()).lastIndexOf(".") + 1);

        String NewPictName = mysqlFilm.getTitle()+"."+pictType;

                    //获取要存储图片的服务器绝对路径
                    String PictAbsolutePath = UserUpPictPath + "\\" + NewPictName;
                    //存储图片到服务器

            File Pict = new File(PictAbsolutePath);
               pictFile.transferTo(Pict);

                    //裁剪不同大小的图片
                    String PictsPath = servletContext.getRealPath("pict");
                    File[] files = new File(PictsPath).listFiles();
                    if(files!=null&&files.length>0) {
                        for (File file : files) {
                            List<Integer> integerList = Utils.GetIntegersByString(file.getName());
                            if (integerList.size() > 0) {
                                String PictPathBySize = file.getAbsolutePath() + "\\" + NewPictName;
                                Utils.cut(Pict.getAbsolutePath(), PictPathBySize, integerList.get(0), integerList.get(1));
                            }
                        }
                    }
            }
    /*
     * 存储影片
     * @param request
     * @param list
     * @param filmMap
     */
    private void storageFilm(MultipartFile[] filmFiles,MysqlFilm mysqlFilm) throws IOException {

        for (MultipartFile filmFile : filmFiles) {
            String FilmAbsolutePath=servletContext.getRealPath("film")+"\\"+ mysqlFilm.getType()+"\\"+mysqlFilm.getTitle()+"\\"+filmFile.getOriginalFilename();
            FilmAbsolutePath=FilmAbsolutePath.replaceAll("\\[","(").replaceAll("\\]",")");
            /*
            用transferTo方法写入磁盘会抛异常
             */
            InputStream is = filmFile.getInputStream();
            Utils.storage(is,FilmAbsolutePath);

            is.close();
        }


                }

    @Override
    public void UpFilms(MysqlFilm mysqlFilm, MultipartFile pictFile, MultipartFile[] filmFiles) throws Exception {
        //调用dao层 把对象存储到数据表中
        mysqlDao.InsertFilm(mysqlFilm);
        //*************************************************************************************

        //存储图片
        this.storagePict(pictFile,mysqlFilm);

        //存储影片
        this.storageFilm(filmFiles,mysqlFilm);

        /*
        根据mysqlfilm对象 创建一个film对象
        并存到ServletContext域对象中  并更新redis中的json字符串
         */

        Film film = queryFilmAll.fillFilm(mysqlFilm,servletContext.getRealPath("") );

        /*

        把film添加到redis中
         */

        List<Film> filmList = (List<Film>) redisTemplate.opsForHash().get("FilmMap",film.getType());


       if(filmList==null) {
           filmList=new ArrayList<>();

       }
        filmList.add(film);
        redisTemplate.opsForHash().put("FilmMap",film.getType(),filmList);
    }


      /**
                     * 获取全部影片  封装到一个list集合中
                     * @param FilmMap
                     * @return
                     */
      @Override
      public List<Film> GetFilmTitleAll (Map < String, List < Film >> FilmMap){
                        List<Film> filmList = new ArrayList<>();
                        for (String type : FilmMap.keySet()) {
                            List<Film> films = FilmMap.get(type);
                            filmList.addAll(films);
                        }
                        return filmList;
                    }

}