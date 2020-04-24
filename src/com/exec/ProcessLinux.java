package com.exec;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

// cmd tested successfully in CentOS 7

public class ProcessLinux {
    public String stderr, stdout;

    /**
     * run a cmd
     * @param cmd: the cmd to run
     * @return int: exitValue
     */
    public int RunCmd(List<String> cmd) {
        ProcessBuilder processBuilder;
        Process process;
        long processTimeout = 5L;
        InputStream stdoutIS, stderrIS;
        BufferedReader stdoutBR, stderrBR;
        String line;
        StringBuilder stdoutSB, stderrSB;

        // check arguments
        if (cmd.size() <= 0) {
            System.err.print("RunCmd() Error: Need command to run.\n\n");
            return -1;
        }

        try {
            // run cmd in process
            processBuilder = new ProcessBuilder(cmd);
            process = processBuilder.start();
            process.waitFor(processTimeout, TimeUnit.SECONDS);

            // get stderr of process
            stderrIS = process.getErrorStream();
            stderrBR = new BufferedReader(new InputStreamReader(stderrIS));
            stderrSB = new StringBuilder();
            while ((line = stderrBR.readLine()) != null) {
                stderrSB.append(line).append('\n');
            }
            this.stderr = stderrSB.toString();

            // get stdout of process
            stdoutIS = process.getInputStream();
            stdoutBR = new BufferedReader(new InputStreamReader(stdoutIS));
            stdoutSB = new StringBuilder();
            while ((line = stdoutBR.readLine()) != null) {
                stdoutSB.append(line).append('\n');
            }
            this.stdout = stdoutSB.toString();

            return process.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * List a dir
     * @param path: the path to list
     * @return int: exitValue
     */
    public int ListDir(String path) {
        List<String> cmd;

        cmd = Arrays.asList("sudo", "ls", path);
        return RunCmd(cmd);
    }

    /**
     * Create a dir
     * @param path: the path to create
     * @return int: exitValue
     */
    public int CreateDir(String path) {
        List<String> cmd;

        cmd = Arrays.asList("sudo", "mkdir", "-p", path);
        return RunCmd(cmd);
    }

    /**
     * Change owner of a dir
     * @param path: the path to change owner
     * @param user: the owner user
     * @param group: the owner group
     * @return int: exitValue
     */
    public int ChownDir(String path, String user, String group) {
        List<String> cmd;

        cmd = Arrays.asList("sudo", "chown", user + ":" + group, path);
        return RunCmd(cmd);
    }

    /**
     * Create a group
     * @param groupName: group name
     * @return int: exitValue
     */
    public int CreateGroup(String groupName) {
        List<String> cmd;

        cmd = Arrays.asList("sudo", "groupadd", groupName);
        return RunCmd(cmd);
    }

    /**
     * Create a user
     * @param username: username of the new account
     * @return int: exitValue
     */
    public int CreateUser(String username) {
        List<String> cmd;

        cmd = Arrays.asList("sudo", "useradd", username);
        return RunCmd(cmd);
    }

    /**
     * Create a user
     * @param username: username of the new account
     * @param password: password of the new account
     * @return int: exitValue
     */
    public int CreateUser(String username, String password) {
        String cmd;
        List<String> fullCmd;

        cmd = String.format("sudo useradd " +
                        "-p $(openssl passwd -1 %s) " +
                        "%s",
                password, username);
        fullCmd = Arrays.asList("bash", "-c", cmd);
        return RunCmd(fullCmd);
    }

    /**
     * Create a user
     * @param username: username of the new account
     * @param password: password of the new account
     * @return int: exitValue
     */
    public int CreateUser(String username, String password, String groupName) {
        String cmd;
        List<String> fullCmd;

        cmd = String.format("sudo useradd " +
                        "-g %s " +
                        "-p $(openssl passwd -1 %s) " +
                        "%s",
                groupName, password, username);
        fullCmd = Arrays.asList("bash", "-c", cmd);
        return RunCmd(fullCmd);
    }

    /**
     * Create a user
     * @param username: username of the new account
     * @param password: password of the new account
     * @param homeDir: home directory of the new account
     * @return int: exitValue
     */
    public int CreateUser(String username, String password, String groupName,
                          String homeDir) {
        String cmd;
        List<String> fullCmd;

        cmd = String.format("sudo useradd " +
                        "-d \"%s\" -m " +
                        "-g %s " +
                        "-p $(openssl passwd -1 %s) " +
                        "%s",
                homeDir, groupName, password, username);
        fullCmd = Arrays.asList("bash", "-c", cmd);
        return RunCmd(fullCmd);
    }

    /**
     * Create a user
     * @param username: username of the new account
     * @param password: password of the new account
     * @param homeDir: home directory of the new account
     * @param shell: login shell of the new account
     * @return int: exitValue
     */
    public int CreateUser(String username, String password, String groupName,
                          String homeDir, String shell) {
        String cmd;
        List<String> fullCmd;

        cmd = String.format("sudo useradd " +
                        "-d \"%s\" -m " +
                        "-g %s " +
                        "-p $(openssl passwd -1 %s) " +
                        "-s %s " +
                        "%s",
                homeDir, groupName, password, shell, username);
        fullCmd = Arrays.asList("bash", "-c", cmd);
        return RunCmd(fullCmd);
    }

    /**
     * Create a user
     * @param username: username of the new account
     * @param password: password of the new account
     * @param homeDir: home directory of the new account
     * @param shell: login shell of the new account
     * @param comment: GECOS field of the new account
     * @return int: exitValue
     */
    public int CreateUser(String username, String password, String groupName,
                          String homeDir, String shell, String comment) {
        String cmd;
        List<String> fullCmd;

        cmd = String.format("sudo useradd " +
                        "-c \"%s\" " +
                        "-d \"%s\" -m " +
                        "-g %s " +
                        "-p $(openssl passwd -1 %s) " +
                        "-s %s " +
                        "%s",
                comment, homeDir, groupName, password, shell, username);
        fullCmd = Arrays.asList("bash", "-c", cmd);
        return RunCmd(fullCmd);
    }
}
