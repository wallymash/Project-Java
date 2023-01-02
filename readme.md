# Group gr2258 repository

[[_TOC_]]

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2258/gr2258)

### Docs

Denne mappen inneholder dokumentasjonen for hver utgivelse av prosjektet.

I Docs-mapppen vil man finne tre releaser, "release1", "release2" og "release3". Inn i hver av de ligger det en .md-fil som beskriver funksjonalitetene implementert og en utvidet beskrivelse av de.

### bank

Denne mappen inneholder kjernen av prosjektet, det viktigste av koden som utgjør programmet, tester og filbehandling i form av JSON. Inne i denne mappen finner vi også brukerhistorier.md som beskriver brukerhistorien - gir altså en gjennomgang av appen og hvilke oppgaver metodene gjennomfører.

Bank-mappen inneholder fire barnemoduler: config, core, gui og rest, som alle blir beskrevet tydligere i underseksjonene videre.


- #### config
    - Inneholder en mappe som heter spotbugs; inni den finnes exclude.xml - en fil som brukes til å ignorere noen av feilene som Spotbugs generer

- #### core

  - Denne mappen er modellen i MVC frammeverket
  - Den inneholder hovedklassene og testene til prosjektet. 
  - Den inneholder også mappen som heter JSON, som bruks for lagring av bruker data

- #### gui

  - gui-mappen inneholder både "viewet" og kontrolleren i MVC frammeverket, som inneholder kode for å lage en GUI og vise det til brukeren
    - I undermappen "java" finnes kontrolleren og selve appen
    - I undermappen "resources" har vi "viewet" BankApp.fxml 
  - Finner også GUI-tester i denne mappen

- #### rest
  - Inneholder hovedklassene og testene til REST-API
  - Det er i denne mappen man finner funksjonaliteten for bl.a. kontakt vha. api-forespørsler. Grunnet dette vil f.eks. programmet kommunisere med serveren når brukeren vil ta ut penger, og få det til å fungere. Feilmeldinger er håndtert av koden skrevet i core-mappen.
    - Angående metodene vi har implementert kan en lese om det i Docs-mappen hvor det er en egen .md-fil for akkurat det

---

## Hvordan kjøre gjennom prosjektet

Det er to måter å kjøre prosjektet:

### 1. Klone repo

Klone repositoriet lokalt i egen datamaskin. Her antas det at brukeren som kjører prosjektet har Maven og Java installert.

>- <code> git clone <https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2258/gr2258> </code>  
>- Start opp serveren i et eget terminalvindu og skriv <code>mvn spring-boot:run</code> når du er inne i mappen. (NB! Anbefales å bruke en split terminal for å ha opp to terminaler samtidig; en i rest-mappen og en bank-mappen). Du kan også starte servern fra forelder modulen med kommandoen <code>mvn -pl rest spring-boot:run</code>.
>- Kompiler kode med <code>mvn compile</code> når du er inne i bank mappen.  
>- Kjør testene med <code>mvn test</code>. **Man må være i bank-mappen for å kjøre testen normalt**. Vanligvis kan man kjøre enkelte tester ved å være i hver barnmodul, men i dette tilfellet vil Maven ikke kunne finne fram til config/spotbugs/exclude.xml pga. stien er definert i pom-filen til foreldren.  
>- **Viktig å ikke røre datamaskinmusen når testen kjøres** på grunn av robotten som gjennomfører testene.
>- Generer JaCoCo-rapport med <code>mvn verify</code>. Du kan finne rapporten i filstien barnmodule/target/site. Inne i "site"-mappen finnes det en fil med navnet index.html. Høyre klikk på filen og velg "Reveal in File Explorer", deretter åpner du index.html i en nettleser. 
>- Generer Checkstyle-rapport med kommandoen <code>mvn checkstyle:checkstyle</code>. Rapporten finner du i hver modul i stien module/target/site. Inne i "site"-mappen finner du en fil med navnet checkstyle.html.
>- Kjør appen med <code>mvn javafx:run</code>, **du må være i gui mappen** for å kunne kjøre denne kommandoen. Du kan også kjøre appen fra foreldermodulen med kommandoen <code>mvn javafx:run -f ./gui/pom.xml </code>


### 2. Gitpod

>- Øverst i denne filen finner du en knapp "Gitpod Ready-to-Code" og den inneholder link til gitpod workspace. I dette arbeidsområde finner du repoen for bankappen. 
>- Vi har konfigurert Gitpod slik at koden <code>mvn clean install</code> kjøres for at alle avhengighetene skal bli lastet ned og koden <code>mvn compile</code> kjøres også første gangen du åpner prosjektet. Siden <code>mvn clean install</code> kjører også så feiler de testene som er avhengig av at serveren er på, men etter at <code>mvn compile</code> blir kjørt automatisk av Gitpod, så kan man starte serveren og kjøre appen eller kjøre testene.   
>- Starte opp serveren i eget terminalvindu og kjøre <code>mvn spring-boot:run</code> fra rest mappen.
>- For å kjøre prosjektet følger du de vanlige instruksjonene gitt under **1. git clone**.
>- På Gitpod generer du kodekvalitetsrapporter på samme måte som beskrevet under **1. git clone**. For å åpne rapporten høyreklikker du på filen og trykker på "open with LiveServer". 


I tilfelle testene ikke kjører som normalt kan det hjelpe at man kjører <code>mvn clean install</code> i bank mappen. Sjekk også om serveren er på. 

### 3. Shippable produkt - Jlink & Jpackage

>- Prosjektet skal være konfigurert for å lage et "shippable" produkt, dette gjøres med jpackage og jlink.
>
>- For å gjøre dette må terminalen åpnes i "gui" folderen, deretter kjør <code>mvn javafx:jlink</code>.
>- Etter dette, kjøres <code>mvn jpackage:jpackage</code> i samme terminal.
>- Du vil da få opp appen, "BankFX". Nedlastningsfil av appen kan også finnes i folderen gui -> target -> dist. Der vil det være en fil "BankFX-1.0.0.dmg" for de med Mac eller "BankFX-1.0.0.exe" for de med Windows.
>
>- (Hvis det brukes Windows som operativsystem må Wix Toolset nedlastes -> last ned [wix311.exe](https://github.com/wixtoolset/wix3/releases/download/wix3112rtm/wix311.exe))

