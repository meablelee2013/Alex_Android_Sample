package com.oriente.aptsample;

public class ResponseData<T> {
    T data;
    int code;
    String msg;

    public ResponseData(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
