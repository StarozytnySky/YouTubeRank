package pl.starozytny.commands;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import pl.starozytny.utils.ConfigFile;
import pl.starozytny.utils.MessageFile;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class AddCommand extends SimpleSubCommand {

	protected AddCommand(CommandGroup commandGroup) {
		super("add");
		setPermission("youtube.add");
		setPermissionMessage(MessageFile.Error.NO_PERMISSION);
	}

	LuckPerms luckPerms = LuckPermsProvider.get();

	public CompletableFuture<Boolean> hasGroup(String group, UUID who) {

		return luckPerms.getUserManager().loadUser(who)
				.thenApplyAsync(user -> {
					Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
					return inheritedGroups.stream().anyMatch(g -> g.getName().equals(group));
				});
	}

	public void manageRank(String group, UUID who, String nickName) {
		hasGroup(group, who).thenAcceptAsync(result -> {
			if (!result) {

				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_SERVER.replace("{player}", nickName).replace("{rank}", group));
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_BUNGEE.replace("{player}", nickName).replace("{rank}", group));

				Common.tell(sender, MessageFile.Success.ADDED.replace("{rank}", group).replace("{player}", nickName));

			} else
				Common.tell(sender, MessageFile.Error.HAVE_NOW_RANK.replace("{rank}", group));
		});
	}

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

		Player player = Bukkit.getPlayer(args[0]);
		UUID targetUUID = player.getUniqueId();
		String group = luckPerms.getGroupManager().getGroup(args[1]).getName();

		if (PlayerUtil.hasPerm(sender, "youtube.add")) {
			if (ConfigFile.getInstance().ALLOWED_USERS.contains(sender.getName())) {
				
				if (AllowedRanks.contains(args[1])) {
					manageRank(group, targetUUID, player.getName());
					return;
				}
				Common.tell(sender, MessageFile.Error.ONLY_SPECIFIC_RANK);
				return;
			}
			Common.tell(sender, MessageFile.Error.YOU_ARE_NO_LIST);
		}

	}
}