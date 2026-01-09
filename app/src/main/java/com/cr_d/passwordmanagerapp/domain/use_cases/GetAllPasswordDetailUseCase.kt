package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository

//TODO: Implementar cambios
class GetAllPasswordDetailUseCase(
    private val passwordRepository: IPasswordRepository,
    private val obtainDetailInfo: ObtainPasswordDetailInfoUseCase
) {
    suspend operator fun invoke(): List<PasswordDetail> {
        return passwordRepository.findAll().map { it.toDomain() }
            .map { password ->
                val extra = obtainDetailInfo.invoke(password)
                password.toDetail(extra)
            }
    }
}