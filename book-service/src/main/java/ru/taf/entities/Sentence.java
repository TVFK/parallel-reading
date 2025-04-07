package ru.taf.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Sentence")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    private Page page;

    @Column(name = "sentence_index")
    private Integer sentenceIndex;

    @Column(name = "original_text")
    private String originalText;

    @Column(name = "translated_text")
    private String translatedText;
}
