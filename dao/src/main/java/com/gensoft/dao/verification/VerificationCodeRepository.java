package com.gensoft.dao.verification;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alan on 16-7-6.
 */
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, Long> {

    @Query("select v from VerificationCode v where mobile=?1 and codeType =?2")
    public VerificationCode getCode(Long mobile, int type);

}
