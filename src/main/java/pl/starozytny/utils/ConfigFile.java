package pl.starozytny.utils;

import lombok.Getter;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.settings.YamlSectionConfig;

import java.util.List;

public class ConfigFile extends YamlSectionConfig {

	/**
	 * The singleton of this class
	 */
	@Getter
	public final static ConfigFile instance = new ConfigFile();

	@Override
	protected boolean saveComments() {
		return true;
	}

	public List<String> ALLOWED_USERS;

	public String ADD_COMMAND_SERVER;
	public String ADD_COMMAND_BUNGEE;

	public String REMOVE_COMMAND_SERVER;
	public String REMOVE_COMMAND_BUNGEE;


	public ConfigFile() {
		super(null);

		this.loadConfiguration(this.getSettingsFileName());
	}


	/*
	 * Automatically load the data upon calling CustomDataStorage#getInstance()
	 */
	public String getSettingsFileName() {
		return "config.yml";
	}

	/**
	 * Automatically called when the file is loaded, you can
	 * pull your values from the file here.
	 */
	@Override
	protected void onLoadFinish() {

		ALLOWED_USERS = getStringList("Settings.ALLOWED_USERS");

		ADD_COMMAND_SERVER = getString("Settings.Commands.Add.SERVER");
		ADD_COMMAND_BUNGEE = getString("Settings.Commands.Add.BUNGEE");

		REMOVE_COMMAND_SERVER = getString("Settings.Commands.Remove.SERVER");
		REMOVE_COMMAND_BUNGEE = getString("Settings.Commands.Remove.BUNGEE");

	}

	/**
	 * Collect all data from this class that can be saved.
	 * Called automatically on save.
	 */
	@Override
	protected SerializedMap serialize() {
		return SerializedMap.ofArray();

	}

	/**
	 * An example of setting a value to the disk and saving immediatelly.
	 */
	public void setDemoValue(final List<String> words, final String word) {

		this.ALLOWED_USERS = words;

		this.ADD_COMMAND_SERVER = word;
		this.ADD_COMMAND_BUNGEE = word;

		this.REMOVE_COMMAND_SERVER = word;
		this.REMOVE_COMMAND_BUNGEE = word;

		this.save();
	}


}
