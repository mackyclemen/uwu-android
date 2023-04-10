package com.mackyc.uwutranslator.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import com.mackyc.uwutranslator.R;

import java.util.Objects;


/**
 * Class used to put something into the clipboard.
 * Handles stuff and automatically sends toasts
 * to the user (hence the context.)
 */
public final class ClipboardHandler {

    public static final String ITEM_DESCRIPTION = "Plaintext";

    private final Context ctx;

    public ClipboardHandler(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Adds plain text to the clipboard.
     *
     * @param text    Text that will be added to the clipboard
     */
    public void addPlainText(String text) {

        // Gets the instance of the clipboard.
        ClipboardManager manager =
                (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);

        // Creates an object to add in the clipboard
        ClipData data = ClipData.newPlainText(ITEM_DESCRIPTION, text);

        if (manager != null) {
            // Copies argument sent in this method to the clipboard.
            manager.setPrimaryClip(data);
            // Notifies the user that the item is copied to the clipboard.
            Toast.makeText(
                    ctx,
                    ctx.getString(R.string.translate_clipboard_toast),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Grabs the recent plaintext from the clipboard.
     *
     * @return Plain text in clipboard; null otherwise.
     */
    public String getClipPlainText() {
        // Gets the instance of the clipboard.
        ClipboardManager manager =
                (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);

        if (manager != null && manager.hasPrimaryClip()) {
            // Stores the recent ClipData as a reference.
            ClipData.Item item = Objects.requireNonNull(manager.getPrimaryClip()).getItemAt(0);

            // This checks to see if the following ClipData is a plaintext.
            if (item.getText() != null) {

                // Returns the plaintext as a string.
                return item.getText().toString();
            }
        }

        // If all else failed, return null.
        return null;
    }
}
