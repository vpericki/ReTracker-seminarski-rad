package hr.trio.realestatetracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @SequenceGenerator(name = "image_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "image_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "path", nullable = false)
    private String path;

}
