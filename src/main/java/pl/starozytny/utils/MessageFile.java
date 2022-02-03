package pl.starozytny.utils;

import org.mineacademy.fo.settings.SimpleLocalization;

public class MessageFile extends SimpleLocalization {

	@Override
	protected int getConfigVersion() {
		return 1;
	}

	// --------------------------------------------------------------------------------------------------------
	// The actual implementation
	// --------------------------------------------------------------------------------------------------------

	public static class Reload {
		public static String RELOADED;


		private static void init() {

			pathPrefix("Reload");
			RELOADED = getString("RELOADED");
		}
	}

	public static class Error {
		public static String NO_PERMISSION;
		public static String ONLY_SPECIFIC_RANK;
		public static String YOU_ARE_NO_LIST;
		public static String TOO_MANY_ARGS;

		public static String NO_MINIYT;
		public static String NO_MEDIA;
		public static String NO_MEDIA_PLUS;

		public static String HAVE_NOW_MINIYT;
		public static String HAVE_NOW_MEDIA;
		public static String HAVE_NOW_MEDIA_PLUS;

		private static void init() {

			pathPrefix("Error");
			NO_PERMISSION = getString("NO_PERMISSION");
			ONLY_SPECIFIC_RANK = getString("ONLY_SPECIFIC_RANK");
			YOU_ARE_NO_LIST = getString("YOU_ARE_NO_LIST");
			TOO_MANY_ARGS = getString("TOO_MANY_ARGS");

			NO_MINIYT = getString("NO_MINIYT");
			NO_MEDIA = getString("NO_MEDIA");
			NO_MEDIA_PLUS = getString("NO_MEDIA+");

			HAVE_NOW_MINIYT = getString("HAVE_NOW_MINIYT");
			HAVE_NOW_MEDIA = getString("HAVE_NOW_MEDIA");
			HAVE_NOW_MEDIA_PLUS = getString("HAVE_NOW_MEDIA+");

		}
	}

	public static class Usage {
		public static String ADD_RANK;
		public static String REMOVE_RANK;
		public static String MISSING_RANK;


		private static void init() {

			pathPrefix("Usage");
			ADD_RANK = getString("ADD_RANK");
			REMOVE_RANK = getString("REMOVE_RANK");
			MISSING_RANK = getString("MISSING_RANK");

		}
	}

	public static class Success {
		public static String ADDED;
		public static String REMOVED;


		private static void init() {

			pathPrefix("Success");
			ADDED = getString("ADDED");
			REMOVED = getString("REMOVED");

		}
	}
}
