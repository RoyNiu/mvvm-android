package com.example.newstart.fortest

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindElectricEngine

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindGasEngine
