package com.botmaker.gamebot;

import com.botmaker.sdk.api.bot.Activities;
import com.botmaker.sdk.api.interaction.Wait;

/**
 * Does nothing, for as long as {@link Parameters#restBetween} says.
 *
 * <p>Worth having as an activity rather than a {@code sleep} in the middle of another one: it is a node on
 * the canvas, so it can be re-routed or switched off without touching code. How long it waits is a
 * parameter for the same reason — and reading it is one field access, with no name to misspell and no
 * fallback to write, because the declaration is right there in {@link Parameters}.
 */
final class Rest {

    private Rest() {}

    static void define() {
        Activities.define("Rest", ctx -> {
            Wait.time(Parameters.restBetween);
            return ctx.done();
        });
    }
}
