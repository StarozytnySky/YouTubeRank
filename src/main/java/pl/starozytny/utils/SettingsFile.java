package pl.starozytny.utils;

import org.mineacademy.fo.settings.SimpleSettings;

public class SettingsFile extends SimpleSettings {

	@Override
	protected boolean saveComments() {
		return true;
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
