package it.xpug.hangman.domain;

import java.util.*;

public class UserBase {

	private List<User> users = new ArrayList<User>();
	private Map<UserId, List<Prisoner>> prisoners = new HashMap<UserId, List<Prisoner>>();
	private Random random;

	public UserBase(Random random) {
		this.random = random;
	}

	public UserBase() {
		this(new Random());
	}

	public void add(UserId userId, String name, String password) {
		this.users.add(new User(userId, name, password));
	}

	public boolean contains(UserId userId, String password) {
		return find(userId, password) != null;
	}

	public String getNextUserId() {
		return Long.toHexString((Math.abs(random.nextInt())));
	}

	public User find(UserId userId, String password) {
		for (User user : users) {
			if (user.authenticates(userId, password)) {
				return user;
			}
		}
		return null;
	}

	public void addPrisoner(UserId userId, Prisoner prisoner) {
		if (!prisoners.containsKey(userId)) {
			prisoners.put(userId, new ArrayList<Prisoner>());
		}
		prisoners.get(userId).add(prisoner);
	}

	public Collection<Prisoner> findPrisoners(UserId userId) {
		if (!prisoners.containsKey(userId)) {
			return Collections.emptyList();
		}
		return prisoners.get(userId);
	}

	public Prisoner findPrisoner(UserId userId, String prisonerId) {
		for (Prisoner prisoner : findPrisoners(userId)) {
			if (prisoner.getId().equals(prisonerId)) {
				return prisoner;
			}
		}
		return null;
	}

}
