public class Messages_fr extends com.agateau.gettext4j.Messages {
    public Messages_fr() {
        plainEntries.put("Usage: Example <number>", "Utilisation : Example <nombre>");
        plainEntries.put("Hello!", "Bonjour !");
        pluralEntries.put(
            new PluralId("%n second left.", "%n seconds left."),
            new String[] {
                "%n seconde restante.",
                "%n secondes restantes.",
            }
        );
    }

    @Override
    public int plural(int n) {
        return (n > 1) ? 1 : 0;
    }
}