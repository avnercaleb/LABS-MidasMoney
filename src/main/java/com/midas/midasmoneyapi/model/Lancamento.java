package com.midas.midasmoneyapi.model;

import com.midas.midasmoneyapi.model.enums.TipoLancamento;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "lancamentos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Lancamento {

    @Id @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String descricao;

    @NotNull @Column(name = "data_vencimento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPagamento;

    @NotNull
    private BigDecimal valor;

    private String observacao;

    @NotNull @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

    @NotNull
    @ManyToOne @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @NotNull
    @ManyToOne @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
}
