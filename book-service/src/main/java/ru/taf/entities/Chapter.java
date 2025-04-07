package ru.taf.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Chapter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "chapter_order")
    private int chapterOrder;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY)
    private List<Page> pages;
}
