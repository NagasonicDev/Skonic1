package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

public class ExprCitizenEntity extends SimplePropertyExpression<NPC, Entity> {
    static {
        register(ExprCitizenEntity.class, Entity.class, "(citizen|npc) entity", "npc");
    }


    @Override
    protected String getPropertyName() {
        return "citizen entity";
    }

    @Override
    public @Nullable Entity convert(NPC npc) {
        return npc.getEntity();
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }
}
