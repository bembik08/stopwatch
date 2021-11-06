package com.gb.stopwatch.model

interface Repository {
    fun getMilliseconds(): Long
}