package ru.mipt.bit.platformer.command;

// Command to toggle health bar display
public class ToggleHealthCommand implements Command {
    private final Runnable toggleAction;
    private boolean executed = false;

    public ToggleHealthCommand(Runnable toggleAction) {
        this.toggleAction = toggleAction;
    }

    @Override
    public void execute() {
        if (!executed) {
            executed = true;
            toggleAction.run();
        }
    }

    @Override
    public boolean isFinished() {
        return executed;
    }
}
