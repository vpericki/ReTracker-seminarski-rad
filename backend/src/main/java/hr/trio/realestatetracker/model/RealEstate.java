package hr.trio.realestatetracker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "real_estate")
public class RealEstate {

    @Id
    @SequenceGenerator(name = "real_estate_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "real_estate_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rent_price")
    private BigDecimal rentPrice;

    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "quadrature", nullable = false)
    private Integer quadrature;

    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @Column(name = "baths", nullable = false)
    private Integer baths;

    @Column(name = "rent", nullable = false)
    private boolean forRent;

    @Column(name = "sale", nullable = false)
    private boolean forSale;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "real_estate_id", referencedColumnName = "id")
    private Set<Image> images;

    @ManyToOne(optional = false)
    private RealEstateType realEstateType;

    @ManyToOne(optional = false)
    private User createdBy;

    @ManyToOne
    private User updatedBy;

}
