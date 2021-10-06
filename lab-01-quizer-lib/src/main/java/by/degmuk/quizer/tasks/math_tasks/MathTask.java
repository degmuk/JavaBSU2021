package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.tasks.Task;

public interface MathTask extends Task {
    enum Operator {
        ADD {
            public String getStringRepresentation() {
                return "+";
            }

            public double perform(double x, double y) {
                return x + y;
            }
        },
        SUB {
            public String getStringRepresentation() {
                return "-";
            }

            public double perform(double x, double y) {
                return x - y;
            }
        },
        MUL {
            public String getStringRepresentation() {
                return "*";
            }

            public double perform(double x, double y) {
                return x * y;
            }
        },
        DIV {
            public String getStringRepresentation() {
                return "/";
            }

            public double perform(double x, double y) {
                return x / y;
            }
        };

        public abstract String getStringRepresentation();
        public abstract double perform(double x, double y);
    }

    interface Generator extends Task.Generator {
        @Override
        MathTask generate();

        double getMinNumber();
        double getMaxNumber();

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
