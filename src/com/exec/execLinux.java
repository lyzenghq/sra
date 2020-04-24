package com.exec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class execLinux {
    // exec a cmd
    public static String exec(String cmd) {
        String[] fullCMD = {"/bin/bash", "-c", cmd};
        Process process;
        InputStreamReader processOut;
        BufferedReader read;
        StringBuffer outBuffer;

        try {
            // run cmd in process
            process = Runtime.getRuntime().exec(fullCMD);
            process.waitFor(5L, TimeUnit.SECONDS);

            // get output of process
            processOut = new InputStreamReader((process.getInputStream()));
            read = new BufferedReader(processOut);

            String line;
            outBuffer = new StringBuffer();
            while ((line = read.readLine()) != null) {
                outBuffer.append(line).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return outBuffer.toString();
    }

    public static String listDir(String path) {
        String cmd;
        String out;

        cmd = String.format("sudo ls %s", path);
        out = exec(cmd);

        return out;
    }

    // create a user
    public static boolean createUser(String username, String password) {
        String cmd;
        String groupName = "sra";
        String out;

        // cmd tested successfully in CentOS 7
        // try to create a group
        cmd = String.format("sudo groupadd %s", groupName);
        out = exec(cmd);
        if (out == null) {
            // cmd run failed
            System.out.println("groupadd failed.");
            return false;
        } else {
            // cmd run successfully
            System.out.println("groupadd successfully.");
        }

        // try to create a dir
        out = exec("sudo mkdir -p /data/sra ; sudo chown root:sra /data/sra");
        if (out == null) {
            // cmd run failed
            System.out.println("create a dir failed.");
            return false;
        } else {
            // cmd run successfully
            System.out.println("create a dir successfully.");
        }

        // try to create a user
        cmd = String.format("sudo useradd -c \"sra user\" " +
                "-d \"/data/sra/%s\" -g %s -m " +
                "-p $(openssl passwd -1 %s) " +
                "-s /usr/sbin/nologin %s", username, groupName, password, username);
        out = exec(cmd);
        if (out == null) {
            // exec() error.
            System.out.println("exec() error.");
            return false;
        } else {
            // cmd run successfully
            System.out.println("useradd successfully.");
        }

        return true;
    }
}
