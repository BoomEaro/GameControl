package ru.boomearo.gamecontrol.objects;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.exceptions.PlayerGameException;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.defactions.IDefaultAction;
import ru.boomearo.gamecontrol.objects.statistics.IStatisticsManager;

/**
 * Базовое представление об любой игре.
 */
public interface IGameManager<T extends IGamePlayer> {

    /**
     * @return имя игры
     */
    public String getGameName();

    /**
     * @return отображаемое имя игры (обычно с использованием ChatColor)
     */
    public String getGameDisplayName();

    /**
     * @return основной цвет текста игры в чате
     * @see ChatColor
     */
    public ChatColor getMainColor();

    /**
     * @return цвет переменных в тексте
     * @see ChatColor
     */
    public ChatColor getVariableColor();

    /**
     * @return альтернативный цвет текста (для разнообразия)
     * @see ChatColor
     */
    public ChatColor getOtherColor();

    /**
     * @return объект плагина, зарегистрировавший эту игру
     * @see JavaPlugin
     */
    public JavaPlugin getPlugin();

    /**
     * Добавляет игрока в игру. Этот метод вызывает сам GameControl, его не нужно вызывать где-то еще.
     * @return игрок, который успешно присоединился к игре (а то, в какую арену, уже зависит от реализации этого метода)
     * @see T
     */
    public T join(Player pl, String arena, IDefaultAction action) throws ConsoleGameException, PlayerGameException;

    /**
     * Удаляет игрока из игры. Этот метод вызывает сам GameControl, его не нужно вызывать где-то еще.
     */
    public void leave(Player pl, IDefaultAction action) throws ConsoleGameException, PlayerGameException;

    /**
     * @return указанного игрока, если был в игре
     * @see T
     */
    public T getGamePlayer(String name);

    /**
     * @return коллекцию, содержащую всех игроков этой игры
     */
    public Collection<T> getAllPlayers();

    /**
     * @return указанную арену, если она существует в этой игре
     * @see AbstractGameArena<T>
     */
    public AbstractGameArena<T> getGameArena(String name);

    /**
     * @return коллекцию, содержащую все арены этой игры
     */
    public Collection<? extends AbstractGameArena<T>> getAllArenas();

    /**
     * @return менеджер статистики, используемый для записи достижений игроков
     * @see IStatisticsManager
     */
    public IStatisticsManager getStatisticManager();
}
