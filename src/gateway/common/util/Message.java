package gateway.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-6-7
 * Time: 15:03:14
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Message   implements Serializable {
    private static Log log = LogFactory.getLog(Message.class);
    private final String basename;
    private final String key;
    private final Object[] arguments;

    public Message(String basename, String key) {
        this(basename, key, null);
    }

    public Message(String basename, String key, Object[] arguments) {
        if (basename == null) {
            throw new NullPointerException("参数 basename 未指定");
        }

        if (key == null) {
            throw new NullPointerException("参数 key 未指定");
        }

        this.basename = basename;
        this.key = key;
        if ((arguments == null) || (arguments.length == 0))
            this.arguments = null;
        else
            this.arguments = ((Object[]) (Object[]) arguments.clone());
    }

    public String getBasename() {
        return this.basename;
    }

    public String getKey() {
        return this.key;
    }

    public Object[] getArguments() {
        if (this.arguments == null) {
            return null;
        }
        return (Object[]) (Object[]) this.arguments.clone();
    }

    public String get() {
        return create().toString();
    }

    public String get(Object arg0) {
        return create(arg0).toString();
    }

    public String get(Object arg0, Object arg1) {
        return create(arg0, arg1).toString();
    }

    public String get(Object arg0, Object arg1, Object arg2) {
        return create(arg0, arg1, arg2).toString();
    }

    public String get(Object arg0, Object arg1, Object arg2, Object arg3) {
        return create(arg0, arg1, arg2, arg3).toString();
    }

    public String get(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return create(arg0, arg1, arg2, arg3, arg4).toString();
    }

    public String get(Object[] args) {
        return create(args).toString();
    }

    public Message create() {
        return new Message(this.basename, this.key, null);
    }

    public Message create(Object arg0) {
        return new Message(this.basename, this.key, new Object[]{arg0});
    }

    public Message create(Object arg0, Object arg1) {
        return new Message(this.basename, this.key, new Object[]{arg0, arg1});
    }

    public Message create(Object arg0, Object arg1, Object arg2) {
        return new Message(this.basename, this.key, new Object[]{arg0, arg1, arg2});
    }

    public Message create(Object arg0, Object arg1, Object arg2, Object arg3) {
        return new Message(this.basename, this.key, new Object[]{arg0, arg1, arg2, arg3});
    }

    public Message create(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return new Message(this.basename, this.key, new Object[]{arg0, arg1, arg2, arg3, arg4});
    }

    public Message create(Object[] args) {
        return new Message(this.basename, this.key, args);
    }

    public String toString() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null)
            classLoader = super.getClass().getClassLoader();
        ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle(this.basename, Locale.getDefault(), classLoader);
        }
        catch (MissingResourceException e) {
            log.error("Resource bundle not found. basename=" + this.basename, e);

            return createFallbackMessage();
        }
        String pattern;
        try {
            pattern = bundle.getString(this.key);
        } catch (MissingResourceException e) {
            log.error("Message not found. basename=" + this.basename + ",key=" + this.key, e);

            return createFallbackMessage();
        }
        if (this.arguments == null)
            return pattern;
        try {
            return MessageFormat.format(pattern, this.arguments);
        } catch (Exception e) {
            log.error("Failed to format message. basename=" + this.basename + ",key=" + this.key + ",pattern=" + pattern + ",arguments=" + ArrayUtils.toString(this.arguments), e);
        }

        return createFallbackMessage();
    }

    private String createFallbackMessage() {
        return " basename=" + this.basename + ",key=" + this.key + ",arguments=" + ArrayUtils.toString(this.arguments) + "???";
    }
}