package com.sra;

import com.exec.*;

import java.util.ArrayList;
import java.util.List;

public class shell {
    public static void main(String[] args) {
        ProcessLinux pl = new ProcessLinux();

        // list a dir
        ListDirExample(pl, "/data/sra/zhq");
        ListDirExample(pl, "/notexist");

        // create a sra user
        CreateUserExample( pl,"zhq", "123");
        CreateUserExample( pl,"zhq1", "123");

        // a wrong usage of RunCmd()
        RunCmdWrongExample(pl);
    }

    // example - list a dir
    private static void ListDirExample(ProcessLinux pl, String path) {
        int exitValue;

        exitValue = pl.ListDir(path);

        // print stderr or stdout
        System.out.println("ls " + path);
        if (exitValue != 0 && pl.stderr.length() > 0) {
            System.out.println(pl.stderr);
        } else if (pl.stdout.length() > 0) {
            System.out.println(pl.stdout);
        } else {
            System.out.println();
        }
    }

    // example - create a sra user
    private static void CreateUserExample(ProcessLinux pl, String username,
                                          String password) {
        int exitValue;
        String groupName = "sra";
        String topHomeDir = "/data/sra";

        exitValue = pl.CreateUser(username, password, groupName,
                String.format("%s/%s", topHomeDir, username),
                "/usr/sbin/nologin",
                "sra user");

        // print stderr or stdout
        System.out.println("create sra user - " + username);
        if (exitValue != 0 && pl.stderr.length() > 0) {
            System.out.println(pl.stderr);
        } else if (pl.stdout.length() > 0) {
            System.out.println(pl.stdout);
        } else {
            System.out.println();
        }
    }

    // example - a wrong usage of RunCmd()
    private static void RunCmdWrongExample(ProcessLinux pl) {
        List<String> voidCmd = new ArrayList<>();

        System.out.println("a wrong example of RunCmd():");
        pl.RunCmd(voidCmd);
    }

    /*
    // old
    public static void main1(String[] args) {
        String out;
        boolean flag;

        // run a cmd
        out = execLinux.exec("sudo pwd");
        System.out.print("run a cmd 'sudo pwd':\n" + out);

        // create a user
        System.out.print("\ncreate a user:\n");
        flag = execLinux.createUser("zhq", "123");
        if (flag) {
            System.out.println("finish.");
        } else {
            System.out.println("error.");
        }

        // list a dir
        out = execLinux.listDir("/data/sra/zhq");
        System.out.print("\nls /data/sra/zhq:\n" + out);
    }
    */
}
