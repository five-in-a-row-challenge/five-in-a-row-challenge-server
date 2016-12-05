package com.gomoku.game;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("game")
public class GameProperties {

    private int lengthOfOneRoundInMinutes;

    private int lengthOfTheGameInMinutes;

    public GameProperties() {

    }

    public GameProperties(final int lengthOfOneRoundInMinutes, final int lengthOfTheGameInMinutes) {
        this.lengthOfOneRoundInMinutes = lengthOfOneRoundInMinutes;
        this.lengthOfTheGameInMinutes = lengthOfTheGameInMinutes;
    }

    public int getLengthOfOneRoundInMinutes() {
        return lengthOfOneRoundInMinutes;
    }

    public void setLengthOfOneRoundInMinutes(final int lengthOfOneRoundInMinutes) {
        this.lengthOfOneRoundInMinutes = lengthOfOneRoundInMinutes;
    }

    public int getLengthOfTheGameInMinutes() {
        return lengthOfTheGameInMinutes;
    }

    public void setLengthOfTheGameInMinutes(final int lengthOfTheGameInMinutes) {
        this.lengthOfTheGameInMinutes = lengthOfTheGameInMinutes;
    }

}
