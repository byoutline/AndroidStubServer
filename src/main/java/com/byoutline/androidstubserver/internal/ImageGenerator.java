package com.byoutline.androidstubserver.internal;

import android.graphics.*;
import android.util.Log;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
public class ImageGenerator {
    private final static Pattern PATH_PAT = Pattern.compile("(\\d+)x(\\d+)(/[a-fA-F0-9]+)?(/[a-fA-F0-9]+)?");
    private final static String DEFAULT_BG_COLOR = "#222222";
    private final static String DEFAULT_TXT_COLOR = "#AAAAAA";

    public static InputStream generateImage(String path) {
        ImageInfo imageInfo = parsePath(path, new ColorParser(), new LogWrap());
        return generateImage(imageInfo);
    }

    public static ImageInfo parsePath(String path, ColorParser colorParser, LogWrap log) {
        if (path == null) {
            log.d("Empty image path not allowed");
            return null;
        }
        Matcher matcher = PATH_PAT.matcher(path);
        if (!matcher.find()) {
            log.d("Invalid image path: " + path);
            return null;
        }

        MatchResult matchResult = matcher.toMatchResult();
        int width = Integer.parseInt(matchResult.group(1));
        int height = Integer.parseInt(matchResult.group(2));
        String txt = width + "x" + height;

        String bgColor = matchResult.group(3);
        String txtColor = matchResult.group(4);
        int bgColorInt = getColorInt(bgColor, DEFAULT_BG_COLOR, colorParser);
        int txtColorInt = getColorInt(txtColor, DEFAULT_TXT_COLOR, colorParser);

        return new ImageInfo(width, height, bgColorInt, txt, txtColorInt);
    }

    private static int getColorInt(String color, String defVal, ColorParser colorParser) {
        color = color == null ? defVal : color;
        color = color.replaceFirst("/", "#");
        return colorParser.parseColor(color);
    }

    public static InputStream generateImage(ImageInfo info) {
        Bitmap dest = Bitmap.createBitmap(info.width, info.height, Bitmap.Config.RGB_565);
        Canvas cs = new Canvas(dest);
        drawBg(info, cs);
        drawTxt(info, cs);

        return convertToStream(dest);
    }

    private static InputStream convertToStream(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        return new ByteArrayInputStream(bitmapdata);
    }

    private static void drawTxt(ImageInfo info, Canvas cs) {
        Paint tPaint = new Paint();
        tPaint.setTextSize(info.height / 10);
        tPaint.setColor(info.txtColorInt);
        tPaint.setStyle(Paint.Style.FILL);

        Rect tBounds = new Rect();
        tPaint.getTextBounds(info.text, 0, info.text.length(), tBounds);
        float yTextStart = (info.height - tBounds.top) / 2;
        float xTextStart = (info.width - tBounds.right) / 2;
        cs.drawText(info.text, xTextStart, yTextStart, tPaint);
    }

    private static void drawBg(ImageInfo info, Canvas cs) {
        Paint bgPaint = new Paint();
        bgPaint.setColor(info.bgColorInt);
        bgPaint.setStyle(Paint.Style.FILL);

        cs.drawPaint(bgPaint);
    }
}

class ImageInfo {
    public final int width;
    public final int height;
    public final int bgColorInt;
    @Nonnull
    public final String text;
    public final int txtColorInt;

    public ImageInfo(int width, int height, int bgColorInt, @Nonnull String text, int txtColorInt) {
        this.width = width;
        this.height = height;
        this.bgColorInt = bgColorInt;
        this.text = text;
        this.txtColorInt = txtColorInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        if (width != imageInfo.width) return false;
        if (height != imageInfo.height) return false;
        if (bgColorInt != imageInfo.bgColorInt) return false;
        if (txtColorInt != imageInfo.txtColorInt) return false;
        return text.equals(imageInfo.text);

    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + bgColorInt;
        result = 31 * result + text.hashCode();
        result = 31 * result + txtColorInt;
        return result;
    }
}

class ColorParser {

    public int parseColor(String color) throws IllegalArgumentException {
        return Color.parseColor(color);
    }
}

class LogWrap {
    public static final String TAG = "AndroidStubServer";

    public void d(String msg) {
        Log.d(TAG, msg);
    }
}