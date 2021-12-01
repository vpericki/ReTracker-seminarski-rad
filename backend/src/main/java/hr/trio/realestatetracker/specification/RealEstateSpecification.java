package hr.trio.realestatetracker.specification;

import hr.trio.realestatetracker.dto.FilterRealEstateDto;
import hr.trio.realestatetracker.model.RealEstate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class RealEstateSpecification implements Specification<RealEstate> {

    private final FilterRealEstateDto filter;

    @Override
    public Predicate toPredicate(final Root<RealEstate> root, final CriteriaQuery<?> criteriaQuery, final CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (filter != null) {
            if (filter.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), filter.getName()));
            }

            if (filter.getDescription() != null) {
                predicates.add(criteriaBuilder.like(root.get("description"), filter.getDescription()));
            }

            if (filter.getMinRentPrice() != null && filter.getMaxRentPrice() != null) {
                predicates.add(criteriaBuilder.between(root.get("rent_price"), filter.getMinRentPrice(), filter.getMaxRentPrice()));
            }

            if (filter.getMinSellPrice() != null && filter.getMaxSellPrice() != null) {
                predicates.add(criteriaBuilder.between(root.get("sell_price"), filter.getMinSellPrice(), filter.getMaxSellPrice()));
            }

            if (filter.getMinRentPrice() != null && filter.getMaxRentPrice() != null) {
                predicates.add(criteriaBuilder.between(root.get("rent_price"), filter.getMinRentPrice(), filter.getMaxRentPrice()));
            }

            if (filter.getCreationDateAfter() != null && filter.getCreationDateBefore() != null) {
                predicates.add(criteriaBuilder.between(root.get("creation_date"), filter.getCreationDateAfter(), filter.getCreationDateBefore()));
            }

            if (filter.getMinRating() != null && filter.getMaxRating() != null) {
                predicates.add(criteriaBuilder.between(root.get("rating"), filter.getMinRating(), filter.getMaxRating()));
            }

            if (filter.getMinQuadrature() != null && filter.getMaxQuadrature() != null) {
                predicates.add(criteriaBuilder.between(root.get("quadrature"), filter.getMinQuadrature(), filter.getMaxQuadrature()));
            }

            if (filter.getMinRooms() != null && filter.getMaxRooms() != null) {
                predicates.add(criteriaBuilder.between(root.get("rooms"), filter.getMinRooms(), filter.getMaxRooms()));
            }

            if (filter.getMinBaths() != null && filter.getMaxBaths() != null) {
                predicates.add(criteriaBuilder.between(root.get("baths"), filter.getMinBaths(), filter.getMaxBaths()));
            }

            if (filter.isForRent()) {
                predicates.add(criteriaBuilder.isTrue(root.get("rent")));
            }

            if (filter.isForSale()) {
                predicates.add(criteriaBuilder.isTrue(root.get("sale")));
            }

            if (filter.getRealEstateTypeList() != null && !filter.getRealEstateTypeList().isEmpty()) {
                predicates.add(root.get("real_estate_type").get("id").in(filter.getRealEstateTypeList()));
            }

            if (filter.getLongitudeA() != null && filter.getLongitudeB() != null && filter.getLatitudeA() != null && filter.getLatitudeB() != null) {
                predicates.add(criteriaBuilder.between(root.get("address").get("longitude"), filter.getLongitudeA(), filter.getLongitudeB()));
                predicates.add(criteriaBuilder.between(root.get("address").get("latitude"), filter.getLatitudeA(), filter.getLatitudeB()));
            }

            if (filter.getUserIdList() != null && !filter.getUserIdList().isEmpty()) {
                predicates.add(root.get("createdBy").get("id").in(filter.getUserIdList()));
            }

            if (filter.getCompanyIdList() != null && !filter.getCompanyIdList().isEmpty()) {
                predicates.add(root.get("created_by").get("company").get("id").in(filter.getCompanyIdList()));
            }
        }

        criteriaQuery.where(predicates.toArray((new Predicate[0])));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("rating")));

        //TODO implement orderBy by any field

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
