package pl.starozytny;

import lombok.Getter;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;
import pl.starozytny.commands.CommandGroup;
import pl.starozytny.utils.ConfigFile;
import pl.starozytny.utils.MessageFile;
import pl.starozytny.utils.SettingsFile;

import java.util.Arrays;
import java.util.List;

public class YouTubeRank extends SimplePlugin {

	@Getter
	private final CommandGroup mainCommand = new CommandGroup();

	@Override
	public List<Class<? extends YamlStaticConfig>> getSettings() {
		return Arrays.asList(MessageFile.class, SettingsFile.class);
	}

	@Override
	protected void onPluginStart() {

		ConfigFile.getInstance();

	}
}
