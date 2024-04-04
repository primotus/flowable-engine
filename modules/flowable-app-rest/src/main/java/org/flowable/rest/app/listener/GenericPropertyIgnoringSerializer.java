package org.flowable.rest.app.listener;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.IdentityHashMap;

/*public class GenericPropertyIgnoringSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        JavaType javaType = serializers.constructType(value.getClass());
        BeanDescription beanDesc = serializers.getConfig().introspect(javaType);
        for (BeanPropertyDefinition prop : beanDesc.findProperties()) {
            try {
                Object propValue = prop.getAccessor().getValue(value);
                if (propValue == value) { // Referencia circular simple
                    continue; // Ignora esta propiedad
                }
                gen.writeObjectField(prop.getName(), propValue);
            } catch (Exception e) {
                // Maneja el error o ignora la propiedad
            }
        }
        gen.writeEndObject();
    }
}*/

public class GenericPropertyIgnoringSerializer extends StdSerializer<Object> {
    // ThreadLocal to hold the cache for each serialization operation
    private static final ThreadLocal<IdentityHashMap<Object, Boolean>> cache = ThreadLocal.withInitial(IdentityHashMap::new);

    public GenericPropertyIgnoringSerializer() {
        this(null);
    }

    public GenericPropertyIgnoringSerializer(Class<Object> t) {
        super(t);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (!checkCircularReference(value)) {
            cache.get().put(value, Boolean.TRUE);
            // Serialize normally
            //gen.writeObject(value);
            /*JsonSerializer<Object> serializer = provider.findValueSerializer(value.getClass(), null);
            // Asegúrate de no llamar al mismo serializador para evitar un bucle infinito.
            if (!(serializer instanceof GenericPropertyIgnoringSerializer)) {
                serializer.serialize(value, gen, provider);
            } else {
                // Procesa como un tipo no soportado o maneja de otra manera si el serializador es el mismo
                // Esto podría significar serializar solo ciertos campos, o lanzar un error, etc.
                gen.writeString("notSupported: " + value.getClass().getName());
            }*/
            provider.defaultSerializeValue(value, gen);
        } else {
            // Serialize a placeholder for circular references
            gen.writeStartObject();
            gen.writeStringField("circularReference", value.getClass().getSimpleName());
            gen.writeEndObject();
        }
    }

    // Check for circular references using cache
    private boolean checkCircularReference(Object value) {
        return cache.get().containsKey(value);
    }

    // Call this method at the beginning of the root serialization
    public static void initCache() {
        cache.get().clear();
    }

    // Call this method at the end of the root serialization to avoid memory leaks
    public static void clearCache() {
        cache.remove();
    }
}
