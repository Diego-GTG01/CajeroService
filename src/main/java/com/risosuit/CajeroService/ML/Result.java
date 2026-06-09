package com.risosuit.CajeroService.ML;

import java.util.ArrayList;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "Result",
    description = "Objeto estándar utilizado por la API para devolver el resultado de una operación."
)
public class Result<T> {

    @Schema(
        description = "Indica si la operación se ejecutó correctamente",
        example = "true"
    )
    public boolean correct;

    @Schema(
        description = "Objeto resultado de la operación cuando se devuelve un único registro"
    )
    public T object;

    @Schema(
        description = "Colección de objetos resultado cuando la operación devuelve múltiples registros"
    )
    public ArrayList<T> objects;

    @Schema(
        description = "Información de la excepción generada durante la ejecución",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    public Exception ex;

    @Schema(
        description = "Mensaje descriptivo del resultado de la operación",
        example = "Operación realizada correctamente"
    )
    public String message;
}