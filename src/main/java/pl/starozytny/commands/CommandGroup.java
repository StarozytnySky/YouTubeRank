package pl.starozytny.commands;

import org.bukkit.command.CommandSender;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.SimpleComponent;

import java.util.ArrayList;
import java.util.List;

public class CommandGroup extends SimpleCommandGroup {

	@Override
	protected void registerSubcommands() {
		registerSubcommand(new AddCommand());
		registerSubcommand(new RemoveCommand());
		registerSubcommand(new ReloadCommand());
	}

	@Override
	protected boolean sendHelpIfNoArgs() {
		return false;
	}

	@Override
	protected final List<SimpleComponent> getNoParamsHeader(CommandSender sender) {
		final List<String> messages = new ArrayList<>();

		return Common.convert(messages, SimpleComponent::of);
	}
}
