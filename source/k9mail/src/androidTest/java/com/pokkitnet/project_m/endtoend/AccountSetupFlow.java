package com.pokkitnet.project_m.endtoend;

import com.pokkitnet.project_m.endtoend.framework.AccountForTest;
import com.pokkitnet.project_m.endtoend.framework.ApplicationState;
import com.pokkitnet.project_m.endtoend.framework.StubMailServer;
import com.pokkitnet.project_m.endtoend.framework.UserForImap;
import com.pokkitnet.project_m.endtoend.pages.AccountOptionsPage;
import com.pokkitnet.project_m.endtoend.pages.AccountSetupNamesPage;
import com.pokkitnet.project_m.endtoend.pages.AccountSetupPage;
import com.pokkitnet.project_m.endtoend.pages.AccountTypePage;
import com.pokkitnet.project_m.endtoend.pages.AccountsPage;
import com.pokkitnet.project_m.endtoend.pages.IncomingServerSettingsPage;
import com.pokkitnet.project_m.endtoend.pages.OutgoingServerSettingsPage;
import com.pokkitnet.project_m.endtoend.pages.WelcomeMessagePage;
import com.pokkitnet.project_m.mail.ConnectionSecurity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulated the steps required to set up a new mail account.
 */
public class AccountSetupFlow {

    static final String ACCOUNT_NAME = "sendAndReceiveTestName";

    public AccountsPage setupAccountFromWelcomePage(WelcomeMessagePage welcomeMessagePage) {
        AccountSetupPage accountSetupPage = welcomeMessagePage.clickNext();
        return setupAccountFromSetupNewAccountActivity(accountSetupPage);
    }

    public AccountsPage setupAccountFromAccountsPage(AccountsPage accountPage) {
        AccountSetupPage accountSetupPage = accountPage.clickAddNewAccount();
        return setupAccountFromSetupNewAccountActivity(accountSetupPage);
    }

    public AccountsPage setupAccountFromSetupNewAccountActivity(AccountSetupPage accountSetupPage) {
        AccountTypePage accountTypePage = fillInCredentialsAndClickManualSetup(accountSetupPage);

        IncomingServerSettingsPage incoming = accountTypePage.clickImap();


        StubMailServer stubMailServer = ApplicationState.getInstance().stubMailServer;

        OutgoingServerSettingsPage outgoing = setupIncomingServerAndClickNext(incoming, stubMailServer);

        AccountOptionsPage accountOptionsPage = setupOutgoingServerAndClickNext(outgoing, stubMailServer);

        AccountSetupNamesPage accountSetupNamesPage = accountOptionsPage.clickNext();

        String accountDescription = tempAccountName();
        accountSetupNamesPage.inputAccountDescription(accountDescription);
        accountSetupNamesPage.inputAccountName(ACCOUNT_NAME);

        AccountsPage accountsPage = accountSetupNamesPage.clickDone();

        accountsPage.assertAccountExists(accountDescription);

        ApplicationState.getInstance().accounts.add(new AccountForTest(ACCOUNT_NAME, accountDescription, stubMailServer));

        return accountsPage;
    }


    private String tempAccountName() {
        return "sendAndReceiveTest-" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
    }

    private AccountTypePage fillInCredentialsAndClickManualSetup(AccountSetupPage page) {
        return page
                .inputEmailAddress(UserForImap.TEST_USER.emailAddress)
                .inputPassword(UserForImap.TEST_USER.password)
                .clickManualSetup();
    }

    private AccountOptionsPage setupOutgoingServerAndClickNext(OutgoingServerSettingsPage page, StubMailServer stubMailServer) {
        return page
                .inputSmtpServer(stubMailServer.getSmtpBindAddress())
                .inputSmtpSecurity(ConnectionSecurity.NONE)
                .inputPort(stubMailServer.getSmtpPort())
                .inputRequireSignIn(false)
                .clickNext();
    }

    private OutgoingServerSettingsPage setupIncomingServerAndClickNext(IncomingServerSettingsPage page, StubMailServer stubMailServer) {
        return page
                .inputImapServer(stubMailServer.getImapBindAddress())
                .inputImapSecurity(ConnectionSecurity.NONE)
                .inputPort(stubMailServer.getImapPort())
                .inputUsername(UserForImap.TEST_USER.loginUsername)
                .clickNext();
    }
}
