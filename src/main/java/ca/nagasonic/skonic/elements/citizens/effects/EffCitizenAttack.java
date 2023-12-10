package ca.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Attack")
@Description("Make a citizen attack an entity")
@Since("1.0.0")
@Examples("")
@RequiredPlugins("Citizens")
public class EffCitizenAttack extends Effect {
    static {
        Skript.registerEffect(EffCitizenAttack.class, "make (citizen|npc) %number% (attack|fight) %entity%");
    }

    private Expression<Number> id;
    private Expression<Entity> victim;

    @Override
    protected void execute(Event e) {
        //Check if ID is null
        if (id.getSingle(e) != null){
            NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
            //Check if there is a citizen with the ID
            if (npc != null){
                npc.getNavigator().setTarget(victim.getSingle(e), true);
            }else Bukkit.getLogger().log(Level.SEVERE, "There is no npc with ID " + id.getSingle(e));
        }else Bukkit.getLogger().log(Level.SEVERE, "The Specified ID is null");

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make citizen with id " + id.getSingle(e) + " attack entity " + victim.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        victim = (Expression<Entity>) exprs[1];
        return true;
    }
}
