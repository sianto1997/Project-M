package com.pokkitnet.project_m.mail.store;

import android.content.Context;
import android.net.ConnectivityManager;

import com.pokkitnet.project_m.mail.MessagingException;
import com.pokkitnet.project_m.mail.ServerSettings;
import com.pokkitnet.project_m.mail.ServerSettings.Type;
import com.pokkitnet.project_m.mail.Store;
import com.pokkitnet.project_m.mail.ssl.DefaultTrustedSocketFactory;
import com.pokkitnet.project_m.mail.ssl.TrustedSocketFactory;
import com.pokkitnet.project_m.mail.store.imap.ImapStore;
import com.pokkitnet.project_m.mail.store.pop3.Pop3Store;
import com.pokkitnet.project_m.mail.store.webdav.WebDavStore;

import java.util.HashMap;
import java.util.Map;

public abstract class RemoteStore extends Store {
    public static final int SOCKET_CONNECT_TIMEOUT = 30000;
    public static final int SOCKET_READ_TIMEOUT = 60000;

    protected StoreConfig mStoreConfig;
    protected TrustedSocketFactory mTrustedSocketFactory;

    /**
     * Remote stores indexed by Uri.
     */
    private static Map<String, Store> sStores = new HashMap<String, Store>();


    public RemoteStore(StoreConfig storeConfig, TrustedSocketFactory trustedSocketFactory) {
        mStoreConfig = storeConfig;
        mTrustedSocketFactory = trustedSocketFactory;
    }

    /**
     * Get an instance of a remote mail store.
     */
    public synchronized static Store getInstance(Context context,
                                                 StoreConfig storeConfig) throws MessagingException {
        String uri = storeConfig.getStoreUri();

        if (uri.startsWith("local")) {
            throw new RuntimeException("Asked to get non-local Store object but given LocalStore URI");
        }

        Store store = sStores.get(uri);
        if (store == null) {
            if (uri.startsWith("imap")) {
                store = new ImapStore(storeConfig,
                        new DefaultTrustedSocketFactory(context),
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            } else if (uri.startsWith("pop3")) {
                store = new Pop3Store(storeConfig,
                        new DefaultTrustedSocketFactory(context));
            } else if (uri.startsWith("webdav")) {
                store = new WebDavStore(storeConfig);
            }

            if (store != null) {
                sStores.put(uri, store);
            }
        }

        if (store == null) {
            throw new MessagingException("Unable to locate an applicable Store for " + uri);
        }

        return store;
    }

    /**
     * Release reference to a remote mail store instance.
     *
     * @param storeConfig {@link com.pokkitnet.project_m.mail.store.StoreConfig} instance that is used to get the remote mail store instance.
     */
    public static void removeInstance(StoreConfig storeConfig) {
        String uri = storeConfig.getStoreUri();
        if (uri.startsWith("local")) {
            throw new RuntimeException("Asked to get non-local Store object but given " +
                    "LocalStore URI");
        }
        sStores.remove(uri);
    }

    /**
     * Decodes the contents of store-specific URIs and puts them into a {@link com.pokkitnet.project_m.mail.ServerSettings}
     * object.
     *
     * @param uri
     *         the store-specific URI to decode
     *
     * @return A {@link com.pokkitnet.project_m.mail.ServerSettings} object holding the settings contained in the URI.
     *
     * @see com.pokkitnet.project_m.mail.store.imap.ImapStore#decodeUri(String)
     * @see com.pokkitnet.project_m.mail.store.pop3.Pop3Store#decodeUri(String)
     * @see com.pokkitnet.project_m.mail.store.webdav.WebDavStore#decodeUri(String)
     */
    public static ServerSettings decodeStoreUri(String uri) {
        if (uri.startsWith("imap")) {
            return ImapStore.decodeUri(uri);
        } else if (uri.startsWith("pop3")) {
            return Pop3Store.decodeUri(uri);
        } else if (uri.startsWith("webdav")) {
            return WebDavStore.decodeUri(uri);
        } else {
            throw new IllegalArgumentException("Not a valid store URI");
        }
    }

    /**
     * Creates a store URI from the information supplied in the {@link com.pokkitnet.project_m.mail.ServerSettings} object.
     *
     * @param server
     *         The {@link com.pokkitnet.project_m.mail.ServerSettings} object that holds the server settings.
     *
     * @return A store URI that holds the same information as the {@code server} parameter.
     *
     * @see com.pokkitnet.project_m.mail.store.imap.ImapStore#createUri(com.pokkitnet.project_m.mail.ServerSettings)
     * @see com.pokkitnet.project_m.mail.store.pop3.Pop3Store#createUri(com.pokkitnet.project_m.mail.ServerSettings)
     * @see com.pokkitnet.project_m.mail.store.webdav.WebDavStore#createUri(com.pokkitnet.project_m.mail.ServerSettings)
     */
    public static String createStoreUri(ServerSettings server) {
        if (Type.IMAP == server.type) {
            return ImapStore.createUri(server);
        } else if (Type.POP3 == server.type) {
            return Pop3Store.createUri(server);
        } else if (Type.WebDAV == server.type) {
            return WebDavStore.createUri(server);
        } else {
            throw new IllegalArgumentException("Not a valid store URI");
        }
    }
}
