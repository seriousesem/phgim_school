package com.serioussem.phgim.school.core

sealed class CustomErrors: Exception() {
    class AppError(override val message: String): CustomErrors()
}
