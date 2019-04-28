package io.github.armani.common.protocol.serialize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializeFactory {

    public static final Serializer DEFAULT = new FastJSONSerializer();

    private static final Map<SerializerAlgorithm, Serializer> serializerTable = new ConcurrentHashMap();


    static {
        serializerTable.put(SerializerAlgorithm.JSON, new FastJSONSerializer());
    }

    public static Serializer get(SerializerAlgorithm algorithm) {
        return serializerTable.get(algorithm);
    }

    public static Serializer get(byte algorithmCode) {
        return serializerTable.get(SerializerAlgorithm.toAlgorithm(algorithmCode));
    }
}
