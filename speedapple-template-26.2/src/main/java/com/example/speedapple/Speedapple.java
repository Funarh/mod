package com.example.speedapple;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SpeedAppleMod implements ModInitializer {

    // Создаём наш предмет - Скоростное яблоко
    public static final Item SPEED_APPLE = new Item(new Item.Settings().food(
            new FoodComponent.Builder()
                    .hunger(4)                // Восстанавливает 4 единицы голода
                    .saturationModifier(0.3f) // Насыщение
                    .alwaysEdible()           // Можно есть даже при полном голоде
                    .build()
    )) {
        @Override
        public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
            // Если это серверная сторона (не клиент)
            if (!world.isClient) {
                // Даём эффект скорости на 30 секунд (600 тиков), уровень 1 (усиление)
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
            }
            // Возвращаем обычное поведение (уменьшение стака, звук и т.д.)
            return super.finishUsing(stack, world, user);
        }
    };

    @Override
    public void onInitialize() {
        // Регистрируем предмет в игре под ID "speedapple:speed_apple"
        Registry.register(Registries.ITEM, new Identifier("speedapple", "speed_apple"), SPEED_APPLE);

        System.out.println("Speed Apple Mod загружен!");
    }
}