package ca.nagasonic.skonic.elements.citizens.classes;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.skriptlang.skript.lang.converter.Converters;

@SuppressWarnings({"unused", "deprecation"})
public class Types {
    static {
        Classes.registerClass(new ClassInfo<>(NPC.class, "npc")
                .user("npcs?")
                .name("Citizens NPC")
                .description("Represents a Citizens NPC.")
                .examples("last spawned npc", "delete last spawned npc")
                .since("1.0.0")
                .parser(new Parser<NPC>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    @NonNull
                    public String toString(NPC npc, int flags) {
                        return "citizen with id " + npc.getId();
                    }

                    @Override
                    @NonNull
                    public String toVariableNameString(NPC npc) {
                        return "citizen:" + npc.getId();
                    }
                })
                .changer(new Changer<NPC>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
                        if (mode == ChangeMode.DELETE) return CollectionUtils.array();
                        return null;
                    }

                    @SuppressWarnings("NullableProblems")
                    @Override
                    public void change(NPC[] what, @Nullable Object[] delta, Changer.ChangeMode mode) {
                        if (mode == ChangeMode.DELETE) {
                            for (NPC npc : what) {
                                npc.destroy();
                            }
                        }
                    }
                }));
        Converters.registerConverter(NPC.class, Entity.class, NPC::getEntity);
    }
}
