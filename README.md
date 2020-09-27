# VolumeApp
Volume view app
Navodilo
Naloga mora biti v celoti napisana v programskem jeziku Kotlin (lahko je tudi JAVA). Pri oddaji
naloge je potrebno priloziti celotno izvorno kodo (idealno URL do GIT repozitorija).
Opis naloge
Implementirati je potrebno custom view, ki mora delovati kot volume control (screenshot v prilogi
na kocu dokumenta) in omogocati:
- Nastavljanje glasnosti
- Nastavljanje skale (stevilo crtic, ki nakazujejo nastavljeno glasnost)
- Nastavljanje barve
Zgornje vrednosti se morajo dati nastavljati preko XML atributov in programsko preko setterjev
na kontroli. Kontrola mora znati reagirati na setterje v runtime-u.
Kontrola mora tudi reagirati na touch event-e (drag) tako da se nastavlja gasnost med 0% in
100%, vsako spremembo glasnosti mora sporociti na callback interface, ce je le ta nastavljen.
