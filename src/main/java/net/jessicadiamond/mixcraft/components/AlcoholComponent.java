package net.jessicadiamond.mixcraft.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AlcoholComponent(int millilitres,float unitsPerShot, float ABV, String color) {

    public static final Codec<AlcoholComponent> CODEC = RecordCodecBuilder.create(builder ->{
        return builder.group(
                Codec.INT.optionalFieldOf("millilitres", 1000).forGetter(AlcoholComponent::millilitres),
                Codec.FLOAT.optionalFieldOf("unitsPerShot", 1f).forGetter(AlcoholComponent::unitsPerShot),
                Codec.FLOAT.optionalFieldOf("ABV", 40f).forGetter(AlcoholComponent::ABV),
                Codec.STRING.optionalFieldOf("color", "WHITE").forGetter(AlcoholComponent::color)
        ).apply(builder, AlcoholComponent::new);
    });

}
