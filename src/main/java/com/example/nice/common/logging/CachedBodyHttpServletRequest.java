package com.example.nice.common.logging;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 支持InputStream多次读的HttpServletRequest
 *
 * @author heixiaochun
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    /**
     * 缓存在内存中的请求Body
     */
    private final byte[] cachedBody;

    /**
     * 初始化时就将InputStream信息读取到cachedBody数组
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    /**
     * 重写getInputStream方法，返回一个支持多次读取的ServletInputStream对象
     */
    @Override
    public ServletInputStream getInputStream() {
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    /**
     * 重写getInputStream方法，返回一个支持多次读取的BufferedReader对象
     */
    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

}
