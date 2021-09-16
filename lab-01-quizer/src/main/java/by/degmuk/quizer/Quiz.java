package by.degmuk.quizer;

import by.degmuk.quizer.task_generators.TaskGenerator;
import by.degmuk.quizer.tasks.Task;

class Quiz {
    private final TaskGenerator generator;
    private int taskCount;
    private int taskOk = 0;
    private int taskWrong = 0;
    private int taskIncorrect = 0;
    private Task currentTask = null;

    public Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        if (taskCount <= 0) {
            throw new IllegalArgumentException();
        }
        this.taskCount = taskCount;
    }

    public Task nextTask() {
        if (isFinished()) {
            throw new RuntimeException("Trying get task of finished quiz");
        }
        currentTask = generator.generate();
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        if (currentTask == null) {
            throw new RuntimeException("Checking answer without calling " +
                    "nextTask()");
        }
        --taskCount;
        Result result = currentTask.validate(answer);
        switch (result) {
            case OK:
                ++taskOk;
                break;
            case WRONG:
                ++taskWrong;
                break;
            case INCORRECT_INPUT:
                ++taskIncorrect;
                break;
        }
        return result;
    }

    public boolean isFinished() {
        return taskCount == 0;
    }

    public int getCorrectAnswerNumber() {
        return taskOk;
    }

    public int getWrongAnswerNumber() {
        return taskWrong;
    }

    public int getIncorrectInputNumber() {
        return taskIncorrect;
    }

    public double getMark() {
        if (!isFinished()) {
            throw new RuntimeException("Trying get mark of unfinished quiz");
        }
        return 100.0 * (double) taskOk / (taskOk + taskWrong);
    }
}
