/*
 * Copyright (C) 2021 AOSP-Krypton Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.krypton.updater.util;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.krypton.updater.util.Constants.MB;

import android.os.Environment;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public final class Utils {
    private static final String TAG = "UpdaterUtils";
    private static final String PROP_DEVICE = "ro.krypton.build.device";
    private static final String PROP_VERSION = "ro.krypton.build.version";
    private static final String PROP_DATE = "ro.build.date.utc";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

    public static String getDevice() {
        return SystemProperties.get(PROP_DEVICE, "unavailable");
    }

    public static String getVersion() {
        return SystemProperties.get(PROP_VERSION, "unavailable");
    }

    public static long getBuildDate() {
        return Long.parseLong(SystemProperties.get(PROP_DATE, "0")) * 1000; // Convert to millis here
    }

    public static String formatDate(long date) {
        return dateFormat.format(new Date(date));
    }

    public static void setVisibile(boolean visible, View... views) {
        for (View v: views) {
            v.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    public static File getDownloadFile(String fileName) {
        return new File(Environment.getExternalStoragePublicDirectory(
            DIRECTORY_DOWNLOADS), fileName);
    }

    public static String computeMd5(File file) {
        if (file == null) {
            return null;
        }
        try (FileInputStream inStream = new FileInputStream(file)) {
            final MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            final byte[] buffer = new byte[MB]; // Files processed will be of GB order usually, so 1MB buffer will speed up the process
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                md5Digest.update(buffer, 0, bytesRead);
            }
            StringBuilder builder = new StringBuilder();
            for (byte b: md5Digest.digest()) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException|IOException e) {
            Log.e(TAG, "Error when computing md5 of file " + file.getAbsolutePath(), e);
        }
        return null;
    }

    public static String parseRawContent(URL url) {
        if (url == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        try (Scanner scanner = new Scanner(url.openStream())) {
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
                builder.append("\n");
            }
            return builder.toString();
        } catch(IOException e) {
            Log.e(TAG, "IOException when parsing content from url " + url.toString(), e);
        }
        return null;
    }
}
