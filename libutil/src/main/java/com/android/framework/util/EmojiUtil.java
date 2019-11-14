package com.android.framework.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by meikai on 2019/11/14.
 */
public class EmojiUtil {

    //emoji的unicode编码范围
    private static final Pattern EMOJI_PATTERN = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\ud83e\\udc00-\\ud83e\\udfff]|[\\ud83f\\udc00-\\ud83f\\udfff]|[\\u2600-\\u27ff]|[\\u200D]|[\\u0020]|[\\u2642]|[\\u2640]|[\\uFE0F]|[\\u2300-\\u23FF]|[\\udb40\\udc00-\\udb40\\uddff]");

    /**
     * 删除字符串中的 emoji 字符
     *
     * @param source
     * @return
     */
    public static CharSequence filterEmoji(CharSequence source) {
        if (TextUtils.isEmpty(source)) {
            return "";
        }
        return EMOJI_PATTERN.matcher(source).replaceAll("");
    }

}
