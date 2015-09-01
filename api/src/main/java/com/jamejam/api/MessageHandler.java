package com.jamejam.api;

import com.jamejam.api.types.Message;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHandler {
    Message.Type[] contentTypes() default Message.Type.TEXT;
}