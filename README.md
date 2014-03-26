Linguaj
=======

Linguaj provides a simple way to use [gettext][] to translate user visible
strings in Java code.

[gettext]: http://www.gnu.org/software/gettext/

## License

Linguaj is licensed under the Apache v2.0 license.

## How It Works

Linguaj makes use of gettext string extraction tool (xgettext) and gettext .po
file format, but does not use gettext Java support. Instead it provides a tool
named po-compile to turn .po files into .java files, which can then be used with
the provided Translator Java class.

## Dependencies

You need to have gettext installed, or at least access to the xgettext tool.

One of Linguaj components (scripts/po-compile) is written in Python and requires
the [jinja2][] and [polib][] modules. Those can be installed with:

    pip install polib jinja2

If you are running Linux, they should be available as packages as well.

[jinja2]: http://jinja.pocoo.org/docs/
[polib]: http://polib.readthedocs.org/en/latest/index.html

## Using Linguaj in Your Code

### Build With Linguaj

Either create a .jar from the code with `ant jar`, or add a reference in your
project to the `src/com` folder from this repository.

### Mark Your Text as Translatable

Add this import line to each Java class which contains text to be translated:

    import static com.greenyetilab.linguaj.Translator.tr;

Then wrap translatable text in `tr()` calls. This line:

    System.out.println("Hello");

Becomes:

    System.out.println(tr("Hello"));

### Extract Translatable Texts

Gettext provides the xgettext tool to extract translatable texts from the source
code. To get it to work with Linguaj, invoke it like this:

    xgettext --keyword=tr --keyword=trn:1,2 -o po/messages.pot *.java

This will go through all .java files in the current folder and create a file
named messages.pot in the po folder. The messages.pot file is a "message
template". It is a text file which contains all the translatable texts.

### Translate Texts

Create a .po for your language. For example to translate your texts to French,
either copy messages.pot to fr.po and adjust the header fields, or run:

    msginit -i messages.pot -l fr -o fr.po

You can now translate strings in fr.po, using either your text editor or,
preferably, a tool such as [poedit][] or [lokalize][].

[poedit]: http://poedit.net/
[lokalize]: http://userbase.kde.org/Lokalize

### Compile Texts

Once your .po files are ready, compile them into .java files using the scripts
in the scripts/ folder. The easiest way is to run:

    scripts/po-compile-all <path/to/po/dir> <where/to/generate/java/files>

Make sure the folder where the generated .java files is in the class path. Now
run your code again, your texts should be translated.

### Plural Handling

One of the benefits of gettext is advanced plural handling. To translate a
plural string, use trn() instead of tr(). It works like this:

    import static com.greenyetilab.linguaj.Translator.trn;

    ...

    class Backup {
        ...
        void work(String[] files) {
            ...
            System.out.println(trn("%# file saved", "%# files saved", files.length));
        }
    }

The syntax is `trn("singular form", "plural form", number)`. Depending on the
value of number and of the current locale, trn() will return the appropriate
string. All occurrences of "%#" in the string will be replaced with `number`.

This is much better than using code like that:

    if (files.length == 1) {
        System.out.println(tr("1 file saved"));
    } else {
        System.out.println(tr("%d files saved", files.length));
    }

Because that code fails for languages with different grammar rules for plural.
For example French does not use an 's' when `number` is 0, Polish uses on plural
form when `number` is between 2 and 4, and another form when it is between 5 and
9.

Linguaj comes with an example which demonstrates these different plural forms.
You can try it by running `ant example-run`.

### String Formatting

Linguaj API supports string formatting, based on String.format(). This means
that instead of writing this:

    String msg = String.format(tr("Hello %s, how are you?"), name);

You can write:

    String msg = tr("Hello %s, how are you?", name);

This works with trn() as well.

### Switching Languages

By default, Linguaj will load the most appropriate translation based on the
current locale, but you can force it to use a different locale by calling
`init()` with an argument, like this:

    import com.greenyetilab.linguaj.Translator;
    ...

    // Let's speak French
    Translator.init("fr");
    ...

    // Now switch to Brazilian Portuguese. If there is no Brazilian Portuguese
    // translation available, Linguaj falls back to Portuguese.
    Translator.init("pt_BR");

## Why Linguaj?

When I realized I needed translations for my [Burger Party][bp] Android game, I
looked at existing solutions for Java. I was not happy with the Java way of
using string constants: I find it nicer to work with English sentences than with
string constants. I also wanted to have good plural support, something which I
know is tricky.

Having past experience with gettext, I looked into using it. gettext is a
widespread, robust tool. There are many tools to work with it. I saw it had
Java support so I started using it, but bumped into a legal issue.

Java code can make use of gettext plural support, but to do so one has to use
the libintl.jar library provided by gettext. This is where the issue appears:
the code for libintl.jar is licensed under LGPL 2.1 or later. It is not clear
whether it is legal to use LGPL code in a proprietary .apk file, because the
.jar is not shipped as is in the .apk: it must be converted to work on the
Android VM (Dalvik), which does not use the same byte-code as the JVM.

I am quite confident that the spirit of the LGPL would allow LGPL code to be
used in a proprietary .apk, but I don't want to take a decision in the field of
licensing based on gut feeling. I reached out to the gettext maintainers, who
redirected me to the FSF lawyers, but the FSF lawyers ask for a $150 fee to
answer any question which implies proprietary software. I am not even sure I am
going to make that much money with my game, so I'd rather use Linguaj API for
now. Linguaj is licensed under the Apache 2.0 license so it does not have that
problem.

## Users

As I mentioned above, I am using Linguaj in Burger Party. It would be great to
hear from you if you use Linguaj in your projects!

[bp]: http://greenyetilab.com/burgerparty
