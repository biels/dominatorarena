package com.biel.dominatorarena.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * The result of the battle itself (to complete)
 */
@Entity
public class BattleResult {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    Battle battle;
}
