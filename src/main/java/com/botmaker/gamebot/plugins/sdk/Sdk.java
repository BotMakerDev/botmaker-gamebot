package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.gamebot.Battle;
import com.botmaker.gamebot.Collect;
import com.botmaker.gamebot.Gamebot;
import com.botmaker.gamebot.Rest;
import com.botmaker.sdk.api.bot.BotSettings;
import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.capture.CaptureSource;
import com.botmaker.sdk.api.flow.Flow;
import com.botmaker.sdk.api.flow.FlowLayout;

import java.util.List;
import java.util.Map;

/**
 * The BotMaker SDK's values for this bot.
 *
 * <p>This file was copied here when the SDK was added, and it is <b>yours from that moment</b> — nothing
 * regenerates it. BotMaker rewrites the expression an {@code @SdkValue} method returns and nothing else, so
 * comments, helper methods, imports you add and anything else you write around them survive untouched.
 *
 * <p>Your {@code main} names this class — {@code Bot.runFlow(Sdk.class)} — and the SDK reads
 * each {@code @SdkValue} method off it. Leave it out of that call and the bot runs with no flow, the default
 * capture source and the default settings.
 */
public final class Sdk {

    /**
     * The activity flow: what runs, in what order, and what stops it.
     *
     * <p>Drawn in <i>Project ▸ Activity Flow</i>. An activity's work is a method reference, so renaming or
     * deleting the method is a compile error here rather than a flow that quietly does nothing.
     *
     * <p>What this one says: collect until there is nothing left, then fight; a win goes back to collecting
     * and a loss rests first. Every arrow is a line below, every activity is a class in this project and an enum
     * of {@link Activities}, and every outcome is a constant of its activity's enum. {@code home} is what gets the
     * game back to a known screen: at start-up, after anything unexpected, and before Battle.
     */
    @SdkValue(SdkValue.Id.FLOW)
    public static Flow flow() {
        return Flow.of(
                List.of(Flow.activity(Activities.Collect.class, Collect::body)
                                .described("Click the collect button while there is one.")
                                .checksPopups(),
                        Flow.activity(Activities.Battle.class, Battle::body)
                                .described("Start a fight and wait for it to end.")
                                .goesHome()
                                .checksPopups(),
                        Flow.activity(Activities.Rest.class, Rest::body)
                                .described("Wait for Parameters.restBetween, then go round again.")),
                List.of(Flow.edge(Activities.Collect.NEXT, Activities.Collect.class),
                        Flow.edge(Activities.Collect.NOTHING_LEFT, Activities.Battle.class),
                        Flow.edge(Activities.Battle.WON, Activities.Collect.class),
                        Flow.edge(Activities.Battle.LOST, Activities.Rest.class),
                        Flow.edge(Activities.Rest.NEXT, Activities.Collect.class)),
                List.of(),
                Activities.Collect.class,
                Flow.limits(1000, 1000))
                .home(Gamebot::goHome)
                .setup(Gamebot::setup);
    }

    /**
     * Where each card sits in <i>Project ▸ Activity Flow</i>, keyed by activity name.
     *
     * <p>Nothing at runtime reads it: it is here so a copy of this bot opens on the same canvas. Dragging a
     * card rewrites it, and renaming a card rewrites it together with {@link #flow()}.
     */
    @SdkValue(SdkValue.Id.FLOW_LAYOUT)
    public static FlowLayout flowLayout() {
        return FlowLayout.of(Map.ofEntries(
                Map.entry("Collect", FlowLayout.at(80, 80)),
                Map.entry("Battle", FlowLayout.at(380, 80)),
                Map.entry("Rest", FlowLayout.at(380, 300))), true);
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
