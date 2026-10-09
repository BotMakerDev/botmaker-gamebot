package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.sdk.api.bot.Outcome;
import com.botmaker.sdk.api.bot.SdkValue;

/**
 * What this bot's activities can report, one constant each — the ports on the Activity Flow's cards.
 *
 * <p>Managed by <i>Project ▸ Activity Flow</i>, which adds, renames and removes these together with every use of
 * them: the arrows in {@code Sdk.java}, and every body that returns one. Every activity also has
 * {@code Outcome.NEXT}, "nothing special to report", without declaring it here.
 *
 * <p>Everywhere else this is an ordinary class: a body returns {@code Outcomes.WON}, and a misspelled outcome is
 * a compile error rather than a run that quietly ends.
 */
@SdkValue(SdkValue.Id.OUTCOMES)
public final class Outcomes {

    public static final Outcome NOTHING_LEFT = Outcome.named("Nothing left");
    public static final Outcome WON = Outcome.named("Won");
    public static final Outcome LOST = Outcome.named("Lost");

    private Outcomes() {}
}
