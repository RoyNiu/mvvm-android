package com.example.newstart.fortest

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class EngineModule {

    @Binds
    @BindGasEngine
    abstract fun bindEngine(engine: GasEngine):Engine

    @Binds
    @BindElectricEngine
    abstract fun bindElectric(engine: ElectricEngine):Engine
}