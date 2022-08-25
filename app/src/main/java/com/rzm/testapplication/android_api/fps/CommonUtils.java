package com.rzm.testapplication.android_api.fps;

import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ArgusAPM Team
 */
public class CommonUtils {

    private static final String SUB_TAG = "CommonUtils";
    private static final String DEFAULT_IMEI = "360_DEFAULT_IMEI";
    private static String sImei = DEFAULT_IMEI;

    public static String getStack() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        new Throwable().printStackTrace(pw);
        String stacks = sw.toString();
        if (!TextUtils.isEmpty(stacks)) {
            String[] lines = stacks.split("\n\tat");
            StringBuilder sb = new StringBuilder();

            final int start = 4;
            final int end = Math.min(start + 10, lines.length);
            // 前4行没有意义，都是aop带来的额外开销，最多只取10行
            for (int i = start; i < end; i++) {
                sb.append(lines[i]).append("\n\tat");
            }
            return sb.toString();
        }

        return "";
    }
}