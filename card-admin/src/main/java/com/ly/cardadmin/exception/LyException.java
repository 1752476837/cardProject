package com.ly.cardadmin.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LyException extends RuntimeException {

    private ExceptionEnum exceptionEnum;


}
