# Lutemon Battle – Final Report  
**Author:** *Solo project – no team‑mates*  
**Date:** 15 April 2025  

---

## 1  General Project Description
Lutemon Battle is an Android application written in **Java 17** (Android Studio Flamingo) that lets a player **create, train and battle** a roster of colourful creatures called *Lutemons*.  
The goal of the coursework was to reproduce a lightweight Pokémon‑style loop while demonstrating:

* **Object‑Oriented Programming**: encapsulation, inheritance & polymorphism.  
* Proper use of Android components (Activities, RecyclerView, CardView navigation).  
* Internal **persistence** (JSON on internal storage).  
* A clear separation between **UI** and **game logic**.

The app ships with five elemental species (White, Green, Pink, Orange, Black). Each species has a base‐line **attack, defence and max HP**, stored in a parent `Lutemon` class and specialised in concrete subclasses.

---

## 2  High‑Level Architecture

| Class | Responsibility | Key Methods |
|-------|----------------|-------------|
| **`Lutemon`** (abstract)                | Base creature model. Stores combat stats, XP, wins/losses.                       |`train()`, `takeDamage()`, `heal()`, `getAttack()` |
| `White/Green/Pink/Orange/BlackLutemon`  | Species presets (stats & colour).                                                | constructor only |
| **`Storage`**                           | In‑memory repository + persistence. Owns lists for *home*, *training*, *battle*. | `moveToXXX()`, `saveData()`, `loadData()` |
| **`BattleActivity`**                    | UI for arena. Selects two Lutemons, runs `BattleLogic`.                          | `doNextAttack()`, HP bar animation |
| `LutemonAdapter`                        | RecyclerView ViewHolder → card with avatar, stats & overflow menu.               | `showMoveDialog()` |
| `TrainLutemonActivity`                  | “Train All” button; increments XP; returns to Home.                              | `onClickTrainAll()` |<-- it was in old version new version don't have it
| `StatsActivity` + `LutemonStatsAdapter` | Reads Storage, shows per‑Lutemon KPI.                                            | `onBindViewHolder()` |


*UI classes (`CreateLutemonActivity`, `HomeActivity`, etc.) are intentionally left out of the diagram, per the report instructions.*

## 3  Implemented Features (rubric cross‑check)

| Mandatory row | Status | Notes |
|---------------|-----------|-------|
| Object‑oriented core                              | ✅ | `Lutemon` hierarchy + `Storage` singleton |
| English code + comments                           | ✅ | All identifiers & strings in English |
| Runs on Android, Java                             | ✅ | Tested on API 24–34 |
| **Create / Move / Train / Battle** flow           | ✅ | Home → Training → Battle |
| Health restores **only when deliberately healed** | ✅ | Defeated Lutemon returns to Home at 0 HP |
| Turn‑based battle loop                            | ✅ | Player taps **Next Attack** button |
| Winner gains +1 XP                                | ✅ | XP affects `getAttack()` |
| JSON persistence                                  | ✅ | `Storage.saveData(..)` / `loadData(..)` |

### Bonus check‑list

| Extra feature  | Status |
|---------------|--------|
| RecyclerView lists          | ✅ custom `LutemonAdapter` |
| Species images              | ✅ five PNG drawables |<--- got them from www.flaticon.com
| Battle visualisation        | ✅ HP bars + image flash| 
| Player‑controlled turn loop | ✅ `Next Attack` button |
| Statistics screen           | ✅ wins, losses, XP, location |
| **Randomness** in damage    | ✅ `Math.random() * 3` added |
| Data save/load              | ✅ internal JSON |
| Custom polish               | ✅ Card‑based home screen, animated HP bar |

## 4  Division of Work

> *This project was completed entirely by me.*  
> Responsibilities included requirement analysis, UML drafting, UX mock‑ups, Java coding, drawable design, and final packaging.

---

## 5  Randomness & Balance

Damage is calculated as :

int base = attacker.getAttack() - defender.getDefense();
int actual = Math.max(0, base + (int)(Math.random()*3)); // 0–2 extra
This ±2 spread prevents deterministic outcomes when stats are very close. 

## 6  Data Persistence
Serialization: Gson dumps the entire Storage singleton into lutemon_data.json.

Save‑points: on every state‑changing action (addLutemon, moveTo…, training, battle result).

Load: MainActivity.onCreate() calls Storage.loadData().

## 7  Build & Run
Android Studio → File ▸ Open → select project root.

Compile with Gradle; minSdk = 24, targetSdk = 34.

Install on device or emulator (tested on Pixel 4a, API 34).

First launch seeds an empty Home screen; create a Lutemon to begin.

## 8  Diagram

//www.plantuml.com/plantuml/dpng/lPRDZY8t4CVlVeeIbuLas6IJdD1ejBt39211PhMG7ULOTDU2XTjksGK3IKQQLuYI9vmdIRTfJp3nP4ziYqDJlpzskJxAl7MCbZUb4cujTOKMIyWLEfSnMxdOC8rpesVoDu9lZoGLvcjSqeptYi1IpaAyUlhxyHmVyGhUz61uEyt6FyCtCBqTtapEjrbxCEYKfY3LWT-5yE606HjRWze0q8SHkjKKgyknPbgIlPXic4gZhm2LyeBCMjnFfEFBPxGo9Rt4sABKKYzVuWjaLkIfr0oQTZmkcgzz0V0rO56qqguQGFjw0Jdl1d1jDDVodXUMPajpCp8btKbUZGXLlMbNUMbAOJRyicZkdKwYTzxV25XgK70paESZEDpI41cxZHbW05iZ2q-KmUA_v448N3YscFEXOCEnsmp90g9dq0JE4uqbnMJ6DiG-DyhObo0oOxw-W88-aNPqj6Q9kr5L7sbZxQYATjlLvdkftGKeunovVuaoER8qEh5L6nDV5Tr3-5Q4gdrbNDCGIrnIjnX8pRq1B8nHXDhZ9Kfziwl8Ij8vjRzP4cVU-sxlE1vr54FOBl_ez-5k9JdaA2O_MIAT9E-bNYV1hKMzf2HwfsfN0X6XjpflEc1gslcg0q4-W19r3PruEEkC-Qu7sJ1xFx_vC0FeVg1ydoko7oNTzxx0jC42AoRhce4JLNDs06rHnqD1IUShgQqRNtsaUTBMbKTjzpVD9XxYfMUCx3wtVc8HSU9QltzrbAr2kahXtedKBadfkJFxVGz-6KDsFHz_7C_7DxClaSwSvLQo97TqWqnHwkm0zgtfsXA6VZjbpGW-COM1swGfXT8BJSsMqkGmfK-CXuG5Mns_MMw9zDFZdwwYN7wIEOHg5SS-mklNLoTLAn8E9tK9xvCwVvI4xFd6597tdYNgi3bLzfmYa9h9toFtabVXeXV7wGB_DAi5cinSf8XYqpwH81sSKy7_R94CHoI84vpm8GvNGd3UrloX5_hoLtXw_Elmvmv-dDpUdR5BqwcBHrFkDU0s2zyKv48euyeAyeM7e8ofugobvU5UVqkws9Jg7m00
<div align="center">
</div> ```
