package com.gomoku.game.task;

import static com.gomoku.game.GameStatus.FINISHED;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gomoku.game.Game;
import com.gomoku.game.repository.GameRepository;
import com.gomoku.history.History;
import com.gomoku.history.repository.HistoryRepository;
import com.gomoku.player.Player;
import com.gomoku.score.Score;
import com.gomoku.score.ScoreType;
import com.gomoku.score.repository.ScoreRepository;

/**
 * Unit test for {@link GameTaskScheduler}.
 */
@SpringBootTest
@TestPropertySource(value = "classpath:application-test.properties")
public class GameTaskSchedulerIntegrationTest extends AbstractTestNGSpringContextTests {

    private static final int LENGTH_OF_ONE_ROUND_IN_MINUTES = 0;
    private static final int LENGTH_OF_THE_GAME_IN_MINUTES = 0;

    private static final Player FIRST_PLAYER = new Player("player1", "http://localhost:8080");
    private static final Player SECOND_PLAYER = new Player("player2", "http://localhost:8081");

    @Mock
    private GameTask gameTask;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ScheduledExecutorService scheduler;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    private GameTaskScheduler underTest;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        underTest = new GameTaskScheduler(gameTask, historyRepository, scheduler, gameRepository, scoreRepository, LENGTH_OF_ONE_ROUND_IN_MINUTES,
                LENGTH_OF_THE_GAME_IN_MINUTES);
        historyRepository.deleteAll();
        scoreRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void shoudPlayMatchToEveryOneWithEveryOne() {
        // GIVEN
        final Game savedGame = gameRepository.save(new Game());
        final GameTaskResult anyGameTaskResult = new GameTaskResult(ofNullable(null), emptyList());
        when(gameTask.matchAgainstEachOther(Mockito.any(Player.class), Mockito.any(Player.class))).thenReturn(anyGameTaskResult);

        // WHEN
        underTest.startAndScheduleGames(savedGame.getId(), asList(FIRST_PLAYER, SECOND_PLAYER));

        // THEN
        verify(gameTask).matchAgainstEachOther(FIRST_PLAYER, SECOND_PLAYER);
        verify(gameTask).matchAgainstEachOther(SECOND_PLAYER, FIRST_PLAYER);
    }

    @Test
    public void shouldStoreHistoryAfterGame() {
        // GIVEN
        final Game savedGame = gameRepository.save(new Game());
        final GameTaskResult anyGameTaskResult = new GameTaskResult(ofNullable(null), emptyList());
        when(gameTask.matchAgainstEachOther(Mockito.any(Player.class), Mockito.any(Player.class))).thenReturn(anyGameTaskResult);

        // WHEN
        underTest.startAndScheduleGames(savedGame.getId(), asList(FIRST_PLAYER, SECOND_PLAYER));

        // THEN
        final List<History> histories = historyRepository.findAll();
        assertEquals(histories.size(), 2);
    }

    @Test
    public void shouldSetGameStatusToFinishedAfterGame() {
        // GIVEN
        final Game savedGame = gameRepository.save(new Game());
        final GameTaskResult anyGameTaskResult = new GameTaskResult(ofNullable(null), emptyList());
        when(gameTask.matchAgainstEachOther(Mockito.any(Player.class), Mockito.any(Player.class))).thenReturn(anyGameTaskResult);

        // WHEN
        underTest.startAndScheduleGames(savedGame.getId(), asList(FIRST_PLAYER, SECOND_PLAYER));

        // THEN
        final Game actualGame = gameRepository.findOne(savedGame.getId());
        assertEquals(actualGame.getGameStatus(), FINISHED);
    }

    @Test
    public void shouldFirstPlayerGetTwoWinnerScores() {
        // GIVEN
        final Game savedGame = gameRepository.save(new Game());
        final GameTaskResult anyGameTaskResult = new GameTaskResult(of(FIRST_PLAYER), emptyList());
        when(gameTask.matchAgainstEachOther(Mockito.any(Player.class), Mockito.any(Player.class)))
                .thenReturn(anyGameTaskResult)
                .thenReturn(anyGameTaskResult);

        // WHEN
        underTest.startAndScheduleGames(savedGame.getId(), asList(FIRST_PLAYER, SECOND_PLAYER));

        // THEN
        final List<Score> scores = scoreRepository.findAll();
        assertEquals(scores.size(), 2);
        assertEquals(scores.get(0).getPlayer(), FIRST_PLAYER.getUserName());
        assertEquals(scores.get(0).getScore(), ScoreType.VICTORY.getScore());
        assertEquals(scores.get(1).getPlayer(), FIRST_PLAYER.getUserName());
        assertEquals(scores.get(1).getScore(), ScoreType.VICTORY.getScore());
    }
}
