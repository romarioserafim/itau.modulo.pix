package com.modulo.pix.repositories;

import com.modulo.pix.dto.response.SearchResponseDto;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.models.PixKeyModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PixKeyRepository extends JpaRepository<PixKeyModel, UUID> {

    PixKeyModel findByKeyValueAndStatus(String keyValue, int status);

    @EntityGraph(attributePaths = "account")
    Optional<PixKeyModel> findById(UUID id);

    @Query("""
    SELECT a FROM AccountModel a
    JOIN a.pixKeys p
    WHERE (:agencyNumber IS NULL OR a.agencyNumber = :agencyNumber)
    AND (:accountNumber IS NULL OR a.accountNumber = :accountNumber)
    AND (:accountHolderFirstName IS NULL OR LOWER(a.accountHolderFirstName) LIKE LOWER(CONCAT('%', :accountHolderFirstName, '%')))
    AND (:accountHolderLastName IS NULL OR LOWER(a.accountHolderLastName) LIKE LOWER(CONCAT('%', :accountHolderLastName, '%')))
    AND (:accountType IS NULL OR a.accountType = :accountType)
    AND (:clientType IS NULL OR a.clientType = :clientType)
    AND (:keyType IS NULL OR p.keyType = :keyType)
    AND (:keyValue IS NULL OR p.keyValue = :keyValue)
    AND (
        :dateFilterString IS NULL OR 
        (:dateFilterString = 'DATA_CADASTRO' AND p.createdAt BETWEEN :dtStart AND :dtEnd) OR
        (:dateFilterString = 'DATA_INATIVACAO' AND p.disabledAt BETWEEN :dtStart AND :dtEnd)
    )""")
    List<AccountModel> findByFilters(
            @Param("agencyNumber") String agencyNumber,
            @Param("accountNumber") String accountNumber,
            @Param("accountHolderFirstName") String accountHolderFirstName,
            @Param("accountHolderLastName") String accountHolderLastName,
            @Param("accountType") AccountType accountType,
            @Param("clientType") ClientType clientType,
            @Param("keyType") KeyType keyType,
            @Param("keyValue") String keyValue,
            @Param("dateFilterString") String dateFilterString,
            @Param("dtStart") LocalDateTime dtStart,
            @Param("dtEnd") LocalDateTime dtEnd
    );

    SearchResponseDto findAll(Specification<AccountModel> accountModelSpecification);
}
