package com.gb.stopwatch.model

class RepositoryImpl : Repository {
    override fun getMilliseconds(): Long = System.currentTimeMillis()
}