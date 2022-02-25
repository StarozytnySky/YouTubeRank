package pl.starozytny.commands;

import org.bukkit.Bukkit;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import pl.starozytny.YouTubeRank;
import pl.starozytny.file.ConfigFile;
import pl.starozytny.file.MessageFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddCommand extends SimpleSubCommand {

	protected AddCommand() {
		super("add");
		setPermission("youtube.add");
		setPermissionMessage(MessageFile.Error.NO_PERMISSION);
	}

	private void manageRank(String group, UUID who, String nickName) {

		YouTubeRank.getInstance().hasGroup(group, who, nickName).thenAcceptAsync(result -> {
			if (!result) {

				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_SERVER.replace("{player}", nickName).replace("{rank}", group));
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_BUNGEE.replace("{player}", nickName).replace("{rank}", group));
				Common.tell(sender, MessageFile.Success.ADDED.replace("{rank}", group).replace("{player}", nickName));

			} else
				Common.tell(sender, MessageFile.Error.HAVE_NOW_RANK.replace("{rank}", group));
		});
	}

	@Deprecated
	@Override
	protected void onCommand() {

		if (args.length != 2) {
			if (args.length == 0) {
				Common.tell(sender, MessageFile.Usage.ADD_RANK);
			} else if (args.length == 1) {
				Common.tell(sender, MessageFile.Usage.MISSING_RANK);
			} else
				Common.tell(sender, MessageFile.Error.TOO_MANY_ARGS);
			return;

		}

		List<String> AllowedRanks = new ArrayList<>(Arrays.asList("miniyt", "media", "media+"));

		String group = args[1];
		String playerName = args[0];
		UUID playerUUID = Bukkit.getOfflinePlayer(args[0]).getUniqueId();

		if (PlayerUtil.hasPerm(sender, "youtube.add")) {
			if (ConfigFile.getInstance().ALLOWED_USERS.contains(sender.getName())) {
				if (AllowedRanks.contains(args[1])) {
					manageRank(group, playerUUID, playerName);
					return;
				}
				Common.tell(sender, MessageFile.Error.ONLY_SPECIFIC_RANK);
				return;
			}
			Common.tell(sender, MessageFile.Error.YOU_ARE_NO_LIST);
		}

	}
}