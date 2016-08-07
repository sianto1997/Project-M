package com.pokkitnet.project_m.mail.internet;


import com.pokkitnet.project_m.mail.Body;


/**
 * See {@link MimeUtility#decodeBody(Body)}
 */
public interface RawDataBody extends Body {
    String getEncoding();
}
