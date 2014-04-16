package it.xpug.hangman.domain;


import static java.util.Arrays.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class UserBaseTest {
	Random random = new Random(123);
	UserBase userBase = new UserBase(random);

	@Test
	public void returnsNextId() throws Exception {
		assertEquals("46de0e23", userBase.getNextUserId());
		assertEquals("3cbc0495", userBase.getNextUserId());
	}

	@Test
	public void authenticateUsers() throws Exception {
		userBase.add(new UserId("123"), "pippo", "secret");
		assertEquals("authenticates", true, userBase.contains(new UserId("123"), "secret"));
		assertEquals("wrong password", false, userBase.contains(new UserId("123"), "zot"));
		assertEquals("non-existing user", false, userBase.contains(new UserId("999"), "secret"));
	}

	@Test
	public void moreThanOneUser() throws Exception {
		userBase.add(new UserId("123"), "pippo", "secret");
		userBase.add(new UserId("456"), "pluto", "asdf");

		assertEquals("first user", true, userBase.contains(new UserId("123"), "secret"));
		assertEquals("second user", true, userBase.contains(new UserId("456"), "asdf"));
	}

	@Test
	public void findsUsers() throws Exception {
		userBase.add(new UserId("123"), "pippo", "secret");
		User expected = new User(new UserId("123"), "pippo", "secret");
		User actual = userBase.find(new UserId("123"), "secret");
		assertEquals(expected, actual);
	}

	@Test
	public void addPrisoners() throws Exception {
		UserId userId = new UserId("123");
		userBase.add(userId, "pippo", "secret");
		Prisoner prisoner = new Prisoner("zzz", "abc");
		userBase.addPrisoner(userId, prisoner);

		assertEquals(asList(prisoner), userBase.findPrisoners(userId));
		assertEquals(prisoner, userBase.findPrisoner(userId, "zzz"));
	}
}
