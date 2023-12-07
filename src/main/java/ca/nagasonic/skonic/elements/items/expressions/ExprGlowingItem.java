package ca.nagasonic.skonic.elements.items.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class ExprGlowingItem extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprGlowingItem.class, ItemStack.class, ExpressionType.COMBINED,
                "(glowing|shiny) %itemstack%");
    }
    private Expression<ItemStack> item;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.item = (Expression<ItemStack>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable ItemStack[] get(Event event) {
        ItemStack item = this.item.getSingle(event);
        if (item != null) {
            if (item.getData().getItemType() == Material.FISHING_ROD) {
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(Enchantment.LOYALTY, 1, false);
                item.setItemMeta(meta);
                return new ItemStack[]{item};
            }else{
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(Enchantment.LUCK, 1, false);
                item.setItemMeta(meta);
                return new ItemStack[]{item};
            }
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
        return "glowing " + this.item;
    }
}
