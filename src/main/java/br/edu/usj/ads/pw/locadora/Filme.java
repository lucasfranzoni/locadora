package br.edu.usj.ads.pw.locadora;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Filme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Integer ano;

    @NotNull
    private String tipoMidia;

    @NotNull
    @Min(1)
    private Integer quantidadeTotal;

    @NotNull
    private Integer quantidadeDisponivel;  
}
