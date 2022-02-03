package pl.starozytny.commands;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleSubCommand;
import pl.starozytny.utils.ConfigFile;
import pl.starozytny.utils.MessageFile;

public class ReloadCommand extends SimpleSubCommand {

	protected ReloadCommand(CommandGroup commandGroup) {
		super("reload");
		setPermission("youtube.reload");
		setPermissionMessage(MessageFile.Error.NO_PERMISSION);
	}

	@Override
	protected void onCommand() {


		ConfigFile.getInstance().reload();

		Common.tell(sender, MessageFile.Reload.RELOADED);

	}
}
