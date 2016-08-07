package com.pokkitnet.project_m.mailstore;

import com.pokkitnet.project_m.mail.Part;

import java.util.List;

/**
 * Store viewable text of a message as plain text and HTML, and the parts considered
 * attachments.
 *
 * @see LocalMessageExtractor#extractTextAndAttachments(android.content.Context, com.pokkitnet.project_m.mail.Message)
 */
public class ViewableContainer {
    /**
     * The viewable text of the message in plain text.
     */
    public final String text;

    /**
     * The viewable text of the message in HTML.
     */
    public final String html;

    /**
     * The parts of the message considered attachments (everything not viewable).
     */
    public final List<Part> attachments;

    public ViewableContainer(String text, String html, List<Part> attachments) {
        this.text = text;
        this.html = html;
        this.attachments = attachments;
    }
}
