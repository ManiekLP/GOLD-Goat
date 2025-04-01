package pl.maniek.goldKoza.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ChatUtil
{
    public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    public static String fixColor(final String text) {
        final String[] texts = text.split(String.format("((?<=%1$s)|(?=%1$s))", "&"));
        final StringBuilder finalText = new StringBuilder();
        for (int i = 0; i < texts.length; ++i) {
            if (texts[i].equalsIgnoreCase("&")) {
                ++i;
                if (texts[i].charAt(0) == '#') {
                    finalText.append(ChatColor.of(texts[i].substring(0, 7)) + texts[i].substring(7));
                }
                else {
                    finalText.append(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&" + texts[i]).replace(">>", "»"));
                }
            }
            else {
                finalText.append(texts[i]);
            }
        }
        return finalText.toString();
    }

    public static List<String> fixLore(final List<String> lore) {
        final ArrayList<String> fixLore = new ArrayList<String>();
        if (lore == null) {
            return fixLore;
        }
        lore.forEach(s -> fixLore.add(fixColor(s)));
        return fixLore;
    }

    public static void sendActionbar(final Player player, final String message) {
        if (player == null || message == null) {
            return;
        }
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        if (!(nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1)).startsWith("v1_9_R") && !nmsVersion.startsWith("v1_8_R")) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(message));
            return;
        }
        try {
            final Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            final Object craftPlayer = craftPlayerClass.cast(player);
            final Class<?> ppoc = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            final Class<?> packet = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            final Class<?> chat = Class.forName("net.minecraft.server." + nmsVersion + (nmsVersion.equalsIgnoreCase("v1_8_R1") ? ".ChatSerializer" : ".ChatComponentText"));
            final Class<?> chatBaseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
            Method method = null;
            if (nmsVersion.equalsIgnoreCase("v1_8_R1")) {
                method = chat.getDeclaredMethod("a", String.class);
            }
            final Object object = nmsVersion.equalsIgnoreCase("v1_8_R1") ? chatBaseComponent.cast(method.invoke(chat, "{'text': '" + message + "'}")) : chat.getConstructor(String.class).newInstance(message);
            final Object packetPlayOutChat = ppoc.getConstructor(chatBaseComponent, Byte.TYPE).newInstance(object, 2);
            final Method handle = craftPlayerClass.getDeclaredMethod("getHandle", (Class<?>[])new Class[0]);
            final Object iCraftPlayer = handle.invoke(craftPlayer, new Object[0]);
            final Field playerConnectionField = iCraftPlayer.getClass().getDeclaredField("playerConnection");
            final Object playerConnection = playerConnectionField.get(iCraftPlayer);
            final Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", packet);
            sendPacket.invoke(playerConnection, packetPlayOutChat);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendTitle(final Player p, final String t, final String s) {
        p.sendTitle(fixColor(t), fixColor(s));
    }

    private ChatUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

