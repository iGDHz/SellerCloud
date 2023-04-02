package com.hz.sellcloud.domain.response;

import lombok.Data;

@Data
public class CommonResponse<T> {
    int code;

    String message;
    T data;

    public CommonResponse(){
    }
    public CommonResponse(T data){
        this.data = data;
    }

    public CommonResponse<T> sucess(){
        this.code = 20000;
        this.message = "sucess";
        return this;
    }

    public CommonResponse<T> error(){
        return this.error(404,"error");
    }

    public CommonResponse<T> error(int code){
        return this.error(code,"error");
    }

    public CommonResponse<T> error(String message){
        return this.error(404,message);
    }

    public CommonResponse<T> error(int code,String message){
        this.code = code;
        this.message = message;
        return this;
    }

}
