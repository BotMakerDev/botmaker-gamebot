package com.botmaker.gamebot;

import com.botmaker.sdk.api.bot.Bot;
import com.botmaker.sdk.api.config.Wire;
import com.botmaker.sdk.api.flow.FlowGraph;
import com.botmaker.sdk.api.interaction.Wait;
import com.botmaker.sdk.api.vision.ImageClicker;

/**
 * A game bot to start from: it finds a picture on the screen, clicks it, waits, and does it again —
 * with three activities and a flow deciding which of them runs next.
 *
 * <p>Everything here is yours. Rename this class, split it up, throw away the parts you do not want.
 * Nothing regenerates it and nothing else in the project depends on its name.
 *
 * <h2>What it does out of the box</h2>
 *
 * <p>It runs, and it matches nothing — the pictures under {@code src/main/resources/images} are blank
 * placeholders. Replace them with ✂ <b>Capture Templates</b> in BotMaker Studio, keeping the file names,
 * and the same code starts working against your game. Nothing here has to be edited to do that:
 * {@link Wire#image} names a file, not a compiled constant.
 *
 * <h2>The three pieces, and which one to edit</h2>
 *
 * <ul>
 *   <li><b>The activities</b> — {@link Collect}, {@link Battle}, {@link Rest}. One file each, holding the
 *       work. This is the half you write.</li>
 *   <li><b>The flow</b> — {@code activities.json}, drawn on the Activity Flow canvas. It says which
 *       activity starts and where each outcome leads. Open it in Studio rather than editing the file.</li>
 *   <li><b>This class</b> — the wiring between the two. Each activity's {@code define()} attaches a body to
 *       a name on the canvas; {@code Bot.start} runs the flow with a way home.</li>
 * </ul>
 *
 * <p>The two halves are joined by a <b>string</b>, deliberately: renaming an activity on the canvas does
 * not rename it here, and until you change both they stop matching. An activity with no {@code define}
 * call is not an error — it behaves exactly as one switched off and follows its {@code DISABLED} wire —
 * so deleting any of the three below leaves a bot that still runs.
 *
 * <h2>Capture</h2>
 *
 * <p>Nothing here names where the pixels come from, so every match reads the desktop. Point it somewhere
 * narrower — a window, a monitor, an emulator instance — in <b>Project ▸ Settings</b>, and the same code
 * follows without a line changing.
 */
public final class Gamebot {

    private Gamebot() {}

    public static void main(String[] args) {
        Collect.define();
        Battle.define();
        Rest.define();

        // The flow is read from activities.json beside this class and walked until it ends or the bot is
        // stopped. goHome is what it runs to get back to a known screen — between activities, and after
        // anything unexpected.
        Bot.start(() -> FlowGraph.run(Gamebot.class, Gamebot::goHome), Gamebot::goHome);
    }

    /**
     * Back to the screen every activity expects to start from.
     *
     * <p>Called between activities and whenever the bot loses its place, so it has to be safe to run at any
     * moment — including when the game is already home. Pressing a close button until there is no close
     * button left is the usual shape, and it is what this does.
     */
    static void goHome() {
        for (int attempt = 0; attempt < 5; attempt++) {
            if (!ImageClicker.click(Wire.image("home"))) return;
            Wait.milliseconds(400);
        }
    }

    /**
     * Whether an activity that has not got anywhere yet should keep trying.
     *
     * <p>Every loop inside an activity needs a way out that is not "the game changed" — a bot that cannot
     * find its picture has to give up rather than spin. This is the shared answer: a bounded number of
     * attempts, read from the project's own {@code maxAttempts} variable so it can be changed in
     * <b>Project ▸ Parameters</b> without a rebuild. {@code declares} is what keeps this working in a
     * project where somebody deleted that variable.
     */
    static boolean keepTrying(int attempt) {
        return attempt < (Wire.declares("maxAttempts") ? Wire.whole("maxAttempts") : 20);
    }
}
