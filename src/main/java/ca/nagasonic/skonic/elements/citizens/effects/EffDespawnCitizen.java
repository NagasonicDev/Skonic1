package ca.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

public class EffDespawnCitizen extends Effect {
    static {
        Skript.registerEffect(EffDespawnCitizen.class, "[(citizen|npc)] despawn %npc%",
                "[(citizen|npc)] respawn %npc% at %direction% %location%");
    }

    private int pattern;
    private Expression<NPC> npc;
    private Expression<Location> loc;

    @Override
    protected void execute(Event e) {
        Location location = loc != null ? loc.getSingle(e) : null;
        for (NPC npcs : npc.getArray(e)) {
            if (this.pattern == 0) {
                npcs.despawn(DespawnReason.PLUGIN);
            } else if (location != null) {
                npcs.spawn(location, SpawnReason.PLUGIN);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        pattern = matchedPattern;
        npc = (Expression<NPC>) exprs[0];
        if (matchedPattern == 1) {
            loc = Direction.combine((Expression<? extends Direction>) exprs[1], (Expression<? extends Location>) exprs[2]);
        }
        return true;
    }
}
