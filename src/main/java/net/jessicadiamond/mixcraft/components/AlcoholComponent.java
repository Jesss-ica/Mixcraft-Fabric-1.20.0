package net.jessicadiamond.mixcraft.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AlcoholComponent(int millilitres,float unitsPerShot, float ABV) {

    public static final Codec<AlcoholComponent> CODEC = RecordCodecBuilder.create(builder ->{
        return builder.group(
                Codec.INT.optionalFieldOf("millilitres", 1000).forGetter(AlcoholComponent::millilitres),
                Codec.FLOAT.optionalFieldOf("unitsPerShot", 1f).forGetter(AlcoholComponent::unitsPerShot),
                Codec.FLOAT.optionalFieldOf("ABV", 40f).forGetter(AlcoholComponent::ABV)
        ).apply(builder, AlcoholComponent::new);
    });

}
