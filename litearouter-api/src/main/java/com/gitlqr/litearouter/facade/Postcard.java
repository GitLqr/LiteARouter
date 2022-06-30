package com.gitlqr.litearouter.facade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.gitlqr.litearouter.facade.callback.NavigationCallback;
import com.gitlqr.litearouter.facade.model.RouteMeta;
import com.gitlqr.litearouter.launcher.LiteARouter;

import java.io.Serializable;
import java.util.ArrayList;

public class Postcard extends RouteMeta {
    // Base
    private Uri uri;
    private Object tag;             // A tag prepare for some thing wrong. inner params, DO NOT USE!
    private Bundle mBundle;         // Data to transform
    private int flags = 0;         // Flags of route
    private int timeout = 300;      // Navigation timeout, TimeUnit.Second
    private Context context;        // May application or activity, check instance type before use it.
    private String action;

    // Animation
    private Bundle optionsCompat;    // The transition animation of activity
    private int enterAnim = -1;
    private int exitAnim = -1;

    public Bundle getOptionsBundle() {
        return optionsCompat;
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public Postcard() {
        this(null, null);
    }

    public Postcard(String path, String group) {
        this(path, group, null, null);
    }

    public Postcard(String path, String group, Uri uri, Bundle bundle) {
        setPath(path);
        setGroup(group);
        setUri(uri);
        this.mBundle = (null == bundle ? new Bundle() : bundle);
    }

    public Object getTag() {
        return tag;
    }

    public Postcard setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public Bundle getExtras() {
        return mBundle;
    }

    public int getTimeout() {
        return timeout;
    }

    /**
     * Set timeout of navigation this time.
     *
     * @param timeout timeout
     * @return this
     */
    public Postcard setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public Uri getUri() {
        return uri;
    }

    public Postcard setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Navigation to the route with path in postcard.
     * No param, will be use application context.
     */
    public Object navigation() {
        return navigation(null);
    }

    /**
     * Navigation to the route with path in postcard.
     *
     * @param context Activity and so on.
     */
    public Object navigation(Context context) {
        return navigation(context, null);
    }

    /**
     * Navigation to the route with path in postcard.
     *
     * @param context Activity and so on.
     */
    public Object navigation(Context context, NavigationCallback callback) {
        return LiteARouter.getInstance().navigation(context, this, -1, callback);
    }

    /**
     * Navigation to the route with path in postcard.
     *
     * @param mContext    Activity and so on.
     * @param requestCode startActivityForResult's param
     */
    public void navigation(Activity mContext, int requestCode) {
        navigation(mContext, requestCode, null);
    }

    /**
     * Navigation to the route with path in postcard.
     *
     * @param mContext    Activity and so on.
     * @param requestCode startActivityForResult's param
     */
    public void navigation(Activity mContext, int requestCode, NavigationCallback callback) {
        LiteARouter.getInstance().navigation(mContext, this, requestCode, callback);
    }

    /**
     * BE ATTENTION TO THIS METHOD WAS <P>SET, NOT ADD!</P>
     */
    public Postcard with(Bundle bundle) {
        if (null != bundle) {
            mBundle = bundle;
        }

        return this;
    }

    /**
     * Set special flags controlling how this intent is handled.  Most values
     * here depend on the type of component being executed by the Intent,
     * specifically the FLAG_ACTIVITY_* flags are all for use with
     * {@link Context#startActivity Context.startActivity()} and the
     * FLAG_RECEIVER_* flags are all for use with
     * {@link Context#sendBroadcast(Intent) Context.sendBroadcast()}.
     */
    public Postcard withFlags(int flag) {
        this.flags = flag;
        return this;
    }

    /**
     * Add additional flags to the intent (or with existing flags
     * value).
     *
     * @param flags The new flags to set.
     * @return Returns the same Intent object, for chaining multiple calls
     * into a single statement.
     * @see #withFlags
     */
    public Postcard addFlags(int flags) {
        this.flags |= flags;
        return this;
    }

    public int getFlags() {
        return flags;
    }

    /**
     * Set object value, the value will be convert to string by 'Fastjson'
     *
     * @param key   a String, or null
     * @param value a Object, or null
     * @return current
     */
    public Postcard withObject(String key, Object value) {
        mBundle.putString(key, JSON.toJSONString(value));
        return this;
    }

    // Follow api copy from #{Bundle}

    /**
     * Inserts a String value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a String, or null
     * @return current
     */
    public Postcard withString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    /**
     * Inserts a Boolean value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a boolean
     * @return current
     */
    public Postcard withBoolean(String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    /**
     * Inserts a short value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a short
     * @return current
     */
    public Postcard withShort(String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    /**
     * Inserts an int value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value an int
     * @return current
     */
    public Postcard withInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    /**
     * Inserts a long value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a long
     * @return current
     */
    public Postcard withLong(String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    /**
     * Inserts a double value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a double
     * @return current
     */
    public Postcard withDouble(String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    /**
     * Inserts a byte value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a byte
     * @return current
     */
    public Postcard withByte(String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }

    /**
     * Inserts a char value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a char
     * @return current
     */
    public Postcard withChar(String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    /**
     * Inserts a float value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a float
     * @return current
     */
    public Postcard withFloat(String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    /**
     * Inserts a CharSequence value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence, or null
     * @return current
     */
    public Postcard withCharSequence(String key, CharSequence value) {
        mBundle.putCharSequence(key, value);
        return this;
    }

    /**
     * Inserts a Parcelable value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Parcelable object, or null
     * @return current
     */
    public Postcard withParcelable(String key, Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    /**
     * Inserts an array of Parcelable values into the mapping of this Bundle,
     * replacing any existing value for the given key.  Either key or value may
     * be null.
     *
     * @param key   a String, or null
     * @param value an array of Parcelable objects, or null
     * @return current
     */
    public Postcard withParcelableArray(String key, Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
        return this;
    }

    /**
     * Inserts a List of Parcelable values into the mapping of this Bundle,
     * replacing any existing value for the given key.  Either key or value may
     * be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList of Parcelable objects, or null
     * @return current
     */
    public Postcard withParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }

    /**
     * Inserts a SparceArray of Parcelable values into the mapping of this
     * Bundle, replacing any existing value for the given key.  Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a SparseArray of Parcelable objects, or null
     * @return current
     */
    public Postcard withSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        mBundle.putSparseParcelableArray(key, value);
        return this;
    }

    /**
     * Inserts an ArrayList value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList object, or null
     * @return current
     */
    public Postcard withIntegerArrayList(String key, ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    /**
     * Inserts an ArrayList value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList object, or null
     * @return current
     */
    public Postcard withStringArrayList(String key, ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    /**
     * Inserts an ArrayList value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList object, or null
     * @return current
     */
    public Postcard withCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        mBundle.putCharSequenceArrayList(key, value);
        return this;
    }

    /**
     * Inserts a Serializable value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Serializable object, or null
     * @return current
     */
    public Postcard withSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    /**
     * Inserts a byte array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a byte array object, or null
     * @return current
     */
    public Postcard withByteArray(String key, byte[] value) {
        mBundle.putByteArray(key, value);
        return this;
    }

    /**
     * Inserts a short array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a short array object, or null
     * @return current
     */
    public Postcard withShortArray(String key, short[] value) {
        mBundle.putShortArray(key, value);
        return this;
    }

    /**
     * Inserts a char array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a char array object, or null
     * @return current
     */
    public Postcard withCharArray(String key, char[] value) {
        mBundle.putCharArray(key, value);
        return this;
    }

    /**
     * Inserts a float array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a float array object, or null
     * @return current
     */
    public Postcard withFloatArray(String key, float[] value) {
        mBundle.putFloatArray(key, value);
        return this;
    }

    /**
     * Inserts a CharSequence array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence array object, or null
     * @return current
     */
    public Postcard withCharSequenceArray(String key, CharSequence[] value) {
        mBundle.putCharSequenceArray(key, value);
        return this;
    }

    /**
     * Inserts a Bundle value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Bundle object, or null
     * @return current
     */
    public Postcard withBundle(String key, Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    /**
     * Set normal transition anim
     *
     * @param enterAnim enter
     * @param exitAnim  exit
     * @return current
     */
    public Postcard withTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    /**
     * Set options compat
     *
     * @param compat compat
     * @return this
     */
    // @RequiresApi(16)
    // public Postcard withOptionsCompat(ActivityOptionsCompat compat) {
    //     if (null != compat) {
    //         this.optionsCompat = compat.toBundle();
    //     }
    //     return this;
    // }
    @Override
    public String toString() {
        return "Postcard{" +
                "uri=" + uri +
                ", tag=" + tag +
                ", mBundle=" + mBundle +
                ", flags=" + flags +
                ", timeout=" + timeout +
                ", optionsCompat=" + optionsCompat +
                ", enterAnim=" + enterAnim +
                ", exitAnim=" + exitAnim +
                "}\n" +
                super.toString();
    }

    public String getAction() {
        return action;
    }

    public Postcard withAction(String action) {
        this.action = action;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
