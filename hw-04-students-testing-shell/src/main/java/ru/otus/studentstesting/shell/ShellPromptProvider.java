package ru.otus.studentstesting.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;


@Component
public class ShellPromptProvider implements PromptProvider {
    private boolean isTestStarted = false;
    @Override
    public AttributedString getPrompt() {
        if (isTestStarted) {
            return new AttributedString("answer:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
        }
        return new AttributedString("test:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
