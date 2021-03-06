/*
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
/*
 *
 */

import java.text.*;
import java.util.*;
import sun.util.*;
import sun.util.resources.*;

public class DecimalFormatSymbolsProviderTest extends ProviderTest {

    com.foo.DecimalFormatSymbolsProviderImpl dfsp = new com.foo.DecimalFormatSymbolsProviderImpl();
    List<Locale> availloc = Arrays.asList(DecimalFormatSymbols.getAvailableLocales());
    List<Locale> providerloc = Arrays.asList(dfsp.getAvailableLocales());
    List<Locale> jreloc = Arrays.asList(LocaleData.getAvailableLocales());

    public static void main(String[] s) {
        new DecimalFormatSymbolsProviderTest();
    }

    DecimalFormatSymbolsProviderTest() {
        availableLocalesTest();
        objectValidityTest();
    }

    void availableLocalesTest() {
        Set<Locale> localesFromAPI = new HashSet<Locale>(availloc);
        Set<Locale> localesExpected = new HashSet<Locale>(jreloc);
        localesExpected.addAll(providerloc);
        if (localesFromAPI.equals(localesExpected)) {
            System.out.println("availableLocalesTest passed.");
        } else {
            throw new RuntimeException("availableLocalesTest failed");
        }
    }

    void objectValidityTest() {

        for (Locale target: availloc) {
            // pure JRE implementation
            ResourceBundle rb = LocaleData.getNumberFormatData(target);
            boolean jreSupportsLocale = jreloc.contains(target);

            // JRE string arrays
            String[] jres = new String[2];
            if (jreSupportsLocale) {
                try {
                    String[] tmp = rb.getStringArray("NumberElements");
                    jres[0] = tmp[9]; // infinity
                    jres[1] = tmp[10]; // NaN
                } catch (MissingResourceException mre) {}
            }

            // result object
            DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(target);
            String[] result = new String[2];
            result[0] = dfs.getInfinity();
            result[1] = dfs.getNaN();

            // provider's object (if any)
            DecimalFormatSymbols providersDfs= null;
            String[] providers = new String[2];
            if (providerloc.contains(target)) {
                providersDfs = dfsp.getInstance(target);
                providers[0] = providersDfs.getInfinity();
                providers[1] = providersDfs.getNaN();
            }

            for (int i = 0; i < result.length; i ++) {
                checkValidity(target, jres[i], providers[i], result[i], jreSupportsLocale);
            }
        }
    }
}
