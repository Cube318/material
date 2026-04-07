package com.cube.material.common;

import lombok.Data;

/**
 * @author cube
 */
@Data
public class RetInfo<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> RetInfo<T> ok() {
        RetInfo<T> r = new RetInfo<>();
        r.setCode(200);
        r.setMsg("success");
        return r;
    }

    public static <T> RetInfo<T> ok(T data) {
        RetInfo<T> r = new RetInfo<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    public static <T> RetInfo<T> error(String msg) {
        RetInfo<T> r = new RetInfo<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
}