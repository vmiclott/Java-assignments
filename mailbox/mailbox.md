Mailbox conversie
-----------------

In deze oefening passen we de les over Java invoer- en uitvoer toe in de praktijk.
Van die les zijn de slides te vinden
[op Minerva](http://minerva.ugent.be/courses2014/C00050402014/document/slides-io.pdf?cidReq=C00050402014).
De code voorbeelden uit die les staan
[op Github](https://github.ugent.be/kcoolsae/Prog2/tree/master/src/prog2/nio).

Een mailbox is een bestand met e-mailberichten,
waarbij elk bericht aan een precies gedefinieerd formaat voldoet.
Dit stelt een email leesprogramma in staat om de afzonderlijke berichten
van elkaar te onderscheiden.
In deze oefening schrijven we een Java programma om een mailbox bestand
te converteren naar HTML.

Voor deze oefening gebruiken we het volgende mailbox bestand:

* [mailbox.txt](http://twizz.ugent.be/student/prog2/mailbox.txt)

Het eerste bericht in dit bestand ziet er als volgt uit:
```
From: Sawyer Peterson <Sawyer.Peterson@wilkes.edu>
Date: Mon Sep  1 15:30:03 CEST 2014
Subject: Thus spake the

Thus spake the master programmer:
        "A well-written program is its own heaven; a poorly-written program
is its own hell."
                -- Geoffrey James, "The Tao of Programming"
```

De header van deze email bestaat uit drie velden:

* Een `From:` veld met daarin het e-mailadres van de afzender.
* Een `Date:` veld met de datum waarop het bericht verstuurd is.
* Een `Subject:` veld met de titel van het bericht.

Dan volgt een lege regel, gevolgd door de inhoud van het bericht.
Een volgend bericht begint weer met een `From:` veld aan het begin van een nieuwe regel.

We schrijven een Java programma dat deze mailbox omzet naar HTML.
Daarbij scheiden we de berichten van elkaar met een `<hr>` tag.
De drie velden `From:`, `Date:` en `Subject:` in een bericht
zetten we in boldface (met `<b>`, of beter `<strong>`, tags).
Deze drie regels eindigen we alledrie met een break tag `<br>`,
opdat zij in HTML op hun eigen regel worden weergegeven.
Elke paragraaf in de inhoud van het bericht krijgt
zijn eigen paragraaf in HTML door middel van `<p>` tags.

Het resultaat van deze omzetting schrijven we weg naar een nieuw HTML bestand.
We verifiëren de correctheid van ons Java programma door dit bestand te openen
in een webbrowser. Dat moet er uit zien als [dit voorbeeld](mailboxhtml.png).

De gebruiker van ons programma moet op verschillende manieren 
ons programma kunnen aanroepen. Daarbij kijken we naar de
commandoregelargumenten die zijn meegegeven aan de `main` methode.
(`public static void main(String[] args) { ... }`)
Wij verwachten steeds twee argumenten. De eerste is voor de invoer,
en de tweede voor de uitvoer.
Er zijn drie mogelijkheden voor het lezen van de mailbox
afhankelijk van de vorm van het eerste argument:

* Als dit begint met `http://` of `https://`,
dan veronderstellen wij dat het bestand van het internet gelezen moet
worden. Daarvoor kunnen wij dan de Java klasse `URL` gebruiken.
* Als dit gelijk is aan een minteken "-", dan betekent dit
dat wij de mailbox moeten lezen van de standaard invoer.
* In het derde geval is de mailbox gegeven als een bestand in het filesysteem.
Wij moeten deze dan zelf openen.

Het tweede commandoregelargument specificeert de wijze van uitvoer,
waarvoor twee mogelijkheden bestaan:

* Als dit gelijk is aan een minteken "-", dan betekent dit
dat wij de HTML moeten schrijven naar de standaard uitvoer.
* In het andere geval moet de HTML worden geschreven naar
een bestand in het filesysteem, waarvan de naam is gegeven
als het tweede argument.

Daarna werken we verder aan de oefeningen uit de
[**JavaFX - GUIs in Java**](http://minerva.ugent.be/courses2014/C00050402014/document/jvlfx.pdf?cidReq=C00050402014) tutorial.
Deze week moeten de oefeningen 2.1 en 3.1 van deze tutorial gedaan zijn.
