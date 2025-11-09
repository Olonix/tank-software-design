package ru.mipt.bit.platformer.command;

public interface Command {
    void execute();
    boolean isFinished();
}
