package com.cwj.common.Utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 操作文件相关的工具类
 */
@SuppressWarnings("all")
public class Utils {
    /**
     * 获取某路径下的所有文件
     * @param Path
     * @return
     */
    public static List<File> GetFileAllByPath(String Path){
        List<File> fileList=new ArrayList<>();
        if(new File(Path).exists()) {
            //如果该路径存在
            GetFile(Path, fileList);
        }else {

        }
        return fileList;
    }

    /**
     * 配合GetFileAllByPath使用 目的是使用递归查询所有文件  不能单独使用
     * @param path
     */
    private  final static void GetFile(String path,List<File> fileList){
            File f=new File(path);

        File[] files = f.listFiles();
        if(files.length==0||files==null){
            return;
        }
        for (File file : files) {
            if(file.isFile()){
                fileList.add(file);
            }else if(file.isDirectory()){
                String absolutePath = file.getAbsolutePath();
                GetFile(absolutePath,fileList);
            }
        }
    }

    /**
     * 获取min到max范围的size个随机数  随机数各不相同
     * 缺点  随机数中没有0
     * @param max
     * @param min
     * @param size
     * @return
     */
    public static int[] GetRandom(int max,int min,int size){
        int[] RandArray=null;

        if(max-min>=size){
            RandArray=new int[size];
            Random r=new Random();
            for(int i=0;i<RandArray.length;i++){
                int temp = r.nextInt(max + 1);
                boolean PdExists=false;
                for (int a : RandArray) {
                    if(a==temp){
                        PdExists=true;
                        break;
                    }
                }
                if(temp>=min&&PdExists==false){

                    RandArray[i] = temp;
                }else {
                    i--;
                }
            }
        }
        return RandArray;
    }
    /**
     * 从一个字符串中读取全部数字
     * @param s
     * @return
     */
    public static List<Integer> GetIntegersByString(String s){
        List<Integer> integerList=new ArrayList<>();
        StringBuilder IntegerSb=new StringBuilder();
        for(int i=0;i<s.length();i++){
            char charAt = s.charAt(i);
            if(charAt>='0'&&charAt<='9'){
                IntegerSb.append(charAt);
            }
            if(charAt<'0'||charAt>'9'||(i==s.length()-1)){
                if(IntegerSb.length()>0){
                    Integer integer = Integer.valueOf(IntegerSb.toString());
                    integerList.add(integer);
                    IntegerSb.delete(0,IntegerSb.length());
                }
            }
        }
        return  integerList;
    }

    /**
     * 裁剪图片
     * @param srcpath
     * @param subpath
     * @param width
     * @param height
     * @throws IOException
     * @author     https://www.cnblogs.com/gavinYang/p/11197756.html
     */
    public static void cut(String srcpath,String subpath,int width,int height) throws IOException {

        FileInputStream is = null ;
        ImageInputStream iis =null ;


        //读取图片文件


            is = new FileInputStream(srcpath);

            /**//*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader
             * 声称能够解码指定格式。参数：formatName - 包含非正式格式名称 .
             *（例如 "jpeg" 或 "tiff"）等。
             */
            String suffix = srcpath.substring(srcpath.lastIndexOf(".")+1);


            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = it.next();
            //获取图片流

            iis = ImageIO.createImageInputStream(is);


            /**//*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis,true) ;

            /**//*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回
             * ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /**//*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect = new Rectangle(0, 0, width, height);


            //提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

            /**//*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
             * 它作为一个完整的 BufferedImage 返回。
             */
            BufferedImage bi = null;
            bi = reader.read(0,param);
            //保存新图片
            ImageIO.write(bi, suffix, new File(subpath));
            is.close() ;
            iis.close();


    }
    /**
     *  往AbsolutePath路径下存储文件
     * @param is
     * @param AbsolutePath
     * @return
     */
    public static File storage(InputStream is, String AbsolutePath) throws IOException {

        File NewFile=new File(AbsolutePath);

        File parentFile = NewFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

            BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(AbsolutePath));
            byte[] b=new byte[1024*1024*100];
            int len=-1;
            while ((len=is.read(b))!=-1){
                os.write(b,0,len);
            }
            os.close();
            is.close();

        return NewFile;
    }

    /**
     *
     * 对影片各集进行排序  如Ep1.mp4 Ep10.mp4 Ep2.mp4 Ep21.mp4 Ep3.mp4
     * 排序后结果为Ep1.mp4  Ep2.mp4  Ep3.mp4   Ep10.mp4 Ep21.mp4
     * 影片名称命名规则要一致
     * @param videoPathList
     * @return
     */
    public static List<String> sortByListString(List<String> videoPathList){

            //如果影片数量大于10可能会乱序 要进行重新排序
            int NoSame=-1;
            for(int i=0;i<Utils.GetIntegersByString(videoPathList.get(0)).size()-1;i++){
                if(Utils.GetIntegersByString(videoPathList.get(0)).get(i)!=Utils.GetIntegersByString(videoPathList.get(1)).get(i)){
                    NoSame=i;
                    break;
                }
            }
            TreeMap<Integer,String> treeMap=new TreeMap<Integer, String>();
            for(int i=0;i<videoPathList.size();i++){
                List<Integer> integers = Utils.GetIntegersByString(videoPathList.get(i));
                Integer integer = integers.get(NoSame);
                treeMap.put(integer,videoPathList.get(i));
            }
            Collection<String> values = treeMap.values();

            String[] strings = values.toArray(new String[values.size()]);
            List<String> list = Arrays.asList(strings);



        return list;
    }
}
