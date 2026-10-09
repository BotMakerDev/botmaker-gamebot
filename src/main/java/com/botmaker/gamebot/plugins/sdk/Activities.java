package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.flow.Activity;

/**
 * This bot's activities, one constant each — the cards of <i>Project ▸ Activity Flow</i>, labelled with the
 * constant's name as words ({@code COLLECT} is "Collect"), which the canvas draws and the run's trace prints.
 *
 * <p>Managed by the Activity Flow, which adds, renames and removes these together with the cards and every use
 * of them: the flow in {@code Sdk.java}, and any {@code ActivitySwitch.disable(Activities.BATTLE)} in your code.
 *
 * <p>Everywhere else this is an ordinary enum: a misspelled activity is a compile error.
 */
@SdkValue(SdkValue.Id.ACTIVITIES)
public enum Activities implements Activity {
    COLLECT, BATTLE, REST
}
