package ru.boomearo.gamecontrol.objects;

import org.bukkit.entity.Player;

import ru.boomearo.board.Board;
import ru.boomearo.board.exceptions.BoardException;
import ru.boomearo.board.objects.PlayerBoard;
import ru.boomearo.board.objects.boards.AbstractPageList;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;

/**
 * Базовое представление об игроке, который будет участвовать в любых играх.
 */
public interface IGamePlayer {

    /**
     * @return ник игрока
     */
    public String getName();

    /**
     * @return объект игрока
     * @see Player
     */
    public Player getPlayer();

    /**
     * @return арена, в которой участвует игрок
     * @see AbstractGameArena
     */
    public AbstractGameArena<? extends IGamePlayer> getArena();

    /**
     * Устанавливает правую панель статистики этому игроку.
     * @param pageList Панель статистики. Если указан null, панель будет сброшена.
     */
    public default void sendBoard(AbstractPageList pageList) {
        PlayerBoard pb = Board.getInstance().getBoardManager().getPlayerBoard(getName());
        if (pb != null) {
            try {
                if (pageList == null) {
                    pb.setNewPageList(Board.getInstance().getBoardManager().getPageListFactory().createPageList(pb));
                    return;
                }

                pb.setNewPageList(pageList);
            }
            catch (BoardException e) {
                e.printStackTrace();
            }
        }
    }
}
