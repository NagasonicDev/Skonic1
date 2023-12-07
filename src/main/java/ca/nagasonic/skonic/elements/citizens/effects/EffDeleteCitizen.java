package ca.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Delete Citizen")
@Description("Destroy a citizen by id.")
@Since("1.0.0")
@Examples("")
@RequiredPlugins("Citizens")
public class EffDeleteCitizen extends Effect {
    static {
        Skript.registerEffect(EffDeleteCitizen.class, "delete citizen %number%");
    }

    private Expression<Number> id;

    @Override
    protected void execute(Event e) {
        if (id != null && CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue()) != null){
            NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
            if (npc != null && npc.getOwningRegistry() != null){
                npc.destroy();
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        return true;
    }
}
