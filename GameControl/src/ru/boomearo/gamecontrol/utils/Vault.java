package ru.boomearo.gamecontrol.utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {
	
	public static Economy econ = null;

	public static boolean hasVault() {
		return Bukkit.getServer().getPluginManager().getPlugin("Vault") != null;
	}
	
	public static boolean setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	@SuppressWarnings("deprecation")
	public static double getMoney(String player) {
		return econ.getBalance(player);
	}
	
	@SuppressWarnings("deprecation")
	public static void removeMoney(String player, double amount) {
		econ.withdrawPlayer(player, amount);
	}
	
	@SuppressWarnings("deprecation")
	public static void addMoney(String player, double amount) {
		econ.depositPlayer(player, amount);
	}
	
	
}