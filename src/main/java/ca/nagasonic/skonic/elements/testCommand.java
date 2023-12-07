package ca.nagasonic.skonic.elements;

import ca.nagasonic.skonic.elements.util.MojangSkinGenerator;
import com.google.gson.JsonObject;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.Placeholders;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.ExecutionException;

public class testCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(Integer.parseInt(args[0]));
        SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
        JsonObject data = null;
        try {
            data = MojangSkinGenerator.generateFromURL(args[1], false);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
        String uuid = data.get("uuid").getAsString();
        JsonObject texture = data.get("texture").getAsJsonObject();
        String textureEncoded = texture.get("value").getAsString();
        String signature = texture.get("signature").getAsString();
        sender.sendMessage(uuid);
        sender.sendMessage(textureEncoded);
        sender.sendMessage(signature);
        return true;
    }
}
