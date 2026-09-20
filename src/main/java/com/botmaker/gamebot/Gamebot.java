package com.botmaker.gamebot;

import com.botmaker.gamebot.plugins.sdk.Pictures;
import com.botmaker.gamebot.plugins.sdk.Sdk;
import com.botmaker.sdk.api.bot.Bot;
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
 * and the same code starts working against your game. Nothing here has to be edited to do that: a
 * {@link Pictures} entry names a file, not a compiled constant.
 *
 * <h2>The four pieces, and which one to edit</h2>
 *
 * <ul>
 *   <li><b>The activities</b> — {@link Collect}, {@link Battle}, {@link Rest}. One file each, holding the
 *       work, each a {@code public static Outcome body(ActivityContext ctx)}. This is the half you
 *       write.</li>
 *   <li><b>The parameters</b> — {@link Parameters}, one {@code @Param} field each. They are what
 *       <b>Project ▸ Parameters</b> shows and writes, and what the bot reads by name.</li>
 *   <li><b>The flow</b> — {@code plugins/sdk/Sdk.java}, drawn on the Activity Flow canvas. It says which
 *       activity starts, which method each card runs, and where each outcome leads. Draw it in Studio, or
 *       edit the expression by hand — it is ordinary Java either way.</li>
 *   <li><b>This class</b> — the wiring. {@code Sdk.install()} hands the flow and the capture source to the
 *       SDK; {@code Bot.start} runs the flow with a way home.</li>
 * </ul>
 *
 * <p>The two halves are joined by a <b>method reference</b>: {@code Collect::body} in the flow is the same
 * four tokens javac resolves here, so renaming or deleting an activity's method is a compile error naming
 * {@code Sdk.java}, not a card that silently stops doing anything. An activity's <em>label</em> on the
 * canvas is a separate string on purpose — renaming the card does not touch your code, and renaming your
 * class does not touch the canvas.
 *
 * <h2>Capture</h2>
 *
 * <p>Where the pixels come from is {@code Sdk.captureSource()}, which reads the whole desktop out of the
 * box. Point it somewhere narrower — a window, a monitor, an emulator instance — in <b>Project ▸
 * Settings</b>, and the same code follows without a line here changing.
 */
public final class Gamebot {

    private Gamebot() {}

    public static void main(String[] args) {
        // Hands this project's flow and capture source to the SDK. Everything it installs is a value
        // written in plugins/sdk/Sdk.java, so what the bot runs is readable without opening Studio.
        Sdk.install();

        // The flow is walked until it ends or the bot is stopped. goHome is what it runs to get back to a
        // known screen — between activities, and after anything unexpected.
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
            if (!ImageClicker.click(Pictures.HOME)) return;
            Wait.milliseconds(400);
        }
    }

    /**
     * Whether an activity that has not got anywhere yet should keep trying.
     *
     * <p>Every loop inside an activity needs a way out that is not "the game changed" — a bot that cannot
     * find its picture has to give up rather than spin. This is the shared answer: a bounded number of
     * attempts, read from {@link Parameters#maxAttempts}, which <b>Project ▸ Parameters</b> edits in place.
     *
     * <p>One field access and no fallback. The parameter is a field of this project, so it cannot be
     * missing at run time — deleting it is a compile error here, which is the point of declaring it in Java
     * rather than looking it up by name in a file.
     */
    static boolean keepTrying(int attempt) {
        return attempt < Parameters.maxAttempts;
    }
}
