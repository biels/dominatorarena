package com.biel.dominatorarena.shell;

import org.crsh.cli.Command;
import org.crsh.cli.Usage;
import org.springframework.stereotype.Component;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
@Usage("Arena commands")
public class arena {
    @Command
    String version(){
        return String.valueOf(1.0);
    }
    @Command
    String list(){
        return "hello";
    }
}
