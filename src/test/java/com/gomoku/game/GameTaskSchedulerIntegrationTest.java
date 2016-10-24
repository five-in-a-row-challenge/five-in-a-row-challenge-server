package com.gomoku.game;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gomoku.history.History;
import com.gomoku.player.Player;
import com.gomoku.repository.HistoryRepository;

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
    private HistoryRepository historyRepository;

    @Autowired
    private ScheduledExecutorService scheduler;

    private GameTaskScheduler underTest;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        underTest = new GameTaskScheduler(gameTask, historyRepository, scheduler, LENGTH_OF_ONE_ROUND_IN_MINUTES,
                LENGTH_OF_THE_GAME_IN_MINUTES);
        historyRepository.deleteAll();
    }

    @Test
    public void shoudPlayMatchToEveryOneWithEveryOne() {
        // GIVEN
        final GameTaskResult anyGameTaskResult = new GameTaskResult(ofNullable(null), emptyList());
        when(gameTask.matchAgainstEachOther(Mockito.any(Player.class), Mockito.any(Player.class))).thenReturn(anyGameTaskResult);
        final Player firstPlayer = new Player("player1", "http://localhost:8080");
        final Player secondPlayer = new Player("player2", "http://localhost:8081");

        // WHEN
        underTest.startAndScheduleGames(asList(firstPlayer, secondPlayer));

        // THEN
        verify(gameTask).matchAgainstEachOther(firstPlayer, secondPlayer);
        verify(gameTask).matchAgainstEachOther(secondPlayer, firstPlayer);
    }

    @Test
    public void shouldStoreHistoryAfterGame() {
        // GIVEN
        final GameTaskResult anyGameTaskResult = new GameTaskResult(ofNullable(null), emptyList());
        when(gameTask.matchAgainstEachOther(Mockito.any(Player.class), Mockito.any(Player.class))).thenReturn(anyGameTaskResult);
        final Player firstPlayer = new Player("player1", "http://localhost:8080");
        final Player secondPlayer = new Player("player2", "http://localhost:8081");

        // WHEN
        underTest.startAndScheduleGames(asList(firstPlayer, secondPlayer));

        // THEN
        final List<History> histories = historyRepository.findAll();
        Assert.assertEquals(histories.size(), 2);
    }
}
