# Plugin na Kozę
### Opis:
- Respi się koza która ma wyznaczoną ilość hp w configu która odpycha co ileś uderzeń i po zabiciu daje nagrady dla topowych graczy (Liczba osób która dostanie nagrody jak i jakie nagrody dostanie które miejsce jest do ustalenia w configu). Plugin obsługuje Scorbordy z pluginu TAB.
### Komendy:
- `/koza <spawn/setspawn/kill/reload>` -  bazowa komenda łatwa w obsłudze.
### Permisje: 
- `gold.koza.admin` - Permisjia do komendy `/koza`
### Placeholdery:
- `%GOLD-Koza_boss-spawned%` - zwraca czy koza jest zrespiona czy nie
- `%GOLD-Koza_boss-name%` - zwraca nazwę kozy
- `%GOLD-Koza_boss-location%` - zwraca lokalizacjię
- `%GOLD-Koza_boss-damage-top-XXX%` - zwraca osobę z topki. Zamiast `XXX` wpisz numer topki np: `%GOLD-Koza_boss-damage-top-1%`
### Wersja:
- Testowane na 1.18.2 i 1.19.4
- Java 18+
### Wymagania:
- PlaceholderAPI - https://www.spigotmc.org/resources/placeholderapi.6245/
- TAB - https://www.spigotmc.org/resources/tab-1-5-1-21-5.57806/
### Config Scoreboard:
```yaml
  scoreboards:
    scoreboard_boss:
      display-condition: "%GOLD-Koza_boss-spawned%=1"
      title: "&f              &f&lTWOJEIP.&e&lPL&f              &f"
      lines:
      - '%GOLD-Koza_boss-name% &7się zrespiła!'
      - '&7Znajduje się ona na /warp event!'
      - '&7Lokalizacja: &f%GOLD-Koza_boss-location%'
      - ''
      - '&eNagrody otrzymają:'
      - '1. %GOLD-Koza_boss-damage-top-1%'
      - '2. %GOLD-Koza_boss-damage-top-2%'
      - '3. %GOLD-Koza_boss-damage-top-3%'
      - '4. %GOLD-Koza_boss-damage-top-4%'
      - '5. %GOLD-Koza_boss-damage-top-5%'
      - ''
```
