package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.sdk.api.bot.Outcome;
import com.botmaker.sdk.api.bot.SdkValue;

/**
 * What this bot's activities can report, one constant each — the ports on the Activity Flow's cards, labelled
 * with the constant's name as words ({@code NOTHING_LEFT} is "Nothing left").
 *
 * <p>Managed by <i>Project ▸ Activity Flow</i>, which adds, renames and removes these together with every use of
 * them: the arrows in {@code Sdk.java}, and every body that returns one. Every activity also has
 * {@code Outcome.NEXT}, "nothing special to report", without declaring it here.
 *
 * <p>Everywhere else this is an ordinary enum: a body returns {@code Outcomes.WON}, and a misspelled outcome is
 * a compile error rather than a run that quietly ends.
 */
@SdkValue(SdkValue.Id.OUTCOMES)
public enum Outcomes implements Outcome {
    NOTHING_LEFT, WON, LOST
}
