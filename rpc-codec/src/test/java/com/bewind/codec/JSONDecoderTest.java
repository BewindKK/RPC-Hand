package com.bewind.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONDecoderTest {

    @Test
    public void decode() {
        Encoder encoder=new JSONEncoder();
        TestBean testBean = new TestBean();
        testBean.setAge(18);
        testBean.setName("bewind");
        byte[] encode = encoder.encode(testBean);

        Decoder decoder=new JSONDecoder();
        TestBean decode = decoder.decode(encode, TestBean.class);
        assertEquals(testBean.getName(),decode.getName());
        assertEquals(testBean.getAge(),decode.getAge());
    }
}
