package com.pokkitnet.project_m.endtoend.framework;

/**
 * An account that was added by a test.
 */
public class AccountForTest {

    public final String name;
    public final String description;
    public final StubMailServer stubMailServer;

    public AccountForTest(String name, String description, StubMailServer stubMailServer) {
        this.name = name;
        this.description = description;
        this.stubMailServer = stubMailServer;
    }
}
