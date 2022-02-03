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

public class RemoveCommand extends SimpleSubCommand {

	protected RemoveCommand(CommandGroup commandGroup) {
		super("remove");
		setPermission("youtube.remove");
		setPermissionMessage(MessageFile.Error.NO_PERMISSION);
	}

	LuckPerms luckPerms = LuckPermsProvider.get();


	public CompletableFuture<Boolean> isMiniyt(UUID who) {
		return luckPerms.getUserManager().loadUser(who)
				.thenApplyAsync(user -> {
					Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
					return inheritedGroups.stream().anyMatch(g -> g.getName().equals("miniyt"));
				});
	}

	public boolean informIfMiniyt(CommandSender sender, UUID who) {
		isMiniyt(who).thenAcceptAsync(result -> {
			if (result) {
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().REMOVE_COMMAND_SERVER.replace("{player}", args[0]).replace("{rank}", args[1]));
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().REMOVE_COMMAND_BUNGEE.replace("{player}", args[0]).replace("{rank}", args[1]));

				Common.tell(sender, MessageFile.Success.REMOVED.replace("{rank}", args[1]).replace("{player}", args[0]));

			} else {
				Common.tell(sender, MessageFile.Error.NO_MINIYT);
			}
		});
		return false;
	}

	public CompletableFuture<Boolean> isMedia(UUID who) {
		return luckPerms.getUserManager().loadUser(who)
				.thenApplyAsync(user -> {
					Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
					return inheritedGroups.stream().anyMatch(g -> g.getName().equals("media"));
				});
	}

	public boolean informIfMedia(CommandSender sender, UUID who) {
		isMedia(who).thenAcceptAsync(result -> {
			if (result) {
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().REMOVE_COMMAND_SERVER.replace("{player}", args[0]).replace("{rank}", args[1]));
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().REMOVE_COMMAND_BUNGEE.replace("{player}", args[0]).replace("{rank}", args[1]));

				Common.tell(sender, MessageFile.Success.REMOVED.replace("{rank}", args[1]).replace("{player}", args[0]));

			} else {
				Common.tell(sender, MessageFile.Error.NO_MEDIA);

			}

		});
		return false;
	}

	public CompletableFuture<Boolean> isMediaplus(UUID who) {
		return luckPerms.getUserManager().loadUser(who)
				.thenApplyAsync(user -> {
					Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
					return inheritedGroups.stream().anyMatch(g -> g.getName().equals("media+"));
				});
	}

	public boolean informIfMediaplus(CommandSender sender, UUID who) {
		isMediaplus(who).thenAcceptAsync(result -> {
			if (result) {
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().REMOVE_COMMAND_SERVER.replace("{player}", args[0]).replace("{rank}", args[1]));
				Common.dispatchCommand(Bukkit.getConsoleSender(), ConfigFile.getInstance().REMOVE_COMMAND_BUNGEE.replace("{player}", args[0]).replace("{rank}", args[1]));

				Common.tell(sender, MessageFile.Success.REMOVED.replace("{rank}", args[1]).replace("{player}", args[0]));

			} else {
				Common.tell(sender, MessageFile.Error.NO_MEDIA_PLUS);
			}

		});
		return false;
	}

	@Override
	protected void onCommand() {

		if (args.length == 0) {
			Common.tell(sender, MessageFile.Usage.REMOVE_RANK);
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


		if (PlayerUtil.hasPerm(sender, "youtube.remove")) {
			if (ConfigFile.getInstance().ALLOWED_USERS.contains(sender.getName())) {
				if (args.length == 2) {
					Player target = Bukkit.getPlayer(args[0]);
					UUID targetUUID = target.getUniqueId();
					if (AllowedRanks.contains(args[1])) {
						if (Objects.equals(args[1], "miniyt")) {
							if (informIfMiniyt(sender, UUID.fromString(String.valueOf(targetUUID)))) {
								return;
							}
						}
						if (Objects.equals(args[1], "media")) {

							if (informIfMedia(sender, UUID.fromString(String.valueOf(targetUUID)))) {
								return;
							}
						}
						if (Objects.equals(args[1], "media+")) {

							if (informIfMediaplus(sender, UUID.fromString(String.valueOf(targetUUID)))) {
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
