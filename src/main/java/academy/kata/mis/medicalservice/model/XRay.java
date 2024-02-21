package academy.kata.mis.medicalservice.model;

import academy.kata.mis.medicalservice.enums.DocumentType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Рентген снимок
 */
@Entity
@Table(name = "x_ray")
public class XRay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * время и дата снимка
     */
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    /**
     * тип документа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DocumentType type;

    /**
     * идентификатор документа в сервисе-хранилище документов
     */
    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    /**
     * вес документа
     */
    @Column(name = "size", nullable = false)
    private long size;

    /**
     * hash документа
     */
    @Column(name = "hash", nullable = false)
    private String hash;

    /**
     * связь с обращением по заболеванию
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id", nullable = false)
    private Appeal appeal;
}
