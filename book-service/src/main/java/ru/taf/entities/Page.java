package ru.taf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Pages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Page {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    private Chapter chapter;

    @Column(name = "page_number")
    private int pageNumber;

    @Column(name = "content")
    private String content;
}
