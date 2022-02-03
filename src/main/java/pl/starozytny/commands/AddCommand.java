package pl.starozytny.commands;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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


	public boolean ExecuteCommand(CommandSender sender, UUID who) {
		switch (args[1]) {
			case "miniyt":
				isMiniyt(who).thenAcceptAsync(result -> {
					if (!result) {
						Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_SERVER.replace("{player}", args[0]).replace("{rank}", args[1]));
						Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_BUNGEE.replace("{player}", args[0]).replace("{rank}", args[1]));

						Common.tell(sender, MessageFile.Success.ADDED.replace("{rank}", args[1]).replace("{player}", args[0]));

					} else {
						Common.tell(sender, MessageFile.Error.HAVE_NOW_MINIYT);
					}
				});
				return false;
			case "media":
				isMedia(who).thenAcceptAsync(result -> {
					if (!result) {
						Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_SERVER.replace("{player}", args[0]).replace("{rank}", args[1]));
						Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_BUNGEE.replace("{player}", args[0]).replace("{rank}", args[1]));

						Common.tell(sender, MessageFile.Success.ADDED.replace("{rank}", args[1]).replace("{player}", args[0]));

					} else {
						Common.tell(sender, MessageFile.Error.HAVE_NOW_MEDIA);

					}

				});
				return false;
			case "media+":
				isMediaplus(who).thenAcceptAsync(result -> {
					if (!result) {
						Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_SERVER.replace("{player}", args[0]).replace("{rank}", args[1]));
						Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().ADD_COMMAND_BUNGEE.replace("{player}", args[0]).replace("{rank}", args[1]));

						Common.tell(sender, MessageFile.Success.ADDED.replace("{rank}", args[1]).replace("{player}", args[0]));

					} else {
						Common.tell(sender, MessageFile.Error.HAVE_NOW_MEDIA_PLUS);
					}

				});
				return false;
		}
		return false;
	}

	public CompletableFuture<Boolean> isMiniyt(UUID who) {
		return luckPerms.getUserManager().loadUser(who)
				.thenApplyAsync(user -> {
					Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
					return inheritedGroups.stream().anyMatch(g -> g.getName().equals("miniyt"));
				});
	}

	public CompletableFuture<Boolean> isMedia(UUID who) {
		return luckPerms.getUserManager().loadUser(who)
				.thenApplyAsync(user -> {
					Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
					return inheritedGroups.stream().anyMatch(g -> g.getName().equals("media"));
				});
	}

	public CompletableFuture<Boolean> isMediaplus(UUID who) {
		return luckPerms.getUserManager().loadUser(who)
				.thenApplyAsync(user -> {
					Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
					return inheritedGroups.stream().anyMatch(g -> g.getName().equals("media+"));
				});
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
						if (Objects.equals(args[1], "miniyt")) {
							if (ExecuteCommand(sender, UUID.fromString(String.valueOf(targetUUID)))) {
								return;
							}
						}
						if (Objects.equals(args[1], "media")) {

							if (ExecuteCommand(sender, UUID.fromString(String.valueOf(targetUUID)))) {
								return;
							}
						}
						if (Objects.equals(args[1], "media+")) {

							if (ExecuteCommand(sender, UUID.fromString(String.valueOf(targetUUID)))) {
								return;
							}
						}


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
