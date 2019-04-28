package io.github.armani.common.protocol.serialize;

import lombok.Getter;

public enum SerializerAlgorithm {

    JSON((byte) 0);

    @Getter
    private byte code;

    SerializerAlgorithm(Byte code) {
        this.code = code;
    }

    public static SerializerAlgorithm toAlgorithm(byte code) {
        for (SerializerAlgorithm algorithm : SerializerAlgorithm.values())
            if (algorithm.getCode() == code) {
                return algorithm;
            }
        return null;
    }
}
