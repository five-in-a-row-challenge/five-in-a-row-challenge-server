package com.gomoku;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.gomoku.board.BoardProperties;
import com.gomoku.game.GameProperties;

/**
 * Five in a Row is an abstract strategy board game and is also called Gomoku.
 *
 * @author zeldan
 *
 */
@SpringBootApplication
@EnableMongoAuditing
@EnableConfigurationProperties({ BoardProperties.class, GameProperties.class })
public class FiveInARowChallengeApplication {

    public static void main(final String[] args) {
        run(FiveInARowChallengeApplication.class, args);
    }
}
