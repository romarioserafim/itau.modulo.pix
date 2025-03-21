package com.modulo.pix.repositories;

import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.models.AccountModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountModel, UUID> {
    AccountModel findByAccountNumber(String accountNumber);

    @EntityGraph(attributePaths = "pixKeys")
    Optional<AccountModel> findById(UUID id);

    @Query("SELECT a FROM AccountModel a " +
            "LEFT JOIN FETCH a.pixKeys p " +
            "WHERE (:tipoConta IS NULL OR a.accountType = :tipoConta) " +
            "AND (:numeroAgencia IS NULL OR a.agencyNumber = :numeroAgencia) " +
            "AND (:numeroConta IS NULL OR a.accountNumber = :numeroConta) " +
            "AND (:nomeCorrentista IS NULL OR a.accountHolderFirstName LIKE %:nomeCorrentista%) " +
            "AND (:sobrenomeCorrentista IS NULL OR a.accountHolderLastName LIKE %:sobrenomeCorrentista%) " +
            "AND (:tipoCliente IS NULL OR a.clientType = :tipoCliente) " +
            "AND (:tipoChave IS NULL OR p.keyType = :tipoChave) " +
            "AND (:valorChave IS NULL OR p.keyValue = :valorChave) " +
            "AND (:tipoFiltroData IS NULL OR " +
            "(p.createdAt BETWEEN :dataInicio AND :dataFim AND :tipoFiltroData = 'DATA_CADASTRO') OR " +
            "(p.disabledAt BETWEEN :dataInicio AND :dataFim AND :tipoFiltroData = 'DATA_INATIVACAO'))")
    List<AccountModel> findAccountsWithPixKeys(
            AccountType tipoConta,
            String numeroAgencia,
            String numeroConta,
            String nomeCorrentista,
            String sobrenomeCorrentista,
            ClientType tipoCliente,
            KeyType tipoChave,
            String valorChave,
            String tipoFiltroData,
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    );


    @Query("SELECT a FROM AccountModel a " +
            "LEFT JOIN FETCH a.pixKeys p " +
            "WHERE p.id = :idDaChavePix")
    List<AccountModel> findAccountByPixKeyId(UUID idDaChavePix);
}
