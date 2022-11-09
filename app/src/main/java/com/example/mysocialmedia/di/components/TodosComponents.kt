package com.example.mysocialmedia.di.components

import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.di.modules.TodosModule
import com.example.mysocialmedia.fragments.todos.TodosFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        CommonServicesProvider::class,
        TodosModule::class
    ]
)
interface TodosComponents {

    fun inject(fragment: TodosFragment)
}