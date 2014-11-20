package com.lecz.clubdelosvencedores;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Luis on 11/19/2014.
 */
public class CustomTextView extends TextView {

    private static final String sScheme =
            "http://schemas.android.com/apk/res-auto";
    private static final String sAttribute = "customFont";

    static enum CustomFont {
        OLD_WEST("fonts/Go 2 Old Western.ttf"),
        FUTURA("fonts/FuturaStd_Book.otf"),
        FUTURA_BOLD("fonts/FuturaStd_Bold.otf"),
        FUTURA_CBOLD("fonts/FuturaStd_CondensedBold.otf"),
        FUTURA_HEAVY("fonts/FuturaStd_Heavy.otf");

        private final String fileName;

        CustomFont(String fileName) {
            this.fileName = fileName;
        }

        static CustomFont fromString(String fontName) {
            return CustomFont.valueOf(fontName.toUpperCase(Locale.US));
        }

        public Typeface asTypeface(Context context) {
            return Typeface.createFromAsset(context.getAssets(), fileName);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        } else {
            final String fontName = attrs.getAttributeValue(sScheme, sAttribute);

            if (fontName == null) {
                throw new IllegalArgumentException("You must provide \"" + sAttribute + "\" for your text view");
            } else {
                final Typeface customTypeface = CustomFont.fromString(fontName).asTypeface(context);
                setTypeface(customTypeface);
            }
        }
    }
}