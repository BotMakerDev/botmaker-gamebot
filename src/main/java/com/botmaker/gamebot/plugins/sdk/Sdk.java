package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.gamebot.Gamebot;
import com.botmaker.plugin.api.params.Param;
import com.botmaker.sdk.api.bot.BotSettings;
import com.botmaker.sdk.api.bot.Outcome;
import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.capture.CaptureSource;
import com.botmaker.sdk.api.flow.Flow;
import com.botmaker.sdk.api.flow.FlowLayout;
import com.botmaker.sdk.api.flow.Run;

import java.util.Map;
import java.util.Set;

/**
 * The BotMaker SDK's values for this bot.
 *
 * <p>This file was copied here when the SDK was added, and it is <b>yours from that moment</b> — nothing
 * regenerates it. BotMaker rewrites the expression an {@code @SdkValue} method returns and nothing else, so
 * comments, helper methods, imports you add and anything else you write around them survive untouched.
 *
 * <p>Your {@code main} names this class — {@code Bot.runFlow(Sdk.flow, Sdk.doSetup, Sdk.skip, Sdk.class)} — and
 * the SDK reads each {@code @SdkValue} method off it. Leave it out of that call and the bot runs with no home, the
 * default capture source and the default settings.
 */
public final class Sdk {

    /** Which of {@link Flows} the bot runs. The person running the bot picks it in the Runner. */
    @Param(category = "Run", visibility = Param.PUBLIC, description = "Which flow the bot runs")
    public static Flow flow = Flows.MAIN;

    /** Whether {@link #run()}'s setup runs first. Tick it on a new computer, or after changing a game setting. */
    @Param(category = "Run", visibility = Param.PUBLIC,
            description = "Set the game up first: tick it on a new computer or after changing a game setting")
    public static boolean doSetup = true;

    /** The activities this run skips: only one the flow's card lets the runner switch off. */
    @Param(category = "Run", visibility = Param.PUBLIC, description = "Activities this run skips")
    public static Set<Class<? extends Outcome>> skip = Set.of();

    /**
     * The game's home and setup, which no flow owns.
     *
     * <p>Changed in <i>Project ▸ Activity Flow</i>. {@code home} is what gets the game back to a known screen: at
     * start-up, after anything unexpected, and before Battle. {@code setup} makes the game ready for the bot once
     * it has started it, when {@link #doSetup} is ticked.
     */
    @SdkValue(SdkValue.Id.RUN)
    public static Run run() {
        return Run.of()
                .home(Gamebot::goHome)
                .setup(Gamebot::setup);
    }

    /**
     * Where each card sits in <i>Project ▸ Activity Flow</i>, keyed by flow, then by activity name.
     *
     * <p>Nothing at runtime reads it: it is here so a copy of this bot opens on the same canvas. Dragging a
     * card rewrites it, and renaming a card rewrites it together with {@link Flows}.
     */
    @SdkValue(SdkValue.Id.FLOW_LAYOUT)
    public static FlowLayout flowLayout() {
        return FlowLayout.of(Map.ofEntries(
                Map.entry("MAIN", FlowLayout.cards(Map.ofEntries(
                        Map.entry("Collect", FlowLayout.at(80, 80)),
                        Map.entry("Battle", FlowLayout.at(380, 80)),
                        Map.entry("Rest", FlowLayout.at(380, 300)))))), true);
    }

    /**
     * Where pixels are read from.
     *
     * <p>Chosen with <i>🎯 Capture Source</i> on the toolbar. {@code CaptureSource.desktop()} is the whole screen;
     * {@code CaptureSource.window("Game")} follows one window, wherever it moves.
     */
    @SdkValue(SdkValue.Id.CAPTURE)
    public static CaptureSource captureSource() {
        return CaptureSource.desktop();
    }

    /**
     * How the bot clicks and looks: the pauses around a match, how sure a match has to be, whether it drives the
     * real mouse and keyboard, and whether it runs on a private display of its own.
     *
     * <p>Changed in <i>⚙ Bot Settings</i>, and applied before the first click wherever the bot runs. Each
     * setting changed from the default is a link after {@code defaults()} — {@code .foundDelay(400)},
     * {@code .takesOver()} — so this bot changes none. What it <em>launches</em> is not here: that is a fact
     * about each computer, which Studio passes to the run.
     */
    @SdkValue(SdkValue.Id.SETTINGS)
    public static BotSettings settings() {
        return BotSettings.defaults();
    }

    private Sdk() {}
}
