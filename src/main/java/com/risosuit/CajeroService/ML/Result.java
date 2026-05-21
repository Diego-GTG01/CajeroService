package com.risosuit.CajeroService.ML;
import java.util.ArrayList;

public class Result <T> {
    public boolean correct;
    public T object;
    public ArrayList<T> objects;
    public Exception ex;
    public String message;
}
