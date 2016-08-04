package com.pokkitnet.project_m.mail.store.imap;

import com.pokkitnet.project_m.mail.AuthType;
import com.pokkitnet.project_m.mail.ConnectionSecurity;
import com.pokkitnet.project_m.mail.NetworkType;

/**
 * Settings source for IMAP. Implemented in order to remove coupling between {@link ImapStore} and {@link ImapConnection}.
 */
interface ImapSettings {
    String getHost();

    int getPort();

    ConnectionSecurity getConnectionSecurity();

    AuthType getAuthType();

    String getUsername();

    String getPassword();

    String getClientCertificateAlias();

    boolean useCompression(NetworkType type);

    String getPathPrefix();

    void setPathPrefix(String prefix);

    String getPathDelimiter();

    void setPathDelimiter(String delimiter);

    String getCombinedPrefix();

    void setCombinedPrefix(String prefix);
}
