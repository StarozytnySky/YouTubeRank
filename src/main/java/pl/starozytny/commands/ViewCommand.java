package pl.starozytny.commands;


import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import pl.starozytny.file.ConfigFile;
import pl.starozytny.file.MessageFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ViewCommand extends SimpleSubCommand {

	protected ViewCommand() {
		super("view");
		setPermission("youtube.view");
		setPermissionMessage(MessageFile.Error.NO_PERMISSION);
	}

	public Collection<String> getUsersInGroup(String group) {
		NodeMatcher<InheritanceNode> matcher = NodeMatcher.key(InheritanceNode.builder(group).build());
		LuckPermsProvider.get().getUserManager().searchAll(matcher).thenComposeAsync(results -> {
			List<CompletableFuture<User>> users = new ArrayList<>();
			return CompletableFuture.allOf(
					results.keySet().stream()
							.map(uuid -> LuckPermsProvider.get().getUserManager().loadUser(uuid))
							.peek(users::add)
							.toArray(CompletableFuture[]::new)
			).thenApplyAsync(x -> users.stream()
					.map(CompletableFuture::join)
					.collect(Collectors.toList())
			);

		});
		return null;
	}

	@Override
	protected void onCommand() {

		List<String> AllowedRanks = new ArrayList<>(Arrays.asList("miniyt", "media", "media+"));

		if (args.length == 0) {
			Common.tell(sender, "....");
			return;
		}
		String group = args[0];

		if (PlayerUtil.hasPerm(sender, "youtube.view")) {
			if (ConfigFile.getInstance().ALLOWED_USERS.contains(sender.getName())) {
				//if (AllowedRanks.contains(args[0])) {
				Common.tell(sender, getUsersInGroup(group));
				return;
				//}
				//Common.tell(sender, MessageFile.Error.ONLY_SPECIFIC_RANK);
				//return;
			}
			Common.tell(sender, MessageFile.Error.YOU_ARE_NO_LIST);
		}
	}
}