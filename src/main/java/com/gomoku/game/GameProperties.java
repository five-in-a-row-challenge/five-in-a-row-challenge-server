package com.gomoku.game;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("game")
public class GameProperties {

    private int lengthOfOneRoundInMinutes;

    private int lengthOfTheGameInMinutes;

    public GameProperties() {

    }

    public GameProperties(int lengthOfOneRoundInMinutes, int lengthOfTheGameInMinutes) {
        this.lengthOfOneRoundInMinutes = lengthOfOneRoundInMinutes;
        this.lengthOfTheGameInMinutes = lengthOfTheGameInMinutes;
    }

    public int getLengthOfOneRoundInMinutes() {
        return lengthOfOneRoundInMinutes;
    }

    public void setLengthOfOneRoundInMinutes(int lengthOfOneRoundInMinutes) {
        this.lengthOfOneRoundInMinutes = lengthOfOneRoundInMinutes;
    }

    public int getLengthOfTheGameInMinutes() {
        return lengthOfTheGameInMinutes;
    }

    public void setLengthOfTheGameInMinutes(int lengthOfTheGameInMinutes) {
        this.lengthOfTheGameInMinutes = lengthOfTheGameInMinutes;
    }

}
