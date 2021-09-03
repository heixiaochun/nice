package com.example.nice.common.logging;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;

/**
 * 支持多次读取的InputStream
 * 参考：https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times
 *
 * @author heixiaochun
 */
public class CachedBodyServletInputStream extends ServletInputStream {

    /***
     * 缓存在内存中的请求Body
     */
    private final ByteArrayInputStream cachedBodyInputStream;

    /**
     * 内部使用ByteArrayInputStream实现
     */
    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    /**
     * 通过ByteArrayInputStream的available方法判定读取是否完成
     */
    @Override
    public boolean isFinished() {
        return cachedBodyInputStream.available() == 0;
    }

    /**
     * 构造函数已经初始化好ByteArrayInputStream，就绪返回true
     */
    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }

    @Override
    public int read() {
        return cachedBodyInputStream.read();
    }

}
