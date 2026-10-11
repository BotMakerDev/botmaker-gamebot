# gamebot

A game bot to start from — window capture, image matching and a click loop, with three activities and a
flow deciding which of them runs next.

Create it from **New Project ▸ Start from ▸ Gamebot** in BotMaker Studio, or from the command line:

```bash
botmaker bot new mybot --from BotMakerDev/botmaker-gamebot --package com.example.mybot
```

## It runs, and it matches nothing yet

The five pictures under `src/main/resources/images` are blank placeholders — a teal checker. That is
deliberate: a template cannot ship pictures of *your* game, and a bot that refuses to start until you supply
them teaches nothing.

Replace them with ✂ **Capture Templates** in Studio, **keeping the file names**, and the code starts working
without an edit. `Pictures.COLLECT` names a file, not baked-in pixels, so recapturing a picture is never a
source change. (`Pictures` sits in `plugins/sdk/` — 🖼 **Manage Pictures** owns that class, because adding
or renaming a picture has to move the file, the constant and every use of it together.)

| Picture | What to capture |
|---|---|
| `collect.png` | The button that collects something — a reward, a resource, a chest. |
| `battle.png` | The button that starts a fight. |
| `victory.png` | Something that is on screen only after a win. |
| `defeat.png` | Something that is on screen only after a loss. |
| `home.png` | The close/back button that gets you back to the main screen. |

## The five pieces

- **The activities** — `Collect`, `Battle`, `Rest`, one file each. This is the half you write. Each `body()`
  returns its own activity's enum from `plugins/sdk/Activities.java` (`Activities.Collect`), whose constants
  are the outcomes its card has ports for — so a body reporting another activity's outcome does not compile.
- **The parameters** — `Parameters.java`, one `@Param` field each. That file *is* what
  **Project ▸ Parameters** shows, and the window writes back into it.
- **The pictures** — `plugins/sdk/Pictures.java`, one constant per file under `src/main/resources/images`.
- **The flows** — `plugins/sdk/Flows.java`, one constant per way of playing, each drawn on the Activity Flow
  canvas in Studio. `Flows.MAIN` says which activity starts, which method each card runs and where each outcome
  leads: `Collect` loops on itself until there is nothing left, then `Battle`; a win goes back to collecting, a
  loss rests first. `Rest` is `.switchable()`: the person running the bot may skip it, through `skip` below.
- **The run** — `Sdk.run()` in `plugins/sdk/Sdk.java`: how the bot gets home (`.home(Gamebot::goHome)`) and how
  it makes the game ready, its settings included (`.setup(Gamebot::setup)`) — one each for the whole bot,
  whichever flow runs.
- **The run choices** — three `@Param`s beside it in `Sdk.java`, shown in the Runner: `flow` (which `Flows`
  constant runs, `Flows.MAIN`), `doSetup` (run the setup first: tick it on a new computer or after changing a game
  setting) and `skip` (activities switched off, each one a card marks `.switchable()`: `Set.of(Activities.Rest.class)`).
- **`Gamebot`** — the wiring. `Bot.runFlow(Sdk.flow, Sdk.doSetup, Sdk.skip, Sdk.class)` walks the chosen flow with
  the run and the capture source it reads off `Sdk.java`.

The canvas and the code are joined by a **method reference**: `Collect::body` in `Sdk.java` is the same four
tokens javac resolves in `Collect.java`, so renaming or deleting an activity's method is a compile error
naming the file it broke — never a card that silently stops doing anything. A card's *label* is its enum's
name as words (`Activities.Collect` reads "Collect"), so renaming the card renames the enum and every use of
it, while the class holding the work keeps its own name: renaming `Collect` does not touch the canvas.

`Sdk.java` and `Pictures.java` were copied into your project when the SDK was added, and they are yours from
that moment. Studio rewrites the expression an `@SdkValue` method returns and nothing else, so comments,
helpers and imports you add around them survive.

## Where the pixels come from

`Sdk.captureSource()` reads the whole desktop out of the box. Point it at a window, a monitor or an emulator
instance with **🎯 Capture Source** on the toolbar and the same code follows — the one expression changes, nothing else.

## Two knobs, in your own code

`Parameters.restBetween` (how long `Rest` waits) and `Parameters.maxAttempts` (how many times an activity
polls for a picture before giving up) are `@Param` fields in `Parameters.java`. **Project ▸ Parameters**
lists them, and editing one there rewrites that field's initialiser — your formatting and comments stay put.

The bot reads them as ordinary fields: `Wait.time(Parameters.restBetween)`. A misspelled name is a compile
error rather than a silent fallback, which is the whole reason they are Java and not a settings file. Both
are marked `visibility = Param.PUBLIC`, so they also appear in the Runner window for whoever runs the bot.

## Building it yourself

```bash
mvn -q package
```

The `provided` dependencies in `pom.xml` are the SDK plugin's own needs — they let Studio draw the palette,
the pictures and the capture tools. Your bot links none of them at run time.
