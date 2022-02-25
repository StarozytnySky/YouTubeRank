package pl.starozytny.commands;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleSubCommand;
import pl.starozytny.file.ConfigFile;
import pl.starozytny.file.MessageFile;

public class ReloadCommand extends SimpleSubCommand {

	protected ReloadCommand() {
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
