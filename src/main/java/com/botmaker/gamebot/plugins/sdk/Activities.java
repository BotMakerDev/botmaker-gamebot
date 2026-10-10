package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.sdk.api.bot.Outcome;
import com.botmaker.sdk.api.bot.SdkValue;

/**
 * This bot's activities, one enum each, and what each can report, one constant each — the cards of
 * <i>Project ▸ Activity Flow</i> and the ports on them, labelled with their names as words ({@code NOTHING_LEFT}
 * is "Nothing left").
 *
 * <p>An activity's body returns its own enum — {@code public static Activities.Collect body()} — so a body
 * reporting another activity's outcome is a compile error, not a run that quietly ends.
 *
 * <p>Managed by the Activity Flow, which adds, renames and removes these together with the cards and every use
 * of them: the flow in {@code Sdk.java}, every body returning one, and any
 * {@code ActivitySwitch.disable(Activities.Battle.class)} in your code. {@code NEXT} is an ordinary constant the
 * Activity Flow puts in each new activity: rename it, or remove it once the activity has another.
 */
@SdkValue(SdkValue.Id.ACTIVITIES)
public final class Activities {

    public enum Collect implements Outcome { NEXT, NOTHING_LEFT }

    public enum Battle implements Outcome { WON, LOST }

    public enum Rest implements Outcome { NEXT }

    private Activities() {}
}
