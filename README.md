# gamebot

A game bot to start from — window capture, image matching and a click loop, with three activities and a
flow deciding which of them runs next.

Create it from **New Project ▸ Start from ▸ Gamebot** in BotMaker Studio, or from the command line:

```bash
botmaker bot new mybot --from LiQiyeDev/botmaker-gamebot --package com.example.mybot
```

## It runs, and it matches nothing yet

The five pictures under `src/main/resources/images` are blank placeholders — a teal checker. That is
deliberate: a template cannot ship pictures of *your* game, and a bot that refuses to start until you supply
them teaches nothing.

Replace them with ✂ **Capture Templates** in Studio, **keeping the file names**, and the code starts working
without an edit. `Wire.image("collect")` names a file, not a compiled constant, so adding, renaming and
recapturing pictures is never a source change.

| Picture | What to capture |
|---|---|
| `collect.png` | The button that collects something — a reward, a resource, a chest. |
| `battle.png` | The button that starts a fight. |
| `victory.png` | Something that is on screen only after a win. |
| `defeat.png` | Something that is on screen only after a loss. |
| `home.png` | The close/back button that gets you back to the main screen. |

## The three pieces

- **The activities** — `Collect`, `Battle`, `Rest`, one file each. This is the half you write.
- **The flow** — `src/main/resources/activities.json`, drawn on the Activity Flow canvas in Studio. It says
  which activity starts and where each outcome leads: `Collect` loops on itself until there is nothing left,
  then `Battle`; a win goes back to collecting, a loss rests first.
- **`Gamebot`** — the wiring. Each activity's `define()` attaches a body to a name on the canvas, and
  `Bot.start` walks the flow with a way home.

The canvas and the code are joined by a **string**. Renaming an activity in Studio does not rename it here,
and until you change both they stop matching. An activity with no `define` call is not an error — it behaves
exactly as one switched off — so you can delete any of the three and still have a bot that runs.

## Where the pixels come from

Nothing in the code names a capture source, so every match reads the desktop. Point it at a window, a
monitor or an emulator instance in **Project ▸ Settings** and the same code follows.

## Two knobs, no rebuild

`restBetween` (how long `Rest` waits) and `maxAttempts` (how many times an activity polls for a picture
before giving up) are project variables, editable in **Project ▸ Parameters**.

## Building it yourself

```bash
mvn -q package
```

The `provided` dependencies in `pom.xml` are the SDK plugin's own needs — they let Studio draw the palette,
the pictures and the capture tools. Your bot links none of them at run time.
