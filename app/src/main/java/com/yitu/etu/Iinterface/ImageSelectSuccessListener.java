package com.yitu.etu.Iinterface;

import java.util.List;

/**
 * @className:ImageSelectSuccessListener
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 12:11
 */
public interface ImageSelectSuccessListener {
    void selectSuccess(String path);

    void selectSuccess(List<String> pathList);
}
