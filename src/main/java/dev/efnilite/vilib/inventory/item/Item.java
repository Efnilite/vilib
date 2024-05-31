package dev.efnilite.vilib.inventory.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.efnilite.vilib.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

/**
 * A class for creating items.
 *
 * @author Efnilite
 */
@SuppressWarnings("unused")
public class Item extends MenuItem {

    private int amount;
    private int modelId = -1;
    private int durability;
    private boolean glowing = false;
    private boolean unbreakable = false;
    private String name;
    private ItemMeta meta;
    private Material material;
    private List<String> lore = new ArrayList<>();
    private Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    /**
     * Creates a new instance
     *
     * @param material The material
     */
    public Item(Material material) {
        this(material, 1, "");
    }

    /**
     * Creates a new instance
     *
     * @param material The material
     * @param name     The name of the item
     */
    public Item(Material material, String name) {
        this(material, 1, name);
    }

    /**
     * Creates a new instance
     *
     * @param material The material
     * @param amount   The amount of the item
     * @param name     The name of the item
     */
    public Item(Material material, int amount, String name) {
        this.amount = amount;

        if (material != null) {
            this.durability = material.getMaxDurability();
        } else {
            material = Material.GRASS_BLOCK;
        }

        this.name = name;
        this.material = material;
    }

    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);

        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        // if this item's meta cant be modified, return itemstack instance
        if (meta == null) {
            return item;
        }

        if (glowing) {
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            for (Enchantment enchantment : enchantments.keySet()) {
                meta.addEnchant(enchantment, enchantments.get(enchantment), true);
            }
        }

        meta.setDisplayName(Strings.colour(name));
        meta.setLore(Strings.colour(lore));
        meta.setCustomModelData(modelId);

        attributes.forEach(meta::addAttributeModifier);

        ((Damageable) meta).setDamage(Math.abs(durability - material.getMaxDurability()));
        meta.setUnbreakable(unbreakable);

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Builds the meta for this item and passes it in f.
     * The result of f is set as the meta for this item.
     * The updated item is returned.
     *
     * @param f The function where the beginning item meta is provided and the final meta returned.
     * @return This item with the returned item meta.
     */
    public ItemStack build(Function<ItemMeta, ItemMeta> f) {
        ItemStack item = build();
        item.setItemMeta(f.apply(item.getItemMeta()));
        return item;
    }

    @SuppressWarnings("all")
    @Override
    public Item clone() {
        Item item = new Item(material, amount, name);

        item.glowing = glowing;
        item.durability = durability;
        item.unbreakable = unbreakable;
        item.meta = meta;
        item.lore = lore;
        item.attributes = attributes;
        item.enchantments = enchantments;

        return item;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    /**
     * Set unbreakable
     *
     * @return the instance of this class
     */
    public Item unbreakable() {
        this.unbreakable = true;
        return this;
    }

    /**
     * Set glowing
     *
     * @return the instance of this class
     */
    public Item glowing() {
        this.glowing = true;
        return this;
    }

    /**
     * Set glowing
     *
     * @return the instance of this class
     */
    public Item glowing(boolean predicate) {
        if (predicate) {
            this.glowing = true;
        }
        return this;
    }

    /**
     * Sets the name
     *
     * @param name The name
     * @return the instance of this class
     */
    public Item name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the ItemMeta
     *
     * @param meta The meta
     * @return the instance of this class
     */
    public Item meta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Sets the durability of the item
     *
     * @return the instance of this class
     */
    public Item durability(int durability) {
        this.durability = durability;
        return this;
    }


    /**
     * Sets the item amount
     *
     * @param amount The item amount
     * @return the instance of this class
     */
    public Item amount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Sets the type
     *
     * @param material The type
     * @return the instance of this class
     */
    public Item material(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Sets the lore
     *
     * @param lore The lore
     * @return the instance of this class
     */
    public Item lore(@Nullable List<String> lore) {
        if (lore != null) {
            this.lore = lore;
        }

        return this;
    }

    /**
     * Sets the lore
     *
     * @param lore The lore
     * @return the instance of this class
     */
    public Item lore(String... lore) {
        return lore(List.of(lore));
    }

//    /**
//     * Sets the lore supplier.
//     *
//     * @param supplier The lore supplier.
//     * @return the instance of this class
//     */
//    public Item lore(@Nullable Supplier<List<String>> supplier) {
//        if (supplier != null) {
//            this.lore = lore;
//        }
//
//        return this;
//    }

    /**
     * Enchants this item with a specific enchantment and a provided level.
     * This ignores enchantment level limit restrictions.
     *
     * @param enchantment The enchantment instance.
     * @param level       The level.
     * @return the instance of this class
     */
    public Item enchant(@NotNull Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);

        return this;
    }

    /**
     * Adds the provided attribute to this item, with the specified value and the operation.
     * Example:
     * <br>
     * <code>
     * item.attribute(Attribute.GENERIC_ATTACK_SPEED, -10, AttributeModifier.Operation.ADD_NUMBER)
     * </code>
     *
     * @param attribute The attribute
     * @param value     The value
     * @param operation The operation
     * @return the instance of this class
     * @see AttributeModifier.Operation
     */
    public Item attribute(@NotNull Attribute attribute, double value, AttributeModifier.Operation operation) {
        attributes.put(attribute, new AttributeModifier(attribute.getKey().getKey(), value, operation));

        return this;
    }

    /**
     * Adds the provided attribute to this item, with the specified value and the operation, only applying to a specific slot.
     *
     * @param attribute The attribute
     * @param value     The value
     * @param operation The operation
     * @param slot      The slot
     * @return the instance of this class
     * @see AttributeModifier.Operation
     */
    public Item attribute(@NotNull Attribute attribute, double value, @NotNull AttributeModifier.Operation operation, @NotNull EquipmentSlot slot) {
        attributes.put(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getKey().getKey(), value, operation, slot));

        return this;
    }

    /**
     * Adds the provided {@link AttributeModifier} to the item with {@link Attribute} used as identification.
     *
     * @param attribute The attribute.
     * @param modifier  The modifier.
     * @return the instance of this class
     */
    public Item attribute(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        attributes.put(attribute, modifier);

        return this;
    }

    /**
     * Sets this item's model id
     *
     * @param modelId The model id
     * @return the instance of this class
     */
    public Item modelId(int modelId) {
        this.modelId = modelId;

        return this;
    }

    /**
     * Modifies the lore line by line.
     * Useful for updating items.
     *
     * @param function The function. The line of lore is given, and it must return the modified version of that lore line
     * @return the instance of this class
     */
    public Item modifyLore(Function<String, String> function) {
        this.lore = lore.stream()
                .map(function)
                .toList();
        return this;
    }

    /**
     * Modifies the name of an item.
     * Useful for updating items.
     *
     * @param function The function. The title is given, and it must return an altered version of this title.
     * @return the instance of this class
     */
    public Item modifyName(Function<String, String> function) {
        name = function.apply(name);
        return this;
    }

    /**
     * Gets the amount
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the lore
     *
     * @return the lore
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Gets the item type
     *
     * @return the type
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Gets the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
