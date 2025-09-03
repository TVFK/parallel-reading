package ru.taf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "dictionary_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DictionaryCard {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String word;

    private String translation;

    private String context;

    private LocalDate createdAt;

    private LocalDate nextReviewDate;

    private int difficulty;

    private int repetitionCount;

    private double easeFactor;

    private int intervalDays;

    @ElementCollection
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
}
