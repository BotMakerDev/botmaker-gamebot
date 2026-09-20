package com.botmaker.gamebot;

import com.botmaker.gamebot.plugins.sdk.Pictures;
import com.botmaker.sdk.api.bot.ActivityContext;
import com.botmaker.sdk.api.bot.Outcome;
import com.botmaker.sdk.api.interaction.Wait;
import com.botmaker.sdk.api.vision.ImageClicker;

import java.time.Duration;

/**
 * Clicks the collect button while there is one, and says so when there is not.
 *
 * <p>The shape to copy for an activity of your own: do <em>one</em> unit of work, report what happened, and
 * let the flow decide what comes next. An activity that loops forever inside itself is one the canvas
 * cannot route around and the Stop button cannot interrupt cleanly.
 *
 * <p>{@link #body} is an ordinary {@code public static} method, named in the flow as {@code Collect::body}.
 * Nothing requires the name {@code body} and nothing requires one class per activity — what matters is that
 * the flow names a method javac resolves, so renaming this one is a compile error in {@code Sdk.java} rather
 * than an activity that quietly stops running. It is also, being a plain static method, one a JUnit test can
 * call with an {@code ActivityContext} of its own.
 */
public final class Collect {

    private Collect() {}

    public static Outcome body(ActivityContext ctx) {
        if (!ImageClicker.click(Pictures.COLLECT)) {
            // Nothing to collect. Not a failure — the flow sends this on to Battle.
            return ctx.outcome("NOTHING_LEFT");
        }
        // Randomized rather than fixed: a bot that clicks on exactly the same beat is a bot that reads
        // as one. Wait.between is here for that.
        Wait.between(Duration.ofMillis(600), Duration.ofMillis(1200));
        return ctx.done();
    }
}
