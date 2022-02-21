package ru.boomearo.gamecontrol.objects.states.perms;

/**
 * Маркерный интерфейс, используемый, чтобы разграничить доступ к арене.
 * Классы состояния арены которые реализуют это, будут означать, что если при входе в игру есть эта реализация, предполагается добавление игрока сначала в наблюдатели.
 */
public interface SpectatorFirst extends IStatePerms {

}