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

	public CompletableFuture<Boolean> hasGroup(UUID who) {
		switch (args[1]) {
			case "miniyt":
				return luckPerms.getUserManager().loadUser(who)
						.thenApplyAsync(user -> {
							Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
							return inheritedGroups.stream().anyMatch(g -> g.getName().equals("miniyt"));
						});
			case "media":
				return luckPerms.getUserManager().loadUser(who)
						.thenApplyAsync(user -> {
							Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
							return inheritedGroups.stream().anyMatch(g -> g.getName().equals("media"));
						});
			case "media+":
				return luckPerms.getUserManager().loadUser(who)
						.thenApplyAsync(user -> {
							Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
							return inheritedGroups.stream().anyMatch(g -> g.getName().equals("media+"));
						});
		}
		return null;
	}


	public boolean ExecuteCommand(UUID who) {
		hasGroup(who).thenAcceptAsync(result -> {
			if (!result) {

				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_SERVER.replace("{player}", args[0]).replace("{rank}", args[1]));
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_BUNGEE.replace("{player}", args[0]).replace("{rank}", args[1]));

				Common.tell(sender, MessageFile.Success.ADDED.replace("{rank}", args[1]).replace("{player}", args[0]));

			} else
				Common.tell(sender, MessageFile.Error.HAVE_NOW_RANK.replace("{rank}", args[1]));

		});
		return false;
	}

	@Override
	protected void onCommand() {

		if (args.length == 0) {
			Common.tell(sender, MessageFile.Usage.ADD_RANK);
			return;
		}

		if (args.length == 1) {
			Common.tell(sender, MessageFile.Usage.MISSING_RANK);
			return;
		}

		if (args.length != 2) {
			Common.tell(sender, MessageFile.Error.TOO_MANY_ARGS);
			return;
		}

		List<String> AllowedRanks = new ArrayList<>(Arrays.asList("miniyt", "media", "media+"));


		if (PlayerUtil.hasPerm(sender, "youtube.add")) {
			if (ConfigFile.getInstance().ALLOWED_USERS.contains(sender.getName())) {
				if (args.length == 2) {
					Player target = Bukkit.getPlayer(args[0]);
					UUID targetUUID = target.getUniqueId();
					if (AllowedRanks.contains(args[1])) {
						ExecuteCommand(UUID.fromString(String.valueOf(targetUUID)));
						return;
					}
					Common.tell(sender, MessageFile.Error.ONLY_SPECIFIC_RANK);
					return;
				}
			}
			Common.tell(sender, MessageFile.Error.YOU_ARE_NO_LIST);
		}

	}
}
