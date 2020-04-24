package com.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FTPUtil {

    public static FTPClient ftpClient = null;


    /**
     * 连接FTP服务器
     *
     * @param hostname 主机名
     * @param port     端口号
     * @return boolean 是否执行成功
     */
    public static boolean connect(String hostname, Integer port) {
        boolean flag;
        ftpClient = new FTPClient();

        ftpClient.setControlEncoding("utf-8");

        try {
            ftpClient.connect(hostname, port);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }


    /**
     * 登录FTP服务器
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean 是否执行成功
     */
    public static boolean login(String username, String password) {
        boolean flag;

        try {
            flag = ftpClient.login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }


    /**
     * 登出FTP服务器
     *
     * @return boolean 是否执行成功
     */
    public static boolean logout() {
        boolean flag;

        try {
            flag = ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }


    /**
     * 断开FTP服务器
     *
     * @return boolean 是否执行成功
     */
    public static boolean disconnect() {
        boolean flag = false;

        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                flag = true;
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }


    /**
     * 上传文件
     *
     * @param filename    上传到FTP的文件名
     * @param inputStream 输入文件流
     * @return boolean 是否执行成功
     */
    public static boolean uploadFile(InputStream inputStream, String filename) {
        boolean flag;

        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            flag = ftpClient.storeFile(filename, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }


    /**
     * 下载文件
     *
     * @param localPath 保存到的本地路径
     * @param filename  FTP服务器内的文件名
     * @return boolean 是否执行成功
     */
    public static boolean downloadFile(String localPath, String filename) {
        boolean flag = false;
        OutputStream os = null;

        try {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localPath + "/" + file.getName());
                    os = new FileOutputStream(localFile);

                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    flag = ftpClient.retrieveFile(file.getName(), os);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }


    /**
     * 删除文件
     *
     * @param filename 要删除的文件名
     * @return boolean 是否执行成功
     */
    public static boolean deleteFile(String filename) {
        boolean flag;

        try {
            ftpClient.dele(filename);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }


    /**
     * 改变工作目录
     *
     * @param directory 目录名
     * @return boolean 是否执行成功
     */
    public static boolean changeWorkingDirectory(String directory) {
        boolean flag;

        try {
            flag = ftpClient.changeWorkingDirectory(directory);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            flag = false;
        }

        return flag;
    }


    /**
     * 判断FTP服务器文件是否存在
     *
     * @param path 文件路径
     * @return boolean 是否执行成功
     */
    public static boolean existFile(String path) throws IOException {
        boolean flag;

        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        flag = ftpFileArr.length > 0;

        return flag;
    }

    /**
     * 创建目录
     *
     * @param dir 目录名
     * @return boolean 是否执行成功
     */
    public static boolean makeDirectory(String dir) {
        boolean flag;

        try {
            flag = ftpClient.makeDirectory(dir);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    /**
     * 在当前工作目录下创建子路径，并切换到该路径；如果FTP服务器已存在该路径，则不创建
     *
     * @param remote 要创建的子路径
     * @throws IOException 错误
     */
    public static void CreateDirecroty(String remote) throws IOException {

        String directory = remote + "/";

        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(directory)) {
            int start;
            int end;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            //StringBuilder paths = new StringBuilder();
            do {
                String subDirectory = new String(remote.substring(start, end).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                //paths.append("/").append(subDirectory);
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
            } while (end > start);
        }

    }


}