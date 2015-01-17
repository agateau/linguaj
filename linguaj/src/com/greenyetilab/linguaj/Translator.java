/*
 * Copyright 2014 Aurélien Gâteau <mail@agateau.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greenyetilab.linguaj;

import java.util.Locale;

public class Translator {
    private static class Impl {
        private Messages mMessages;

        public Impl(Messages messages) {
            mMessages = messages;
        }

        public String tr(String src) {
            if (mMessages == null) {
                return src;
            }
            String txt = mMessages.plainEntries.get(src);
            return txt == null ? src : txt;
        }

        public String trn(String singular, String plural, int n) {
            String txt = findPluralTranslation(singular, plural, n);
            if (txt == null) {
                txt = n == 1 ? singular : plural;
            }
            return txt.replace("%#", String.valueOf(n));
        }

        private String findPluralTranslation(String singular, String plural, int n) {
            if (mMessages == null) {
                return null;
            }
            Messages.PluralId id = new Messages.PluralId(singular, plural);
            String[] lst = mMessages.pluralEntries.get(id);
            if (lst == null) {
                return null;
            }
            return lst[mMessages.plural(n)];
        }
    }

    private static Impl sImpl;

    public static String tr(String src) {
        initImpl();
        return sImpl.tr(src);
    }

    public static String tr(String src, Object... args) {
        return String.format(tr(src), args);
    }

    public static String trn(String singular, String plural, int n) {
        initImpl();
        return sImpl.trn(singular, plural, n);
    }

    public static String trn(String singular, String plural, int n, Object... args) {
        return String.format(trn(singular, plural, n), args);
    }

    private static void initImpl() {
        if (sImpl != null) {
            return;
        }
        init();
    }

    public static void init() {
        init(null);
    }

    public static void init(String locale) {
        if (locale == null) {
            locale = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
        }
        Messages messages;
        messages = tryLoad(locale);
        if (messages == null) {
            int idx = locale.indexOf('_');
            if (idx > -1) {
                messages = tryLoad(locale.substring(0, idx));
            }
        }
        sImpl = new Impl(messages);
    }

    private static Messages tryLoad(String suffix) {
        Class<?> cls;
        try {
            cls = Class.forName("Messages_" + suffix);
        } catch (ClassNotFoundException exception) {
            return null;
        }

        try {
            return (Messages) cls.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }
}
