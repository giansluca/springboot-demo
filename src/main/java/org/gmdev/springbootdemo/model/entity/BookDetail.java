package org.gmdev.springbootdemo.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter @Setter
@Entity
@Table(name = "book_detail")
public class BookDetail {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "book_detail_timestamp")
    private ZonedDateTime bookDetailTimestamp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Book book;
}
