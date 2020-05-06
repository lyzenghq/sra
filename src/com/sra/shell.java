package com.sra;

import com.exec.*;

import java.util.ArrayList;
import java.util.List;

public class shell {
    public static void main(String[] args) {
        ProcessLinux pl = new ProcessLinux();

        String topDirUserName = "root";
        String groupName = "sra";
        String topHomeDir = "/data/sra";
        String shell = "/bin/bash";
        String comment = "sra user";

        // The whole process of creating sra users
        // Creating the group of sra users
        CreateGroup(pl, groupName);
        // Creating the top-home-dir of sra users
        CreateDir(pl, topHomeDir);
        // Change owner of the top-home-dir
        ChownDir(pl, topHomeDir, topDirUserName, groupName);
        // Change the mode of dir
        ChmodDir(pl, topHomeDir, "g+rX");
        // Creating a sra user
        CreateUser(pl,"zhq", "123", groupName,
                topHomeDir, shell, comment);
        // Creating one more sra users
        CreateUser(pl,"zhq1", "123", groupName,
                topHomeDir, shell, comment);

        // Move all files between two Dirs
        MoveAllFiles(pl, "/data/sra/zhq", "/data/sra/zhq1");

        // Listing a dir
        ListDir(pl, "/data/sra/zhq");
        ListDir(pl, "/data/sra/zhq1");
        ListDir(pl, "/notexist");

        // A wrong usage of RunCmd()
        RunCmdWrongExample(pl);
    }

    // Print stderr or stdout
    private static void PrintOutOrErr(ProcessLinux pl, int exitValue) {
        if (exitValue != 0 && pl.stderr.length() > 0) {
            System.out.println(pl.stderr);
        } else if (pl.stdout.length() > 0) {
            System.out.println(pl.stdout);
        } else {
            System.out.println();
        }
    }

    // Listing a dir
    private static void ListDir(ProcessLinux pl, String path) {
        int exitValue;

        exitValue = pl.ListDir(path);

        System.out.println("Listing dir " + path);
        PrintOutOrErr(pl, exitValue);
    }

    // Move all files between two Dirs
    private static void MoveAllFiles(ProcessLinux pl, String Source, String Dest) {
        int exitValue;

        exitValue = pl.MoveAllFiles(Source, Dest);

        System.out.println("Moving all files from " + Source + " to " + Dest);
        PrintOutOrErr(pl, exitValue);
    }

    // Creating a dir
    private static void CreateDir(ProcessLinux pl, String path) {
        int exitValue;

        exitValue = pl.CreateDir(path);

        System.out.println("Creating dir " + path);
        PrintOutOrErr(pl, exitValue);
    }

    // Changing owner of a dir
    private static void ChownDir(ProcessLinux pl, String path,
                                 String username, String groupName) {
        int exitValue;

        exitValue = pl.ChownDir(path, username, groupName);

        System.out.println("Changing owner of dir " + path);
        PrintOutOrErr(pl, exitValue);
    }

    // Change the mode of dir
    private static void ChmodDir(ProcessLinux pl, String path,
                                 String mode) {
        int exitValue;

        exitValue = pl.ChmodDir(path, mode);

        System.out.println("Changing mode of dir " + path);
        PrintOutOrErr(pl, exitValue);
    }

    // Creating a group
    private static void CreateGroup(ProcessLinux pl, String groupName) {
        int exitValue;

        exitValue = pl.CreateGroup(groupName);

        System.out.println("Creating group " + groupName);
        PrintOutOrErr(pl, exitValue);
    }

    // Creating a user
    private static void CreateUser(ProcessLinux pl, String username,
                                   String password, String groupName,
                                   String topHomeDir, String shell,
                                   String comment) {
        int exitValue;

        exitValue = pl.CreateUser(username, password, groupName,
                String.format("%s/%s", topHomeDir, username),
                shell, comment);

        System.out.println("Creating user " + username);
        PrintOutOrErr(pl, exitValue);
    }

    // A wrong usage of RunCmd()
    private static void RunCmdWrongExample(ProcessLinux pl) {
        List<String> voidCmd = new ArrayList<>();

        System.out.println("A wrong example of RunCmd():");
        pl.RunCmd(voidCmd);
    }

}
