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
import com.greenyetilab.linguaj.Translator;
import static com.greenyetilab.linguaj.Translator.tr;
import static com.greenyetilab.linguaj.Translator.trn;

class Example {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(tr("Using default locale, pass locales as argument to test with other locales"));
            printMessage();
        } else {
            for (int idx = 0; idx < args.length; ++idx) {
                String locale = args[idx];
                System.out.println("\n- " + locale + "\n");
                Translator.init(locale);
                printMessage();
            }
        }
    }

    private static void printMessage() {
        System.out.println(tr("Hello!"));
        for (int n=0; n < 10; ++n) {
            System.out.println(trn("%# file.", "%# files.", n));
        }
    }
}
