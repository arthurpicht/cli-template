package de.arthurpicht.cliTemplate;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.InfoDefaultCommand;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.*;
import de.arthurpicht.cliTemplate.cli.executors.DemoExecutor;

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

        Options demoSpecificOptions = new Options().add(new OptionBuilder()
                .withLongName("dummy")
                .withShortName('d')
                .withDescription("a dummy specific option of type boolean")
                .build(OPTION_SPECIFIC_DUMMY));

        Commands commands = new Commands();

        commands.setDefaultCommand(new InfoDefaultCommand());

        commands.add(new CommandSequenceBuilder()
                .addCommands("demo")
                .withSpecificOptions(demoSpecificOptions)
                .withCommandExecutor(new DemoExecutor())
                .withDescription("Execute demo.")
                .build()
        );

        CliDescription cliDescription = new CliDescriptionBuilder()
                .withDescription("A CLI template \nhttps://github.com/arthurpicht/cli-template")
                .withVersionByTag("0.0.1-SNAPSHOT", "2022-07-20")
                .build("cli-template");

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withAutoHelp()
                .build(cliDescription);
    }

    public static void main(String[] args) {
//        try {
//            ApplicationInitialization.execute();
//        } catch (ApplicationInitializationException e) {
//            System.out.println(e.getMessage());
//            System.exit(1);
//        }

        Cli cli = createCli();
        CliCall cliCall = null;
        try {
            cliCall = cli.parse(args);
        } catch (UnrecognizedArgumentException e) {
            System.out.println(e.getExecutableName() + " call syntax error. " + e.getMessage());
            System.out.println(e.getCallString());
            System.out.println(e.getCallPointerString());
            System.exit(10);
        }

        boolean showStacktrace = cliCall.getOptionParserResultGlobal().hasOption(OPTION_GLOBAL_STACKTRACE);

        try {
            cli.execute(cliCall);
        } catch (CommandExecutorException e) {
            System.out.println(cliCall.getCliDefinition().getCliDescription().getExecutableName() + " execution failed.");
            if (e.getMessage() != null) System.out.println(e.getMessage());
            System.exit(1);
        } catch (RuntimeException e) {
            System.out.println("RuntimeException: " + e.getMessage());
            if (showStacktrace) e.printStackTrace();
            System.exit(100);
        } catch (AssertionError e) {
            System.out.println("AssertionError: " + e.getMessage());
            if (showStacktrace) e.printStackTrace();
            System.exit(110);
        }

    }


}
