package ca.nagasonic.skonic.elements.items.heads;

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

public class ExprHeadFromName extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprHeadFromName.class, ItemStack.class, ExpressionType.COMBINED,
                "(head|skull) from name %string%");
    }
    private Expression<String> name;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.name = (Expression<String>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable ItemStack[] get(Event event) {
        String name = this.name.getSingle(event);
        if (name != null) {
            return new ItemStack[]{HeadUtils.headFromName(name)};
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
        return "head from name " + this.name;
    }
}
