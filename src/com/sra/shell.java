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
        String shell = "/usr/sbin/nologin";
        String comment = "sra user";

        // The whole process of creating sra users
        CreateGroup(pl, groupName);
        CreateDir(pl, topHomeDir);
        ChownDir(pl, topHomeDir, topDirUserName, groupName);
        // creating a user
        CreateUser(pl,"zhq", "123", groupName,
                topHomeDir, shell, comment);
        // one more user
        CreateUser(pl,"zhq1", "123", groupName,
                topHomeDir, shell, comment);

        // Listing a dir
        ListDirExample(pl, "/data/sra/zhq");
        ListDirExample(pl, "/notexist");

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
    private static void ListDirExample(ProcessLinux pl, String path) {
        int exitValue;

        exitValue = pl.ListDir(path);

        System.out.println("Listing dir " + path);
        PrintOutOrErr(pl, exitValue);
    }

    // Creating the top dir of home dir
    private static void CreateDir(ProcessLinux pl, String topHomeDir) {
        int exitValue;

        exitValue = pl.CreateDir(topHomeDir);

        System.out.println("Creating dir " + topHomeDir);
        PrintOutOrErr(pl, exitValue);
    }

    // Changing owner of a dir
    private static void ChownDir(ProcessLinux pl, String topHomeDir,
                                 String username, String groupName) {
        int exitValue;

        exitValue = pl.ChownDir(topHomeDir, username, groupName);

        System.out.println("Changing owner of dir " + topHomeDir);
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
