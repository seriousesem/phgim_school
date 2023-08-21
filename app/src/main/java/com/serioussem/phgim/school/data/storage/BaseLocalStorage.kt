package com.serioussem.phgim.school.data.storage

interface BaseLocalStorage {

    fun saveData(key: String, data: Any)
    fun <T>  loadData(key: String, defaultValue: Any): T
    fun clearStorage()
}