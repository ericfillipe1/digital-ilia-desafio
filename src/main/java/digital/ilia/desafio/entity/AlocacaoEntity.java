/*
 *   Copyright (c) 2021 
 *   All rights reserved.
 */
package digital.ilia.desafio.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AlocacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private Date dia ;

    @Getter
    @Setter
    private Duration tempo ;

    @Getter
    @Setter
    private String nomeProjeto ;

}
