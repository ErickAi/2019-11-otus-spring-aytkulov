package ru.otus.studentstesting.shell;

import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class Exit implements Quit.Command {

    @ShellMethod(value = "Exit the shell.", key = {"выйти", "exit", "-"})
    public void quit() {
        throw new ExitRequest();
    }
}
