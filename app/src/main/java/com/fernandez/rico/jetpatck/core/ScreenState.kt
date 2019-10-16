package com.fernandez.rico.jetpatck.core

sealed class ScreenState<out T>
{
    object Loading : ScreenState<Nothing>()
    class Render<T>(val screenState: T) : ScreenState<T>()
}