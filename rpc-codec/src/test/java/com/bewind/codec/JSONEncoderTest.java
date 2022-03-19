package com.bewind.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder=new JSONEncoder();
        TestBean testBean = new TestBean();
        testBean.setAge(18);
        testBean.setName("bewind");
        byte[] encode = encoder.encode(testBean);
        assertNotNull(encode);
    }
}
