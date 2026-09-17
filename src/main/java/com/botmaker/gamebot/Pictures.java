package com.botmaker.gamebot;

import com.botmaker.sdk.api.vision.ImageTemplate;

/**
 * The pictures this bot looks for, one field each.
 *
 * <p>An {@link ImageTemplate} names a file under {@code src/main/resources/images}. Replace those files with
 * ✂ <b>Capture Templates</b> in BotMaker Studio, keeping the names, and the same code starts working against
 * your game — nothing here has to be edited to do that.
 *
 * <p>They are built once and held, rather than constructed at each use: a template opens its file, and a bot
 * that polls for a picture once a second would otherwise open the same file once a second.
 */
final class Pictures {

    static final ImageTemplate COLLECT = new ImageTemplate("src/main/resources/images/collect.png");
    static final ImageTemplate BATTLE = new ImageTemplate("src/main/resources/images/battle.png");
    static final ImageTemplate VICTORY = new ImageTemplate("src/main/resources/images/victory.png");
    static final ImageTemplate DEFEAT = new ImageTemplate("src/main/resources/images/defeat.png");
    static final ImageTemplate HOME = new ImageTemplate("src/main/resources/images/home.png");

    private Pictures() {}
}
