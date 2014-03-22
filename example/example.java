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
            System.out.println(trn("%n file.", "%n files.", n));
        }
    }
}
