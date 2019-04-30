package io.github.armani.common.protocol.packet;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.armani.common.utils.ToJSON;

public abstract class Packet implements ToJSON {

    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
