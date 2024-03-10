package co.com.bancolombia.api.handler.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductCommand {
    private String nombre;
    private Integer precio ;
}

