
package com.pokkitnet.project_m.mail.transport;

import android.util.Log;

import com.pokkitnet.project_m.mail.K9MailLib;
import com.pokkitnet.project_m.mail.Message;
import com.pokkitnet.project_m.mail.MessagingException;
import com.pokkitnet.project_m.mail.ServerSettings;
import com.pokkitnet.project_m.mail.Transport;
import com.pokkitnet.project_m.mail.store.StoreConfig;
import com.pokkitnet.project_m.mail.store.webdav.WebDavStore;

import java.util.Collections;

import static com.pokkitnet.project_m.mail.K9MailLib.LOG_TAG;

public class WebDavTransport extends Transport {

    /**
     * Decodes a WebDavTransport URI.
     *
     * <p>
     * <b>Note:</b> Everything related to sending messages via WebDAV is handled by
     * {@link WebDavStore}. So the transport URI is the same as the store URI.
     * </p>
     */
    public static ServerSettings decodeUri(String uri) {
        return WebDavStore.decodeUri(uri);
    }

    /**
     * Creates a WebDavTransport URI.
     *
     * <p>
     * <b>Note:</b> Everything related to sending messages via WebDAV is handled by
     * {@link WebDavStore}. So the transport URI is the same as the store URI.
     * </p>
     */
    public static String createUri(ServerSettings server) {
        return WebDavStore.createUri(server);
    }


    private WebDavStore store;

    public WebDavTransport(StoreConfig storeConfig) throws MessagingException {
        store = new WebDavStore(storeConfig);

        if (K9MailLib.isDebug())
            Log.d(LOG_TAG, ">>> New WebDavTransport creation complete");
    }

    @Override
    public void open() throws MessagingException {
        if (K9MailLib.isDebug())
            Log.d(LOG_TAG, ">>> open called on WebDavTransport ");

        store.getHttpClient();
    }

    @Override
    public void close() {
    }

    @Override
    public void sendMessage(Message message) throws MessagingException {
        store.sendMessages(Collections.singletonList(message));
    }
}
