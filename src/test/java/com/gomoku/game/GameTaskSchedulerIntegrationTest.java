package com.gomoku.game;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.concurrent.ScheduledExecutorService;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gomoku.player.Player;
import com.gomoku.repository.PlayerRepository;

/**
 * Unit test for {@link GameTaskScheduler}.
 */
@SpringBootTest
public class GameTaskSchedulerIntegrationTest extends AbstractTestNGSpringContextTests {

    private static final int LENGTH_OF_ONE_ROUND_IN_MINUTES = 0;
    private static final int LENGTH_OF_THE_GAME_IN_MINUTES = 0;

    @Mock
    private GameTask gameTask;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScheduledExecutorService scheduler;

    private GameTaskScheduler underTest;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        underTest = new GameTaskScheduler(gameTask, playerRepository, scheduler, LENGTH_OF_ONE_ROUND_IN_MINUTES, LENGTH_OF_THE_GAME_IN_MINUTES);
    }

    @Test
    public void shoudPlayMatchToEveryOneWithEveryOne() {
        // GIVEN
        final GameTaskResult anyGameTaskResult = new GameTaskResult(ofNullable(null), emptyList());
        when(gameTask.matchAgainstEachOther(Mockito.any(Player.class), Mockito.any(Player.class))).thenReturn(anyGameTaskResult);
        final Player firstPlayer = new Player("player1", "http://localhost:8080");
        final Player secondPlayer = new Player("player2", "http://localhost:8081");
        playerRepository.save(firstPlayer);
        playerRepository.save(secondPlayer);

        // WHEN
        underTest.startAndScheduleGames();

        // THEN
        verify(gameTask).matchAgainstEachOther(firstPlayer, secondPlayer);
        verify(gameTask).matchAgainstEachOther(secondPlayer, firstPlayer);
    }
}
