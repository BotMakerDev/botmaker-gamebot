package com.botmaker.gamebot;

import com.botmaker.sdk.api.bot.Activities;
import com.botmaker.sdk.api.interaction.Wait;
import com.botmaker.sdk.api.vision.ImageClicker;
import com.botmaker.sdk.api.vision.ImageFinder;

/**
 * Starts a fight and waits for it to end, reporting which way it went.
 *
 * <p>The shape to copy for anything that takes time: press the button, then poll for the two pictures that
 * mean it is over, with a bounded number of attempts so a game that shows neither ends the activity instead
 * of hanging it.
 */
final class Battle {

    private Battle() {}

    static void define() {
        Activities.define("Battle", ctx -> {
            if (!ImageClicker.click(Pictures.BATTLE)) {
                // No fight available. Treated as a loss so the flow rests and comes back, rather than
                // needing a third outcome for "could not even start".
                return ctx.outcome("LOST");
            }

            for (int attempt = 0; Gamebot.keepTrying(attempt); attempt++) {
                if (ImageFinder.find(Pictures.VICTORY)) return ctx.outcome("WON");
                if (ImageFinder.find(Pictures.DEFEAT)) return ctx.outcome("LOST");
                Wait.seconds(1);
            }
            // Neither picture ever appeared. Something is on screen that this bot does not know about, so
            // report the outcome that leads somewhere safe and let goHome sort it out.
            return ctx.outcome("LOST");
        });
    }
}
