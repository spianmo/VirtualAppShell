package com.lody.virtual.utils;

import android.content.Context;
import com.lody.virtual.client.core.VirtualCore;

public class RenameAppUtils {
    public static void undoRenameByUid(String str, int i) {
        String str2 = "uSetName";
        try {
            Context context = VirtualCore.get().getContext();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(i);
            stringBuilder.append(str2);
            if (BanNotificationProvider.getString(context, stringBuilder.toString()) != null) {
                context = VirtualCore.get().getContext();
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(i);
                stringBuilder.append(str2);
                BanNotificationProvider.remove(context, stringBuilder.toString());
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("permission_tips_");
            stringBuilder2.append(str.replaceAll("\\.", "_"));
            str = stringBuilder2.toString();
            if (i == 0 && BanNotificationProvider.getString(VirtualCore.get().getContext(), str) != null) {
                BanNotificationProvider.remove(VirtualCore.get().getContext(), str);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void undoRenameByUid(String str, String str2) {
        String str3 = "uSetName";
        try {
            Context context = VirtualCore.get().getContext();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str2);
            stringBuilder.append(str3);
            if (BanNotificationProvider.getString(context, stringBuilder.toString()) != null) {
                context = VirtualCore.get().getContext();
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(str2);
                stringBuilder.append(str3);
                BanNotificationProvider.remove(context, stringBuilder.toString());
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static String getRenamedApp(String str, int i, String str2) {
        Context context = VirtualCore.get().getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(i);
        stringBuilder.append("uSetName");
        str = BanNotificationProvider.getString(context, stringBuilder.toString());
        return str != null ? str : str2;
    }

    public static String getRenamedApp(String str, int i) {
        Context context = VirtualCore.get().getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(i);
        stringBuilder.append("uSetName");
        str = BanNotificationProvider.getString(context, stringBuilder.toString());
        return str != null ? str : null;
    }

    public static String getRenamedApp(String str, String str2, String str3) {
        Context context = VirtualCore.get().getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(str2);
        stringBuilder.append("uSetName");
        str = BanNotificationProvider.getString(context, stringBuilder.toString());
        return str != null ? str : str3;
    }

    public static String getRenamedApp(String str, String str2) {
        Context context = VirtualCore.get().getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(str2);
        stringBuilder.append("uSetName");
        str = BanNotificationProvider.getString(context, stringBuilder.toString());
        return str != null ? str : null;
    }
}
