package com.botmaker.gamebot;

import com.botmaker.sdk.api.bot.Activities;
import com.botmaker.sdk.api.config.Wire;
import com.botmaker.sdk.api.interaction.Wait;

import java.time.Duration;

/**
 * Does nothing, for as long as the project says.
 *
 * <p>Worth having as an activity rather than a {@code sleep} in the middle of another one: it is a node on
 * the canvas, so it can be re-routed, skipped, or switched off in <b>Project ▸ Set Activity Values</b>
 * without touching code. How long it waits is a project variable for the same reason.
 */
final class Rest {

    private Rest() {}

    static void define() {
        Activities.define("Rest", ctx -> {
            Duration length = Wire.declares("restBetween")
                    ? Wire.duration("restBetween")
                    : Duration.ofMinutes(1);
            Wait.time(length);
            return ctx.done();
        });
    }
}
