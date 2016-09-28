package com.gomoku;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Five in a Row is an abstract strategy board game and is also called Gomoku.
 *
 * @author zeldan
 *
 */
@SpringBootApplication
public class FiveInARowChallengeApplication {

    public static void main(final String[] args) {
        run(FiveInARowChallengeApplication.class, args);
    }
}
