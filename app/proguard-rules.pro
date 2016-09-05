# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wangbin/Documents/android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings                     # 忽略警告，避免打包时某些警告出现
-optimizationpasses 5               # 指定代码的压缩级别
-dontusemixedcaseclassnames         # 是否使用大小写混合
-dontskipnonpubliclibraryclasses    # 是否混淆第三方jar
-dontpreverify                      # 混淆时是否做预校验
-verbose                            # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法


-dontwarn android.support.v4.**     #缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错。
-dontwarn android.os.**
-keep class android.support.v4.** { *; }        # 保持哪些类不被混淆
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class android.os.**{*;}
-keep class com.amap.api.** { *; }
-keep class com.autonavi.aps.amapapi.model.** { *; }
-keep class com.loc.** { *; }
-keep class com.fuxy.android.ide.plugin.** { *; }
-keep class com.baidu.location.** { *; }
-keep class org.apache.** { *; }
-keep class com.google.** { *; }
-keep class de.greenrobot.dao.** { *; }
-keep class com.android.** { *; }
-keep class com.leaf.library.** { *; }
-keep class scala.** { *; }
-keep class com.tandong.sa.** { *; }
-keep class com.tencent.** { *; }
-keep class com.umeng.** { *; }
-keep class com.sina.sso.** { *; }
-keep class com.testin.agent.** { *; }
-keep class com.facebook.rebound.** { *; }
-keep class com.zcw.togglebutton.** { *; }
-keep class u.aly.** { *; }
###排除所有注解类

-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }


###保留使用xUtils的方法和类，并且不要混淆名字
-keep @com.lidroid.xutils.db.annotation.Table class *
-keepclassmembers class * {
    @com.lidroid.xutils.db.annotation.* <fields>;
}
-keepclassmembers, allowobfuscation class * {
@com.lidroid.xutils.view.annotation.* <fields>;
    @com.lidroid.xutils.view.annotation.event.* <methods>;
}

-keep class io.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keepclasseswithmembers class *{
    public *;
}
-assumenosideeffects class android.util.Log {
public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}
-keep class com.beyonditsm.financial.entity.** { *; }
-keep public class RequestManager { *; }
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends android.support.v4.widget
-keep public class * extends com.sqlcrypt.database
-keep public class * extends com.sqlcrypt.database.sqlite
-keep public class * extends com.treecore.**
-keep public class * extends de.greenrobot.dao.**
-keep class * extends com.beyonditsm.financial.view
-keep interface * {
*;
}
##---------------Begin: proguard configuration for Gson ----------
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

-keep public class com.project.mocha_patient.login.SignResponseData { private *; }

##---------------End: proguard configuration for Gson ----------
-keepclasseswithmembernames class * {       # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity { #保持类成员
   public void *(android.view.View);
}

-keep class * implements android.os.Parcelable { *; } # 保持 Parcelable 不被混淆

-keep class de.greenrobot.event.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
# 保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*

# 避免混淆泛型，这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

#抛出异常时保留代码行号，在异常分析中可以方便定位
-keepattributes SourceFile,LineNumberTable

#友盟分享混淆
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class com.umeng.fb.** { *;}
-keep public class com.umeng.fb.ui.ThreadView {*; }
-keep class WXEntryActivity{*;}
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**
-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep public class com.beyonditsm.financial.R$*{
    public static final int *;
}
#友盟结束