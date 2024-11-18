package io.github.balexei.vitrasaparada.data.location

sealed class LocationStatus() {
    class Success : LocationStatus()
    class PermissionError : LocationStatus()
}