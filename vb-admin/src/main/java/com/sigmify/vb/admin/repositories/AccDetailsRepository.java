package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sigmify.vb.admin.entity.AccountDetails;

public interface AccDetailsRepository extends JpaRepository<AccountDetails, Long> {

}
