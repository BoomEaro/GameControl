package ru.boomearo.gamecontrol.objects.arena;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.*;
import org.bukkit.entity.Player;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.states.game.IGameState;

/**
 * Абстрактное представление арены, в которой могут быть игроки и свои состояния активности
 */
public abstract class AbstractGameArena<T extends IGamePlayer> {

    private final String name;
    private final World world;

    private final Material icon;

    private volatile IGameState state = null;

    private final ConcurrentMap<String, T> players = new ConcurrentHashMap<>();

    public AbstractGameArena(String name, World world, Material icon) {
        this.name = name;
        this.world = world;
        this.icon = icon;
    }

    /**
     * @return имя арены
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return мир арены
     * @see World
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * @return иконка арены (обычно используется для меню)
     * @see Material
     */
    public Material getIcon() {
        return this.icon;
    }

    /**
     * @return состояния арены
     * @see IGameState
     */
    public IGameState getState() {
        return this.state;
    }

    /**
     * Устанавливает новое состояние игры, а так же вызывает метод initState()
     */
    public void setState(IGameState state) {
        //Игнорируем нулевые состояния игры.
        if (state == null) {
            return;
        }
        IGameState oldState = this.state;

        //Устанавливаем новое
        this.state = state;

        //Вызываем метод инициализации только если убедились в том что до этого было состояние игры
        if (oldState != null) {
            //Инициализируем новое
            this.state.initState();
        }
    }

    /**
     * Добавляет игрока в список активных игроков арены
     */
    public void addPlayer(T player) {
        this.players.put(player.getName(), player);
    }

    /**
     * Удаляет игрока из списк активных игроков арены
     */
    public void removePlayer(String name) {
        this.players.remove(name);
    }

    /**
     * @return Игрок, который находится в этой арене
     * @see IGamePlayer
     */
    public IGamePlayer getGamePlayer(String name) {
        return this.players.get(name);
    }

    /**
     * @return Коллекция, содержащая всех игроков этой арены
     */
    public Collection<T> getAllPlayers() {
        return this.players.values();
    }
    /**
     * Отправляет всем игрокам этой арены указанное сообщение
     * @param msg Сообщение
     */
    public void sendMessages(String msg) {
        sendMessages(msg, null);
    }

    /**
     * Отправляет всем игрокам этой арены указанное сообщение
     * @param msg Сообщение
     * @param ignore Игрок, которому не надо отправлять сообщение
     */
    public void sendMessages(String msg, String ignore) {
        for (T tp : this.players.values()) {
            if (ignore != null) {
                if (tp.getName().equals(ignore)) {
                    continue;
                }
            }

            Player pl = tp.getPlayer();
            if (pl.isOnline()) {
                pl.sendMessage(msg);
            }
        }
    }

    /**
     * Устанавливает всем игрокам этой арены указанный уровень опыта
     */
    public void sendLevels(int level) {
        if (Bukkit.isPrimaryThread()) {
            handleSendLevels(level);
        }
        else {
            Bukkit.getScheduler().runTask(GameControl.getInstance(), () -> {
                handleSendLevels(level);
            });
        }
    }

    private void handleSendLevels(int level) {
        for (T tp : this.players.values()) {
            Player pl = tp.getPlayer();
            if (pl.isOnline()) {
                pl.setLevel(level);
            }
        }
    }

    /**
     * Отправляет всем игрокам этой арены звуковой эффект
     * @param sound Тип звука
     * @param volume Громкость звука
     * @param pitch Тональность звука
     */
    public void sendSounds(Sound sound, float volume, float pitch) {
        sendSounds(sound, volume, pitch, null);
    }

    /**
     * Отправляет всем игрокам этой арены звуковой эффект
     * @param sound Тип звука
     * @param volume Громкость звука
     * @param pitch Тональность звука
     * @param loc Координаты, относительно которых будет проигрываться звук
     */
    public void sendSounds(Sound sound, float volume, float pitch, Location loc) {
        for (T tp : this.players.values()) {
            Player pl = tp.getPlayer();
            if (pl.isOnline()) {
                pl.playSound((loc != null ? loc : pl.getLocation()), sound, volume, pitch);
            }
        }
    }

    /**
     * Отправляет всем игрокам этой арены тайтл
     * @param first Первое сообщение тайтла
     * @param second Второе сообщение тайтла
     * @param in Время в тиках перед появлением тайтла
     * @param stay Время в тиках до удаления тайтла
     * @param out Время в тиках удаление тайтла
     */
    public void sendTitle(String first, String second, int in, int stay, int out) {
        for (T sp : this.players.values()) {
            Player pl = sp.getPlayer();
            if (pl.isOnline()) {
                pl.sendTitle(first, second, in, stay, out);
            }
        }
    }

    /**
     * @return игра, в которой находится эта арена
     * @see IGameManager
     */
    public abstract IGameManager<T> getManager();

    /**
     * @return минимальное количество игроков для старта в игре
     */
    public abstract int getMinPlayers();

    /**
     * @return максимальное количество игроков которое может быть одновременно в этой арене
     */
    public abstract int getMaxPlayers();

}
