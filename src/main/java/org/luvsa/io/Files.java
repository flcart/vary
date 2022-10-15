package org.luvsa.io;

/**
 * @author Aglet
 * @create 2022/10/12 15:36
 */
public class Files {

    /**
     * 获取文件拓展后缀名
     *
     * @param filename 文件名
     * @return 拓展名
     */
    public static String extension(String filename) {
        if (filename == null){
            return "";
        }
        var index = filename.lastIndexOf(".");
        if (index < 0) {
            return "";
        }
        return filename.substring(index + 1);
    }




}
