package ru.taf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DictionaryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private LocalDateTime createdAt;

    @Column(name = "next_review_date")
    private LocalDateTime nextReviewDate;

    @Column(name = "difficulty")
    private int difficulty;
}
