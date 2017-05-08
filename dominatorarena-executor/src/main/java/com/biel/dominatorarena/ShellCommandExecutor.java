package com.biel.dominatorarena;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class ShellCommandExecutor {
    Logger l = LoggerFactory.getLogger(ShellCommandExecutor.class);
    public String executeCommandBlocking(String command, String[] args, File where) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command, args, where);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader reader_err =
                    new BufferedReader(new InputStreamReader(p.getErrorStream()));


            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            String line_err = "";
            while ((line_err = reader_err.readLine())!= null) {
                output.append(line_err + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
