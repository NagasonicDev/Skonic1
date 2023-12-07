package ca.nagasonic.skonic.elements.items.heads.expressions;

import ca.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import java.util.UUID;

public class ExprHeadFromUUID extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprHeadFromUUID.class, ItemStack.class, ExpressionType.COMBINED,
                "(head|skull) from uuid %string%");
    }
    private Expression<String> uuid;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.uuid = (Expression<String>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable ItemStack[] get(Event event) {
        UUID uuid = UUID.fromString(this.uuid.getSingle(event));
        if (uuid != null) {
            return new ItemStack[]{HeadUtils.headFromUuid(uuid)};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    @NonNull
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    @NonNull
    public String toString(@Nullable Event event, boolean debug) {
        return "head from uuid " + this.uuid;
    }
}
