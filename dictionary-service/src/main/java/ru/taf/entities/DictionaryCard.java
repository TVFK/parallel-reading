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
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "word")
    private String word;

    @Column(name = "translation")
    private String translation;

    @Column(name = "context")
    private String context;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "next_review_date")
    private LocalDate nextReviewDate;

    @Column(name = "difficulty")
    private int difficulty;

    @Column(name = "repetition_count", columnDefinition = "INT DEFAULT 0")
    private int repetitionCount;

    @Column(name = "ease_factor", columnDefinition = "FLOAT DEFAULT 2.5")
    private double easeFactor;

    @Column(name = "interval_days", columnDefinition = "INT DEFAULT 1")
    private int intervalDays;

    @ElementCollection
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
}
