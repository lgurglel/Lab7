package commands;

public abstract class AbstractCommand {
    private String name;
    private String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected AbstractCommand() {
    }


    public abstract boolean execute(String parameter, Object objectArgument);
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
