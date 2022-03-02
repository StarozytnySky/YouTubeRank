package pl.starozytny;

import lombok.Getter;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;
import pl.starozytny.commands.CommandGroup;
import pl.starozytny.file.ConfigFile;
import pl.starozytny.file.MessageFile;
import pl.starozytny.file.SettingsFile;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class YouTubeRank extends SimplePlugin {

	private static YouTubeRank instance;

	/**
	 * @return the main instance class
	 */
	public static YouTubeRank getInstance() {
		return instance;
	}

	@Getter
	private final CommandGroup mainCommand = new CommandGroup();

	@Override
	public List<Class<? extends YamlStaticConfig>> getSettings() {
		return Arrays.asList(MessageFile.class, SettingsFile.class);
	}

	@Override
	protected void onPluginStart() {
		instance = this;

		ConfigFile.getInstance();

	}

	// This will check if player has specific group
	public CompletableFuture<Boolean> hasGroup(String group, UUID who, String nickname) {
		CompletableFuture<User> userFuture = LuckPermsProvider.get().getUserManager().loadUser(who, nickname);

		return userFuture.thenApplyAsync(user -> {
			Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());

			try {
				if (userFuture.get().getUsername() == null) {
					Common.broadcast("User no exist");

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			return inheritedGroups.stream().anyMatch(g -> g.getName().equals(group));
		});
	}
}
