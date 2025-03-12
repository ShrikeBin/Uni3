package eu.jpereira.trainings.designpatterns.behavioral.mediator.appliance.director;

import eu.jpereira.trainings.designpatterns.behavioral.mediator.command.Command;
import eu.jpereira.trainings.designpatterns.behavioral.mediator.command.CouldNotExecuteCommandException;
import eu.jpereira.trainings.designpatterns.behavioral.mediator.command.CouldNotRollbackCommandException;
import eu.jpereira.trainings.designpatterns.behavioral.mediator.command.UndoableCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * A default implementation of a CommandDirector.
 */
public class DefaulCommandDirector implements CommandDirector {

    // Fail Strategy. It will define how the Director will fail. Should it try to recover, ignore, fail-fast?
    protected FailStategy failStrategy = null;
    
    // The commands to be executed by this director
    protected List<Command> commands = null;
    
    // Stack maintaining the commands already executed. Used for rollback operations
    private Stack<Command> executedCommands;

    // Map to store exception types and their associated recovery commands
    private Map<Class<? extends Throwable>, List<Command>> exceptionCommandsMap;

    /**
     * Create a new DefaultCommandDirector.
     */
    public DefaulCommandDirector() {
        // Use DEFAULT. Will do a rollback after a first command execution failure
        this.failStrategy = FailStategy.DEFAULT;
        
        // Delegate instantiation to a factory method
        this.commands = createCommands();
        
        // Delegate instantiation to a factory method
        this.executedCommands = createExecutedStack();
        
        // Initialize the exception commands map
        this.exceptionCommandsMap = new HashMap<>();
    }

    /**
     * Set the fail strategy for the command director.
     * 
     * @param strategy The fail strategy to be applied.
     */
    @Override
    public void setFailStrategy(FailStategy strategy) {
        this.failStrategy = strategy;
    }

    /**
     * Run all commands in the director.
     * 
     * @throws ErrorDirectingCommandsException if any command fails during execution.
     */
    @Override
    public void run() throws ErrorDirectingCommandsException {
        for (Command command : this.commands) {
            try {
                // Push the command to the stack of executed commands
                executedCommands.push(command);
                
                // Execute the command
                command.execute();

            } catch (CouldNotExecuteCommandException e) {
                // Default strategy is to rollback
                if (this.failStrategy == FailStategy.DEFAULT) {
                    rollback();
                }

                // Log the exception (could also be replaced with logging framework)
                e.printStackTrace();

                // Attempt to handle the exception based on registered exception commands
                handleException(e);

                // Throw an exception with the stack trace
                throw new ErrorDirectingCommandsException(e.fillInStackTrace());
            }
        }
    }

    /**
     * Add a command or multiple commands to the list of commands to be executed.
     * 
     * @param command The command to add.
     * @param commands Additional commands to add.
     */
    @Override
    public void addCommand(Command command, Command... commands) {
        // Add the initial command to the list
        this.commands.add(command);
        
        // Add additional commands if provided
        if (commands != null && commands.length > 0) {
            this.commands.addAll(Arrays.asList(commands));
        }
    }

    /**
     * Rollback the command execution in reverse order.
     */
    private void rollback() {
        while (!executedCommands.isEmpty()) {
            // Pop the last executed command
            Command rollBackCommand = executedCommands.pop();
            
            if (rollBackCommand instanceof UndoableCommand) {
                try {
                    // Rollback the command if it is undoable
                    ((UndoableCommand) rollBackCommand).rollback();
                } catch (CouldNotRollbackCommandException e) {
                    // Ignore if rollback fails (can log it as needed)
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Factory method to create the stack of executed commands.
     * 
     * @return A new Stack of commands.
     */
    protected Stack<Command> createExecutedStack() {
        return new Stack<>();
    }

    /**
     * Factory method to create the list of commands.
     * 
     * @return A new list of commands.
     */
    protected List<Command> createCommands() {
        return new ArrayList<>();
    }

    /**
     * Add exception handling commands. This will allow associating specific commands to handle exceptions of a certain type.
     * 
     * @param exClass The exception class type.
     * @param commands The commands to execute when this exception is thrown.
     */
    public void addExceptionCommands(Class<? extends Throwable> exClass, Command... commands) {
        // If no commands are provided, do nothing
        if (commands == null || commands.length == 0) {
            return;
        }
        
        // Add the list of commands for the specific exception class
        List<Command> commandList = exceptionCommandsMap.getOrDefault(exClass, new ArrayList<>());
        commandList.addAll(Arrays.asList(commands));
        exceptionCommandsMap.put(exClass, commandList);
    }

    /**
     * Handle exceptions by attempting to execute recovery commands for the given exception type.
     * 
     * @param exception The exception that was thrown during command execution.
     */
    private void handleException(CouldNotExecuteCommandException exception) {
        List<Command> recoveryCommands = exceptionCommandsMap.get(exception.getClass());
        
        if (recoveryCommands != null && !recoveryCommands.isEmpty()) {
            // Execute recovery commands if they exist for this exception
            for (Command recoveryCommand : recoveryCommands) {
                try {
                    recoveryCommand.execute();
                } catch (CouldNotExecuteCommandException e) {
                    // Log recovery failure (could be extended with a better logging mechanism)
                    e.printStackTrace();
                }
            }
        } else {
            // If no recovery commands are found, handle accordingly
            System.out.println("No recovery commands available for exception: " + exception.getClass().getName());
        }
    }
}
