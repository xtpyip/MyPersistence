package com.pyip.io;

import java.io.InputStream;

public class Resource {

    // 根据配置文件的路径，将配置文件加载成字节输入流，存储到内存中
    public static InputStream getResourceAsStream(String path){
        InputStream resourceAsStream = Resource.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
