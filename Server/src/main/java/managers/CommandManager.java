package managers;

import commands.*;
import utils.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CommandManager {
    private ReadWriteLock collectionLocker = new ReentrantReadWriteLock();

    private List<AbstractCommand> commands = new ArrayList<>();
    private AbstractCommand helpCommand;
    private AbstractCommand infoCommand;
    private AbstractCommand showCommand;
    private AbstractCommand addCommand;
    private AbstractCommand updateCommand;
    private AbstractCommand removeByIdCommand;
    private AbstractCommand clearCommand;
    private AbstractCommand executeScriptCommand;
    private AbstractCommand exitCommand;
    private AbstractCommand removeGreaterCommand;
    private AbstractCommand reorderCommand;
    private AbstractCommand removeAllByPriceCommand;
    private AbstractCommand filterByPriceCommand;
    private AbstractCommand sortCommand;
    private AbstractCommand sumOfDiscountCommand;
    private AbstractCommand signUpCommand;
    private AbstractCommand signInCommand;
    private AbstractCommand logOutCommand;

    public CommandManager(AbstractCommand helpCommand,
                          AbstractCommand infoCommand,
                          AbstractCommand showCommand,
                          AbstractCommand addCommand,
                          AbstractCommand updateCommand,
                          AbstractCommand removeByIdCommand,
                          AbstractCommand clearCommand,
                          AbstractCommand executeScriptCommand,
                          AbstractCommand exitCommand,
                          AbstractCommand removeGreaterCommand,
                          AbstractCommand reorderCommand,
                          AbstractCommand removeAllByPriceCommand,
                          AbstractCommand filterByPriceCommand,
                          AbstractCommand sumOfDiscountCommand,
                          AbstractCommand sortCommand,
                          AbstractCommand signUpCommand,
                          AbstractCommand signInCommand,
                          AbstractCommand logOutCommand) {
        this.helpCommand = helpCommand;
        commands.add(helpCommand);
        this.infoCommand = infoCommand;
        commands.add(infoCommand);
        this.showCommand = showCommand;
        commands.add(showCommand);
        this.addCommand = addCommand;
        commands.add(addCommand);
        this.updateCommand = updateCommand;
        commands.add(updateCommand);
        this.removeByIdCommand = removeByIdCommand;
        commands.add(removeByIdCommand);
        this.clearCommand = clearCommand;
        commands.add(clearCommand);
        this.executeScriptCommand = executeScriptCommand;
        commands.add(executeScriptCommand);
        this.exitCommand = exitCommand;
        commands.add(exitCommand);
        this.removeGreaterCommand = removeGreaterCommand;
        commands.add(removeGreaterCommand);
        this.reorderCommand = reorderCommand;
        commands.add(reorderCommand);
        this.removeAllByPriceCommand = removeAllByPriceCommand;
        commands.add(removeAllByPriceCommand);
        this.filterByPriceCommand = filterByPriceCommand;
        commands.add(filterByPriceCommand);
        this.sumOfDiscountCommand = sumOfDiscountCommand;
        commands.add(sumOfDiscountCommand);
        this.sortCommand = sortCommand;
        commands.add(sortCommand);
        this.signUpCommand = signUpCommand;
        this.signInCommand = signInCommand;
        commands.add(new AbstractCommand("log_in", "авторизоваться") {
            @Override
            public boolean execute(String argument, Object objectArgument, User user) {
                return false;
            }
        });
        this.logOutCommand = logOutCommand;
        commands.add(logOutCommand);
    }

    public boolean help(String argument, Object objectArgument, User user) {
        if (helpCommand.execute(argument, objectArgument, user)) {
            for (AbstractCommand command : commands) {
                ResponseOutputer.appendTable(command.getName(), command.getDescription());
            }
            return true;
        } else return false;
    }

    public boolean info(String argument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return infoCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.readLock().unlock();
        }
    }

    public boolean show(String argument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return showCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.readLock().unlock();
        }
    }

    public boolean add(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return addCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean update(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return updateCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean removeById(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return removeByIdCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean clear(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return clearCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean removeGreater(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return removeGreaterCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean removeAllByPrice(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return removeAllByPriceCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean reorder(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return reorderCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean sort(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return sortCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean filterByPrice(String argument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return filterByPriceCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean sumOfDiscount(String argument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return sumOfDiscountCommand.execute(argument, objectArgument, user);
        }finally {
            collectionLocker.readLock().unlock();
        }
    }

    public boolean exit(String argument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
        return exitCommand.execute(argument, objectArgument, user);
        }finally {
            collectionLocker.readLock().unlock();
        }
    }

    public boolean executeScript(String argument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
        return executeScriptCommand.execute(argument, objectArgument, user);
        } finally {
            collectionLocker.readLock().unlock();
        }
    }

    public boolean sign_up(String argument, Object objectArgument, User user) {
        return signUpCommand.execute(argument, objectArgument, user);
    }

    public boolean sign_in(String argument, Object objectArgument, User user) {
        return signInCommand.execute(argument, objectArgument, user);
    }

    public boolean log_out(String argument, Object objectArgument, User user) {
        return logOutCommand.execute(argument, objectArgument, user);
    }
}