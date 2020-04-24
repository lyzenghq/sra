package com.sra;

import com.ftp.*;

import java.io.*;

public class ftp {
    public static void main(String[] args) {
        String localPath;
        String dirname;
        String filename;
        boolean flag;

        // 连接FTP服务器
        if (FTPUtil.connect("192.168.1.11", 21)) {
            System.out.println("连接FTP服务器成功；");
        } else {
            System.out.println("连接FTP服务器失败；");
        }

        // 登录FTP服务器
        if (FTPUtil.login("sra", "beequuo7Ah")) {
            System.out.println("登录FTP服务器成功；");
        } else {
            System.out.println("登录FTP服务器失败；");
        }

        // 切换工作目录
        dirname = "user1";
        try {
            flag = false;
            if (!FTPUtil.existFile(dirname)){
                flag = FTPUtil.makeDirectory(dirname);
            }
            if (flag) {
                System.out.println("创建目录 " + dirname + " 成功；");
            } else {
                System.out.println("创建目录 " + dirname + " 失败；");
            }

            if(FTPUtil.changeWorkingDirectory(dirname)){
                System.out.println("切换到目录 " + dirname + " 成功；");
            } else {
                System.out.println("切换到目录 " + dirname + " 失败；");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 准备上传文件
        localPath = "D:\\Works\\Java\\sra3\\test\\uploads\\";
        filename = "你好.txt";
        File file = new File(localPath + filename);
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 上传文件
        System.out.println("上传文件" + filename + "开始；");
        if (FTPUtil.uploadFile(input, filename)) {
            System.out.println("上传文件" + filename + "成功；");
        } else {
            System.out.println("上传文件" + filename + "失败；");
        }

        // 下载文件
        localPath = "D:\\Works\\Java\\sra3\\test\\downloads\\";
        filename = "你好.txt";
        System.out.println("下载文件" + filename + "开始；");
        if (FTPUtil.downloadFile(localPath, filename)) {
            System.out.println("下载文件" + filename + "成功；");
        } else {
            System.out.println("下载文件" + filename + "失败；");
        }

        // 删除文件
        if(FTPUtil.deleteFile("你好.txt")){
            System.out.println("删除文件" + filename + "成功；");
        } else {
            System.out.println("删除文件" + filename + "失败；");
        }

        // 登出FTP服务器
        if (FTPUtil.logout()) {
            System.out.println("登出FTP服务器成功；");
        } else {
            System.out.println("登出FTP服务器失败；");
        }

        // 断开FTP服务器
        if (FTPUtil.disconnect()) {
            System.out.println("断开FTP服务器成功；");
        } else {
            System.out.println("断开FTP服务器失败；");
        }
    }

    public static void main1(String[] args) {
        String localPath;
        String dirname;
        String filename;

        // 连接FTP服务器
        FTPUtil.connect("192.168.1.11", 21);
        // 登录FTP服务器
        FTPUtil.login("sra", "beequuo7Ah");

        // 切换工作目录
        dirname = "user1";
        try {
            if (!FTPUtil.existFile(dirname)){
                FTPUtil.makeDirectory(dirname);
            }
            FTPUtil.changeWorkingDirectory(dirname);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 准备上传文件
        localPath = "D:\\Works\\Java\\sra3\\test\\uploads\\";
        filename = "你好.txt";
        File file = new File(localPath + filename);
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 上传文件
        FTPUtil.uploadFile(input, filename);

        // 下载文件
        localPath = "D:\\Works\\Java\\sra3\\test\\downloads\\";
        filename = "你好.txt";
        FTPUtil.downloadFile(localPath, filename);

        // 删除文件
        FTPUtil.deleteFile("你好.txt");

        // 登出FTP服务器
        FTPUtil.logout();
        // 断开FTP服务器
        FTPUtil.disconnect();
    }
}
