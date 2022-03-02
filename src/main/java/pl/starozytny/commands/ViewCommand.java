package pl.starozytny.commands;

/*
public class ViewCommand extends SimpleSubCommand {

	protected ViewCommand() {
		super("view");
	}

	@Override
	protected void onCommand() {


		private CompletableFuture<List<User>> getUsersInGroup (String group){
			NodeMatcher<InheritanceNode> matcher = NodeMatcher.key(InheritanceNode.builder("media").build());
			LuckPermsProvider.get().getUserManager().searchAll(matcher).thenComposeAsync(results -> {
				List<CompletableFuture<User>> users = new ArrayList<>();

				return CompletableFuture.allOf(
						results.keySet().stream()
								.map(uuid -> LuckPermsProvider.get().getUserManager().loadUser(uuid))
								.peek(users::add)
								.toArray(CompletableFuture[]::new)
				).thenApply(x -> users.stream()
						.map(CompletableFuture::join)
						.collect(Collectors.toList())
				);
			});
		}
	}
}


 */