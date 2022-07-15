package de.arthurpicht.cliTemplate;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.InfoDefaultCommand;
import de.arthurpicht.cli.option.*;

public class CliTemplate {

    public static final String OPTION_GLOBAL_VERBOSE = "verbose";
    public static final String OPTION_GLOBAL_STACKTRACE = "stacktrace";
    public static final String OPTION_GLOBAL_SILENT = "silent";
    public static final String OPTION_SPECIFIC_DUMMY = "dummy";

    private static Cli createCli() {

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new ManOption())
                .add(new OptionBuilder().withLongName("verbose").withDescription("verbose output").build(OPTION_GLOBAL_VERBOSE))
                .add(new OptionBuilder().withShortName('s').withLongName("stacktrace").withDescription("Show stacktrace when running on error.").build(OPTION_GLOBAL_STACKTRACE))
                .add(new OptionBuilder().withLongName("silent").withDescription("Make no output to console.").build(OPTION_GLOBAL_SILENT));

        Option dummyOption = new OptionBuilder()
                .withLongName("dummy")
                .withShortName('d')
                .withDescription("a dummy specific option")
                .build(OPTION_SPECIFIC_DUMMY);

        Commands commands = new Commands();

        commands.setDefaultCommand(new InfoDefaultCommand());

        commands.add(new CommandSequenceBuilder()
                .addCommands("status")
                .withSpecificOptions(specificOptionsStatus)
                .withCommandExecutor(new StatusExecutor())
                .withDescription("Show status.")
                .build()
        );


    }

}
