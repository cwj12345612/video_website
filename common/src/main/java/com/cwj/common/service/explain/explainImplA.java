package com.cwj.common.service.explain;


import com.cwj.common.domain.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class explainImplA implements explainInterface{
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Override
    public Film GetFilmByPath(String pictPath,Map<String,List<Film>> FilmMap) {


        Film f=null;
        pictPath = pictPath.replaceAll("/", "\\\\");

        boolean pd=false;   //默认没有此影片
        for (String type : FilmMap.keySet()) {
            List<Film> filmList = FilmMap.get(type);

            for (Film film : filmList) {

                String pictPathAll = film.get_570x516pict() + film.get_870x518pict() + film.get_270x244pict();
                if(pictPathAll.contains(pictPath)){
                    f=film;
                    pd=true;
                    break;
                }
            }
            if(pd==true){
                break;
            }
        }

        return f;
    }

    @Override
    public void downloadFilm(String filmName, String type, int size, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data"); // 通知客户文件的MIME类型;

        Object Map=redisTemplate.opsForHash().entries("FilmMap") ;
        Map<String,List<Film>> filmMap=(Map<String, List<Film>>)Map;

        Film f=null;
        List<Film> films = filmMap.get(type);
        for (Film film : films) {
            String title = film.getTitle();
            if (title.equals(filmName)){
                f=film;
                break;
            }
        }

        List<String> videoPath = f.getVideoPath();
        List<String> AbPath=new ArrayList<>();
        //获取影片服务器绝对路径
        for (String path : videoPath) {
            String realPath = servletContext.getRealPath(path);
            AbPath.add(realPath);
        }
        videoPath=AbPath;

        if(size==-1){
            //前端需要所有影片
            String FilmZip=filmName+".zip";
            response.setHeader("Content-disposition" , "attachment;filename=" + URLEncoder.encode(FilmZip,"utf-8"));
            //要下载的文件文件夹
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

            for (String filePath :videoPath) {
                File file = new File(filePath);
                doZip(file, zos);
            }
            zos.close();
        }else {

            // 找到影片名称为filmName的影片的第size+1集

            String path = videoPath.get(size);
            String name =filmName+" / "+new File(path).getName();
            response.setHeader("Content-disposition" , "attachment;filename=" + URLEncoder.encode(name,"utf-8"));


            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(path));
            ServletOutputStream sos = response.getOutputStream();

            byte[] by=new byte[1024];
            int len=-1;

            while ((len=bis.read(by))!=-1){
                sos.write(by,0,len);
            }
            sos.close();
            bis.close();
        }






    }
    /**
     *  打包为 zip 文件
     * @param file 待打包的文件
     * @param zos zip zip输出流
     * @throws IOException
     */
    private void doZip(File file, ZipOutputStream zos) throws IOException {

        if (file.exists()) {
            if (file.isFile()) {
                //假设是文件，写入到 zip 流中
                zos.putNextEntry(new ZipEntry(file.getName()));
               BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[1024];
                int r = -1;
                while ((r = bis.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }
                zos.flush();
                bis.close();
            }

        }}}
