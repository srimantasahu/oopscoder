interface Command {
    void execute();
}

class RegisterCommand implements Command {
    public void execute() {
        System.out.println("Registering user...");
    }
}

class CommandInvoker {
    public void run(Command command) {
        command.execute();
    }
}
