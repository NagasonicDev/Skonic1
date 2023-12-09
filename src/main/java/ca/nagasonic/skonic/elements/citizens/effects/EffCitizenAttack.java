package ca.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

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
        NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
        if (npc != null){
            npc.getNavigator().setTarget(victim.getSingle(e), true);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        victim = (Expression<Entity>) exprs[1];
        return true;
    }
}
