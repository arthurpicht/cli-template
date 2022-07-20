package de.arthurpicht.cliTemplate.cli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;

public class DemoExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        System.out.println("Command 'demo' called.");

        boolean dummyOption = cliCall.getOptionParserResultSpecific().hasOption("dummy");
        System.out.println("Specific option 'dummy' set? " + dummyOption);


    }

}
