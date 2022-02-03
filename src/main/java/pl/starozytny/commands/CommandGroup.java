package pl.starozytny.commands;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class CommandGroup extends SimpleCommandGroup {

	@Override
	protected void registerSubcommands() {
		registerSubcommand(new AddCommand(this));
		registerSubcommand(new RemoveCommand(this));
		registerSubcommand(new ReloadCommand(this));
	}

	@Override
	protected boolean sendHelpIfNoArgs() {
		return true;
	}
}
