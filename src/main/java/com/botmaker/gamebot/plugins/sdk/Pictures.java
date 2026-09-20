package com.botmaker.gamebot.plugins.sdk;

import com.botmaker.plugin.basics.managed.Managed;
import com.botmaker.sdk.api.vision.ImageTemplate;

/**
 * The pictures this bot looks for, one constant each.
 *
 * <p>Managed by <i>🖼 Manage Pictures</i>, which adds, renames and removes these together with the image
 * files they point at and every use of them in your code. That is why the whole class is
 * {@code @Managed("pictures")} and the code canvas will not edit it: the canvas can rename the constant it
 * is looking at, and nothing else, which leaves the bot calling a name that is gone.
 *
 * <p>Everywhere else this is an ordinary class. Your code reads {@code Pictures.COLLECT} like any other
 * constant, a misspelling is a compile error, and a developer with no BotMaker installed can read the whole
 * of what this bot watches for by opening one file.
 *
 * <p>An {@link ImageTemplate} names a file under {@code src/main/resources/images}. The ones this template
 * ships are blank placeholders: replace those files with ✂ <b>Capture Templates</b>, keeping the names, and
 * the same code starts working against your game.
 *
 * <p>They are built once and held, rather than constructed at each use: a template opens its file, and a bot
 * that polls for a picture once a second would otherwise open the same file once a second.
 */
@Managed("pictures")
public final class Pictures {

    public static final ImageTemplate COLLECT = new ImageTemplate("src/main/resources/images/collect.png");
    public static final ImageTemplate BATTLE = new ImageTemplate("src/main/resources/images/battle.png");
    public static final ImageTemplate VICTORY = new ImageTemplate("src/main/resources/images/victory.png");
    public static final ImageTemplate DEFEAT = new ImageTemplate("src/main/resources/images/defeat.png");
    public static final ImageTemplate HOME = new ImageTemplate("src/main/resources/images/home.png");

    private Pictures() {}
}
