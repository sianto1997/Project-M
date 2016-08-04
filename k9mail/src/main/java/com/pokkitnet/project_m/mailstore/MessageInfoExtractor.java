package com.pokkitnet.project_m.mailstore;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.pokkitnet.project_m.mail.Message;
import com.pokkitnet.project_m.mail.MessagingException;
import com.pokkitnet.project_m.mail.Part;
import com.pokkitnet.project_m.mail.internet.MessageExtractor;
import com.pokkitnet.project_m.mail.internet.Viewable;


class MessageInfoExtractor {
    private final Context context;
    private final Message message;
    private List<Viewable> viewables;
    private List<Part> attachments;

    public MessageInfoExtractor(Context context, Message message) {
        this.context = context;
        this.message = message;
    }

    public String getMessageTextPreview() throws MessagingException {
        getViewablesIfNecessary();
        return MessagePreviewExtractor.extractPreview(context, viewables);
    }

    public int getAttachmentCount() throws MessagingException {
        getViewablesIfNecessary();
        return attachments.size();
    }

    private void getViewablesIfNecessary() throws MessagingException {
        if (viewables == null) {
            attachments = new ArrayList<Part>();
            viewables = MessageExtractor.getViewables(message, attachments);
        }
    }
}
