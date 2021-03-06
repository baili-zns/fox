package com.github.wenbo2018.fox.common.codec.invoker;

import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.common.codec.Serializer;
import com.github.wenbo2018.fox.common.codec.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by shenwenbo on 2016/10/2.
 */
public class InvokerEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public InvokerEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            InvokeRequest invokeRequest=(InvokeRequest)in;

            String s=invokeRequest.getSerialize();
            Serializer serializer= SerializerFactory.getSerializer(s);
            byte[] data = serializer.serialize(in);
            //写入序列化方式
            out.writeByte(serializer.getSerializerType());
            //写入消息长度
            out.writeInt(data.length);
            //写入消息体
            out.writeBytes(data);
        }
    }
}
