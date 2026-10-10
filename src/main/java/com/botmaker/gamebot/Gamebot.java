package com.botmaker.gamebot;

import com.botmaker.gamebot.plugins.sdk.Pictures;
import com.botmaker.gamebot.plugins.sdk.Sdk;
import com.botmaker.sdk.api.bot.Bot;
import com.botmaker.sdk.api.time.Wait;
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
 * <h2>The five pieces, and which one to edit</h2>
 *
 * <ul>
 *   <li><b>The activities</b> — {@link Collect}, {@link Battle}, {@link Rest}. One file each, holding the
 *       work, each a {@code public static} {@code body()} returning one of its own activity's outcomes —
 *       {@code Activities.Collect body()}. This is the half you write.</li>
 *   <li><b>The names</b> — {@code plugins/sdk/Activities.java}, one enum per card and one constant per
 *       outcome. The Activity Flow keeps them, and renames every use of one with it.</li>
 *   <li><b>The parameters</b> — {@link Parameters}, one {@code @Param} field each. They are what
 *       <b>Project ▸ Parameters</b> shows and writes, and what the bot reads by name.</li>
 *   <li><b>The flow</b> — {@code plugins/sdk/Sdk.java}, drawn on the Activity Flow canvas. It says which
 *       activity starts, which method each card runs, where each outcome leads, and how the bot gets home
 *       ({@code .home(Gamebot::goHome)}). Draw it in Studio, or edit the expression by hand — it is ordinary
 *       Java either way.</li>
 *   <li><b>This class</b> — the wiring. {@code Bot.runFlow} reads the flow and the capture source off
 *       {@code Sdk.class} and walks the flow.</li>
 * </ul>
 *
 * <p>The two halves are joined by a <b>method reference</b>: {@code Collect::body} in the flow is the same
 * four tokens javac resolves here, so renaming or deleting an activity's method is a compile error naming
 * {@code Sdk.java}, not a card that silently stops doing anything. A card's <em>label</em> is its enum's name
 * as words — {@code Activities.Collect} reads "Collect" — so renaming the card renames the enum and every use
 * of it, while the class holding the work keeps its own name: renaming {@link Collect} does not touch the
 * canvas.
 *
 * <h2>Capture</h2>
 *
 * <p>Where the pixels come from is {@code Sdk.captureSource()}, which reads the whole desktop out of the
 * box. Point it somewhere narrower — a window, a monitor, an emulator instance — with <b>🎯 Capture
 * Source</b> on the toolbar, and the same code follows without a line here changing.
 */
public final class Gamebot {

    private Gamebot() {}

    public static void main(String[] args) {
        // Installs the flow and the capture source written in plugins/sdk/Sdk.java, so what the bot runs is
        // readable without opening Studio, then walks the flow until it ends or the bot is stopped.
        Bot.runFlow(Sdk.class);
    }

    /**
     * Back to the screen every activity expects to start from — the flow's {@code .home(Gamebot::goHome)}.
     *
     * <p>Called before an activity that goes home first and whenever the bot loses its place, so it has to be
     * safe to run at any moment — including when the game is already home. Pressing a close button until there
     * is no close button left is the usual shape, and it is what this does.
     */
    public static void goHome() {
        for (int attempt = 0; attempt < 5; attempt++) {
            if (!ImageClicker.click(Pictures.HOME)) {
                return;
            }
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
