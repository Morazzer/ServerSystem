package org.morazzer.serversystem.spigot.commands;

import org.junit.BeforeClass;
import org.junit.Test;
import org.morazzer.serversystem.spigot.impl.commands.TestCommand;

import static org.morazzer.serversystem.spigot.Setup.before;

/**
 * @author Morazzer
 * @since Date 23.10.2020 21:10:31
 */
public class TestCommandTest {

    @BeforeClass
    public static void beforeClass() {
        before();
    }

    public void perform() {
        new TestCommand();
    }

}
