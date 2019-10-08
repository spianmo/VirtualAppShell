package com.lody.virtual.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;

public class BanNotificationProvider extends ContentProvider {
    public static final Uri AUTHORITY_URI = Uri.parse("content://sk.vpkg.provider.BanNotificationProvider");
    public static final Uri CONTENT_URI = AUTHORITY_URI;
    public static final String PARAM_KEY = "skBannedNotificationKey";
    public static final String PARAM_VALUE = "skBannedNotificationValue";
    private final String DB_NAME = "SK_Banned_Notification.db";
    private SharedPreferences mStore = null;

    public String getType(Uri uri) {
        return "";
    }

    public static Cursor query(Context context, String... strArr) {
        return context.getContentResolver().query(CONTENT_URI, strArr, null, null, null);
    }

    public static String getString(Context context, String str) {
        try {
            return getString(context, str, null);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String getString(Context r2, String r3, String r4) {
        /*
        r0 = 1;
        r0 = new java.lang.String[r0];
        r1 = 0;
        r0[r1] = r3;
        r2 = query(r2, r0);
        r3 = r2.moveToNext();
        if (r3 == 0) goto L_0x001a;
    L_0x0010:
        r3 = r2.getString(r1);
        r0 = android.text.TextUtils.isEmpty(r3);
        if (r0 == 0) goto L_0x001b;
    L_0x001a:
        r3 = r4;
    L_0x001b:
        r2.close();
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: sk.vpkg.provider.BanNotificationProvider.getString(android.content.Context, java.lang.String, java.lang.String):java.lang.String");
    }

    public static int getInt(Context context, String str, int i) {
        Cursor query = query(context, str);
        if (query.moveToNext()) {
            try {
                i = query.getInt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        query.close();
        return i;
    }

    public static Uri save(Context context, ContentValues contentValues) {
        return context.getContentResolver().insert(CONTENT_URI, contentValues);
    }

    public static Uri save(Context context, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(str, str2);
            return save(context, contentValues);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static Uri remove(Context context, String str) {
        try {
            return save(context, str, null);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public boolean onCreate() {
        try {
            if (getContext() != null) {
                this.mStore = getContext().getSharedPreferences("SK_Banned_Notification.db", 0);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int length = strArr == null ? 0 : strArr.length;
        str2 = null;
        if (length > 0) {
            String[] strArr3 = new String[length];
            for (int i = 0; i < length; i++) {
                strArr3[i] = getValue(strArr[i], null);
            }
            return createCursor(strArr, strArr3);
        }
        String queryParameter = uri.getQueryParameter(PARAM_KEY);
        if (!TextUtils.isEmpty(queryParameter)) {
            str2 = getValue(queryParameter, null);
        }
        return createSingleCursor(queryParameter, str2);
    }

    protected Cursor createSingleCursor(String str, String str2) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{str}, 1);
        if (!TextUtils.isEmpty(str2)) {
            matrixCursor.addRow(new Object[]{str2});
        }
        return matrixCursor;
    }

    protected Cursor createCursor(String[] strArr, String[] strArr2) {
        MatrixCursor matrixCursor = new MatrixCursor(strArr, 1);
        matrixCursor.addRow(strArr2);
        return matrixCursor;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        if (contentValues == null || contentValues.size() <= 0) {
            String queryParameter = uri.getQueryParameter(PARAM_KEY);
            String queryParameter2 = uri.getQueryParameter(PARAM_VALUE);
            if (!TextUtils.isEmpty(queryParameter)) {
                save(queryParameter, queryParameter2);
            }
        } else {
            save(contentValues);
        }
        return null;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        String queryParameter = str == null ? null : uri.getQueryParameter(PARAM_KEY);
        if (TextUtils.isEmpty(queryParameter)) {
            return 0;
        }
        remove(queryParameter);
        return 1;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        if (contentValues == null || contentValues.size() <= 0) {
            String queryParameter = uri.getQueryParameter(PARAM_KEY);
            String queryParameter2 = uri.getQueryParameter(PARAM_VALUE);
            if (TextUtils.isEmpty(queryParameter)) {
                return 0;
            }
            save(queryParameter, queryParameter2);
            return 1;
        }
        save(contentValues);
        return contentValues.size();
    }

    protected String getValue(String str, String str2) {
        SharedPreferences sharedPreferences = this.mStore;
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString(str, str2);
    }

    protected void save(ContentValues contentValues) {
        if (this.mStore != null) {
            Editor edit = this.mStore.edit();
            for (String str : contentValues.keySet()) {
                String asString = contentValues.getAsString(str);
                if (!TextUtils.isEmpty(str)) {
                    if (asString != null) {
                        edit.putString(str, asString);
                    } else {
                        edit.remove(str);
                    }
                }
            }
            edit.apply();
        }
    }

    protected void save(String str, String str2) {
        SharedPreferences sharedPreferences = this.mStore;
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            if (str2 == null) {
                edit.remove(str);
            } else if (!str2.equals("removeAll")) {
                edit.putString(str, str2);
            }
            edit.apply();
        }
    }

    protected void remove(String str) {
        SharedPreferences sharedPreferences = this.mStore;
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            edit.remove(str);
            edit.apply();
        }
    }
}
