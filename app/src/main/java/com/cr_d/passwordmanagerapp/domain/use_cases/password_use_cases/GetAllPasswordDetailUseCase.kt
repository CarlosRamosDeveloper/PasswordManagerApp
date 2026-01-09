package com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository

class GetAllPasswordDetailUseCase(
    private val passwordRepository: IPasswordRepository,
    private val obtainDetailInfo: ObtainPasswordDetailInfoUseCase
) {
    suspend operator fun invoke(): List<PasswordDetail> {
        return passwordRepository.findAll().map { password ->
                val extra = obtainDetailInfo.invoke(password)
                password.toDetail(extra)
            }
    }
}