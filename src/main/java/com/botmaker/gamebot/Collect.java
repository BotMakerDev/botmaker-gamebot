package com.botmaker.gamebot;

import com.botmaker.sdk.api.bot.Activities;
import com.botmaker.sdk.api.interaction.Wait;
import com.botmaker.sdk.api.vision.ImageClicker;

import java.time.Duration;

/**
 * Clicks the collect button while there is one, and says so when there is not.
 *
 * <p>The shape to copy for an activity of your own: do <em>one</em> unit of work, report what happened, and
 * let the flow decide what comes next. An activity that loops forever inside itself is one the canvas
 * cannot route around and the Stop button cannot interrupt cleanly.
 */
final class Collect {

    private Collect() {}

    static void define() {
        Activities.define("Collect", ctx -> {
            if (!ImageClicker.click(Pictures.COLLECT)) {
                // Nothing to collect. Not a failure — the flow sends this on to Battle.
                return ctx.outcome("NOTHING_LEFT");
            }
            // Randomized rather than fixed: a bot that clicks on exactly the same beat is a bot that reads
            // as one. Wait.between is here for that.
            Wait.between(Duration.ofMillis(600), Duration.ofMillis(1200));
            return ctx.done();
        });
    }
}
