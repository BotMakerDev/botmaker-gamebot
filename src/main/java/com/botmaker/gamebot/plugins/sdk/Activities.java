package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.flow.Activity;

/**
 * This bot's activities, one constant each — the cards of <i>Project ▸ Activity Flow</i>.
 *
 * <p>Managed by the Activity Flow, which adds, renames and removes these together with the cards and every use
 * of them: the flow in {@code Sdk.java}, and any {@code ActivitySwitch.disable(Activities.BATTLE)} in your code.
 * The string inside is the card's label, which the canvas draws and the run's trace prints.
 *
 * <p>Everywhere else this is an ordinary class: a misspelled activity is a compile error.
 */
@SdkValue(SdkValue.Id.ACTIVITIES)
public final class Activities {

    public static final Activity COLLECT = Activity.named("Collect");
    public static final Activity BATTLE = Activity.named("Battle");
    public static final Activity REST = Activity.named("Rest");

    private Activities() {}
}
